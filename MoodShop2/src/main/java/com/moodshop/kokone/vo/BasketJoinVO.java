package com.moodshop.kokone.vo;

import java.sql.Date;

public class BasketJoinVO {
	private String basket_id;
	private String product_id;
	private String product_name;
	private String product_image;
	private int product_price;
	private int basket_detail_count;
	private int basket_detail_price;
	private String basket_detail_id;
	private String option_id;
	private String option_color;
	private String option_size;
	private Date basket_date;



	public String getBasket_id() {
		return basket_id;
	}

	public void setBasket_id(String basket_id) {
		this.basket_id = basket_id;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_image() {
		return product_image;
	}

	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public int getBasket_detail_count() {
		return basket_detail_count;
	}

	public void setBasket_detail_count(int basket_detail_count) {
		this.basket_detail_count = basket_detail_count;
	}

	public int getBasket_detail_price() {
		return basket_detail_price;
	}

	public void setBasket_detail_price(int basket_detail_price) {
		this.basket_detail_price = basket_detail_price;
	}

	public String getBasket_detail_id() {
		return basket_detail_id;
	}

	public void setBasket_detail_id(String basket_detail_id) {
		this.basket_detail_id = basket_detail_id;
	}

	public String getOption_id() {
		return option_id;
	}

	public void setOption_id(String option_id) {
		this.option_id = option_id;
	}



	public String getOption_color() {
		return option_color;
	}

	public void setOption_color(String option_color) {
		this.option_color = option_color;
	}

	public String getOption_size() {
		return option_size;
	}

	public void setOption_size(String option_size) {
		this.option_size = option_size;
	}

	public Date getBasket_date() {
		return basket_date;
	}

	public void setBasket_date(Date basket_date) {
		this.basket_date = basket_date;
	}

}
