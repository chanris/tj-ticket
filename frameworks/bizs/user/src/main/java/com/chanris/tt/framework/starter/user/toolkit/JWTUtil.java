package com.chanris.tt.framework.starter.user.toolkit;

import com.alibaba.fastjson2.JSON;
import com.chanris.tt.framework.starter.user.core.UserInfoDTO;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.chanris.tt.framework.starter.bases.constant.UserConstant.*;

/**
 * @author chenyue7@foxmail.com
 * @date 2024/9/1
 * @description JWT 工具类
 */
@Slf4j
public class JWTUtil {
    private static final long EXPIRATION = 86400L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String ISS = "tt";
    public static final String SECRET = "e3uCgb1VS2bvOC4tfF1r4tpsMsDUhIhWv9kVSMWsamRKMiaRJTNPKe2f8giSYDfJwRRoV";

    /**
     * 生成用户 Token
     * @param userInfo 用户信息
     * @return 用户访问 Token
     */
    public static String generateAccessToken(UserInfoDTO userInfo) {
        Map<String, Object> customUserMap = new HashMap<>();
        customUserMap.put(USER_ID_KEY, userInfo.getUserId());
        customUserMap.put(USER_NAME_KEY, userInfo.getRealName());
        customUserMap.put(REAL_NAME_KEY, userInfo.getRealName());
        String jwtToken = Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISS)
                .setIssuedAt(new Date())
                .setSubject(JSON.toJSONString(customUserMap))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION * 1000))
                .compact();
        return TOKEN_PREFIX + jwtToken;
    }

    public static UserInfoDTO parseJwtToken(String jwtToken) {
        if (StringUtils.hasText(jwtToken)) {
            String actualJwtToken = jwtToken.replace(TOKEN_PREFIX, "");
            try {
                Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJwt(actualJwtToken).getBody();
                Date expiration = claims.getExpiration();
                if (expiration.getTime() > System.currentTimeMillis()) {
                    String subject = claims.getSubject();
                    return JSON.parseObject(subject, UserInfoDTO.class);
                }
            }catch (ExpiredJwtException ignored) {
            }catch (Exception ex) {
                log.error("JWT Token解析失败，请检查", ex);
            }
        }
        return null;
    }
}
