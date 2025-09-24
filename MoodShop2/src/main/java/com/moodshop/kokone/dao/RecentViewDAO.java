package com.moodshop.kokone.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.moodshop.kokone.vo.RecentViewVO;

public interface RecentViewDAO {
    public void insertRecentView(RecentViewVO recentViewVO);
    public List<RecentViewVO> selectRecentViewByUser(String userId);
    
    public void deleteRecentViewByUser(String userId);
    public void deleteRecentViewById(String recentId);
    
    public void deleteRecentViewByUserAndProduct(@Param("userId") String userId, @Param("productId") String productId);
    
    public int getRecentViewCount(String userId); // 유저별 개수 조회
    public void deleteOldestRecentView(String userId); // 유저별 가장 오래된 데이터 삭제
}