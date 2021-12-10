package com.whater.common.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whater.common.domain.AjaxResult;
import com.whater.common.util.SnowflakeIdWorker;
import com.whater.dao.AccountRepository;
import com.whater.dao.OrganizationRepository;
import com.whater.dao.UserRepository;
import com.whater.entity.Account;
import com.whater.entity.User;
import com.whater.model.dto.UserDTO;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository ;
	@Autowired
	private OrganizationRepository organizationRepository ;
	@Autowired
	private AccountRepository accountRepository ;
	@Transactional
	public AjaxResult createUserAndAccount(UserDTO userDTO) {
		User user = new User();
		List<User> users = userRepository.findByEmail(userDTO.getEmail());
		if(users != null && users.size() > 0) {
			return AjaxResult.error("Email is exist!");
		}
		users = userRepository.findByUsername(userDTO.getUserName());
		if(users != null && users.size() > 0) {
			return AjaxResult.error("username is exist!");
		}
		user.setEmail(userDTO.getEmail());
		user.setUsername(userDTO.getUserName());
		if(!organizationRepository.findById(userDTO.getOrgId()).isPresent()) {
			return AjaxResult.error("orgId is not exist!");
		}
		user.setOrgId(userDTO.getOrgId());
		user.setPassword(UUID.randomUUID().toString());
		user = userRepository.save(user);
		Account account = new Account();
		account.setAccountNumber("A"+ SnowflakeIdWorker.generateId().toString());
		account.setBalance(0L);
		account.setUserId(user.getId());
		accountRepository.save(account);
		return AjaxResult.success();
	}
}
