package com.coronacapsule.api.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class JpaBase {

	@Builder.Default
	private boolean deleted = false;
	
	@Column(nullable = false, updatable=false)
	private LocalDateTime createdAt;
	@Column(nullable = false)
	private LocalDateTime modifiedAt;
	
	
	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		modifiedAt = now;
	}
	
	@PreUpdate
	public void preUpdate() {
		modifiedAt = LocalDateTime.now();
	}
	
}
