package com.song7749.mail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.song7749.mail.domain.MailConfig;

@Repository
public interface MailConfigRepository extends JpaRepository<MailConfig, Long> {

}
