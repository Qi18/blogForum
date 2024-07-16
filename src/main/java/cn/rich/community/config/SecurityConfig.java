package cn.rich.community.config;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cn.rich.community.util.CommunityConstant.*;
import cn.rich.community.controller.filter.JwtAuthenticationTokenFilter;

//@EnableWebSecurity(debug = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 不启用过滤器链直接使用或拒绝的请求
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");//忽略对静态资源的拦截
    }

    // 启用过滤器链的请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 授权
                .antMatchers(
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "/unfollow"
                )
                .hasAnyAuthority(
                        AUTHORITY_USER,
                        AUTHORITY_ADMIN
                )
                .antMatchers(
                        "/discuss/top",
                        "/discuss/wonderful",
                        "/discuss/delete",
                        "/data/**"
                )
                .hasAnyAuthority(
                        AUTHORITY_ADMIN
                )
                // Spring Security进行认证操作
                .antMatchers(
                        "/auth/login",
                        "/auth/register",
                        "/auth/kaptcha"
                )
                .anonymous()
                .anyRequest().authenticated()
                // Spring Security不进行认证操作，可以在Interceptor里实现认证
//                .anyRequest().permitAll()
                .and()
                // 禁用 CSRF 保护（在使用 token 时通常不需要）
                .csrf().disable()
                // 配置 session 策略为无状态，即不创建 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 拦截器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 权限不够时的处理
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 没有登录
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        response.setHeader("Access-Control-Allow-Origin", "*");
                        response.setHeader("Cache-Control","no-cache");
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
//                        response.getWriter().println(JSONUtil.parse(CommonResult.unauthorized(authException.getMessage())));
                        response.getWriter().println(authException.getMessage());
                        // 刷新响应流，确保数据被发送
                        response.getWriter().flush();
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    // 权限不足
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        response.setHeader("Access-Control-Allow-Origin", "*");
                        response.setHeader("Cache-Control","no-cache");
                        response.setCharacterEncoding("UTF-8");
                        response.setContentType("application/json");
//                        response.getWriter().println(JSONUtil.parse(CommonResult.forbidden(accessDeniedException.getMessage())));
                        response.getWriter().println(accessDeniedException.getMessage());
                        response.getWriter().flush();
                    }
                });

        // Security底层默认会拦截/logout请求,进行退出处理.
        // 覆盖它默认的逻辑,才能执行我们自己的退出代码.
        http.logout().logoutUrl("/securityLogout");
    }

}
