package com.sprint.mission.discodeit.entity;

import java.util.*;

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
        final StringBuilder sb = new StringBuilder("User{");
//        sb.append("uuid=").append(uuid);
//        sb.append(", createdAt=").append(createdAt);
        sb.append("updatedAt=").append(updatedAt);
        sb.append(", id='").append(id).append('\'');
        sb.append(", pw='").append(pw).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(id, user.id) && Objects.equals(pw, user.pw) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, createdAt, updatedAt, id, pw, name);
    }
}
