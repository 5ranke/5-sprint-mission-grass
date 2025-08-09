package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageCreateDto;
import com.sprint.mission.discodeit.dto.MessageUpdateDto;
import com.sprint.mission.discodeit.dto.MessageViewDto;
import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public MessageViewDto create(MessageCreateDto dto) {
        try {
            channelRepository.findById(dto.channelId());
            userRepository.findById(dto.authorId());
        } catch (NoSuchElementException e) {
            throw e;
        }
        List<UUID> attachmentList = new ArrayList<>();
        if(dto.binaryContents() != null) {
            for (BinaryContent binaryContent : dto.binaryContents()) {
                binaryContentRepository.save(binaryContent);
                attachmentList.add(binaryContent.getId());
            }
        }
        Message message = new Message(dto.authorId(), dto.channelId(), dto.content(), attachmentList);

        messageRepository.save(message);
        return messageViewDto(message);
    }

    @Override
    public MessageViewDto find(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 message가 존재하지 않습니다."));
        return messageViewDto(message);
    }

    @Override
    public List<MessageViewDto> findAllByChannelId(UUID channelId) {
        List<Message> messageList = messageRepository.findByChannelId(channelId);

        return messageList.stream()
                .map(this::messageViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageViewDto update(MessageUpdateDto dto) { // BinaryContents로 들어옴
        Message message = messageRepository.findById(dto.id())
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 message가 존재하지 않습니다."));

        List<UUID> newAttachmentList = new ArrayList<>();
        if(dto.newBinaryContents() != null) {
            for (BinaryContent binaryContent : dto.newBinaryContents()) {
                binaryContentRepository.save(binaryContent);
                newAttachmentList.add(binaryContent.getId());
            }
        }
        message.update(dto.newContent(), newAttachmentList);
        messageRepository.save(message);
        return messageViewDto(message);
    }

    @Override
    public void delete(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 message가 존재하지 않습니다."));
        for (UUID attachmentId : message.getAttachmentIds()) {
            binaryContentRepository.deleteById(attachmentId);
        }
        messageRepository.deleteById(id);
    }

    private MessageViewDto messageViewDto(Message message) {
        return new MessageViewDto(
                message.getId(),
                message.getAuthorId(),
                message.getChannelId(),
                message.getContent(),
                message.getAttachmentIds()
        );
    }
}
