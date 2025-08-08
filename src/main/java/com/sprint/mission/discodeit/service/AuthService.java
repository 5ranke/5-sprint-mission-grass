package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserViewDto;

public interface AuthService {
    public UserViewDto login(String username, String password);
}
