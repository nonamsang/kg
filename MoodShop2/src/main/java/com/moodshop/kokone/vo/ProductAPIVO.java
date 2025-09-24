// ProductAPIVO.java (반드시 새로 생성)
package com.moodshop.kokone.vo;

import java.util.List;

public class ProductAPIVO {
	private int id;
	private String title;
	private String description;
	private int price;
	private List<String> images;

	// Getter, Setter
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}
}
