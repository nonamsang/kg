package com.moodshop.kokone.service;

import java.util.List;

import com.moodshop.kokone.vo.RecentViewVO;

public interface RecentViewService {
    public void addRecentView(String userId, String productId);
    public List<RecentViewVO> getRecentViews(String userId);
    
    public void deleteRecentViewById(String recentId); // 개별 삭제
    public void deleteRecentViewByUser(String userId); // 전체 삭제
}
