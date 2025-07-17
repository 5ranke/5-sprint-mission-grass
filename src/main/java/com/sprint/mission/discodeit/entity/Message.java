package com.sprint.mission.discodeit.entity;

import java.util.*;

public class Message {
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;
    private final UUID authorUuid;
    private final UUID channelUuid;
    private String content;

    public Message(UUID authorUuid, UUID channelUuid, String content) {
        this.uuid = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.authorUuid = authorUuid;
        this.channelUuid = channelUuid;
        this.content = content;
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

    public UUID getAuthorUuid() {
        return authorUuid;
    }

    public UUID getChannelUuid() {
        return channelUuid;
    }

    public String getContent() {
        return content;
    }

    private void update() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void updateContent(String newContent) {
        this.content = newContent;
        update();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
//        sb.append("uuid=").append(uuid);
//        sb.append(", createdAt=").append(createdAt);
        sb.append("updatedAt=").append(updatedAt);
        sb.append(", authorUuid=").append(authorUuid);
        sb.append(", channelUuid=").append(channelUuid);
        sb.append(", content='").append(content).append('\'');
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(uuid, message.uuid) && Objects.equals(createdAt, message.createdAt) && Objects.equals(updatedAt, message.updatedAt) && Objects.equals(authorUuid, message.authorUuid) && Objects.equals(channelUuid, message.channelUuid) && Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, createdAt, updatedAt, authorUuid, channelUuid, content);
    }
}

