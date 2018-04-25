package com.song7749.dbclient.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.dbclient.domain.Database;

@Repository
public interface DatabaseRepository extends JpaRepository<Database, Long> {

	Page<Database> findByIdIn(List<Long> ids, Pageable page);
}