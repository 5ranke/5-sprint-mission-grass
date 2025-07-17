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
    public Message create(UUID authorUuid, UUID channelUuid, String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("값이 null 이거나 비어있을 수 없습니다.");
        }

        Message message = new Message(authorUuid, channelUuid, content);
        data.put(message.getUuid(), message);

        return message;
    }

    @Override
    public Message read(UUID messageUuid) {
        if (!data.containsKey(messageUuid)) {
            throw new NoSuchElementException("메시지가 없습니다");
        }
        return data.get(messageUuid);
    }

    @Override
    public List<Message> readAll() {
        List<Message> messagesist = new ArrayList<>(data.values());
        if (messagesist.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return messagesist;
    }

    @Override
    public List<Message> readChannelMessage(UUID channelUuid) {
        List<Message> messageList = new ArrayList<>();
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getChannelUuid().equals(channelUuid)) {
                messageList.add(data.get(uuid));
            }
        }

        if (messageList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }

        return messageList;
    }

    @Override
    public List<Message> searchMessage(String token) {
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
    public Message updateContent(UUID messageUuid, UUID request, String newContent) {
        if (!data.get(messageUuid).getAuthorUuid().equals(request)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        Message message = data.get(messageUuid);
        message.updateContent(newContent);
        return message;
    }

    @Override
    public Message delete(UUID messageUuid, UUID request) {
        if (!data.get(messageUuid).getAuthorUuid().equals(request)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        data.remove(messageUuid);
        return null;
    }

}
