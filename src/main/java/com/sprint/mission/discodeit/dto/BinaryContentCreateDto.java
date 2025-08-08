package com.sprint.mission.discodeit.dto;

import lombok.Builder;

@Builder
public record BinaryContentCreateDto(
        String fileName,
        String contentType,
        Long size,
        byte[] bytes
) {
}
