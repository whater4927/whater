package com.whater.common.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whater.common.domain.AjaxResult;
import com.whater.common.service.AccountService;
import com.whater.model.dto.RechargeDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("account")
@Slf4j
public class AccountController {
	@Autowired
	private AccountService accountService ;
	
	
	@PostMapping("recharge")
	public AjaxResult recharge(@RequestBody RechargeDTO rechargeDTO) {
		try {
			return accountService.recharge(rechargeDTO);
		} catch (Exception e) {
			log.error("transfer fail!", e);
		}
		return AjaxResult.error("transfer fail!");
	}
	
	
}
