package com.song7749.srcenter.repository;


import com.song7749.srcenter.domain.SrDataRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SrDataRequestRepository extends JpaRepository<SrDataRequest, Long>, JpaSpecificationExecutor<SrDataRequest> {


}
