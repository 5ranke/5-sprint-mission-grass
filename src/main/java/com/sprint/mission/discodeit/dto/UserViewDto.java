package com.sprint.mission.discodeit.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserViewDto(
        UUID id,
        String username,
        String email,
        @Nullable UUID profileId,
        boolean online) {
}
