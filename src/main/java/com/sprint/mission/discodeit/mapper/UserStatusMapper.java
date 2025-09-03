package com.sprint.mission.discodeit.mapper;

import com.sprint.mission.discodeit.dto.data.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface UserStatusMapper {
//    UUID id,
//    UUID userId, 얘는 있음. 매핑해주면 될듯하고...
//    UUID channelId, 얘가 없는데?
//    Instant lastActiveAt

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "channelId", source = "channel.id") // ?? 여기 모르겠음 TODO
    UserStatusDto toDto(UserStatus userStatus);
}
