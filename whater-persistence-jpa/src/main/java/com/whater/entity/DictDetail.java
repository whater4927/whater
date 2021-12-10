package com.whater.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "whater_dict_detail")
@Data
public class DictDetail {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "com.whater.common.OrderIdGeneratorConfig")
	@GeneratedValue(generator = "idGenerator")
	private Long id ;
	@Column(name = "pid")
	private Long pid ;
	@Column(name = "name_",nullable = false, length = 32)
	private String name ;
	@Column(name = "key_",nullable = false, length = 32)
	private String key ;
	@Column(name = "value_",nullable = false, length = 100)
	private String value ;
}
