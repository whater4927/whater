package com.whater.common.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whater.common.domain.AjaxResult;
import com.whater.common.service.OrganizationService;
import com.whater.model.dto.OrganizationDTO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("oganization")
@Slf4j
public class OrganizationController {
	
	@Autowired
	private OrganizationService organizationService ;
	
	@PostMapping("create")
	public AjaxResult create(@RequestBody OrganizationDTO organizationDTO) {
		return organizationService.create(organizationDTO);
	}
	
}
