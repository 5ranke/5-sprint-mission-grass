package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new HashMap<>();
    }

    @Override
    public void create(Channel channel) {
        data.put(channel.getUuid(), channel);
    }

    @Override
    public Channel read(UUID id) {
        Channel readChannel = data.get(id);
        return readChannel;
    }

    @Override
    public List<User> readMembers(UUID channelUuid) {
        return new ArrayList<>(data.get(channelUuid).getMembers());
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
    public void removeMember(UUID channelId, UUID memberId) {
        data.get(channelId).removeMember(memberId);
    }

    @Override
    public void addMessage(UUID channelId, Message message) {
        data.get(channelId).addMessage(message);
    }

    @Override
    public void removeMessage(UUID channelId, UUID messageId) {
        data.get(channelId).removeMessage(messageId);
    }

    @Override
    public boolean checkName(String name) {
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
