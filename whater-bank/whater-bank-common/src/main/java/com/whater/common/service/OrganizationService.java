package com.whater.common.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whater.common.domain.AjaxResult;
import com.whater.common.util.SnowflakeIdWorker;
import com.whater.dao.OrganizationRepository;
import com.whater.entity.Organization;
import com.whater.model.dto.OrganizationDTO;

@Service
public class OrganizationService {
	
	@Autowired
	private OrganizationRepository organizationRepository ;
	
	public AjaxResult create(OrganizationDTO organizationDTO) {
		Organization org = new Organization();
		
		if(organizationDTO.getPid() !=null) {
			Optional<Organization> op = organizationRepository.findById(organizationDTO.getPid());
			if(!op.isPresent()) {
				return AjaxResult.error("pid is not exist");
			}
			org.setPid(organizationDTO.getPid());
		}
		org.setOrgName(organizationDTO.getOrgName());
		org.setOrgCode("D"+ SnowflakeIdWorker.generateId().toString());
		organizationRepository.save(org);
		return AjaxResult.success();
	}
	
	
}
