package com.emt.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emt.Rating;
import com.emt.RatingMapper;
import com.emt.RatingService;
import org.springframework.stereotype.Service;

@Service
public class RatingServiceImpl extends ServiceImpl<RatingMapper, Rating> implements RatingService {
}
