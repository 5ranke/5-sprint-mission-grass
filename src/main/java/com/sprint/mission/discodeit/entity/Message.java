package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;
    private final UUID authorId;
    private final UUID channelId;
    private String content;

    public Message(UUID authorId, UUID channelId, String content) {
        this.uuid = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
    }

    public UUID getId() {
        return uuid;
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
        final StringBuilder sb = new StringBuilder();
        sb.append("[").append(content).append("]");
        return sb.toString();
    }
    //TODO : 출력시 작성자 이름 또는 아이디 함께
}

