package com.moodshop.kokone.vo;

public class BasketDetailVO {


	private String basket_id;


	private String basket_detail_id;


	private String product_id;


	private String option_id;


	private int basket_detail_count;


	private int basket_detail_price;

	private String option_color;


	public String getBasket_id() {
		return basket_id;
	}

	public void setBasket_id(String basket_id) {
		this.basket_id = basket_id;
	}

	public String getBasket_detail_id() {
		return basket_detail_id;
	}

	public void setBasket_detail_id(String basket_detail_id) {
		this.basket_detail_id = basket_detail_id;
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

	public String getOption_color() {
		return option_color;
	}

	public void setOption_color(String option_color) {
		this.option_color = option_color;
	}

}
