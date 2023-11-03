package com.emt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
//赛事记录表（Records）实体类
@Data
public class Record {
    @TableId(value = "record_id", type = IdType.ASSIGN_ID)
    private Long recordId;//记录ID
    private Long eventId;//赛事ID
    private Long participantId;//参赛者ID
    private String recordDetails;//记录详情
    private LocalDateTime recordDate;//记录日期
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
