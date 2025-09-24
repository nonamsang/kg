package com.moodshop.kokone.vo;

import java.sql.Date;

public class NoticeVO {
    private String notice_id;
    private String notice_name;
    private String notice_title;
    private String notice_description;
    private java.sql.Date notice_start_date;
    private java.sql.Date notice_end_date;
    private String notice_category;
    private String notice_image_source;
    private String notice_sub_image_source;
    
	public String getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(String notice_id) {
		this.notice_id = notice_id;
	}
	public String getNotice_name() {
		return notice_name;
	}
	public void setNotice_name(String notice_name) {
		this.notice_name = notice_name;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_description() {
		return notice_description;
	}
	public void setNotice_description(String notice_description) {
		this.notice_description = notice_description;
	}
	public Date getNotice_start_date() {
		return notice_start_date;
	}
	public void setNotice_start_date(Date notice_start_date) {
		this.notice_start_date = notice_start_date;
	}
	public Date getNotice_end_date() {
		return notice_end_date;
	}
	public void setNotice_end_date(Date notice_end_date) {
		this.notice_end_date = notice_end_date;
	}
	public String getNotice_category() {
		return notice_category;
	}
	public void setNotice_category(String notice_category) {
		this.notice_category = notice_category;
	}
	public String getNotice_image_source() {
		return notice_image_source;
	}
	public void setNotice_image_source(String notice_image_source) {
		this.notice_image_source = notice_image_source;
	}
	public String getNotice_sub_image_source() {
		return notice_sub_image_source;
	}
	public void setNotice_sub_image_source(String notice_sub_image_source) {
		this.notice_sub_image_source = notice_sub_image_source;
	}
}