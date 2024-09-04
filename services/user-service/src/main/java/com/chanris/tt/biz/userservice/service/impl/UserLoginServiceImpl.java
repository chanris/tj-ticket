package com.chanris.tt.biz.userservice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chanris.tt.biz.userservice.common.enums.UserChainMarkEnum;
import com.chanris.tt.biz.userservice.dao.entity.*;
import com.chanris.tt.biz.userservice.dao.mapper.*;
import com.chanris.tt.biz.userservice.dto.req.UserDeletionReqDTO;
import com.chanris.tt.biz.userservice.dto.req.UserLoginReqDTO;
import com.chanris.tt.biz.userservice.dto.req.UserRegisterReqDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserLoginRespDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserQueryRespDTO;
import com.chanris.tt.biz.userservice.dto.resp.UserRegisterRespDTO;
import com.chanris.tt.biz.userservice.service.UserLoginService;
import com.chanris.tt.biz.userservice.service.UserService;
import com.chanris.tt.framework.starter.DistributedCache;
import com.chanris.tt.framework.starter.common.toolkit.BeanUtil;
import com.chanris.tt.framework.starter.convention.exception.ClientException;
import com.chanris.tt.framework.starter.convention.exception.ServiceException;
import com.chanris.tt.framework.starter.designpattern.chain.AbstractChainContext;
import com.chanris.tt.framework.starter.user.core.UserContext;
import com.chanris.tt.framework.starter.user.core.UserInfoDTO;
import com.chanris.tt.framework.starter.user.toolkit.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.chanris.tt.biz.userservice.common.constant.RedisKeyConstant.*;
import static com.chanris.tt.biz.userservice.common.enums.UserRegisterErrorCodeEnum.*;
import static com.chanris.tt.biz.userservice.toolkit.UserReuseUtil.hashShardingIdx;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/2
 * @description 用户登录接口实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserReuseMapper userReuseMapper;
    private final UserDeletionMapper userDeletionMapper;
    private final UserPhoneMapper userPhoneMapper;
    private final UserMailMapper userMailMapper;
    private final RedissonClient redissonClient;
    private final DistributedCache distributedCache;
    private final AbstractChainContext<UserRegisterReqDTO> abstractChainContext;
    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        String usernameOrMailOrPhone = requestParam.getUsernameOrMailOrPhone();
        boolean mailFlag = false;
        for (char c : usernameOrMailOrPhone.toCharArray()) {
            if (c == '@') {
                mailFlag = true;
                break;
            }
        }
        String username;
        if (mailFlag) {
            LambdaQueryWrapper<UserMailDO> queryWrapper = Wrappers.lambdaQuery(UserMailDO.class)
                    .eq(UserMailDO::getMail, usernameOrMailOrPhone);
            username = Optional.ofNullable(userMailMapper.selectOne(queryWrapper))
                    .map(UserMailDO::getUsername)
                    .orElseThrow(() -> new ClientException("用户名/手机号/邮箱不存在"));
        } else {
            LambdaQueryWrapper<UserPhoneDO> queryWrapper = Wrappers.lambdaQuery(UserPhoneDO.class)
                    .eq(UserPhoneDO::getPhone, usernameOrMailOrPhone);
            username = Optional.ofNullable(userPhoneMapper.selectOne(queryWrapper))
                    .map(UserPhoneDO::getUsername)
                    .orElse(null);
        }
        // 如果username为 null，则说明 username可能为用户名
        username = Optional.ofNullable(username).orElse(requestParam.getUsernameOrMailOrPhone());
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username)
                .eq(UserDO::getPassword, requestParam.getPassword())
                .select(UserDO::getId, UserDO::getUsername, UserDO::getRealName); // 指定结果集需要哪些字段
        UserDO userDO = userMapper.selectOne(queryWrapper);
        if (userDO != null) {
            UserInfoDTO userInfo = UserInfoDTO.builder()
                    .userId(String.valueOf(userDO.getId()))
                    .username(userDO.getUsername())
                    .realName(userDO.getRealName())
                    .build();
            String accessToken = JWTUtil.generateAccessToken(userInfo);
            UserLoginRespDTO actual = new UserLoginRespDTO(userInfo.getUserId(), requestParam.getUsernameOrMailOrPhone(), userDO.getRealName(), accessToken);
            distributedCache.put(accessToken, JSON.toJSONString(actual), 30, TimeUnit.MINUTES);
            return actual;
        }
        throw new ServiceException("账号不存在或密码错误");
    }

    @Override
    public UserLoginReqDTO checkLogin(String accessToken) {
        return distributedCache.get(accessToken, UserLoginReqDTO.class);
    }

    @Override
    public void logout(String accessToken) {
        if (StrUtil.isNotBlank(accessToken)) {
            distributedCache.delete(accessToken);
        }
    }

    @Override
    public boolean hasUsername(String username) {
        boolean hasUsername = userRegisterCachePenetrationBloomFilter.contains(username);
        if (hasUsername) {
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            return instance.opsForSet().isMember(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class) // 业务抛异常，回滚数据库
    @Override
    public UserRegisterRespDTO register(UserRegisterReqDTO requestParam) {
        // 使用责任链检查参数
        abstractChainContext.handler(UserChainMarkEnum.USER_REGISTER_FILTER.name(), requestParam);
        // 根据【username】获得锁
        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER + requestParam.getUsername());
        boolean tryLock = lock.tryLock();
        if (!tryLock) {
            // 获取不到锁，说明有人正在占用锁，抛异常
            throw new ServiceException(HAS_USERNAME_NOTNULL);
        }
        try {
            try {
                int inserted = userMapper.insert(BeanUtil.convert(requestParam, UserDO.class));
                if (inserted < 1) {
                    // 小于1，说明插入失败，抛出异常
                    throw new ServiceException(USER_REGISTER_FAIL);
                }
            } catch (DuplicateKeyException dke) {
                // 插入时抛 DuplicateKeyException，说明用户信息已经插入，抛异常
                log.error("用户名 [{}] 重复注册", requestParam.getUsername());
                throw new ServiceException(HAS_USERNAME_NOTNULL);
            }
            UserPhoneDO userPhoneDO = UserPhoneDO.builder()
                    .phone(requestParam.getPhone())
                    .username(requestParam.getUsername())
                    .build();
            try {
                // 关联用户信息-手机号，插入失败，抛异常
                userPhoneMapper.insert(userPhoneDO);
            } catch (DuplicateKeyException dke) {
                log.error("用户 [{}] 注册手机号 [{}] 重复", requestParam.getUsername(), requestParam.getPhone());
                throw new ServiceException(PHONE_REGISTERED);
            }
            // 如果注册时传了邮箱地址
            if (StrUtil.isNotBlank(requestParam.getMail())) {
                UserMailDO userMailDO = UserMailDO.builder()
                        .mail(requestParam.getMail())
                        .username(requestParam.getUsername())
                        .build();
                // 插入用户信息-邮箱号，插入失败，抛异常
                try {
                    userMailMapper.insert(userMailDO);
                } catch (DuplicateKeyException dke) {
                    log.error("用户 [{}] 注册邮箱 [{}] 重复", requestParam.getUsername(), requestParam.getMail());
                    throw new ServiceException(MAIL_REGISTERED);
                }
            }
            String username = requestParam.getUsername();
            // Wrappers生成 update/delete操作对象 todo 9/3
            userReuseMapper.delete(Wrappers.update(new UserReuseDO(username)));
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            instance.opsForSet().remove(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
            // 布隆过滤器设计问题：设置多大、碰撞率以及初始容量不够了怎么办？详情查看：https://nageoffer.com/12306/question
            userRegisterCachePenetrationBloomFilter.add(username);
        } finally {
            lock.unlock();
        }
        return BeanUtil.convert(requestParam, UserRegisterRespDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletion(UserDeletionReqDTO requestParam) {
        String username = UserContext.getUsername();
        if (!Objects.equals(username, requestParam.getUsername())) {
            throw new ClientException("注销账号与登录账号不一致");
        }
        RLock lock = redissonClient.getLock(USER_DELETION + requestParam.getUsername());
        // 加锁为什么放在 try 语句外？
        lock.lock(); // 如果获取不到锁，会直接抛异常，不会走try的finally语句，避免不必要的锁释放；其次，获取不到锁，也不会触发下面的业务流程
        try {
            UserQueryRespDTO userQueryRespDTO = userService.queryUserByUsername(username);
            UserDeletionDO userDeletionReqDTO = UserDeletionDO.builder()
                    .idType(userQueryRespDTO.getIdType())
                    .idCard(userQueryRespDTO.getIdCard())
                    .build();
            userDeletionMapper.insert(userDeletionReqDTO);
            UserDO userDO = new UserDO();
            userDO.setDeletionTime(System.currentTimeMillis());
            userDO.setUsername(username);
            // Mybatis Plus 不执行修改语句变更 del_flag 字段
            userMapper.deletionUser(userDO);
            UserPhoneDO userPhoneDO = UserPhoneDO.builder()
                    .phone(userQueryRespDTO.getPhone())
                    .deletionTime(System.currentTimeMillis())
                    .build();
            userPhoneMapper.deletionUser(userPhoneDO);
            distributedCache.delete(UserContext.getToken());
            userReuseMapper.insert(new UserReuseDO(username));
            StringRedisTemplate instance = (StringRedisTemplate) distributedCache.getInstance();
            instance.opsForSet().add(USER_REGISTER_REUSE_SHARDING + hashShardingIdx(username), username);
        }finally {
            lock.unlock();
        }
    }
}
