package com.emt;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

//赛事分类表（EventCategories）实体类
@Data
public class EventCategory {
    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    private Long categoryId;//分类ID
    private String categoryName;//分类名称
    private String categoryDescription;//分类描述
    private Boolean exist;//是否存在（1表示存在，0表示删除）
}
