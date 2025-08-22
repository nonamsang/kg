package com.fintecho.littleforest.mapper.admin;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.fintecho.littleforest.dto.ChatRoomDTO;

@Mapper
public interface AdminChatMapper {

	String getChatRoomNameById(int id);

	String getLastMessageByChatRoomId(int chat_Room_Id);

	ArrayList<Integer> getOtherUserByUserId(int user_Id);

	ArrayList<ChatRoomDTO> getChatRoomDTO(int user_Id);
}
