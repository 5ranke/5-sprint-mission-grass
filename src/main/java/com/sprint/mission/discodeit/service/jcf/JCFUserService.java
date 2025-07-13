package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data;

    public JCFUserService(){
        this.data = new HashMap<>();
    }

    @Override
    public void create(User user) {
        data.put(user.getId(), user);
    }

    @Override
    public User read(UUID id) {
        return data.get(id);
    }

    @Override
    public List<User> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String newName) {
        User user = data.get(id);
        user.updateName(newName);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
