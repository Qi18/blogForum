package cn.rich.community.controller.filter;

import cn.rich.community.domain.auth.LoginUser;
import cn.rich.community.entity.User;
import cn.rich.community.service.UserService;
import cn.rich.community.util.JwtTokenUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 登录授权过滤器
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    // 用户详细信息服务（用于从数据库中加载用户信息，需要自定义实现）
    @Autowired
    private UserService userService;

    // JWT 工具类
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // JWT 令牌请求头（即：Authorization）
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    // JWT 令牌前缀（即：Bearer）
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 从请求中获取 JWT 令牌，并根据令牌获取用户信息，最后将用户信息封装到 Authentication 中，方便后续校验（只会执行一次）
     * @param request 请求
     * @param response 响应
     * @param filterChain 过滤器链
     * @throws ServletException Servlet 异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求中获取 JWT 令牌的请求头（即：Authorization）
        String authHeader = request.getHeader(this.tokenHeader);

        // 如果请求头不为空，并且以 JWT 令牌前缀（即：Bearer）开头
        if (authHeader != null && authHeader.startsWith(this.tokenHead)){
            // 获取 JWT 令牌的内容（即：去掉 JWT 令牌前缀后的内容）
            String authToken = authHeader.substring(this.tokenHead.length());
            // 从 JWT 令牌中获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            // 记录日志
            log.info("checking username:{}", username);

            // 如果用户名不为空，并且 SecurityContextHolder 中的 Authentication 为空（表示该用户未登录）
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                // 从数据库中加载用户信息
                User user = userService.findUserByName(username);
                LoginUser userDetails = new LoginUser(user);

                // 如果 JWT 令牌有效
                if (jwtTokenUtil.validateToken(authToken, userDetails)){
                    // 将用户信息封装到 UsernamePasswordAuthenticationToken 对象中（即：Authentication）
                    // 参数：用户信息、密码（因为 JWT 令牌中没有密码，所以这里传 null）、用户权限
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    // 将请求中的详细信息（即：IP、SessionId 等）封装到 UsernamePasswordAuthenticationToken 对象中方便后续校验
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // 记录日志
                    log.info("authenticated user:{}", username);
                    // 将 UsernamePasswordAuthenticationToken 对象封装到 SecurityContextHolder 中方便后续校验
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // 放行，执行下一个过滤器
        filterChain.doFilter(request,response);
    }
}
