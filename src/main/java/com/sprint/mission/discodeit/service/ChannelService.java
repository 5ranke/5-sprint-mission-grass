package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(UUID creatorUuid, String name);

    Channel read(UUID channelUuid);

    List<UUID> readMembers(UUID channelUuid);

    List<Channel> readAll();

    List<Channel> searchChannel(String token);

    Channel update(UUID channelUuid, UUID request, String newName);

    Channel delete(UUID channelUuid, UUID request);

    Channel addMember(UUID channelUuid, UUID userUuid, UUID request);

    Channel removeMember(UUID channelUuid, UUID userUuid, UUID request);

}
