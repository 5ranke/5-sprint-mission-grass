package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileChannelRepository implements ChannelRepository {
    private final String DIRECTORY;
    private final String EXTENSION;

    public FileChannelRepository() {
        this.DIRECTORY = "CHANNEL";
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
    public Channel save(Channel channel) {
        Path path = Paths.get(DIRECTORY, channel.getId() + EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        Channel channel = null;
        Path path = Paths.get(DIRECTORY, id.toString() + EXTENSION);
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            channel = (Channel) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(channel);
    }

    @Override
    public List<Channel> findAll() {
        List<Channel> channelList = new ArrayList<>();

        File directory = new File(DIRECTORY);

        File[] files = directory.listFiles();
        if (files == null) return channelList;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(EXTENSION)) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Channel channel = (Channel) ois.readObject();
                    channelList.add(channel);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return channelList;
    }

    @Override
    public List<Channel> findChannel(String token) {
        List<Channel> channelList = new ArrayList<>();

        File directory = new File(DIRECTORY);
        File[] files = directory.listFiles();

        if (files == null) return channelList;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(EXTENSION)) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Channel channel = (Channel) ois.readObject();

                    if (channel.getName().contains(token)) {
                        channelList.add(channel);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return channelList;
    }

    @Override
    public void delete(UUID id) {
        Path path = Paths.get(DIRECTORY, id.toString() + EXTENSION);

        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByName(String name) {
        File directory = new File(DIRECTORY);
        File[] files = directory.listFiles();

        if (files == null) return false;

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(EXTENSION)) {
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream ois = new ObjectInputStream(fis)) {
                    Channel channel = (Channel) ois.readObject();

                    if (channel.getName().equals(name)) {
                        return true;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    @Override
    public boolean existsById(UUID id) {
        Path path = Paths.get(DIRECTORY, id.toString()+EXTENSION);
        return Files.exists(path);
    }
}
