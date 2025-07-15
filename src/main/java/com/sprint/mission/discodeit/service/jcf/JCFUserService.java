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

    public User read(String id) {
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getId().equals(id)) {
                return data.get(uuid);
            }
        }
        return null;
    }

    public List<User> readByKeyword(String keyword) {
        List<User> userList = new ArrayList<>();
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getId().contains(keyword) || data.get(uuid).getName().contains(keyword)) {
                userList.add(data.get(uuid));
            }
        }
        if (userList.isEmpty()) {
            return null;
        }
        return userList;
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID uuid, String newName) {
        data.get(uuid).updateName(newName);
    }

    @Override
    public void delete(UUID uuid) {
        data.remove(uuid);
    }

    @Override
    public boolean checkId(String id) { // 중복 체크
        for (UUID uuid : data.keySet()) {
            if (data.get(uuid).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
