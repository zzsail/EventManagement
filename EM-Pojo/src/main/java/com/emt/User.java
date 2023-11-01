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
    private Integer power;//用户权限
    private Integer status;//用户状态
    private LocalDateTime lastLoginTime;//最近登录时间
    private LocalDateTime registerTime;//注册时间
    private Boolean ban;//是否封禁
    private Boolean exist;//是否存在

}
