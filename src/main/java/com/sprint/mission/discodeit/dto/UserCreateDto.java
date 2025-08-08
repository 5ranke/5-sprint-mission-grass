package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.annotation.Nullable;
import lombok.Builder;

@Builder
public record UserCreateDto(
        String username,
        String email,
        String password,
        @Nullable BinaryContent binaryContent) {
}
