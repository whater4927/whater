package com.whater.chengdu.rest.api;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whater.chengdu.service.TransferAccountService;
import com.whater.common.domain.AjaxResult;
import com.whater.dao.UserRepository;
import com.whater.entity.User;
import com.whater.model.dto.TransferAccountsDTO;
import com.whater.model.vo.MQMsg;
import com.whater.mq.producer.MsgProducer;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {

	@Autowired
	private UserRepository userDao;
	@Autowired
	private TransferAccountService transferAccountService ;
	
	@GetMapping("/{test}")
	public String test(@PathVariable("test") String test) {
		User user = new User();
		user.setEmail("492711423@qq.com");
		user.setOrgId(346465L);
		user.setUsername("zhangsan2");
		user.setPassword("11312");
		userDao.save(user);
		return "hello:" + test;
	}

	@Autowired
	private MsgProducer msgProducer;

	/**
	 * 发送简单的MQ消息
	 * 
	 * @param msg
	 * @return
	 * @throws MQBrokerException
	 * @throws RemotingException
	 * @throws MQClientException
	 */
	@GetMapping("/send")
	public String send(String msg) {
		if (StringUtils.isEmpty(msg)) {
			return "";
		}
		log.info("发送MQ消息内容：" + msg);
		
		
		
		// 默认3秒超时
		msgProducer.sendMsg(MQMsg.create(msg));
		log.info("消息发送响应：");

		return "success:" + msg;
	}
	
	
	@PostMapping("/transferAccounts")
	public AjaxResult transferAccounts(@RequestBody TransferAccountsDTO transferAccountsDTO) {

		return transferAccountService.doTransferAccountIn(transferAccountsDTO);
	}
	
	
	
	
}
