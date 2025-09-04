package com.sprint.mission.discodeit.dto.response;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int number,
        int size,
        boolean hasNext,
        Long totalElements // T 데이터 총 개수 (null가능)
) {
}