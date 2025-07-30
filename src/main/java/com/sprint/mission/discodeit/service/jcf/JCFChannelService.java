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
    public Channel create(UUID creatorId, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("값이 null 이거나 비어있을 수 없습니다.");
        }
        for (UUID id : data.keySet()) {
            if (data.get(id).getName().equals(name)) {
                throw new IllegalArgumentException("이름이 중복입니다.");
            }
        }
        Channel channel = new Channel(creatorId, name);
        data.put(channel.getId(), channel);
        channel.addMember(creatorId);

        return channel;
    }

    @Override
    public Channel find(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("채널이 없습니다.");
        }
        return data.get(id);
    }

    @Override
    public List<UUID> findMembers(UUID id) {
        List<UUID> memberList = new ArrayList<>(data.get(id).getMembers());
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
        for (UUID id : data.keySet()) {
            if (data.get(id).getName().contains(token)) {
                channelList.add(data.get(id));
            }
        }
        if (channelList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return channelList;
    }

    @Override
    public Channel update(UUID id, UUID requestId, String newName) {
        if (!data.get(id).getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        Channel channel = data.get(id);
        channel.updateName(newName);
        return channel;
    }

    @Override
    public Channel delete(UUID id, UUID requestId) {
        if (!data.get(id).getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        data.remove(id);
        return null;
    }

    @Override
    public Channel addMember(UUID id, UUID memberId, UUID requestId) {
        if (!data.get(id).getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("멤버 추가 권한이 없습니다.");
        }

        Channel channel = data.get(id);

        for (UUID u : channel.getMembers()) {
            if (u.equals(memberId)) {
                throw new IllegalArgumentException("이미 등록된 멤버입니다.");
            }
        }

        channel.addMember(memberId);
        return channel;
    }

    @Override
    public Channel removeMember(UUID id, UUID memberId, UUID requestId) {
        if (!data.get(id).getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("멤버 삭제 권한이 없습니다.");
        }

        Channel channel = data.get(id);
        channel.removeMember(memberId);
        return channel;
    }
}
