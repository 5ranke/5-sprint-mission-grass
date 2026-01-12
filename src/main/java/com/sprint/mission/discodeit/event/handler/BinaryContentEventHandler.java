package com.sprint.mission.discodeit.event.handler;

import com.sprint.mission.discodeit.event.BinaryContentCreatedEvent;
import com.sprint.mission.discodeit.storage.BinaryContentStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinaryContentEventHandler {
    private final BinaryContentStorage binaryContentStorage;

    // 커밋 후 리스너 실행
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleAfterCommitCreate(BinaryContentCreatedEvent event) {
        log.info("[AFTER_COMMIT] binary content 생성 커밋 완료!! id={}", event.binaryContentId());
        binaryContentStorage.put(event.binaryContentId(), event.bytes());
    }

    // 롤백 발생시(예외 발생)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleAfterRollbackCreate(BinaryContentCreatedEvent event) {
        log.info("[AFTER_ROLLBACK] 오류 발생으로 Rollback 발생!!");
    }

    // 트랜잭션 종료
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMPLETION)
    public void handleAfterCompletionCreate(BinaryContentStorage event) {
        log.info("[AFTER_COMPLETION] 작업 종료!!");
    }


}