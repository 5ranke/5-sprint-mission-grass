package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;

import java.util.*;

public class JCFBinaryContentRepository implements BinaryContentRepository {
    private final Map<UUID, User> data;

    public JCFBinaryContentRepository() {
        data = new HashMap<>();
    }


    @Override
    public BinaryContent save(BinaryContent binaryContent) {
        return null;
    }

    @Override
    public Optional<BinaryContent> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<BinaryContent> findByMessageId(UUID messageId) {
        return List.of();
    }

    @Override
    public List<BinaryContent> findAll() {
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
