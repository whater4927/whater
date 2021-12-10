package com.whater.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.whater.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	public Account findByAccountNumber(String accountNumber);

	@Modifying
	@Query("update Account m set m.balance=(?2+?3) where  m.accountNumber=?1 and m.balance=?2")
	public int updateByAccountNumber(String accountNumber,Long balance,Long increaseAmt);
	
}
