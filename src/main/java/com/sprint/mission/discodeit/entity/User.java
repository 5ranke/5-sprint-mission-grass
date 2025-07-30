package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;
    private final String userid;
    private String pw;
    private String name;

    public User(String userid, String pw, String name) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.userid = userid;
        this.pw = pw;
        this.name = name;
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

    public String getUserid() {
        return userid;
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
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", userid='").append(userid).append('\'');
        sb.append(", pw='").append(pw).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(userid, user.userid) && Objects.equals(pw, user.pw) && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, updatedAt, userid, pw, name);
    }
}
