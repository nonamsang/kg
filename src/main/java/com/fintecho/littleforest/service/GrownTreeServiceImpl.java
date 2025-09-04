package com.fintecho.littleforest.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintecho.littleforest.mapper.GrowingTreeMapper;
import com.fintecho.littleforest.mapper.GrownTreeMapper;
import com.fintecho.littleforest.vo.GrowingTreeVO;
import com.fintecho.littleforest.vo.GrownTreeVO;

@Service
public class GrownTreeServiceImpl implements GrownTreeService {

	@Autowired
	private GrownTreeMapper grownTreeMapper;
	
	@Autowired
	private GrowingTreeMapper growingTreeMapper;
	
	public ArrayList<GrownTreeVO> grownSelect(int user_Id) {
		// TODO Auto-generated method stub
		return grownTreeMapper.grownSelect(user_Id);
	}

	@Transactional
	public void grownInsert(GrownTreeVO grownvo) {
		int user_Id=4;
		GrowingTreeVO deletevo=growingTreeMapper.getAllStock(user_Id);
		growingTreeMapper.deletetree(deletevo);
		grownTreeMapper.grownInsert(grownvo);
		
	}
}
