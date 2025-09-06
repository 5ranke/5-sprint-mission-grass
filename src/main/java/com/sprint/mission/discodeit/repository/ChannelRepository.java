package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<Channel, UUID> {

    @Query("""
       select  c
       from Channel c
       left join fetch c.messageList
       left join fetch c.readStatusList
       where c.type = 'PUBLIC' or c.id in :channelIds
       """)
    List<Channel> findPublicOrSubscribedWithMessagesAndStatuses(List<UUID> channelIds);
}
