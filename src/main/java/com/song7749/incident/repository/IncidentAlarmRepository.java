package com.song7749.incident.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.song7749.incident.domain.IncidentAlarm;

@Repository
public interface IncidentAlarmRepository extends JpaRepository<IncidentAlarm, Long>, JpaSpecificationExecutor<IncidentAlarm> {

}
