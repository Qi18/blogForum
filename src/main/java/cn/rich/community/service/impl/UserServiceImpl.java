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
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setCreateTime(new Date());
        userMapper.insert(user);
    }


//    public int activation(int userId, String code) {
//        User user = userMapper.selectById(userId);
//        if (user.getStatus() == 1) {
//            return ACTIVATION_REPEAT;
//        } else if (user.getActivationCode().equals(code)) {
////            userMapper.updateStatus(userId, 1);
//            clearCache(userId);
//            return ACTIVATION_SUCCESS;
//        } else {
//            return ACTIVATION_FAILURE;
//        }
//    }

//    public void login(String username, String password, long expiredSeconds) {
//
//        // 空值处理
//        if (StringUtils.isBlank(username)) {
//            map.put("usernameMsg", "账号不能为空!");
//            return map;
//        }
//        if (StringUtils.isBlank(password)) {
//            map.put("passwordMsg", "密码不能为空!");
//            return map;
//        }
//
//        // 验证账号
////        User user = userMapper.selectByName(username);
//        if (user == null) {
//            map.put("usernameMsg", "该账号不存在!");
//            return map;
//        }
//
//        // 验证状态
//        if (user.getStatus() == 0) {
//            map.put("usernameMsg", "该账号未激活!");
//            return map;
//        }
//
//        // 验证密码
//        password = CommunityUtil.md5(password + user.getSalt());
//        if (!user.getPassword().equals(password)) {
//            map.put("passwordMsg", "密码不正确!");
//            return map;
//        }
//
////        // 生成登录凭证
////        LoginTicket loginTicket = new LoginTicket();
////        loginTicket.setUserId(user.getId());
////        loginTicket.setTicket(CommunityUtil.generateUUID());
////        loginTicket.setStatus(0);
////        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
//////        loginTicketMapper.insertLoginTicket(loginTicket);
////
////        String redisKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
////        redisTemplate.opsForValue().set(redisKey, loginTicket);
////
////        map.put("ticket", loginTicket.getTicket());
//        return map;
//    }
//
////    public void logout(String ticket) {
//////        loginTicketMapper.updateStatus(ticket, 1);
////        String redisKey = RedisKeyUtil.getTicketKey(ticket);
////        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
////        loginTicket.setStatus(1);
////        redisTemplate.opsForValue().set(redisKey, loginTicket);
////    }
////
////    public LoginTicket findLoginTicket(String ticket) {
//////        return loginTicketMapper.selectByTicket(ticket);
////        String redisKey = RedisKeyUtil.getTicketKey(ticket);
////        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
////    }
//
//    public int updateHeader(int userId, String headerUrl) {
////        return userMapper.updateHeader(userId, headerUrl);
//        int rows = userMapper.updateHeader(userId, headerUrl);
//        clearCache(userId);
//        return rows;
//    }
//
//    public User findUserByName(String username) {
//        return userMapper.selectByName(username);
//    }
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