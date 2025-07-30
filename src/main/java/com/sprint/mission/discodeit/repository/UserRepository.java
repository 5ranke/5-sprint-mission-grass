package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    User save(User user);

    Optional<User> findById(UUID id);

    List<User> findAll();

    User delete(UUID id);

//    boolean existsById(UUID id); // 얜 왜 만들었지?

    boolean existsByUserid(String userid);
}
