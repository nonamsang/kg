package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class LikesVO {
private int id;
private int user_Id;
private String table_Type;
private int table_Id;
private Date press_At;
}
