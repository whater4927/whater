package com.whater.rocketmq.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.whater.mq.consumer.MqMsgProcessor;
import com.whater.rocketmq.processor.Processor;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
 
/**
 * @author: lockie
 * @Date: 2020/4/21 10:28
 * @Description: mq消费者配置
 */
@Getter
@Setter
@ToString
@Configuration
@ConfigurationProperties(prefix = "rocketmq.consumer")
@Slf4j
public class MQConsumerConfigure {
 
    private String groupName;
    private String namesrvAddr;
    private String topic;
    // 消费者线程数据量
    private Integer consumeThreadMin;
    private Integer consumeThreadMax;
    private Integer consumeMessageBatchMaxSize;
 
    @Autowired
    private MQConsumeMsgListenerProcessor consumeMsgListenerProcessor;
    
   @Autowired
   private MqMsgProcessor processor ;
    
    /**
     * mq 消费者配置
     * @return
     * @throws MQClientException
     */
    @PostConstruct
    public void defaultConsumer() throws MQClientException {
    	log.info("defaultConsumer 正在创建---------------------------------------");
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeThreadMin(consumeThreadMin);
        consumer.setConsumeThreadMax(consumeThreadMax);
        consumer.setConsumeMessageBatchMaxSize(consumeMessageBatchMaxSize);
        // 设置监听
        consumer.registerMessageListener(consumeMsgListenerProcessor);
 
        /**
         * 设置consumer第一次启动是从队列头部开始还是队列尾部开始
         * 如果不是第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        /**
         * 设置消费模型，集群还是广播，默认为集群
         */
//      consumer.setMessageModel(MessageModel.CLUSTERING);
		List<Processor> processors = new ArrayList<>();
		Method[] methods = processor.getClass().getMethods();
		for (Method method : methods) {
			Processor p = method.getAnnotation(Processor.class);
			if (p != null) {
				processors.add(p);
				consumeMsgListenerProcessor.addProcessor(p.name().name(), method);
			}
		}
        try {
        	for (Processor processor : processors) {
        		consumer.subscribe(topic, processor.name().name());
        		log.info("订阅主题:{},Tag:{}", topic, processor.name().name());
			}
            consumer.start();
            log.info("consumer 创建成功 groupName={}, topics={}, namesrvAddr={}",groupName,topic,namesrvAddr);
        } catch (MQClientException e) {
        	log.error("consumer 创建失败!");
        }
    }
}