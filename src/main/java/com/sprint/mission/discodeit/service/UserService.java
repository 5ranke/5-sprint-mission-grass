package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserCreateDto;
import com.sprint.mission.discodeit.dto.UserUpdateDto;
import com.sprint.mission.discodeit.dto.UserViewDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserViewDto create(UserCreateDto dto);

    UserViewDto find(UUID id);

    List<UserViewDto> findAll();

    UserViewDto update(UserUpdateDto dto);

    void delete(UUID id);
}