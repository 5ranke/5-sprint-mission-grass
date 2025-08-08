package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@Repository
public class FileUserRepository implements UserRepository {
    private final String DIRECTORY;
    private final String EXTENSION;

    public FileUserRepository() {
        this.DIRECTORY = "USER";
        this.EXTENSION = ".ser";
        Path path = Paths.get(DIRECTORY);
        if (!path.toFile().exists()) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Path makePath(UUID id) {
        return Paths.get(DIRECTORY, id.toString() + EXTENSION);
    }

    @Override
    public User save(User user) {
        Path path = makePath(user.getId());
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return user;
    }

    // TODO 공통 로직을 따로 뺄 수 있을 것 같다
    @Override
    public Optional<User> findById(UUID id) {
        Path path = makePath(id);
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return Optional.ofNullable((User) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        File[] fileList = new File(DIRECTORY).listFiles();
        if (fileList == null) {
            return Optional.empty();
        }

        for (File file : fileList) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                User user = (User) ois.readObject();
                if(user.getUsername().equals(username)){
                    return Optional.of(user);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();

        File[] fileList = new File(DIRECTORY).listFiles();
        if (fileList == null) {
            return userList;
        }

        for (File file : fileList) {
            try (FileInputStream fis = new FileInputStream(file);
                 ObjectInputStream ois = new ObjectInputStream(fis)) {
                userList.add((User) ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return userList;
    }


    @Override
    public boolean existsById(UUID id) {
        Path path = makePath(id);
        return Files.exists(path);
    }

    @Override
    public boolean existsByUsername(String username) {
        return existsByCondition(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return existsByCondition(user -> user.getEmail().equals(email));
    }

    private boolean existsByCondition(Predicate<User> condition) {
        File[] fileList = new File(DIRECTORY).listFiles();
        if (fileList == null) {
            return false;
        }

        for (File file : fileList) {
            try (
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis)
            ) {
                User user = (User) ois.readObject();
                if (condition.test(user)) {
                    return true;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void deleteById(UUID id) {
        Path path = makePath(id);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
