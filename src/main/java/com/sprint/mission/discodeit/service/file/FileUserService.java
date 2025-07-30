package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FileUserService implements UserService {

    private final UserRepository userRepository;

    public FileUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(String userid, String pw, String name) {
        if (userid == null || userid.isBlank()) {
            throw new IllegalArgumentException("[!] ID가 null 이거나 비어있을 수 없습니다.");
        }
        if (pw == null || pw.isBlank()) {
            throw new IllegalArgumentException("[!] PW가 null 이거나 비어있을 수 없습니다.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("[!] 이름이 null 이거나 비어있을 수 없습니다.");
        }

        if (userRepository.existsByUserid(userid)) {
            throw new IllegalArgumentException("[!] 중복된 userid가 있습니다.");
        }

        User user = new User(userid, pw, name);
        return userRepository.save(user);
    }

    @Override
    public User find(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 user가 존재하지 않습니다."));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User updateName(UUID targetId, UUID requestId, String newName) {
        if (!targetId.equals(requestId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        User user = find(targetId);
        user.updateName(newName);
        return userRepository.save(user);
    }

    @Override
    public User updatePw(UUID targetId, UUID requestId, String newPw) {
        if (!targetId.equals(requestId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        User user = find(targetId);
        user.updatePw(newPw);
        return userRepository.save(user);
    }

    @Override
    public User delete(UUID targetId, UUID requestId) {
        if (!targetId.equals(requestId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        return userRepository.delete(targetId);
    }

}
