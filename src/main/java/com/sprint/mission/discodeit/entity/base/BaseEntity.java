package com.sprint.mission.discodeit.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
public abstract class BaseEntity {
    @Id
    @Column(columnDefinition = "uuid",
            updatable = false, nullable = false)
    private UUID id;

    @CreatedDate
    @Column(name = "created_at",
            updatable = false, nullable = false)
    private Instant createdAt;
}
