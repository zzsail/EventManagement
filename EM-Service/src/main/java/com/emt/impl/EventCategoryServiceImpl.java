package com.emt.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emt.EventCategory;
import com.emt.EventCategoryMapper;
import com.emt.EventCategoryService;
import org.springframework.stereotype.Service;

@Service
public class EventCategoryServiceImpl extends ServiceImpl<EventCategoryMapper, EventCategory> implements EventCategoryService {
}
