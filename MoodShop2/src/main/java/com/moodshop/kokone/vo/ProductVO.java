package com.moodshop.kokone.vo;

public class ProductVO {
private String product_id;
private String product_name;
private int product_price;
private String product_mood;
private String product_category;
private String product_image;
private String company_id;
private String wish_id;
private String company_name;
private String product_description;
private Double avg_rating;

public Double getAvg_rating() {
	return avg_rating;
}

public void setAvg_rating(Double avg_rating) {
	this.avg_rating = avg_rating;
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

public int getProduct_price() {
	return product_price;
}

public void setProduct_price(int product_price) {
	this.product_price = product_price;
}
public String getProduct_mood() {
	return product_mood;
}
public void setProduct_mood(String product_mood) {
	this.product_mood = product_mood;
}
public String getProduct_category() {
	return product_category;
}
public void setProduct_category(String product_category) {
	this.product_category = product_category;
}
public String getProduct_image() {
	return product_image;
}
public void setProduct_image(String product_image) {
	this.product_image = product_image;
}
public String getCompany_id() {
	return company_id;
}
public void setCompany_id(String company_id) {
	this.company_id = company_id;
}
public String getWish_id() {
	return wish_id;
}
public void setWish_id(String wish_id) {
	this.wish_id = wish_id;
}

public String getCompany_name() {
	return company_name;
}

public void setCompany_name(String company_name) {
	this.company_name = company_name;
}

public String getProduct_description() {
	return product_description;
}

public void setProduct_description(String product_description) {
	this.product_description = product_description;
}

}
