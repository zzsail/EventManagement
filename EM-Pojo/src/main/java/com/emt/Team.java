package com.emt;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class Team {
    private Long teamId;//队伍ID
    private String teamName;//队伍名称
    private String teamDescription;//队伍描述
    private Long captainId;//队长用户ID
    private LocalDateTime creationDate;//创建日期
    private Boolean exist;//是否存在
}
