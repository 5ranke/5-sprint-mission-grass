package com.sprint.mission.discodeit.entity;

import lombok.Getter;
import java.util.*;

@Getter
public class Message extends BaseEntity{
    private final UUID authorId;
    private final UUID channelId;
    private String content;
    private List<UUID> attachmentIds;

    public Message(UUID authorId, UUID channelId, String content, List<UUID> attachmentIds) {
        this.authorId = authorId;
        this.channelId = channelId;
        this.content = content;
        this.attachmentIds = (attachmentIds == null)? new ArrayList<>() : attachmentIds;
    }

    public void update(String newContent, List<UUID> newAttachmentIds) {
        boolean anyValueUpdated = false;
        if (newContent != null && !newContent.equals(this.content)) {
            this.content = newContent;
            anyValueUpdated = true;
        }
        if(attachmentIds != null) {
            this.attachmentIds.addAll(newAttachmentIds);
            anyValueUpdated = true;
        }

        if (anyValueUpdated) {
            super.update();
        }
    }
}

