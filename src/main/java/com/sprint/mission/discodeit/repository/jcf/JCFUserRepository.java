package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> data;

    public JCFUserRepository() {
        data = new HashMap<>();
    }

    @Override
    public User save(User user) {
        data.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        if (data.containsKey(id)) {
            return Optional.of(data.get(id));
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public User delete(UUID id) {
        if (!data.containsKey(id)) {
            throw new NoSuchElementException(id + "사용자를 찾을 수 없습니다.");
        }
        return data.remove(id);
    }

//    @Override
//    public boolean existsById(UUID id) {
//        return data.containsKey(id);
//    }

    @Override
    public boolean existsByUserid(String userid) {
        for (User user : data.values()) {
            if (user.getUserid().equals(userid)) {
                return true;
            }
        }
        return false;
    }
}
