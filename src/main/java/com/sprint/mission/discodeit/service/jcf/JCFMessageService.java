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
    public Message create(UUID authorId, UUID channelId, String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("값이 null 이거나 비어있을 수 없습니다.");
        }

        Message message = new Message(authorId, channelId, content);
        data.put(message.getId(), message);

        return message;
    }

    @Override
    public Message find(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("메시지가 없습니다");
        }
        return data.get(id);
    }

    @Override
    public List<Message> findAll() {
        List<Message> messagesist = new ArrayList<>(data.values());
        if (messagesist.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return messagesist;
    }

    @Override
    public List<Message> findChannelMessage(UUID channelId) {
        List<Message> messageList = new ArrayList<>();
        for (UUID id : data.keySet()) {
            if (data.get(id).getChannelId().equals(channelId)) {
                messageList.add(data.get(id));
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
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getContent().contains(token)) {
                messageList.add(data.get(uuid));
            }
        }
        if (messageList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return messageList;
    }

    @Override
    public Message updateContent(UUID messageId, UUID requestId, String newContent) {
        if (!data.get(messageId).getAuthorId().equals(requestId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        Message message = data.get(messageId);
        message.updateContent(newContent);
        return message;
    }

    @Override
    public void delete(UUID messageId, UUID requestId) {
        if (!data.get(messageId).getAuthorId().equals(requestId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        data.remove(messageId);
    }

}
