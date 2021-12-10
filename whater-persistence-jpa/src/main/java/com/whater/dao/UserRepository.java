package com.whater.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whater.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public List<User> findByUsername(String username);
	
	public List<User> findByEmail(String email);
	
}
