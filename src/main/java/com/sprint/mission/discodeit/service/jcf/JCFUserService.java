package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService() {
        this.data = new HashMap<>();
    }

    @Override
    public User create(String userid, String pw, String name) {
        if (userid == null || pw == null || name == null || userid.isBlank() || pw.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("값이 null 이거나 비어있을 수 없습니다.");
        }
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getUserid().equals(userid)) {
                throw new IllegalArgumentException("id가 중복입니다.");
            }
        }
        User user = new User(userid, pw, name);
        data.put(user.getId(), user);

        return user;
    }


    @Override
    public User find(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException("유저가 없습니다.");
        }
        return data.get(id);
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>(data.values());
        if (userList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return userList;
    }

    @Override
    public User updateName(UUID targetId, UUID requestId, String newName) {
        if (!data.get(requestId).getId().equals(targetId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        User user = data.get(targetId);
        user.updateName(newName);
        return user;
    }

    @Override
    public User updatePw(UUID targetId, UUID requestId, String newPw) {
        if (!data.get(requestId).getId().equals(targetId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        User user = data.get(targetId);
        user.updatePw(newPw);
        return user;
    }

    @Override
    public void delete(UUID targetId, UUID requestId) {
        if (!data.get(requestId).getId().equals(targetId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        data.remove(targetId);
    }
}
