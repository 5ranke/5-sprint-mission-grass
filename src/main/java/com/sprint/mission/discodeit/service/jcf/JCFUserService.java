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
    public void create(User user) {
        data.put(user.getUuid(), user);
    }


    @Override
    public User read(UUID uuid) {
        User readUser = data.get(uuid);
        return readUser;
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<User> findByNameOrEmail(String token) {
        List<User> userList = new ArrayList<>();
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getId().contains(token) || data.get(uuid).getName().contains(token)) {
                userList.add(data.get(uuid));
            }
        }
        if (userList.isEmpty()) {
            return null;
        }
        return userList;
    }

    @Override
    public void updateName(UUID uuid, String newName) {
        data.get(uuid).updateName(newName);
    }

    @Override
    public void updatePw(UUID uuid, String newPw) {
        data.get(uuid).updatePw(newPw);
    }

    @Override
    public void delete(UUID uuid) {
        data.remove(uuid);
    }

    @Override
    public boolean checkId(String inputId) { // 중복 체크
        for (User user : data.values()) {
            if (user.getId().equals(inputId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User login(String loginId, String loginPw) {
        for (User user : data.values()) {
            if (user.getId().equals(loginId) && user.getId().equals(loginPw)) {
                return user;
            }
        }
        return null;
    }
}
