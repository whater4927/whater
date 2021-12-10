package com.whater.common.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whater.common.domain.AjaxResult;
import com.whater.common.util.DateUtil;
import com.whater.dao.AccountRepository;
import com.whater.dao.TransactionRepository;
import com.whater.entity.Account;
import com.whater.entity.Transaction;
import com.whater.model.dto.RechargeDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService {
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Transactional
	public AjaxResult recharge(RechargeDTO rechargeDTO) {
		Account account = accountRepository.findByAccountNumber(rechargeDTO.getAccountNumber());
		AjaxResult ajax = increaseAmt(rechargeDTO.getAccountNumber(), rechargeDTO.getAmt());
		if(!ajax.isSuccess()) {
			return ajax;
		}
		Transaction transaction = new Transaction();
		transaction.setToAccountId(account.getAccountNumber());
		transaction.setToBank(rechargeDTO.getBankType());
		transaction.setAmt(rechargeDTO.getAmt());
		transaction.setTime(DateUtil.getSystemDate());
		transaction.setRemark("recharge");
		transactionRepository.save(transaction);
		return AjaxResult.success();
	}
	
	
	public AjaxResult increaseAmt(String accountNumber,Long increaseAmt) {
		Account account = accountRepository.findByAccountNumber(accountNumber);
		if (account == null) {
			return AjaxResult.error(String.format("%s is not exist",accountNumber));
		}
		if ((account.getBalance() + increaseAmt) < 0) {
			return AjaxResult.error("Sorry, your credit is running low");
		}
		int size = accountRepository.updateByAccountNumber(account.getAccountNumber(), account.getBalance(),increaseAmt);
		if (size != 1) {
			throw new RuntimeException("transfer fail!");
		}
		return AjaxResult.success();
	}
	
}
