package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void create(User user);

    User read(UUID uuid);

    List<User> readAll();

    List<User> findByNameOrEmail(String token);

    void updateName(UUID uuid, String newName);

    void updatePw(UUID uuid, String newPw);

    void delete(UUID uuid);

    boolean checkId(String id);

    User login(String id, String pw);
}