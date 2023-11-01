package com.emt;
//赛事奖项设置表（Awards）实体类：

import lombok.Data;

@Data
public class Award {
    private Long awardId;
    private Event event;
    private String awardName;
    private String awardDescription;
}
