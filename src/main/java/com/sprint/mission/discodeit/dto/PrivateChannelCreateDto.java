package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record PrivateChannelCreateDto(
        ChannelType type,
        UUID authorId,
        List<UUID> userIds){
}
