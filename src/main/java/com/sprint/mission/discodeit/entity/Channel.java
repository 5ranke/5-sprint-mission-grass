package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel {
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;
    private String name;
    private List<User> members;
    private List<Message> messages;

    public Channel(String name) {
        this.uuid = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.name = name;
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    private void update() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void updateName(String newName) {
        this.name = newName;
        update();
    }

    public void addMember(User user) {
        this.members.add(user);
    }

    public void removeMember(UUID id) {
        members.removeIf(member -> member.getId().equals(id));
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void removeMessage(UUID id) {
        messages.removeIf(message -> message.getId().equals(id));

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("- ").append(name);
        sb.append(" (멤버: ").append(members);
        sb.append(", 메시지: ").append(messages).append(")");
        return sb.toString();
    }
}