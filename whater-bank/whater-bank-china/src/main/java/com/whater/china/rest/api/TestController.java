package com.whater.china.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whater.dao.UserRepository;
import com.whater.entity.User;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
	
	@Autowired
	private UserRepository userDao ;
	
	@GetMapping("/{test}")
	public String test(@PathVariable("test")String test) {
		User user = new User();
		user.setEmail("492711423@qq.com");
		user.setOrgId(346465L);
		user.setUsername("zhangsan2");
		user.setPassword("11312");
		userDao.save(user);
		return "hello:"+test ;
	}
}
