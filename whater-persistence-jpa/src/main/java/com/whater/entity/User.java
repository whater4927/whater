package com.whater.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "whater_user")
@Data
public class User {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "com.whater.common.OrderIdGeneratorConfig")
	@GeneratedValue(generator = "idGenerator")
	private Long id;

	@Column(name = "username", unique = true, nullable = false, length = 64)
	private String username;

	@Column(name = "password", nullable = false, length = 64)
	private String password;

	@Column(name = "email", length = 64)
	private String email;
	
	
	@Column(name = "org_id")
	private Long orgId ;
}
