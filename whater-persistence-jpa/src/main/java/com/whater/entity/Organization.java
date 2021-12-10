package com.whater.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "whater_organization")
@Data
public class Organization {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "com.whater.common.OrderIdGeneratorConfig")
	@GeneratedValue(generator = "idGenerator")
	private Long id;

	@Column(name = "org_name", nullable = false, length = 64)
	private String orgName;

	@Column(name = "org_code",unique = true,  nullable = false, length = 64)
	private String orgCode;

	@Column(name = "pid")
	private Long pid;
	
}
