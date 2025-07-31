package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


    @Override
    public Message create(UUID authorId, UUID channelId, String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("[!] 내용이 null 이거나 비어있을 수 없습니다.");
        }
        Message message = new Message(authorId, channelId, content);
        return messageRepository.save(message);
    }

    @Override
    public Message find(UUID id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 메시지가 존재하지 않습니다."));
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public List<Message> findChannelMessage(UUID channelId) {
        return messageRepository.findChannelMessage(channelId);
    }

    @Override
    public List<Message> findByContent(String token) {
        return messageRepository.findByContent(token);
    }

    @Override
    public Message updateContent(UUID id, UUID requestId, String newContent) {
        Message message = find(id);

        if (!message.getAuthorId().equals(requestId)) {
            throw new IllegalArgumentException("[!] 수정 권한이 없습니다.");
        }
        message.updateContent(newContent);
        return messageRepository.save(message);
    }

    @Override
    public void delete(UUID id, UUID requestId) {
        if (!messageRepository.existsById(id)) {
            throw new NoSuchElementException("[!] 메시지가 존재하지 않습니다.");
        }

        Message message = find(id);
        if (!message.getAuthorId().equals(requestId)) {
            throw new IllegalArgumentException("[!] 삭제 권한이 없습니다.");
        }

        messageRepository.delete(id);
    }
}
