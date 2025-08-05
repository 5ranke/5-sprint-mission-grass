package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus extends BaseEntity{
    private final UUID userId;
    private boolean loginFlag;
    private Instant lastConnectedAt;

    public UserStatus(UUID userId) {
        this.userId = userId;
        this.loginFlag = false; // 계정 생성 시 기본은 비활
    }

    // 접속한 경우 (로그인)
    public void updateLoginFlag() {
        loginFlag = true;
        super.update();
    }

    // 로그아웃한 경우
    public void updateLogoutFlag() {
        lastConnectedAt = Instant.now();
        loginFlag = false;
        super.update();
    }

    public boolean isOnline(){
        if(lastConnectedAt == null) {
            return loginFlag; // 로그아웃 안했어도 접속중일 수 있음
        }
        // 로그아웃 했고 마지막 접속이 5분 이내라면
        Instant before = Instant.now().minusSeconds(300);
        if(!loginFlag &&  lastConnectedAt.isAfter(before)) {
            return true;
        }
        return loginFlag; // 로그인 상태(로그아웃 시간은 과거) -> 온라인
                        // 로그아웃 상태 5분이 지난 경우 -> 오프라인
    }
}