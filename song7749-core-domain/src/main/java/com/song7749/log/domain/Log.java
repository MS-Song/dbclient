package com.song7749.log.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.song7749.common.base.Entities;
import com.song7749.log.type.LogType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="type")
public abstract class Log extends Entities {

	private static final long serialVersionUID = 2596224931513532409L;

	@Id
	@Column(name = "log_id", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank
	@Size(min = 8, max = 64)
	@Column(nullable = false, updatable = false)
	private String ip;

	@Column(nullable=false, updatable=false)
	@CreationTimestamp
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date date;

	@Enumerated(EnumType.STRING)
    @Column(name = "type", insertable = false, updatable = false)
    private LogType type;
}