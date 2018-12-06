package com.song7749.chakra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.song7749.chakra.domain.ChakraConfig;

@Repository
public interface ChakraConfigRepository extends JpaRepository<ChakraConfig, Long>, JpaSpecificationExecutor<ChakraConfig> {}