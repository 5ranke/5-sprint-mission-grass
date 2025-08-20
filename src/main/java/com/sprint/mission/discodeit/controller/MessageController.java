package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.MessageUpdateRequest;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name= "Message", description = "Message API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @Operation(
            summary = "Channel의 Message 목록 조회",
            description = "쿼리 파라미터 channelId(UUID)를 받아 해당 채널의 메시지 목록을 반환합니다.",
            operationId = "findAllByChannelId"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message 목록 조회 성공",
                    content = @Content(
                            mediaType = "*/*",
                            array = @ArraySchema(schema = @Schema(implementation = Message.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<Message>> findAllByChannelId (
            @Parameter(
                    name = "channelId",
                    description = "조회할 Channel ID",
                    required = true,
                    schema = @Schema(type = "string", format = "uuid")
            )
            @RequestParam("channelId") UUID channelId
    ) {
        List<Message> messages = messageService.findAllByChannelId(channelId);
        return ResponseEntity.status(HttpStatus.OK).body(messages);
    }


    @Operation(
            summary = "Message 생성",
            description = "multipart/form-data로 messageCreateRequest(JSON)와 attachments(파일 배열)를 업로드합니다.",
            operationId = "create_2"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Message가 성공적으로 생성됨",
                    content = @Content(
                            mediaType = "*/*",
                            schema = @Schema(implementation = Message.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Channel 또는 User를 찾을 수 없음",
                    content = @Content(
                            mediaType = "*/*",
                            examples = @ExampleObject(value = "Channel | Author with id {channelId | authorId} not found")
                    )
            )
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Message> create (
            @RequestPart("messageCreateRequest")
            @Schema(implementation = MessageCreateRequest.class)
            MessageCreateRequest messageCreateRequest,

            @Parameter(
                    name = "attachments",
                    description = "Message 첨부 파일들",
//                    required = false,
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(type = "string", format = "binary"))
                    )
            )
            @RequestPart(value = "attachments", required = false)
            List<MultipartFile> attachments
            ) {
        List<BinaryContentCreateRequest> attachmentRequests = Optional.ofNullable(attachments)
                .map(files -> files.stream()
                        .map(file -> {
                            try {
                                return new BinaryContentCreateRequest(
                                        file.getOriginalFilename(),
                                        file.getContentType(),
                                        file.getBytes()
                                );
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList())
                .orElse(new ArrayList<>());
        Message createdMessage = messageService.create(messageCreateRequest, attachmentRequests);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }


    @Operation(
            summary = "Message 내용 수정",
            description = "Message ID를 기반으로 메시지 내용을 수정합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Message가 성공적으로 수정됨",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = Message.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message를 찾을 수 없음",
                    content = @Content(mediaType = "*/*",
                            examples = @ExampleObject(value = "Message with id {messageId} not found"))
            )
    })
    @PatchMapping(path = "/{messageId}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Message> update (
            @Parameter(
                    description = "수정할 Message ID",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable("messageId") UUID messageId,
            @Valid @RequestBody MessageUpdateRequest messageUpdateRequest
            ) {
        Message updatedMessage = messageService.update(messageId, messageUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedMessage);
    }


    @Operation(
            summary = "Message 삭제",
            description = "특정 Message ID로 메시지를 삭제합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Message가 성공적으로 삭제됨"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Message를 찾을 수 없음",
                    content = @Content(mediaType = "*/*",
                            examples = @ExampleObject(value = "Message with id {messageId} not found"))
            )
    })
    @DeleteMapping("/{messageId}")
    public ResponseEntity<Void> delete (
            @Parameter(
                    description = "삭제할 Message ID",
                    schema = @Schema(type = "string", format = "uuid")
            )
            @PathVariable("messageId") UUID messageId
    ) {
        messageService.delete(messageId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
