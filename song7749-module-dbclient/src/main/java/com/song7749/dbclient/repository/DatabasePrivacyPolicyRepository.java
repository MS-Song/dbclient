package com.song7749.dbclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.song7749.dbclient.domain.DatabasePrivacyPolicy;

public interface DatabasePrivacyPolicyRepository
		extends JpaRepository<DatabasePrivacyPolicy, Long>
		, JpaSpecificationExecutor<DatabasePrivacyPolicy> {
}
