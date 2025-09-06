package com.fintecho.littleforest.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.vo.PaymentVO;

@Mapper
public interface PaymentMapper {
	List<PaymentVO> getPaymentListByUserId(int userId);

	List<PaymentVO> selectPaymentWithPoints(int userId);
	
	void InsertPaymentByShop(PaymentVO paymentvo);

}
