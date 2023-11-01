package com.emt;

import lombok.Data;

import java.time.LocalDateTime;
//赛事信息表（Events）实体类
@Data
public class Event {
    private Long eventId;
    private String eventName;
    private LocalDateTime eventDate;
    private String eventLocation;
    private String eventDescription;
    private EventCategory category;
}
