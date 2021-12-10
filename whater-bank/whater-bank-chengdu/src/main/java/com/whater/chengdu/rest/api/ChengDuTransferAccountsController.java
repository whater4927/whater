package com.whater.chengdu.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whater.chengdu.service.TransferAccountService;
import com.whater.common.domain.AjaxResult;
import com.whater.model.dto.TransferAccountsDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("chengdu/account")
@Slf4j
public class ChengDuTransferAccountsController {
	@Autowired
	private TransferAccountService transferAccountService ;
	
	@PostMapping("transfer")
	public AjaxResult transferAccounts(@RequestBody TransferAccountsDTO transferAccountsDTO) {
		return transferAccountService.doTransferAccountOut(transferAccountsDTO);
	}
	
	
	
}
