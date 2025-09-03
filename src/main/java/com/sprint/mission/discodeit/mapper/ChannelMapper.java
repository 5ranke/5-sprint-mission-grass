package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public abstract class ChannelMapper {
//    UUID id,
//    ChannelType type,
//    String name,
//    String description,
//    List<UserDto> participants,
//    Instant lastMessageAt

    private MessageRepository messageRepository;
    private ReadStatusRepository readStatusRepository;
    private UserMapper userMapper;

    @Mapping(target = "participants", expression = "java(getParticipants(channel)")
    @Mapping(target = "lastMessageAt", expression = "java(getLastMessageAt(channel))")
    public abstract ChannelDto toDto(Channel channel);

    private List<UserDto> getParticipants(Channel channel) {
        List<UserDto> participants = new ArrayList<>();
        readStatusRepository.findAllByChannelId(channel.getId())
                .stream().map(ReadStatus::getUser)
                .map(userMapper::toDto).forEach(participants::add);
        return participants;
    }

    private Instant getLastMessageAt(Channel channel) {
        return messageRepository.findLastMessageAtByChannelId(channel.getId())
                .orElse(channel.getCreatedAt());
    }

}
