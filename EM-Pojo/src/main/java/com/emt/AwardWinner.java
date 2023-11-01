package com.emt;


import lombok.Data;

import java.time.LocalDateTime;

//赛事获奖者信息表（AwardWinners）实体类
@Data
public class AwardWinner {
    private Long winnerId;
    private Long awardId;
    private Participant participant;
    private LocalDateTime awardDate;
}
