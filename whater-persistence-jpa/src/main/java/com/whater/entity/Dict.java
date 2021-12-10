package com.whater.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table(name = "whater_dict")
@Data
public class Dict {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "com.whater.common.OrderIdGeneratorConfig")
	@GeneratedValue(generator = "idGenerator")
	private Long id ;
	@Column(name = "name_",nullable = false, length = 32)
	private String name ;
	@Column(name = "remark_",nullable = false, length = 255)
	private String remark ;
}
