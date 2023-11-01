package com.emt;

import lombok.Data;

import java.time.LocalDateTime;
//赛事记录表（Records）实体类
@Data
public class Record {
    private Long recordId;
    private Event event;
    private Participant participant;
    private String recordDetails;
    private LocalDateTime recordDate;
}
