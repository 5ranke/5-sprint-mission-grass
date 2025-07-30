package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileMessageRepository implements MessageRepository {
    private final String DIRECTORY;
    private final String EXTENSION;

    public FileMessageRepository() {
        this.DIRECTORY = "MESSAGE";
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


    @Override
    public Message save(Message message) {
        Path path = Paths.get(DIRECTORY, message.getId() + EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        Message message = null;
        Path path = Paths.get(DIRECTORY, id.toString() + EXTENSION);
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            message = (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(message);
    }

    @Override
    public List<Message> findAll() {
        List<Message> messageList = new ArrayList<>();

        File directory = new File(DIRECTORY);

        File[] files = directory.listFiles();
        if (files == null) return messageList;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(EXTENSION)) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Message message = (Message) ois.readObject();
                    messageList.add(message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return messageList;
    }

    @Override
    public List<Message> findChannelMessage(UUID channelId) {
        List<Message> messageList = new ArrayList<>();

        File directory = new File(DIRECTORY);
        File[] files = directory.listFiles();

        if (files == null) return messageList;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(EXTENSION)) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Message message = (Message) ois.readObject();

                    if (message.getChannelId().equals(channelId)) {
                        messageList.add(message);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return messageList;
    }

    @Override
    public List<Message> findByContent(String token) {
        List<Message> messageList = new ArrayList<>();

        File directory = new File(DIRECTORY);
        File[] files = directory.listFiles();

        if (files == null) return messageList;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(EXTENSION)) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Message message = (Message) ois.readObject();

                    if (message.getContent().contains(token)) {
                        messageList.add(message);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return messageList;
    }

    @Override
    public Message delete(UUID id) {
        Message message;
        Path path = Paths.get(DIRECTORY, id.toString() + EXTENSION);
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            message = (Message) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return message;
    }
}
