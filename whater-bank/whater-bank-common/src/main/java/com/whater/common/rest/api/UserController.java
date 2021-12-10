package com.whater.common.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whater.common.domain.AjaxResult;
import com.whater.common.service.UserService;
import com.whater.model.dto.UserDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {
	
	
	@Autowired
	private UserService userService ;
	
	@PostMapping("createUserAndAccount")
	public AjaxResult createUserAndAccount(@RequestBody UserDTO userDTO) {
		return userService.createUserAndAccount(userDTO);
	}
	
}
