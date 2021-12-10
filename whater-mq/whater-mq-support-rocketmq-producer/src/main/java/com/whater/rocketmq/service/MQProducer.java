package com.whater.rocketmq.service;

import java.util.Optional;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.whater.model.vo.MQMsg;
import com.whater.mq.producer.MsgProducer;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MQProducer implements MsgProducer {
	
	@Autowired
	DefaultMQProducer defaultMQProducer;

	@Override
	public String sendMsg(MQMsg<?> msg) {
		Message sendMsg = new Message(msg.getTopic(), msg.getTag(), JSON.toJSON(msg).toString().getBytes());
		try {
			SendResult sendResult = defaultMQProducer.send(sendMsg);
			log.info("消息发送响应：" + Optional.ofNullable(sendResult).map(SendResult::toString).get());
		} catch (MQClientException e) {
			e.printStackTrace();
		} catch (RemotingException e) {
			e.printStackTrace();
		} catch (MQBrokerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return msg.getMsgId();
	}

}
