package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;
import jakarta.annotation.Nullable;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserUpdateDto(
        UUID id,
        String newUsername,
        String newEmail,
        String newPassword,
        @Nullable BinaryContent newBinaryContent) {
}
