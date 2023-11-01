package com.emt;

import lombok.Data;

import java.time.LocalDateTime;
//赛事记录表（Records）实体类
@Data
public class Record {
    private Long recordId;
    private Long eventId;
    private Long participantId;
    private String recordDetails;
    private LocalDateTime recordDate;
    private Boolean exist;
}
