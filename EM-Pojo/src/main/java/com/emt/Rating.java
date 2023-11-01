package com.emt;

import lombok.Data;

import java.math.BigDecimal;
//赛事评分表（Ratings）实体类
@Data
public class Rating {
    private Long ratingId;
    private Long eventId;
    private Long participantId;
    private BigDecimal ratingValue;
    private String ratingComments;
}