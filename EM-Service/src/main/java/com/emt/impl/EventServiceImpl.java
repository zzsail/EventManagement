package com.emt.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emt.Event;
import com.emt.EventMapper;
import com.emt.EventService;
import org.springframework.stereotype.Service;

@Service
public class EventServiceImpl extends ServiceImpl<EventMapper, Event> implements EventService {
}
