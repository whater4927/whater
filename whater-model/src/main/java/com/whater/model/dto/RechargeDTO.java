package com.whater.model.dto;

import lombok.Data;

@Data
public class RechargeDTO {
	
	private String accountNumber ;
	
	private Long amt ;
	
	private Integer bankType;
	
}
