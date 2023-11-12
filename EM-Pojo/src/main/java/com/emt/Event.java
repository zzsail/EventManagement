package com.emt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
//赛事信息表（Events）实体类
@Data
public class Event {
    @TableId(value = "event_id", type = IdType.ASSIGN_ID)
    private Long eventId;//赛事ID
    private String eventName;//赛事名称
    private LocalDate eventDate;//赛事日期
    private String eventLocation;//赛事地点
    private String eventDescription;//赛事描述
    private String eventImage;//赛事图片
    private Long categoryId;//分类ID
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
