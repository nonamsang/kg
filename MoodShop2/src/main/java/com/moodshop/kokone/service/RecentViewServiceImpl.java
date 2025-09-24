package com.moodshop.kokone.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moodshop.kokone.dao.RecentViewDAO;
import com.moodshop.kokone.vo.RecentViewVO;

@Service("recentViewService")
public class RecentViewServiceImpl implements RecentViewService {

    @Autowired
    private RecentViewDAO recentViewDAO;


    @Override
    public void addRecentView(String userId, String productId) {
    	// 같은 상품이 있으면 삭제
    	recentViewDAO.deleteRecentViewByUserAndProduct(userId, productId);

    	// 새로 insert
    	RecentViewVO vo = new RecentViewVO();
    	vo.setRecent_id(UUID.randomUUID().toString());
    	vo.setUser_id(userId);
    	vo.setProduct_id(productId);
    	recentViewDAO.insertRecentView(vo);
    	
    	// 유저별 저장 개수 체크 후 10개 초과 시 가장 오래된 데이터 삭제
        int count = recentViewDAO.getRecentViewCount(userId);
        if (count > 10) {
            recentViewDAO.deleteOldestRecentView(userId);
        }
    }

    @Override
    public List<RecentViewVO> getRecentViews(String userId) {
        return recentViewDAO.selectRecentViewByUser(userId);
    }
    
    
    @Override
    public void deleteRecentViewById(String recentId) {
        recentViewDAO.deleteRecentViewById(recentId);
    }

    @Override
    public void deleteRecentViewByUser(String userId) {
        recentViewDAO.deleteRecentViewByUser(userId);
    }
}
