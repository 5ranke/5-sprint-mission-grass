package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record MessageCreateDto(
        UUID authorId,
        UUID channelId,
        String content,
        List<BinaryContent> binaryContents) {
}
