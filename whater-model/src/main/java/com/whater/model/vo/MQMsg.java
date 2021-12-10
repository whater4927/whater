package com.whater.model.vo;

import com.whater.common.constant.MqMsgStatus;
import com.whater.common.constant.Tag;
import com.whater.common.constant.Topic;
import com.whater.common.util.SnowflakeIdWorker;

import lombok.Data;

@Data
public class MQMsg<T> {
	
	private String msgId ;
	
	private int status ;
	
	private String remark ;
	
	private String topic ;
	
	private String tag ;
	
	private T data ;
	
	public static <T> MQMsg<T> create(T t) {
		MQMsg<T> msg = new MQMsg<>();
		msg.setData(t);
		return msg ;
	}
	public static <T> MQMsg<T> create(T t,String topic,String tag) {
		MQMsg<T> msg = new MQMsg<>();
		msg.setData(t);
		msg.setTopic(topic);
		msg.setTag(tag);
		msg.setMsgId("M"+SnowflakeIdWorker.generateId());
		return msg ;
	}
	public static <T> MQMsg<T> create(T t,String msgId,Topic topic,Tag tag) {
		MQMsg<T> msg = new MQMsg<>();
		msg.setData(t);
		msg.setTopic(topic.name());
		msg.setTag(tag.name());
		msg.setMsgId(msgId);
		return msg ;
	}
	public static <T> MQMsg<T> create(String msgId,Topic topic,Tag tag,MqMsgStatus status) {
		MQMsg<T> msg = new MQMsg<>();
		msg.setTopic(topic.name());
		msg.setTag(tag.name());
		msg.setMsgId(msgId);
		msg.setStatus(status.getCode());
		return msg ;
	}
	
	public boolean success() {
		return status == MqMsgStatus.SUCCESS.getCode();
	}
}
