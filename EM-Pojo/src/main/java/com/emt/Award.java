package com.emt;
//赛事奖项设置表（Awards）实体类：

import lombok.Data;

@Data
public class Award {
    private Long awardId;//奖项ID
    private Long eventId;//赛事ID
    private String awardName;//奖项名称
    private String awardDescription;//奖项描述
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
