package com.moodshop.kokone.vo;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class OrderVO {

    private String order_id;
    private Date order_date;
    private BigDecimal total_price;
    private String user_id;
    private List<Order_DetailVO> orderDetails; // �ֹ� �� ����Ʈ

	private String deliverStatus;

    // �ֹ� ID
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    // �ֹ� ����
    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    // �� ���� �ݾ�
    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    // �ֹ��� �����
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    // �ֹ� �� ����Ʈ getter/setter
    public List<Order_DetailVO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<Order_DetailVO> orderDetails) {
        this.orderDetails = orderDetails;
    }

	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}

}
