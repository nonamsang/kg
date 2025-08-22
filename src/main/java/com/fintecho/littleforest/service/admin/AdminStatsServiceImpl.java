package com.fintecho.littleforest.service.admin;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.admin.AdminStatsMapper;
import com.fintecho.littleforest.vo.admin.AdminCountsVO;

@Service
@Transactional(readOnly = true)
public class AdminStatsServiceImpl implements AdminStatsService {

    private final AdminStatsMapper mapper;

    public AdminStatsServiceImpl(AdminStatsMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public AdminCountsVO getCounts() {
        return mapper.selectCounts();
    }
}
