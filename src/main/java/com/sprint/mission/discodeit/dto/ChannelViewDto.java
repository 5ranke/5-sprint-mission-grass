package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record ChannelViewDto(
        UUID id,
        ChannelType type,
        @Nullable String name,
        UUID authorId,
        @Nullable String description,
        List<UUID> userList,
        Instant lastMessageCreatedAt
) {
}
