package com.emt;

import lombok.Data;

import java.time.LocalDateTime;
//赛事评论表（Comments）实体类
@Data
public class Comment {
    private Long commentId;
    private Long eventId;
    private Long participantId;
    private String commentText;
    private LocalDateTime commentDate;
}
