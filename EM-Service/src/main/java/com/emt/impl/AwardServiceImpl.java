package com.emt.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emt.Award;
import com.emt.AwardMapper;
import com.emt.AwardService;
import org.springframework.stereotype.Service;

@Service
public class AwardServiceImpl extends ServiceImpl<AwardMapper, Award> implements AwardService {
}
