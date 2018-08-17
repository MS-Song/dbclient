package com.song7749.dbclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.log.domain.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {


}