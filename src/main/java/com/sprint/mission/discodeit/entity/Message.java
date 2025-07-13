package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private final User author;
    private final Channel channel;
    private String content;

    public Message(User author, Channel channel, String content) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.author = author;
        this.channel = channel;
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

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Channel getChannel() {
        return channel;
    }

    private void update() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void updateContent(String newContent) {
        this.content = newContent;
        update();
    }
}
