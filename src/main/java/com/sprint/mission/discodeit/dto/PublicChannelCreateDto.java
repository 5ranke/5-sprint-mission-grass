package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PublicChannelCreateDto (
        ChannelType type,
        @Nullable String name,
        UUID authorId,
        @Nullable String description ){

}
