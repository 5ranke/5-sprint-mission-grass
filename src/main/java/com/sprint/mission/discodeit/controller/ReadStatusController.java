package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.dto.request.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.service.ReadStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name= "ReadStatus", description = "Message 읽음 상태 API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/readStatuses")
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @Operation(
            summary = "User의 Message 읽음 상태 목록 조회",
            description = "쿼리 파라미터 userId(UUID)를 받아 해당 사용자의 읽음 상태 리스트를 반환합니다.",
            operationId = "findAllByUserId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message 읽음 상태 목록 조회 성공",
                    content = @Content(
                            mediaType = "*/*",
                            array = @ArraySchema(schema = @Schema(implementation = ReadStatus.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<ReadStatus>> findAllByUserId (
            @Parameter(
                    name = "userId",
                    description = "조회할 User ID",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid")
            )
            @RequestParam("userId") UUID userId
    ) {
        List<ReadStatus> readStatuses = readStatusService.findAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(readStatuses);
    }


    @Operation(
            summary = "Message 읽음 상태 생성",
            operationId = "create_1"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Message 읽음 상태가 성공적으로 생성됨",
                    content = @Content(
                            mediaType = "*/*",
                            schema = @Schema(implementation = ReadStatus.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel 또는 User를 찾을 수 없음",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "Channel | User with id {channelId | userId} not found")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "이미 읽음 상태가 존재함",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "ReadStatus with userId {userId} and channelId {channelId} already exists")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<ReadStatus> create (
            //@Valid는 @RequestBody(또는 @RequestPart)로 들어온 JSON을 DTO의 제약 조건(@NotNull, @Email, @Size …)
            //기준으로 자동 검사하고, 틀리면 400 Bad Request로 막아줌.
            @Valid @RequestBody ReadStatusCreateRequest request
            ) {
        ReadStatus createdReadStatus = readStatusService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReadStatus);
    }


    @Operation(
            summary = "Message 읽음 상태 수정",
            description = "특정 ReadStatus ID를 이용해 Message 읽음 상태를 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message 읽음 상태가 성공적으로 수정됨",
                    content = @Content(schema = @Schema(implementation = ReadStatus.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message 읽음 상태를 찾을 수 없음",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "ReadStatus with id {readStatusId} not found"))
            )
    })
    @PatchMapping("/{readStatusId}")
    public ResponseEntity<ReadStatus> update (
            @PathVariable("readStatusId") UUID readStatusId,
            @Valid @RequestBody ReadStatusUpdateRequest request
            ) {
        ReadStatus updatedReadStatus = readStatusService.update(readStatusId, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedReadStatus);
    }
}
