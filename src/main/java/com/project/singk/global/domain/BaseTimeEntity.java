package com.project.singk.global.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder // 상속한 필드를 빌더에 포함
@NoArgsConstructor
public abstract class BaseTimeEntity {

	@Column(updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	protected LocalDateTime createdAt;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	protected LocalDateTime modifiedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = this.createdAt == null ? LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) : this.createdAt;
        this.modifiedAt = this.modifiedAt == null ? LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) : this.modifiedAt;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    public BaseTimeEntity(LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
