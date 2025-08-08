package com.sprint.mission.discodeit.service;


import com.sprint.mission.discodeit.dto.UserStatusCreateDto;
import com.sprint.mission.discodeit.dto.UserStatusViewDto;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {

    UserStatusViewDto create(UserStatusCreateDto dto);

    UserStatusViewDto find(UUID id);

    List<UserStatusViewDto> findAll();

    UserStatusViewDto update(UUID id);

    UserStatusViewDto updateByUserId(UUID userId);

    void delete(UUID id);
}
