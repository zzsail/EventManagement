package com.emt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
//赛事评分表（Ratings）实体类
@Data
public class Rating {
    @TableId(value = "rating_id", type = IdType.ASSIGN_ID)
    private Long ratingId;//评分ID
    private Long eventId;//赛事ID
    private Long userId;//用户ID
    private BigDecimal ratingValue;//评分数值
    private String ratingComments;//评分评论
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
