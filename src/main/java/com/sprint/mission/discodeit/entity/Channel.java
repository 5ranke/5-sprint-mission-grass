package com.sprint.mission.discodeit.entity;

import java.util.*;

public class Channel {
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;
    private String name;
    private final UUID creatorUuid;
    private List<UUID> members;

    public Channel(UUID creatorUuid, String name) {
        this.uuid = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.name = name;
        this.creatorUuid = creatorUuid;
        this.members = new ArrayList<>();
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

    public List<UUID> getMembers() {
        return members;
    }

    public UUID getCreatorUuid() {
        return creatorUuid;
    }

    private void update() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void updateName(String newName) {
        this.name = newName;
        update();
    }

    public void addMember(UUID userUuid) {
        this.members.add(userUuid);
    }

    public void removeMember(UUID userUuid) {
        for (UUID uuid : members) {
            if (uuid.equals(userUuid)) {
                members.remove(userUuid);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
//        sb.append("uuid=").append(uuid);
//        sb.append(", createdAt=").append(createdAt);
        sb.append("updatedAt=").append(updatedAt);
        sb.append(", name='").append(name).append('\'');
        sb.append(", members=").append(members);
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(uuid, channel.uuid) && Objects.equals(createdAt, channel.createdAt) && Objects.equals(updatedAt, channel.updatedAt) && Objects.equals(name, channel.name) && Objects.equals(creatorUuid, channel.creatorUuid) && Objects.equals(members, channel.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, createdAt, updatedAt, name, creatorUuid, members);
    }
}