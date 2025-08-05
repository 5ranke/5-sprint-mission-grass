package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import java.util.*;

@Getter
public class Channel extends BaseEntity{
    private ChannelType type;
    private String name;
    private final UUID authorId;
    private String description;

    public Channel(ChannelType type, String name, UUID authorId, String description) {
        this.type = type;
        this.name = name;
        this.authorId = authorId;
        this.description = description;
    }

    public void update(String newName, String newDescription) {
        boolean anyValueUpdated = false;
        if (newName != null && !newName.equals(this.name)) {
            this.name = newName;
            anyValueUpdated = true;
        }
        if (newDescription != null && !newDescription.equals(this.description)) {
            this.description = newDescription;
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            super.update();
        }
    }
}