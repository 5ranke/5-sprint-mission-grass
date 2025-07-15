package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void create(User user);

    User read(UUID uuid);

    User read(String id);

    List<User> readByKeyword(String keyword);

    List<User> readAll();

    void update(UUID uuid, String newName);

    void delete(UUID uuid);

    boolean checkId(String id);
}