package com.whater.chengdu.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whater.common.constant.BankType;
import com.whater.common.constant.MqMsgStatus;
import com.whater.common.constant.Tag;
import com.whater.common.constant.Topic;
import com.whater.common.domain.AjaxResult;
import com.whater.common.service.AccountService;
import com.whater.common.util.DateUtil;
import com.whater.dao.AccountRepository;
import com.whater.dao.TransactionRepository;
import com.whater.entity.Account;
import com.whater.entity.Transaction;
import com.whater.model.dto.TransferAccountsDTO;
import com.whater.model.vo.MQMsg;
import com.whater.mq.producer.MsgProducer;

@Service
public class TransferAccountService {
	
	@Autowired
	private AccountRepository accountRepository ;
	@Autowired
	private TransactionRepository transactionRepository ;
	
	@Autowired
	private AccountService accountService ;
	@Autowired
	private MsgProducer msgProducer;
	
	
	@Transactional
	public AjaxResult doTransferAccountOut(TransferAccountsDTO transferAccountsDTO) {
		String fromAccount = transferAccountsDTO.getFromAccount();
		Account account = accountRepository.findByAccountNumber(fromAccount);
		if(account == null) {
			return AjaxResult.error(String.format("%s is not exist", transferAccountsDTO.getFromAccount()));
		}
		AjaxResult ajax = accountService.increaseAmt(fromAccount, -transferAccountsDTO.getAmt());
		if(!ajax.isSuccess()) {
			return ajax;
		}
		Transaction transaction = new Transaction();
		transaction.setFromBank(BankType.CHENG_DU_BANK.getCode());
		transaction.setFromAccountId(account.getAccountNumber());
		transaction.setToAccountId(transferAccountsDTO.getToAccount());
		transaction.setToBank(BankType.CHINA_BANK.getCode());
		transaction.setAmt(transferAccountsDTO.getAmt());
		transaction.setTime(DateUtil.getSystemDate());
		transaction.setStatus(MqMsgStatus.TRANSACTION.getCode());
		transaction.setRemark("doTransferAccountOut");
		transactionRepository.save(transaction);
		msgProducer.sendMsg(MQMsg.create(transaction,transaction.getId().toString(), Topic.ChinaTopic, Tag.TransferAccountsTag));
		return AjaxResult.success();
	} 
	@Transactional
	public AjaxResult doTransferAccountOut_rep(MQMsg msg) {
		Transaction transaction = transactionRepository.findById(Long.valueOf(msg.getMsgId())).get();
		if(!msg.success()) {
			transaction.setStatus(MqMsgStatus.FAIL.getCode());
			transactionRepository.save(transaction);
			accountService.increaseAmt(transaction.getFromAccountId(), transaction.getAmt());
		} else {
			transaction.setStatus(MqMsgStatus.SUCCESS.getCode());
			transactionRepository.save(transaction);
		}
		return AjaxResult.success();
	} 
	@Transactional
	public AjaxResult doTransferAccountIn(TransferAccountsDTO transferAccountsDTO) {
		return AjaxResult.success();
	} 
}
