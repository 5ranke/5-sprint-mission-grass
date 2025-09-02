package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Entity
@Table(
        name = "user_statuses",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_status_user",
                columnNames = "user_id"
        )
)
@Getter @SuperBuilder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "UserStatus")
public class UserStatus extends BaseUpdatableEntity {

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_status_user")
    )
    private User user;

    @Schema(description = "마지막 활동 시각", format = "date-time")
    @Column(name = "last_active_at", nullable = false)
    private Instant lastActiveAt;

    public void update(Instant lastActiveAt) {
        if (lastActiveAt != null && !lastActiveAt.equals(this.lastActiveAt)) {
            this.lastActiveAt = lastActiveAt;
        }
    }
}
