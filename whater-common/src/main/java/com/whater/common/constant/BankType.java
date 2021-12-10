package com.whater.common.constant;


public enum BankType {
	
	CHINA_BANK(1),CHENG_DU_BANK(2) ;
	
	private int code ;
	
	BankType(int code) {
		this.code = code ;
	}

	public int getCode() {
		return this.code;
	}
	
	
}
