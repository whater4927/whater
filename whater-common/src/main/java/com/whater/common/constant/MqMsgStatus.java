package com.whater.common.constant;

public enum MqMsgStatus {
	
	TRANSACTION(0), SUCCESS(1), FAIL(-1);

	private int code;

	MqMsgStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code ;
	}
}
