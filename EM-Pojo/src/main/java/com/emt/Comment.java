package com.emt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
//赛事评论表（Comments）实体类
@Data
public class Comment {
    @TableId(value = "comment_id", type = IdType.ASSIGN_ID)
    private Long commentId;//评论ID
    private Long eventId;//赛事ID
    private Long userId;//用户ID
    private String commentText;//评论内容
    private LocalDateTime commentDate;//评论日期
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
