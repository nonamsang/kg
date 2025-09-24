package com.moodshop.kokone.vo;

public class Company1VO {
	private String Company_Id;// 추가함
	private String Company_Name;
	private String Manager_Id;
	
	public String getCompany_Id() {
		return Company_Id;
	}// 추가함

	public void setCompany_Id(String company_Id) {
		Company_Id = company_Id;
	}// 추가함

	public String getCompany_Name() {
		return Company_Name;
	}
	public void setCompany_Name(String company_Name) {
		Company_Name = company_Name;
	}
	public String getManager_Id() {
		return Manager_Id;
	}
	public void setManager_Id(String manager_Id) {
		Manager_Id = manager_Id;
	}
}
