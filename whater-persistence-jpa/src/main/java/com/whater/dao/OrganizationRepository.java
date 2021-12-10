package com.whater.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whater.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long>{

}
