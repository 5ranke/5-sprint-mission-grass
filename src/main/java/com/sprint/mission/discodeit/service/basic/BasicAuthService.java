package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserViewDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    public final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserViewDto login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("[!] 존재하지 않는 id 입니다."));
        if(!user.getPassword().equals(password)) {
            throw new RuntimeException("[!] 비밀번호가 일치하지 않습니다.");
        }
        // 로그인 성공
        UserStatus userStatus = userStatusRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("[!] UserStatus가 존재하지 않습니다."));
        userStatus.updateLoginFlag(); // 로그인 플래그 체크
        userStatusRepository.save(userStatus);
        return new UserViewDto(user.getId(),user.getUsername(),
                user.getEmail(),user.getProfileId(),userStatus.isOnline());
    }
}
