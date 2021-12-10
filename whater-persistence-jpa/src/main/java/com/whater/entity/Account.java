package com.whater.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "whater_account")
@Data
public class Account {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "com.whater.common.OrderIdGeneratorConfig")
	@GeneratedValue(generator = "idGenerator")
	private Long id;
	
	@Column(name = "balance")
	private Long balance ;
	
	@Column(name = "account_number",nullable = false, length = 32)
	private String accountNumber ;
	
	@Column(name = "user_id",nullable = false)
	private Long userId ; 
}
