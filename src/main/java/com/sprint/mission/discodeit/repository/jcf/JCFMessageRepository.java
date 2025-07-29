package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> data;

    public JCFMessageRepository() {
        data = new HashMap<>();
    }

    @Override
    public Message save(Message message) {
        data.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Message> findChannelMessage(UUID channelId) {
        List<Message> messageList = new ArrayList<>();
        for (Message message : data.values()) {
            if (message.getChannelId().equals(channelId)) {
                messageList.add(message);
            }
        }

        if (messageList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }

        return messageList;
    }

    @Override
    public List<Message> findByContent(String token) {
        List<Message> messageList = new ArrayList<>();
        for (Message message : data.values()) {
            if (message.getContent().contains(token)) {
                messageList.add(message);
            }
        }

        if (messageList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }

        return messageList;
    }

    @Override
    public Message delete(UUID id) {
        return data.remove(id);
    }
}
