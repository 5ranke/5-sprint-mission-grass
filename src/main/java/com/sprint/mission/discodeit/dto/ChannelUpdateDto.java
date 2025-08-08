package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ChannelUpdateDto(
        UUID id,
        String newName,
        String newDescription,
        UUID userId) {
}
