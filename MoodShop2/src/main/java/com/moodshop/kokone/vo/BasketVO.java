package com.moodshop.kokone.vo;

public class BasketVO {
private String basket_id;
private String basket_date;
private int total_price;
private String user_id;
private String option_color; // ✔️ 옵션명 (예: 블랙, 레드)
private String option_size; // ✔️ 옵션사이즈 (예: M, L)

public String getBasket_id() {
	return basket_id;
}

public void setBasket_id(String basket_id) {
	this.basket_id = basket_id;
}

public String getBasket_date() {
	return basket_date;
}

public void setBasket_date(String basket_date) {
	this.basket_date = basket_date;
}

public int getTotal_price() {
	return total_price;
}

public void setTotal_price(int total_price) {
	this.total_price = total_price;
}

public String getUser_id() {
	return user_id;
}

public void setUser_id(String user_id) {
	this.user_id = user_id;
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


}
