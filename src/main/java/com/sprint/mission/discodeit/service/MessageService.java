package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message create(UUID authorUuid, UUID channelUuid, String content);

    Message read(UUID messageUuid);

    List<Message> readAll();

    List<Message> readChannelMessage(UUID channelUuid);

    List<Message> searchMessage(String token);

    Message updateContent(UUID messageUuid, UUID request, String newContent);

    Message delete(UUID messageUuid, UUID request);
}
