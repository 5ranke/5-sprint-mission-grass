package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserStatusCreateDto(
        UUID userId
) {
}
