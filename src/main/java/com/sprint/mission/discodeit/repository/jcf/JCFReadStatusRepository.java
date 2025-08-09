package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;

import java.util.*;

public class JCFReadStatusRepository implements ReadStatusRepository {
    private final Map<UUID, User> data;

    public JCFReadStatusRepository() {
        data = new HashMap<>();
    }

    @Override
    public ReadStatus save(ReadStatus readStatus) {
        return null;
    }

    @Override
    public Optional<ReadStatus> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ReadStatus> findByChannelId(UUID channelId) {
        return List.of();
    }

    @Override
    public List<ReadStatus> findByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public List<ReadStatus> findAll() {
        return List.of();
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }

    @Override
    public void deleteById(UUID id) {

    }
}
