package com.whater.chengdu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.whater.common.constant.Tag;
import com.whater.model.vo.MQMsg;
import com.whater.mq.consumer.MqMsgProcessor;
import com.whater.rocketmq.processor.Processor;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChengDuMqMsgProcessor implements MqMsgProcessor{
	@Autowired
	private TransferAccountService transferAccountService ;
	
	@Processor(name = Tag.TransferAccountsTag_Rep)
	public void transferAccountsIn(MQMsg<JSONObject> mq) {
		log.info("tag:{},topic:{},msg_id:{},status:{}",mq.getTag(),mq.getTopic(),mq.getMsgId(),mq.getStatus());
		transferAccountService.doTransferAccountOut_rep(mq);
	}

}
