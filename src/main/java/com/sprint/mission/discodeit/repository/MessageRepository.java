package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("""
            select m.createdAt
            from Message m
            where m.channel.id = :channelId
            order by m.createdAt desc limit 1
            """)
    Optional<Instant> findLastMessageAtByChannelId(UUID channelId);

    List<Message> findAllByChannelId(UUID channelId);

    void deleteAllByChannelId(UUID channelId);

    Slice<Message> findAllByChannelIdOrderByCreatedAtDesc(UUID channelId, Pageable pageable);
}
