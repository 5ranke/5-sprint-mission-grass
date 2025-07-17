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
    public User create(String id, String pw, String name) {
        if (id == null || pw == null || name == null || id.isBlank() || pw.isBlank() || name.isBlank()) {
            throw new IllegalArgumentException("값이 null 이거나 비어있을 수 없습니다.");
        }
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getId().equals(id)) {
                throw new IllegalArgumentException("id가 중복입니다.");
            }
        }
        User user = new User(id, pw, name);
        data.put(user.getUuid(), user);

        return user;
    }


    @Override
    public User read(UUID uuid) {
        if (!data.containsKey(uuid)) {
            throw new NoSuchElementException("유저가 없습니다.");
        }
        return data.get(uuid);
    }

    @Override
    public List<User> readAll() {
        List<User> userList = new ArrayList<>(data.values());
        if (userList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return userList;
    }

    @Override
    public List<User> searchByNameOrEmail(String token) {
        List<User> userList = new ArrayList<>();
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getId().contains(token) || data.get(uuid).getName().contains(token)) {
                userList.add(data.get(uuid));
            }
        }
        if (userList.isEmpty()) {
            throw new NoSuchElementException("결과가 없습니다.");
        }
        return userList;
    }

    @Override
    public User updateName(UUID target, UUID request, String newName) {
        if (!data.get(request).getUuid().equals(target)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        User user = data.get(target);
        user.updateName(newName);
        return user;
    }

    @Override
    public User updatePw(UUID target, UUID request, String newPw) {
        if (!data.get(request).getUuid().equals(target)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        User user = data.get(target);
        user.updatePw(newPw);
        return user;
    }

    @Override
    public User delete(UUID target, UUID request) {
        if (!data.get(request).getUuid().equals(target)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        data.remove(target);
        return null;
    }
}
