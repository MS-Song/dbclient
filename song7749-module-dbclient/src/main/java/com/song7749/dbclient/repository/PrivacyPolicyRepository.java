package com.song7749.dbclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.song7749.dbclient.domain.PrivacyPolicy;

@Repository
public interface PrivacyPolicyRepository
		extends JpaRepository<PrivacyPolicy, Long>
		, JpaSpecificationExecutor<PrivacyPolicy> {
}