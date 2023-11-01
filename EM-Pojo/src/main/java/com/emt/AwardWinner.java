package com.emt;


import lombok.Data;

import java.time.LocalDateTime;

//赛事获奖者信息表（AwardWinners）实体类
@Data
public class AwardWinner {
    private Long winnerId;//获奖者ID
    private Long awardId;//奖项ID
    private Participant participant;//参赛者ID
    private LocalDateTime awardDate;//获奖日期
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
