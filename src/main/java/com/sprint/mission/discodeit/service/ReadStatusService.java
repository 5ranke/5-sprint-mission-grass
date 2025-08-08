package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.ReadStatusCreateDto;
import com.sprint.mission.discodeit.dto.ReadStatusViewDto;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatusViewDto create(ReadStatusCreateDto dto);

    ReadStatusViewDto find(UUID id);

    List<ReadStatusViewDto> findAllByUserId(UUID userId);

    ReadStatusViewDto update(UUID id);

    void delete(UUID id);
}
