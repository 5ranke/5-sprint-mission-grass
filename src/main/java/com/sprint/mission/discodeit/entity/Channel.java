package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Channel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private String name;
    private final UUID creatorId;
    private List<UUID> members;

    public Channel(UUID creatorId, String name) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.name = name;
        this.creatorId = creatorId;
        this.members = new ArrayList<>();

        members.add(creatorId);
    }

    public UUID getId() {
        return id;
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

    public List<UUID> getMembers() {
        return members;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    private void update() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void updateName(String newName) {
        this.name = newName;
        update();
    }

    public void addMember(UUID id) {
        this.members.add(id);
    }

    public void removeMember(UUID id) {
        members.remove(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", name='").append(name).append('\'');
        sb.append(", creatorId=").append(creatorId);
        sb.append(", members=").append(members);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) && Objects.equals(createdAt, channel.createdAt) && Objects.equals(updatedAt, channel.updatedAt) && Objects.equals(name, channel.name) && Objects.equals(creatorId, channel.creatorId) && Objects.equals(members, channel.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, name, creatorId, members);
    }
}