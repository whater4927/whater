package com.whater.mq.consumer;

import com.whater.model.vo.MQMsg;

public interface MsgConsumer {
	public void receiveMsg(MQMsg<?> msg);
}
