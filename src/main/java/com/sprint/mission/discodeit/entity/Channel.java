package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import java.util.*;

@Getter
public class Channel extends BaseEntity{
    private ChannelType type;
    private String name;
    private final UUID authorId;
    private String description;
    private List<UUID> userList;

    public Channel(ChannelType type, String name, UUID authorId, String description, List<UUID> userList) {
        this.type = type;
        this.name = name;
        this.authorId = authorId;
        this.description = description;
        this.userList = (userList == null)? new ArrayList<>() : userList;
    }// TODO 생성자에서 null 선언을 해줘야 하는지, 아니면 기본으로 나둬야하는지(선언 안해주면 값이 아예 없나?)

    public void update(String newName, String newDescription, UUID userId) {
        boolean anyValueUpdated = false;
        if (newName != null && !newName.equals(this.name)) {
            this.name = newName;
            anyValueUpdated = true;
        }
        if (newDescription != null && !newDescription.equals(this.description)) {
            this.description = newDescription;
            anyValueUpdated = true;
        }
        if (userId != null) {
            userList.add(userId);
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            super.update();
        }
    }
}