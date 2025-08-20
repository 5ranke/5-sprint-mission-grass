package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name= "Channel", description = "Channel API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @Operation(
            summary = "Public Channel 생성",
            description = "PublicChannelCreateRequest(JSON)를 받아 Public 채널을 생성합니다.",
            operationId = "create_3"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Public Channel이 성공적으로 생성됨",
                    content = @Content(
                            mediaType = "*/*",
                            schema = @Schema(implementation = Channel.class)
                    )
            )
    })
    @PostMapping(path = "/public",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> create (
            @Valid @RequestBody PublicChannelCreateRequest request
            ) {
        Channel createdChannel = channelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChannel);
    }


    @Operation(
            summary = "Private Channel 생성",
            description = "PrivateChannelCreateRequest(JSON)를 받아 Private 채널을 생성합니다.",
            operationId = "create_4"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Private Channel이 성공적으로 생성됨",
                    content = @Content(
                            mediaType = "*/*",
                            schema = @Schema(implementation = Channel.class)
                    )
            )
    })
    @PostMapping(path = "/private", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> create (
            @Valid @RequestBody PrivateChannelCreateRequest request
            ) {
        Channel createdChannel = channelService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdChannel);
    }


    @Operation(
            summary = "Channel 정보 수정",
            description = "Channel ID를 기반으로 Public Channel 정보를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel 정보가 성공적으로 수정됨",
                    content = @Content(schema = @Schema(implementation = Channel.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel을 찾을 수 없음",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Channel with id {channelId} not found"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Private Channel은 수정할 수 없음",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Private channel cannot be updated"))
            )
    })
    @PatchMapping(path = "/{channelId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Channel> update (
            @PathVariable ("channelId") UUID channelId,
            @Valid @RequestBody PublicChannelUpdateRequest request
            ) {
        Channel udpatedChannel = channelService.update(channelId, request);
        return ResponseEntity.status(HttpStatus.OK).body(udpatedChannel);
    }


    @Operation(
            summary = "Channel 삭제",
            description = "Channel ID를 기반으로 채널을 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Channel이 성공적으로 삭제됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel을 찾을 수 없음",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "Channel with id {channelId} not found"))
            )
    })
    @DeleteMapping("/{channelId}")
    public ResponseEntity<Void> delete (
            @PathVariable ("channelId") UUID channelId
    ) {
        channelService.delete(channelId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "User가 참여 중인 Channel 목록 조회",
            description = "특정 User ID를 기반으로 사용자가 참여 중인 Channel 목록을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Channel 목록 조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ChannelDto.class)))
            )
    })
    @GetMapping
    public ResponseEntity<List<ChannelDto>> findAll (
            @RequestParam("userId") UUID userId
    ) {
        List<ChannelDto> channels = channelService.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(channels);
    }
}
