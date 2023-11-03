package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String userName = user.getUsername();
        // 将密码转为md5格式
        String password = DigestUtils
                .md5DigestAsHex(
                        user.getPassword().getBytes()
                );
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(StringUtils.hasText(userName), User::getUsername, userName);
        user = userService.getOne(lqw);
        if(user != null){
            if (!user.getBan()) {//查看是否封禁
                if(password.equals(user.getPassword())){//比较密码
                    //设置token
                    String token = JwtUtil.GenerateJwt(user.getUsername() +
                            "," + user.getPower() +
                            "," + user.getUserId());
                    Map<String, Object> map = new HashMap<>();
                    map.put("token", token);
                    return Result.success(map);
                }
            }
            return Result.error("该用户已被封禁");
        }else {
            return Result.error("用户名或密码错误");
        }

    }

}
