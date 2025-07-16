package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    void create(Channel channel);

    Channel read(UUID id);

    List<User> readMembers(UUID channelId);

    List<Channel> readAll();

    void update(UUID id, String newName);

    void delete(UUID id);

    void addMember(UUID channelId, User user);

    void removeMember(UUID channelId, UUID memberId);

    void addMessage(UUID channelId, Message message);

    void removeMessage(UUID channelId, UUID messageId);

    boolean checkName(String name);
}
