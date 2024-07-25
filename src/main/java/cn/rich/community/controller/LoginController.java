package cn.rich.community.controller;

import cn.rich.community.domain.ResponseResult;
import cn.rich.community.domain.auth.LoginUser;
import cn.rich.community.service.UserService;
import com.google.code.kaptcha.Producer;
import cn.rich.community.entity.User;
import cn.rich.community.util.CommunityConstant;
import cn.rich.community.util.CommunityUtil;
import cn.rich.community.util.RedisKeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private UserService userService;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private Producer kaptchaProducer;

    @Autowired
    private RedisTemplate redisTemplate;


    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseResult<?> register(User user) {
        userService.register(user);
        return ResponseResult.success("注册成功");
    }

    @RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response) {
        // 生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        // 验证码的归属
        String kaptchaOwner = CommunityUtil.generateUUID();
        Cookie cookie = new Cookie("kaptchaOwner", kaptchaOwner);
        cookie.setMaxAge(60);
        cookie.setPath(contextPath);
        response.addCookie(cookie);
        // 将验证码存入Redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, text, 60, TimeUnit.MINUTES);

        // 将突图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            log.error("响应验证码失败:{}", e.getMessage());
        }
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseResult<?> login(String username, String password, String code, @CookieValue("kaptchaOwner") String kaptchaOwner) {
        String kaptcha = null;
        if (StringUtils.isNotBlank(kaptchaOwner)) {
            String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
            kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        }
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            throw new IllegalArgumentException("验证码不正确");
        }
        // 登陆验证，生成JwtToken
        String jwtToken = userService.login(username, password);
        return ResponseResult.success(jwtToken);
    }

    @RequestMapping(path = "/securityLogout", method = RequestMethod.GET)
    public ResponseResult<?> logout() {
        userService.logout();
        return ResponseResult.success("退出成功");
    }

}
