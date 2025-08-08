package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record UserStatusViewDto(
        UUID id,
        UUID userId
) {
}
