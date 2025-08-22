package com.fintecho.littleforest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PointEventMapper {
  
	/*
	 * int insert(int user_Id, String event_Code, int amount, String memo); int
	 * countToday(int user_Id, String event_Code);
	 */
	    int insert(@Param("user_Id") int userId,
	               @Param("event_Code") String eventCode,
	               @Param("amount") int amount,
	               @Param("memo") String memo);

	    int countToday(@Param("user_Id") int userId,
	                   @Param("event_Code") String eventCode);
	}