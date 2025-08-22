package com.fintecho.littleforest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PaymentVO {
    private int id;
    private int wallet_Id;
    private int product_Id;
    private int amount;
    private String type;         
    private String description;  
    private Date tran_At;       
    
   // JOIN 결과로 가져온 값 저장용
    private String bank_Name;  // 조인된 wallet_table 컬럼
    private String nickname;   
    
}
