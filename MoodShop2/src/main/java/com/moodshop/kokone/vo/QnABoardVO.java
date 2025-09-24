package com.moodshop.kokone.vo;

import java.util.Date;

public class QnABoardVO {
    private String qId;
    private String qTitle;
    private String qContent;
    private String userId;
    private Date qDate;
    private String nickname;
    private int answerCount;

	public QnABoardVO() {
        // 기본 생성자
    }

    // 기존 방식 유지
    public String getqId() {
        return qId;
    }
    public void setqId(String qId) {
        this.qId = qId;
    }

    public String getqTitle() {
        return qTitle;
    }
    public void setqTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getqContent() {
        return qContent;
    }
    public void setqContent(String qContent) {
        this.qContent = qContent;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getqDate() {
        return qDate;
    }
    public void setqDate(Date qDate) {
        this.qDate = qDate;
    }

    // Spring 바인딩 호환용 getter/setter 추가
    public String getQId() {
        return qId;
    }
    public void setQId(String qId) {
        this.qId = qId;
    }

    public String getQTitle() {
        return qTitle;
    }
    public void setQTitle(String qTitle) {
        this.qTitle = qTitle;
    }

    public String getQContent() {
        return qContent;
    }
    public void setQContent(String qContent) {
        this.qContent = qContent;
    }

    public Date getQDate() {
        return qDate;
    }
    public void setQDate(Date qDate) {
        this.qDate = qDate;
    }
    // 아래는 닉네임을 가져올 때 쓰이는 것!!
    public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	// 답변수 확인
	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}
}
