package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;

@Mapper(componentModel = "spring")
public interface ReadStatusMapper {
//    UUID id,
//    UUID userId,
//    UUID channelId,
//    Instant lastReadAt

    // 여기랑 비교해주기~~ TODO
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "channelId", source = "channel.id")
    ReadStatusDto toDto(ReadStatus readStatus);
}
