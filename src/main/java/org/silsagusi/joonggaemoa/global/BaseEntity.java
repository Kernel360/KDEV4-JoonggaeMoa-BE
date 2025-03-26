package org.silsagusi.joonggaemoa.global;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	private String createdAt;

	@LastModifiedDate
	private String updatedAt;

	@PrePersist
	private void prePersist() {
		this.createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.updatedAt = createdAt;
	}

	@PreUpdate
	private void preUpdate() {
		this.updatedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
