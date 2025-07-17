package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User create(String id, String pw, String name);

    User read(UUID uuid);

    List<User> readAll();

    List<User> searchByNameOrEmail(String token);

    User updateName(UUID target, UUID request, String newName);

    User updatePw(UUID target, UUID request, String newPw);

    User delete(UUID target, UUID request);
}