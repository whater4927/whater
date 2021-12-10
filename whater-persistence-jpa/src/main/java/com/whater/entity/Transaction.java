package com.whater.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "whater_transaction")
@Data
public class Transaction {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "com.whater.common.OrderIdGeneratorConfig")
	@GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name = "from_bank")
	private Integer fromBank ;
	
	@Column(name = "from_account_id", length = 32)
	private String fromAccountId ;
	
	@Column(name = "to_bank")
	private Integer toBank ;
	
	@Column(name = "to_account_id", length = 32)
	private String toAccountId ;
	
	@Column(name = "amt")
	private Long amt ;
	
	@Column(name = "time")
	private Date time ;
	
	@Column(name = "remark", length = 255)
	private String remark ;
	
	/**
	 *  0,交易中 -1 失败 ，1交易成功
	 */
	@Column(name = "status_")
	private Integer status ;
	
}
