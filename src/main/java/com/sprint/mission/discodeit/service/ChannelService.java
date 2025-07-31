package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel create(UUID creatorId, String name);

    Channel find(UUID id);

    List<UUID> findMembers(UUID id);

    List<Channel> findAll();

    List<Channel> findChannel(String token);

    Channel update(UUID id, UUID requestId, String newName);

    void delete(UUID id, UUID requestId);

    Channel addMember(UUID id, UUID memberId, UUID requestId);

    Channel removeMember(UUID id, UUID memberId, UUID requestId);
}
