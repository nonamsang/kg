package com.moodshop.kokone.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

public class ReviewVO {
	private String review_id;
	private Date review_date;
	private String review_content;
	private String user_id;
	private String order_id;
	private String review_image; // DB 저장용 파일명
	private MultipartFile[] upload_image; // form에서 넘어오는 파일
	private int rating;

	// Getter, Setter
	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}

	public Date getReview_date() {
		return review_date;
	}

	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}

	public String getReview_content() {
		return review_content;
	}

	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getReview_image() {
		return review_image;
	}

	public void setReview_image(String review_image) {
		this.review_image = review_image;
	}

	public MultipartFile[] getUpload_image() {
		return upload_image;
	}

	public void setUpload_image(MultipartFile[] upload_image) {
		this.upload_image = upload_image;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}
}
