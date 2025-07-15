package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User {
    private final UUID uuid;
    private final Long createdAt;
    private Long updatedAt;
    private final String id;
    private String pw;
    private String name;


    public User(String id, String pw, String name) {
        this.uuid = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = createdAt;
        this.id = id;
        this.pw = pw;
        this.name = name;
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

    public String getId() {
        return id;
    }

    public String getPw() {
        return pw;
    }

    public String getName() {
        return name;
    }

    private void update() {
        this.updatedAt = System.currentTimeMillis();
    }

    public void updateName(String newName) {
        this.name = newName;
        update();
    }

    public void updatePw(String newPw) {
        this.pw = newPw;
        update();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("[").append(name);
        sb.append("님 (id: ").append(id);
        sb.append(", pw: ").append(pw).append(")]");
        return sb.toString();
    }
}
