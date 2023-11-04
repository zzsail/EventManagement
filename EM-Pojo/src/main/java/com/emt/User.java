package com.emt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
//用户表
@Data
public class User {
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;//用户id
    private String username;//用户名
    private String email;//用户邮箱
    private String password;//密码密码
    //用户权限 1为最高权限 2为管理员 3为用户
    private Integer power;
    private String gender;//用户性别
    private Integer age;//用户年龄
    //用户状态 0表示离线 1表示在线
    private Integer status;
    private LocalDateTime lastUpdateTime;//最近更新时间
    private LocalDateTime registerTime;//注册时间
    //是否封禁 0表示封禁 1表示正常
    private Boolean ban;
    private Boolean exist;//是否存在
}
