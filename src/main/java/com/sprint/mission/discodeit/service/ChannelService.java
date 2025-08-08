package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelUpdateDto;
import com.sprint.mission.discodeit.dto.ChannelViewDto;
import com.sprint.mission.discodeit.dto.PrivateChannelCreateDto;
import com.sprint.mission.discodeit.dto.PublicChannelCreateDto;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelViewDto createPublicChannel(PublicChannelCreateDto dto);

    ChannelViewDto createPrivateChannel(PrivateChannelCreateDto dto);

    ChannelViewDto find(UUID id);

//    List<ChannelViewDto> findAll();

    List<ChannelViewDto> findAllByUserId(UUID userId);

    ChannelViewDto update(ChannelUpdateDto dto);

    void delete(UUID id);
}
