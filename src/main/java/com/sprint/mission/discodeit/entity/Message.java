package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private final UUID authorId;
    private final UUID channelId;
    private String content;

    public Message(UUID authorId, UUID channelId, String content) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
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

    public UUID getAuthorId() {
        return authorId;
    }

    public UUID getChannelId() {
        return channelId;
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
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", authorId=").append(authorId);
        sb.append(", channelId=").append(channelId);
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

