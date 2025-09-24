package com.moodshop.kokone.vo;

import java.sql.Date;

public class AnswerVO {

    private String aId;           // 답변 ID
    private Date aDate;           // 답변 작성 날짜
    private String managerId;     // 관리자 ID
    private String aTitle;        // 답변 제목
    private String aContent;      // 답변 내용
    private String qId;           // 질문 ID (외래 키)

    // 기본 생성자
    public AnswerVO() {}

	public String getaId() {
		return aId;
	}
	public void setaId(String aId) {
		this.aId = aId;
	}
	public Date getaDate() {
		return aDate;
	}
	public void setaDate(Date aDate) {
		this.aDate = aDate;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getaTitle() {
		return aTitle;
	}
	public void setaTitle(String aTitle) {
		this.aTitle = aTitle;
	}
	public String getaContent() {
		return aContent;
	}
	public void setaContent(String aContent) {
		this.aContent = aContent;
	}
	public String getQId() {
		return qId;
	}
	public void setQId(String qId) {
		this.qId = qId;
	}
}
