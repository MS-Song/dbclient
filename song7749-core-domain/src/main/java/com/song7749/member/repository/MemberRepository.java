package com.song7749.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.song7749.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> , JpaSpecificationExecutor<Member>{

	Member findByLoginId(String loginId);

	Member findByLoginIdAndPassword(String loginId, String password);
}