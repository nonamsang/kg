package com.moodshop.kokone.vo;

public class Order_DetailVO {
	private String order_id;
	private String detail_id;
	private String product_id; //���� ��ǰ vo�� ������ �ʾư����� ��ɸ� �����ϱ����ؼ� string���θ� �����صΰڽ��ϴ�. --������ ProductVO�� �ٲ���
	private String option_id; //���� ��ǰ������ vo�� ������ �ʾư����� ��ɸ� �����ϱ����ؼ� string���θ� �����صΰڽ��ϴ�. --������ Product_Option���� �ٲܿ���
	private int detail_count;
	private int detail_price;
	private OrderVO order;
	private String deliverStatus; // ��� ����
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public String getDetail_id() {
		return detail_id;
	}
	public void setDetail_id(String detail_id) {
		this.detail_id = detail_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getOption_id() {
		return option_id;
	}
	public void setOption_id(String option_id) {
		this.option_id = option_id;
	}
	public int getDetail_count() {
		return detail_count;
	}
	public void setDetail_count(int detail_count) {
		this.detail_count = detail_count;
	}

	public int getDetail_price() {
		return detail_price;
	}

	public void setDetail_price(int detail_price) {
		this.detail_price = detail_price;
	}
	public OrderVO getOrder() {
		return order;
	}
	public void setOrder(OrderVO order) {
		this.order = order;
	}

	// ��� ����
	public String getDeliverStatus() {
		return deliverStatus;
	}

	public void setDeliverStatus(String deliverStatus) {
		this.deliverStatus = deliverStatus;
	}
	
}
