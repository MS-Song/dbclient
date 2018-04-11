package com.song7749.dbclient.drs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.song7749.dbclient.drs.domain.MemberSaveQuery;

public interface MemberSaveQueryRepository   extends JpaRepository<MemberSaveQuery, Long> {

}
