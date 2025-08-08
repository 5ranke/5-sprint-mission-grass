package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusCreateDto;
import com.sprint.mission.discodeit.dto.ReadStatusViewDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Override
    public ReadStatusViewDto create(ReadStatusCreateDto dto) {
        try {
            channelRepository.findById(dto.channelId());
            userRepository.findById(dto.userId());
        } catch (NoSuchElementException e) {
            throw e;
        }
        if(channelRepository.existsById(dto.channelId()) && userRepository.existsById(dto.userId())){
            throw new NoSuchElementException("[!] 이미 존재합니다");
        }
        ReadStatus readStatus = new ReadStatus(dto.userId(), dto.channelId());
        readStatusRepository.save(readStatus);
        return readStatusViewDto(readStatus);
        // 마지막으로 읽은 시간 넘겨줌
    }

    @Override
    public ReadStatusViewDto find(UUID id) {
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 ReadStatus가 존재하지 않습니다."));
        return readStatusViewDto(readStatus);
    }

    @Override
    public List<ReadStatusViewDto> findAllByUserId(UUID userId) {
        List<ReadStatus> readStatusList = readStatusRepository.findByUserId(userId);

        return readStatusList.stream()
                .map(this::readStatusViewDto)
                .collect(Collectors.toList());
    }

    // TODO 업데이트는 어떻게 구현?
    @Override
    public ReadStatusViewDto update(UUID id) {

        ReadStatus readStatus = readStatusRepository.findById(id).orElse(null);

        if (readStatus == null) {
            return null;
        }
        readStatus.updateLastReadAt();
        readStatusRepository.save(readStatus);
        return readStatusViewDto(readStatus);
    }

    @Override
    public void delete(UUID id) {
        readStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 ReadStatus가 존재하지 않습니다."));
        readStatusRepository.deleteById(id);
    }

    private ReadStatusViewDto readStatusViewDto(ReadStatus readStatus) {
        return new ReadStatusViewDto(
                readStatus.getId(),
                readStatus.getUserId(),
                readStatus.getChannelId(),
                readStatus.getLastReadAt()
        );
    }
}
