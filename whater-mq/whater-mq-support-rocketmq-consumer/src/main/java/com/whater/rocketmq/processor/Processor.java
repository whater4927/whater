package com.whater.rocketmq.processor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.whater.common.constant.Tag;

@Retention(RetentionPolicy.RUNTIME)
public @interface Processor {
	
	public Tag name();
	
}
