package com.whater.china.service;

import java.util.Optional;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
	public AjaxResult doTransferAccountIn(Transaction transaction) {
		try {
			//处理
			Optional<Transaction> o = transactionRepository.findById(transaction.getId());
			if(o.isPresent()) {
				log.warn("该消息已经消费！");
				return null ;
			}
			AjaxResult ajax = accountService.increaseAmt(transaction.getToAccountId(), transaction.getAmt());
			if(!ajax.isSuccess()) {
				msgProducer.sendMsg(MQMsg.create(transaction.getId().toString(), Topic.ChengDuTopic, Tag.TransferAccountsTag_Rep,MqMsgStatus.FAIL));
				return ajax;
			}
			Transaction newTransaction = new Transaction();
			newTransaction.setFromBank(BankType.CHENG_DU_BANK.getCode());
			newTransaction.setFromAccountId(transaction.getFromAccountId());
			newTransaction.setToAccountId(transaction.getToAccountId());
			newTransaction.setToBank(BankType.CHINA_BANK.getCode());
			newTransaction.setAmt(transaction.getAmt());
			newTransaction.setTime(DateUtil.getSystemDate());
			newTransaction.setStatus(MqMsgStatus.SUCCESS.getCode());
			newTransaction.setRemark("success");
			transactionRepository.save(newTransaction);
			msgProducer.sendMsg(MQMsg.create(transaction.getId().toString(), Topic.ChengDuTopic, Tag.TransferAccountsTag_Rep,MqMsgStatus.SUCCESS));
		} catch (Exception e) {
			msgProducer.sendMsg(MQMsg.create(transaction.getId().toString(), Topic.ChengDuTopic, Tag.TransferAccountsTag_Rep,MqMsgStatus.FAIL));
			e.printStackTrace();
			return null;
		}
		return AjaxResult.success();
	} 
}
