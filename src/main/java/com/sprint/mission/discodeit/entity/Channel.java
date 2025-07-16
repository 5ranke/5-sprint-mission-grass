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
        for (User user : members) {
            if(user.getUuid().equals(id)){
                members.remove(user);
            }
        }
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void removeMessage(UUID id) {
        for (Message message : messages) {
            if(message.getUuid().equals(id)){
                messages.remove(message);
            }
        }
    }

    public String channelInfo() {
        return "[%s]\n- 멤버: %s\n- 메시지 : %s\n".formatted(name, members, messages);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
        sb.append("uuid=").append(uuid);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", name='").append(name).append('\'');
        sb.append(", members=").append(members);
        sb.append(", messages=").append(messages);
        sb.append('}');
        return sb.toString();
    }
}