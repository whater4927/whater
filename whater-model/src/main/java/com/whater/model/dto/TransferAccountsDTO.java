package com.whater.model.dto;

import java.util.Date;

import lombok.Data;
@Data
public class TransferAccountsDTO {
	private Long orderId;
	private String fromAccount;
	private String toAccount;
	private Date time;
	private Long amt;
	private String remark;
}
