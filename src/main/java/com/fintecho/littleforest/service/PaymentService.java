package com.fintecho.littleforest.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.fintecho.littleforest.vo.PaymentVO;

@Service
public interface PaymentService {
    List<PaymentVO> getPaymentListByUserId(int userId);
}
