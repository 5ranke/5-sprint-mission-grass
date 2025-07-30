package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data;

    public JCFChannelRepository() {
        data = new HashMap<>();
    }

    @Override
    public Channel save(Channel channel) {
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Channel> findChannel(String token) {
        List<Channel> channelList = new ArrayList<>();
        for (Channel channel : data.values()) {
            if (channel.getName().contains(token)) {
                channelList.add(channel);
            }
        }

        if (channelList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }

        return channelList;
    }

    @Override
    public Channel delete(UUID id) {
        return data.remove(id);
    }

    @Override
    public boolean existsByName(String name) {
        for (Channel channel : data.values()) {
            if (channel.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
