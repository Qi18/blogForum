package cn.rich.community.service.impl;

import cn.rich.community.entity.User;
import cn.rich.community.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author rich
 * @since 2024-07-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IService<User> {

}
