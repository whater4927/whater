package com.whater.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whater.entity.DictDetail;

public interface DictDetailRepository extends JpaRepository<DictDetail, Long>{
	public List<DictDetail> findByName(String name);
}
