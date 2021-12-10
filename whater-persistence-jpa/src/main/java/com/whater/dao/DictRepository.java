package com.whater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whater.entity.Dict;

public interface DictRepository extends JpaRepository<Dict, Long> {
	public Dict findByName(String name);
}
