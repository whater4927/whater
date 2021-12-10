package com.whater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whater.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
