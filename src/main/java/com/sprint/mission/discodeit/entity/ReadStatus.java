package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class ReadStatus extends BaseEntity {
    private final UUID userId;
    private final UUID channelId;
    private Instant lastReadAt;

    public ReadStatus(UUID userId, UUID channelId) {
        this.userId = userId;
        this.channelId = channelId;
    }

    public void updateLastReadAt(){
        lastReadAt = Instant.now();
        super.update();
    }

    // 읽음 -> 메시지 생성 후 확인
    public boolean isRead(Instant messageCreateAt) {
        if(lastReadAt == null) {
            return false; // 다 안읽음
        }
        return lastReadAt.isAfter(messageCreateAt);
    }
}
