package com.whater.china.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whater.common.constant.Tag;
import com.whater.entity.Transaction;
import com.whater.model.vo.MQMsg;
import com.whater.mq.consumer.MqMsgProcessor;
import com.whater.rocketmq.processor.Processor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChinaMqMsgProcessor implements MqMsgProcessor{
	
	@Autowired
	private TransferAccountService transferAccountService ;
	
	@Processor(name = Tag.TransferAccountsTag)
	public void transferAccountsIn(MQMsg<JSONObject> mq) {
		log.info("tag:{},topic:{},msg_id:{},status:{}",mq.getTag(),mq.getTopic(),mq.getMsgId(),mq.getStatus());
		log.info("data:{}",mq.getData().toJSONString());
		Transaction transaction = JSON.toJavaObject(mq.getData(), Transaction.class);
		log.info("id:{}",transaction.getId());
		transferAccountService.doTransferAccountIn(transaction);
	}

}
