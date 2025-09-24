package com.moodshop.kokone.vo;

import java.sql.Date;

public class RecentViewVO {
	private String recent_id;
	private String user_id;
	private String product_id;
	private Date view_date;

	// 조인한 상품 정보
	private String product_name;
	private String product_price;
	private String product_image;
	
	// getter, setter
	public String getRecent_id() {
		return recent_id;
	}
	public void setRecent_id(String recent_id) {
		this.recent_id = recent_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public Date getView_date() {
		return view_date;
	}
	public void setView_date(Date view_date) {
		this.view_date = view_date;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public String getProduct_price() {
		return product_price;
	}
	public void setProduct_price(String product_price) {
		this.product_price = product_price;
	}
	public String getProduct_image() {
		return product_image;
	}
	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}
}
