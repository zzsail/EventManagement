package com.emt.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emt.User;
import com.emt.UserMapper;
import com.emt.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
