package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ReadStatusViewDto(
        UUID id,
        UUID userId ,
        UUID channelId,
        Instant lastReadAt
) {
}
