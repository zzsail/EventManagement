package com.emt;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

//赛事获奖者信息表（AwardWinners）实体类
@Data
public class AwardWinner {
    @TableId(value = "winner_id", type = IdType.ASSIGN_ID)
    private Long winnerId;//获奖者ID
    private Long awardId;//奖项ID
    private Long participantId;//参赛者ID
    private LocalDateTime awardDate;//获奖日期
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
