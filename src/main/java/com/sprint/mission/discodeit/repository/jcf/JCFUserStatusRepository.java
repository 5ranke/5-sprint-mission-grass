package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;

import java.util.*;

public class JCFUserStatusRepository implements UserStatusRepository {
    private final Map<UUID, User> data;

    public JCFUserStatusRepository() {
        data = new HashMap<>();
    }

    @Override
    public UserStatus save(UserStatus userStatus) {
        return null;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        return Optional.empty();
    }

    @Override
    public List<UserStatus> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return false;
    }

    @Override
    public void deleteById(UUID id) {

    }

    @Override
    public void deleteByUserId(UUID userId) {

    }
}
