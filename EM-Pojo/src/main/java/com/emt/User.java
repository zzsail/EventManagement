package com.emt;

import lombok.Data;

import java.time.LocalDateTime;
//用户表
@Data
public class User {
    private Long userId;//用户id
    private String userName;//用户名
    private String email;//用户邮箱
    private String password;//密码密码
    //用户权限 1为最高权限 2为管理员 3为用户
    private Integer power;
    //用户状态 0表示离线 1表示在线
    private Integer status;
    private LocalDateTime lastLoginTime;//最近登录时间
    private LocalDateTime registerTime;//注册时间
    //是否封禁 0表示封禁 1表示正常
    private Boolean ban;
    private Boolean exist;//是否存在
}
