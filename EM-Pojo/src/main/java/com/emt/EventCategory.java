package com.emt;

import lombok.Data;

//赛事分类表（EventCategories）实体类
@Data
public class EventCategory {
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
}
