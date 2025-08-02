package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(UUID authorId, UUID channelId, String content);

    Message find(UUID id);

    List<Message> findAll();

    List<Message> SearchByContent(String token);

    Message update(UUID id, UUID requestId, String newContent);

    void delete(UUID id, UUID requestId);
}
