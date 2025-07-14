package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;


    public JCFChannelService() {
        this.data = new HashMap<>();
    }

    @Override
    public void create(Channel channel) {
        data.put(channel.getId(), channel);
    }

    @Override
    public Channel read(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String newName) {
        data.get(id).updateName(newName);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }

    @Override
    public void addMember(UUID channelId, User user) {
        data.get(channelId).addMember(user);

    }

    @Override
    public void removeMember(UUID channelId, UUID userId) {
        data.get(channelId).removeMember(userId);
    }
}
