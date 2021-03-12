package com.jinfang.util;

import com.jinfang.config.GloablExceptionHandler;
import com.jinfang.exception.CustomerDefinedException;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.vo.LoginUserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 * JWT工具类
 */
@Slf4j
public class JwtTokenUtils implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";
    /**
     * 密钥
     */
    private static final String SECRET = "zheJingJinFangSchool";

    private static final String ISSUER = "Jinfang";

    /**
     * 有效期6小时
     */
    private static final long EXPIRE_TIME = 6 * 60 * 60 * 1000;

    /**
     * 角色
     */
    private static final String ROLE_CLAIMS = "role";

    public static String getRole(Claims claims) {
        if (claims == null || claims.get(ROLE_CLAIMS) == null) {
            return null;
        }

        return claims.get(ROLE_CLAIMS).toString();
    }

    /**
     * 生成token（专业认证系统生成规则）
     *
     * @param username 用户名，一般为教师ID或者学生学号
     * @param role     角色，目前 majorLeaer, student
     * @return token
     */
    public static String createToken(String username, String role) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                //设置角色名
                .setClaims(claims)
                //设置发证人
                .setIssuer(ISSUER)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    private static String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }


    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims getClaimsFromToken(String token) {
        Claims claims;

        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            throw new CustomerDefinedException(ResultEnum.TOKEN_ERR);
            //log.warn("Can't find parse claims from token[{}]", token, e);
           /* claims = null;
            throw new CustomerDefinedException(ResultEnum.TOKEN_ERR);*/
        }
        return claims;
    }
    /**
     * 获取登录信息
     */
    public static LoginUserVo getUserInfo(String token){
        Claims claims;
        LoginUserVo loginUserVo = new LoginUserVo();
        try {
            claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            String ids = claims.getSubject();
            long id = Long.parseLong(ids);
            loginUserVo.setRole(claims.get(ROLE_CLAIMS).toString());
            loginUserVo.setUserId(id);

        }
        catch (Exception e) {
            log.warn("Can't find parse claims from token[{}]", token, e);
            claims = null;
        }
        return loginUserVo;
    }
    /**
     * 验证令牌
     */
    private static boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        try {

            Claims claims = getClaimsFromToken(token);
            if (claims==null){
                log.error("========>token已经过期或错误=========");
                return true;
            }
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            log.error("--------token 错误-----------", token, e);
            //e.printStackTrace();
            return true;
        }
    }


}