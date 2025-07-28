package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(String userid, String pw, String name);

    User find(UUID id);

    List<User> findAll();

    User updateName(UUID targetId, UUID requestId, String newName);

    User updatePw(UUID targetId, UUID requestId, String newPw);

    User delete(UUID targetId, UUID requestId);
}