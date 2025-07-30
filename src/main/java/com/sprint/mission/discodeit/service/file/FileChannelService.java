package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FileChannelService implements ChannelService {

    private final ChannelRepository channelRepository;

    public FileChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel create(UUID creatorId, String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[!] 채널 이름이 null 이거나 비어있을 수 없습니다.");
        }
        if (channelRepository.existsByName(name)) {
            throw new IllegalArgumentException("[!] 중복된 채널 이름이 있습니다.");
        }
        Channel channel = new Channel(creatorId, name);
        return channelRepository.save(channel);
    }

    @Override
    public Channel find(UUID id) {
        return channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 channel이 존재하지 않습니다."));
    }

    @Override
    public List<UUID> findMembers(UUID id) {
        return find(id).getMembers();
    }

    @Override
    public List<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public List<Channel> findChannel(String token) {
        return channelRepository.findChannel(token);
    }

    @Override
    public Channel update(UUID id, UUID requestId, String newName) {
        Channel channel = find(id);

        if (!channel.getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        channel.updateName(newName);
        return channelRepository.save(channel);
    }

    @Override
    public Channel delete(UUID id, UUID requestId) {
        Channel channel = find(id);

        if (!channel.getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        return channelRepository.delete(id);
    }

    @Override
    public Channel addMember(UUID id, UUID memberId, UUID requestId) {
        Channel channel = find(id);

        List<UUID> memberList = findMembers(id);
        for (UUID uuid : memberList) {
            if (uuid.equals(memberId)) {
                throw new IllegalArgumentException("이미 등록된 멤버입니다.");
            }
        }
        if (!channel.getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("멤버 추가 권한이 없습니다.");
        }
        channel.addMember(memberId);
        return channelRepository.save(channel);
    }

    @Override
    public Channel removeMember(UUID id, UUID memberId, UUID requestId) {
        Channel channel = find(id);

        if (!channel.getCreatorId().equals(requestId)) {
            throw new IllegalArgumentException("멤버 삭제 권한이 없습니다.");
        }
        channel.removeMember(memberId);
        return channelRepository.save(channel);
    }


}
