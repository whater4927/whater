package com.whater.mq.producer;

import com.whater.model.vo.MQMsg;

public interface MsgProducer {
	public String sendMsg(MQMsg<?> msg);
}
