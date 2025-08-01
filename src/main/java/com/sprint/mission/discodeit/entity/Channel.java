package com.sprint.mission.discodeit.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

public class Channel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private ChannelType type;
    private String name;
    private final UUID creatorId;
    private String description;

    public Channel(UUID creatorId, String name) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now().getEpochSecond();
        this.name = name;
        this.creatorId = creatorId;
    }

    public Channel(ChannelType type, String name, UUID creatorId, String description) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now().getEpochSecond();

        this.type = type;
        this.name = name;
        this.creatorId = creatorId;
        this.description = description;
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

    public ChannelType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public String getDescription() {
        return description;
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
            this.updatedAt = Instant.now().getEpochSecond();
        }
    }
}