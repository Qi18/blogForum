package cn.rich.community.service.impl;

import cn.rich.community.entity.User;
import cn.rich.community.mapper.UserMapper;
import cn.rich.community.service.UserService;
import cn.rich.community.util.CommunityUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rich
 * @since 2024-07-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User findUserById(int id) {
//        User user = getCache(id);
//        if (user == null) {
//            user = initCache(id);
//        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return userMapper.selectOne(queryWrapper);
    }

    public User findUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public void register(User user) {
        // 空值处理
        if (user == null) throw new IllegalArgumentException("参数不能为空!");
        if (StringUtils.isBlank(user.getUsername())) throw new IllegalArgumentException("账号不能为空!");
        if (StringUtils.isBlank(user.getPassword())) throw new IllegalArgumentException("密码不能为空!");
        if (StringUtils.isBlank(user.getEmail())) throw new IllegalArgumentException("邮箱不能为空!");

        // 验证账号
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        if (userMapper.exists(queryWrapper)) throw new IllegalArgumentException("该账号已存在!");

        // 验证邮箱
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", user.getEmail());
        if (userMapper.exists(queryWrapper)) throw new IllegalArgumentException("该邮箱已被注册!");

        // 注册用户
        //TODO:加盐和其他加密算法
        user.setSalt(CommunityUtil.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        userMapper.insert(user);
    }


    public void login(String username, String password, long expiredSeconds) {
        // 空值处理
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("账号不能为空!");
        if (StringUtils.isBlank(password)) throw new IllegalArgumentException("密码不能为空!");
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) throw new IllegalArgumentException("该账号不存在!");
        if (user.getStatus() == 0) throw new IllegalArgumentException("该账号已注销!");

        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (Objects.equals(user.getPassword(), password)) throw new IllegalArgumentException("密码不正确!");

//        // 生成登录凭证
//        LoginTicket loginTicket = new LoginTicket();
//        loginTicket.setUserId(user.getId());
//        loginTicket.setTicket(CommunityUtil.generateUUID());
//        loginTicket.setStatus(0);
//        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
////        loginTicketMapper.insertLoginTicket(loginTicket);
//
//        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
//        redisTemplate.opsForValue().set(redisKey, loginTicket);
//
//        map.put("ticket", loginTicket.getTicket());
    }

    public void logout() {

    }
////
//
//
//
//    // 1.优先从缓存中取值
//    private User getCache(int userId) {
//        String redisKey = RedisKeyUtil.getUserKey(userId);
//        return (User) redisTemplate.opsForValue().get(redisKey);
//    }
//
//    // 2.取不到时初始化缓存数据
//    private User initCache(int userId) {
//        User user = userMapper.selectById(userId);
//        String redisKey = RedisKeyUtil.getUserKey(userId);
//        redisTemplate.opsForValue().set(redisKey, user, 3600, TimeUnit.SECONDS);
//        return user;
//    }
//
//    // 3.数据变更时清除缓存数据
//    private void clearCache(int userId) {
//        String redisKey = RedisKeyUtil.getUserKey(userId);
//        redisTemplate.delete(redisKey);
//    }
//
//    public Collection<? extends GrantedAuthority> getAuthorities(int userId) {
//        User user = this.findUserById(userId);
//
//        List<GrantedAuthority> list = new ArrayList<>();
//        list.add(new GrantedAuthority() {
//
//            @Override
//            public String getAuthority() {
//                switch (user.getType()) {
//                    case 1:
//                        return AUTHORITY_ADMIN;
//                    case 2:
//                        return AUTHORITY_MODERATOR;
//                    default:
//                        return AUTHORITY_USER;
//                }
//            }
//        });
//        return list;
//    }
//
}