package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserCreateDto;
import com.sprint.mission.discodeit.dto.UserUpdateDto;
import com.sprint.mission.discodeit.dto.UserViewDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserViewDto create(UserCreateDto dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new IllegalArgumentException("[!] 중복된 id가 있습니다.");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new IllegalArgumentException("[!] 중복된 email이 있습니다.");
        }

        if (dto.binaryContent() != null) {
            binaryContentRepository.save(dto.binaryContent());
        }

        User user = new User(dto.username(), dto.email(), dto.password(),
                (dto.binaryContent() == null) ? null : dto.binaryContent().getId());
        userRepository.save(user);
        UserStatus userStatus = new UserStatus(user.getId());
        userStatusRepository.save(userStatus);
        return userViewDto(user, userStatus);
    }

    @Override
    public UserViewDto find(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 user가 존재하지 않습니다."));
        UserStatus userStatus = userStatusRepository.findById(id).orElse(null);
        return userViewDto(user, userStatus);
    }

    @Override
    public List<UserViewDto> findAll() {
        List<User> userList = userRepository.findAll();

        List<UserViewDto> userViewDtoList = new ArrayList<>();
        for (User user : userList) {
            UserStatus userStatus = userStatusRepository.findById(user.getId()).orElse(null);
            userViewDtoList.add(userViewDto(user, userStatus));
        }
        return userViewDtoList;
    }

    @Override
    public UserViewDto update(UserUpdateDto dto) {
        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 user가 존재하지 않습니다."));
        if (dto.newBinaryContent() != null) {
            binaryContentRepository.save(dto.newBinaryContent());
        }
        user.update(dto.newUsername(), dto.newEmail(), dto.newPassword(),
                (dto.newBinaryContent() == null) ? null : dto.newBinaryContent().getId());
        userRepository.save(user);
        UserStatus userStatus = userStatusRepository.findById(dto.id()).orElse(null);
        return userViewDto(user, userStatus);
    }

    @Override
    public void delete(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("[!] 해당 user가 존재하지 않습니다."));
        if (user.getProfileId() != null) {
            binaryContentRepository.deleteById(user.getProfileId());
        }
        userStatusRepository.deleteByUserId(id);
        userRepository.deleteById(id);
    }

    private UserViewDto userViewDto(User user, UserStatus userStatus) {
        return new UserViewDto(user.getId(), user.getUsername(),
                user.getEmail(), user.getProfileId(), userStatus != null && userStatus.isOnline());
    }
}
