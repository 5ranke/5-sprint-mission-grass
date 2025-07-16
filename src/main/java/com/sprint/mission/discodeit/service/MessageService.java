package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    void create(Message message);

    Message read(UUID id);

    List<Message> searchMessage(String token);

    List<Message> readAll();

    void updateContent(UUID id, String newContent);

    void delete(UUID id);
}
