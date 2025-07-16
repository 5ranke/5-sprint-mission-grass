package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;


public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;

    public JCFMessageService() {
        this.data = new HashMap<>();
    }

    @Override
    public void create(Message message) {
        data.put(message.getUuid(), message);
    }

    @Override
    public Message read(UUID uuid) {
        Message readMessage = data.get(uuid);
        return readMessage;
    }

    @Override
    public List<Message> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Message> searchMessage(String token) {
        List<Message> searchList = new ArrayList<>();
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getContent().contains(token)) {
                searchList.add(data.get(uuid));
            }
        }
        return searchList;
    }

    @Override
    public void updateContent(UUID uuid, String newContent) {
        data.get(uuid).updateContent(newContent);
    }

    @Override
    public void delete(UUID uuid) {
        data.remove(uuid);
    }
}
