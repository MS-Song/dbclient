package com.song7749.dbclient.drs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.dbclient.drs.domain.Member;

@Repository
public interface MemberRepository  extends JpaRepository<Member, Long> {

	Member findByLoginId(String loginId);

	Member findByLoginIdAndPassword(String loginId, String password);

}