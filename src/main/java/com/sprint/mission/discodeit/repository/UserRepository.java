package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID uuid);

    List<User> findAll();

    User delete(UUID uuid);

    boolean existsById(UUID uuid);

    boolean existsByUserid(String userid);
}
