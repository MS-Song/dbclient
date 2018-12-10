package com.song7749.traffic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.song7749.traffic.domain.TrafficGuard;

@Repository
public interface TrafficGuardRepository extends JpaRepository<TrafficGuard, Long> , JpaSpecificationExecutor<TrafficGuard>{
}