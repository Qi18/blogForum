package cn.rich.community;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGetToken() {
        // 创建 Token 过期时间（7 天）
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        // 创建有效载荷中的声明
        Map<String,Object> claims = new HashMap<>();
        claims.put("sub", "javgo"); // 用户名
        claims.put("created", new Date()); // 创建时间
        claims.put("roles", "admin"); // 角色
        claims.put("authorities", "admin"); // 权限
        claims.put("id", 1); // 用户 ID

        // 生成 Token
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") // 设置 Token 类型（默认是 JWT）
                .setHeaderParam("alg", "HS256") // 设置签名算法（默认是 HS256）
                .setClaims(claims) // 设置有效载荷中的声明
                .signWith(SignatureAlgorithm.HS256, "hags213#ad&*sdk".getBytes()) // 设置签名使用的密钥和签名算法
                .setExpiration(calendar.getTime()) // 设置 Token 过期时间
                .compact();

        System.out.println(token);
    }

    @Test
    public void analysisToken(){
        Claims claims = Jwts.parser() // 解析
                .setSigningKey("hags213#ad&*sdk".getBytes()) // 设置密钥(会自动推断算法)
                .parseClaimsJws("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqYXZnbyIsImNyZWF0ZWQiOjE2OTAwMDkyMjk3NzksInJvbGVzIjoiYWRtaW4iLCJpZCI6MSwiZXhwIjoxNjkwNjE0MDI5LCJhdXRob3JpdGllcyI6ImFkbWluIn0.-VYyJNemNB0XS2Qk3Ai77MirRPobyZ0EnQgoKiv9IXE") // 设置要解析的 Token
                .getBody();// 获取有效载荷中的声明

        System.out.println("用户名：" + claims.get("sub"));
        System.out.println("创建时间：" + claims.get("created"));
        System.out.println("角色：" + claims.get("roles"));
        System.out.println("权限：" + claims.get("authorities"));
        System.out.println("用户 ID：" + claims.get("id"));
        System.out.println("过期时间：" + claims.getExpiration());
    }



}

