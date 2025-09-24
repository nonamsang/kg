package com.moodshop.kokone.vo;

public class EventVO {
	private String event_id;
	private String event_name;
	private String event_type;
	private String event_title;
	private String event_description;
	private java.sql.Date event_start_date;
	private java.sql.Date event_end_date;
	private String event_category;
	private String event_image_source;
	private String event_sub_image_source;

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getEvent_title() {
		return event_title;
	}

	public void setEvent_title(String event_title) {
		this.event_title = event_title;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}
	public java.sql.Date getEvent_start_date() {
		return event_start_date;
	}
	public void setEvent_start_date(java.sql.Date event_start_date) {
		this.event_start_date = event_start_date;
	}
	public java.sql.Date getEvent_end_date() {
		return event_end_date;
	}
	public void setEvent_end_date(java.sql.Date event_end_date) {
		this.event_end_date = event_end_date;
	}
	public String getEvent_category() {
		return event_category;
	}
	public void setEvent_category(String event_category) {
		this.event_category = event_category;
	}
	public String getEvent_image_source() {
		return event_image_source;
	}
	public void setEvent_image_source(String event_image_source) {
		this.event_image_source = event_image_source;
	}

	public String getEvent_sub_image_source() {
		return event_sub_image_source;
	}

	public void setEvent_sub_image_source(String event_sub_image_source) {
		this.event_sub_image_source = event_sub_image_source;
	}
}
