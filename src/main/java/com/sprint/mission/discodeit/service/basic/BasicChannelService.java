package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelUpdateDto;
import com.sprint.mission.discodeit.dto.ChannelViewDto;
import com.sprint.mission.discodeit.dto.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.PublicChannelCreateDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;

    @Override
    public ChannelViewDto createPublicChannel(PublicChannelCreateDto dto) {
        try {
            userRepository.findById(dto.authorId());
        } catch (NoSuchElementException e) {
            throw e;
        }
        Channel channel = new Channel(ChannelType.PUBLIC,
                dto.name(),
                dto.authorId(),
                dto.description(),
                Collections.emptyList());
        channel = channelRepository.save(channel);

        return channelViewDto(channel);
    }

    @Override
    public ChannelViewDto createPrivateChannel(PrivateChannelCreateDto dto) {
        try {
            userRepository.findById(dto.authorId());
        } catch (NoSuchElementException e) {
            throw e;
        }
        Channel channel = new Channel(ChannelType.PRIVATE,
                null,
                dto.authorId(),
                null,
                dto.userIds());
        channel = channelRepository.save(channel);

        for (UUID userId : dto.userIds()) {
            ReadStatus readStatus = new ReadStatus(userId, channel.getId());
            readStatus.updateLastReadAt();
            readStatusRepository.save(readStatus);
        }

        return channelViewDto(channel);
    }

    private Instant getLastMessageCreateAt(UUID channelId) {
        List<Message> messaggeList = messageRepository.findByChannelId(channelId);
        if(messaggeList.isEmpty()) {
            return null;
        }
        return messaggeList.stream().map(Message::getCreatedAt)
                .max(Instant::compareTo)
                .orElse(null);
    }

    @Override
    public ChannelViewDto find(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 channel이 존재하지 않습니다."));
        return channelViewDto(channel);
    }

    @Override
    public List<ChannelViewDto> findAllByUserId(UUID userId) {
        // 1) userId 검증 (사용자 존재 확인)
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("[!] 사용자가 존재하지 않습니다."));

        // 2) PUBLIC 채널 전체 조회
        List<Channel> channelList = channelRepository.findByType(ChannelType.PUBLIC);

        // 3) PRIVATE 채널 중 userId가 속한 채널만 조회
        List<Channel> privateChannels = channelRepository.findByType(ChannelType.PRIVATE);
        for (Channel privateChannel : privateChannels) {
            if(privateChannel.getUserList().contains(userId)){
                channelList.add(privateChannel);
            }
        }

        // 5) DTO 변환 후 반환
        return channelList.stream()
                .map(this::channelViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public ChannelViewDto update(ChannelUpdateDto dto) {
        Channel channel = channelRepository.findById(dto.id())
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 channel이 존재하지 않습니다."));
        if(channel.getType() == ChannelType.PRIVATE){
            throw new IllegalArgumentException("[!] private 채널은 수정불가합니다.");
        }
        if(dto.userId() != null) {
            userRepository.findById(dto.userId())
                    .orElseThrow(() -> new NoSuchElementException("[!] 사용자가 존재하지 않습니다."));
        }
        channel.update(dto.newName(),dto.newDescription(),dto.userId());
        channelRepository.save(channel);
        return channelViewDto(channel);
    }

    @Override
    public void delete(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 channel이 존재하지 않습니다."));
        channelRepository.deleteById(id);

        messageRepository.findByChannelId(id).
                forEach(m -> {
                    messageRepository.deleteById(m.getId());
        });

        channel.getUserList().forEach(userId -> {
            readStatusRepository.findByChannelId(id)
                    .forEach(rs -> {
                        readStatusRepository.deleteById(rs.getId());
                    });
        });
    }

    private ChannelViewDto channelViewDto(Channel channel) {
        Instant lastAt = getLastMessageCreateAt(channel.getId());
        List<UUID> participants = Collections.emptyList();
        if (channel.getType() == ChannelType.PRIVATE) {
            participants = readStatusRepository.findByChannelId(channel.getId())
                    .stream()
                    .map(ReadStatus::getUserId)
                    .collect(Collectors.toList());
        }

        return new ChannelViewDto(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getAuthorId(),
                channel.getDescription(),
                participants,
                lastAt
        );
    }
}