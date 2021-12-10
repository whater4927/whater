package com.whater.rocketmq.config;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.whater.model.vo.MQMsg;
import com.whater.mq.consumer.MqMsgProcessor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: lockie
 * @Date: 2020/4/21 11:05
 * @Description: 消费者监听
 */
@Component
@Slf4j
public class MQConsumeMsgListenerProcessor implements MessageListenerConcurrently {
	@Autowired
	private MqMsgProcessor processor;

	/**
	 * 默认msg里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息 不要抛异常，如果没有return
	 * CONSUME_SUCCESS ，consumer会重新消费该消息，直到return CONSUME_SUCCESS
	 * 
	 * @param msgList
	 * @param consumeConcurrentlyContext
	 * @return
	 */
	@Override
	public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList,
			ConsumeConcurrentlyContext consumeConcurrentlyContext) {
		if (CollectionUtils.isEmpty(msgList)) {
			log.info("MQ接收消息为空，直接返回成功");
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		MessageExt messageExt = msgList.get(0);
		log.info("MQ接收到的消息为：" + messageExt.toString());
		try {
			String topic = messageExt.getTopic();
			String tags = messageExt.getTags();
			String body = new String(messageExt.getBody(), "utf-8");
			log.info("MQ消息topic={}, tags={}, 消息内容={}", topic, tags, body);
			MQMsg msg = JSON.parseObject(body, MQMsg.class);
			PROCESSOR.get(msg.getTag()).invoke(processor, msg);
		} catch (Exception e) {
			log.error("获取MQ消息内容异常{}", e);
		}
		// TODO 处理业务逻辑
		return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	}

	private static Map<String, Method> PROCESSOR = new HashMap<>();

	public void addProcessor(String tag, Method method) {
		PROCESSOR.put(tag, method);
	}

}