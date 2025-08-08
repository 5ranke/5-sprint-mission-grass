package com.sprint.mission.discodeit.dto;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record MessageViewDto(
        UUID id,
        UUID authorId,
        UUID channelId,
        String content,
        List<UUID> attachmentIds
) {
}
