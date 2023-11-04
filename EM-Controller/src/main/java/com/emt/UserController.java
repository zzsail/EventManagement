package com.emt;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@CrossOrigin
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
            if (user.getBan()) {//查看是否封禁
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
    @GetMapping("/info")
    public Result getInfo(String token){
        String str = JwtUtil.parseJwt(token).getSubject();
        String[] split = str.split(",");
        Map<String, Object> map = new HashMap<>();
        map.put("username", split[0]);
        map.put("power", split[1]);
        map.put("userId", split[2]);
        return Result.success(map);
    }

    //登出
    @PostMapping("/logout")
    public Result logout(){
        Map<String,Object> map = new HashMap<>();
        map.put("token","");
        return Result.success(new HashMap<>());
    }


    @PostMapping(path = "/register")
    public Result register(@RequestBody User user){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername ,user.getUsername());
        if(userService.getOne(lqw) != null){
            return Result.error("用户名已存在");
        }
        if(user.getPassword().length() < 6){
            return Result.error("密码不能少于6位");
        }
        String password = DigestUtils.md5DigestAsHex(
                        user.getPassword().getBytes()
                );
        user.setPassword(password);
        user.setRegisterTime(LocalDateTime.now());
        user.setPower(1);
        userService.save(user);
        return Result.success();
    }

    @PostMapping(path = "/improveInfo")
    public Result improveInfo(@RequestBody User user){
        String gender = user.getGender();
        Integer age = user.getAge();
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUsername ,user.getUsername());
        User one = userService.getOne(lqw);
        if(one == null){
            return Result.error("用户名不存在");
        }
        one.setAge(age);
        one.setGender(gender);
        userService.saveOrUpdate(one);
        return Result.success();


    }







}
