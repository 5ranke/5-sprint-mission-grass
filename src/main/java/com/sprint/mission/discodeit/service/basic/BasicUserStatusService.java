package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusCreateDto;
import com.sprint.mission.discodeit.dto.UserStatusViewDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserService userService;

    @Override
    public UserStatusViewDto create(UserStatusCreateDto dto) {
        try {
            userRepository.findById(dto.userId());
        } catch (NoSuchElementException e) {
            throw e;
        }
        if(userStatusRepository.existsByUserId(dto.userId())) {
            throw new IllegalArgumentException("[!] 이미 존재하는 UserStatus 입니다.");
        }
        UserStatus userStatus = new UserStatus(dto.userId());
        userStatusRepository.save(userStatus);
        return userStatusViewDto(userStatus);
    }

    @Override
    public UserStatusViewDto find(UUID id) {
        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 UserStatus가 존재하지 않습니다."));
        return userStatusViewDto(userStatus);
    }

    @Override
    public List<UserStatusViewDto> findAll() {
        List<UserStatus> userStatusList = userStatusRepository.findAll();

        return userStatusList.stream()
                .map(this::userStatusViewDto)
                .collect(Collectors.toList());
    }

    // TODO 업데이트 수정해야할 듯
    @Override
    public UserStatusViewDto update(UUID id) {
        UserStatus userStatus = userStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 UserStatus가 존재하지 않습니다."));
        userStatus.update();
        userStatusRepository.save(userStatus);
        return userStatusViewDto(userStatus);
    }

    @Override
        public UserStatusViewDto updateByUserId(UUID userId) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 UserStatus가 존재하지 않습니다."));
        userStatus.update();
        userStatusRepository.save(userStatus);
        return userStatusViewDto(userStatus);
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 ReadStatus가 존재하지 않습니다."));
        userStatusRepository.deleteById(id);

    }
    
    private UserStatusViewDto userStatusViewDto(UserStatus userStatus) {
        return new UserStatusViewDto(
                userStatus.getId(),
                userStatus.getUserId()
        );
    }
}
