package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new HashMap<>();
    }

    @Override
    public Channel create(UUID creatorUuid, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("값이 null 이거나 비어있을 수 없습니다.");
        }
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getName().equals(name)) {
                throw new IllegalArgumentException("이름이 중복입니다.");
            }
        }
        Channel channel = new Channel(creatorUuid, name);
        data.put(channel.getUuid(), channel);
        channel.addMember(creatorUuid);

        return channel;
    }

    @Override
    public Channel find(UUID channelUuid) {
        if (!data.containsKey(channelUuid)) {
            throw new NoSuchElementException("채널이 없습니다.");
        }
        return data.get(channelUuid);
    }

    @Override
    public List<UUID> findMembers(UUID channelUuid) {
        List<UUID> memberList = new ArrayList<>(data.get(channelUuid).getMembers());
        if (memberList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return memberList;
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channelList = new ArrayList<>(data.values());
        if (channelList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return channelList;
    }

    public List<Channel> findChannel(String token) {
        List<Channel> channelList = new ArrayList<>();
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getName().contains(token)) {
                channelList.add(data.get(uuid));
            }
        }
        if (channelList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return channelList;
    }

    @Override
    public Channel update(UUID channelUuid, UUID request, String newName) {
        if (!data.get(channelUuid).getCreatorUuid().equals(request)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        Channel channel = data.get(channelUuid);
        channel.updateName(newName);
        return channel;
    }

    @Override
    public Channel delete(UUID channelUuid, UUID request) {
        if (!data.get(channelUuid).getCreatorUuid().equals(request)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        data.remove(channelUuid);
        return null;
    }

    @Override
    public Channel addMember(UUID channelUuid, UUID userUuid, UUID request) {
        if (!data.get(channelUuid).getCreatorUuid().equals(request)) {
            throw new IllegalArgumentException("멤버 추가 권한이 없습니다.");
        }

        Channel channel = data.get(channelUuid);

        for (UUID uuid : channel.getMembers()) {
            if (uuid.equals(userUuid)) {
                throw new IllegalArgumentException("이미 등록된 멤버입니다.");
            }
        }

        channel.addMember(userUuid);
        return channel;
    }

    @Override
    public Channel removeMember(UUID channelUuid, UUID userUuid, UUID request) {
        if (!data.get(channelUuid).getCreatorUuid().equals(request)) {
            throw new IllegalArgumentException("멤버 삭제 권한이 없습니다.");
        }

        Channel channel = data.get(channelUuid);
        channel.removeMember(userUuid);
        return channel;
    }
}
