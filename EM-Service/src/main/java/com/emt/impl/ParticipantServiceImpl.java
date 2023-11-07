package com.emt.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emt.Participant;
import com.emt.ParticipantMapper;
import com.emt.ParticipantService;
import org.springframework.stereotype.Service;

@Service
public class ParticipantServiceImpl extends ServiceImpl<ParticipantMapper, Participant> implements ParticipantService {
}
