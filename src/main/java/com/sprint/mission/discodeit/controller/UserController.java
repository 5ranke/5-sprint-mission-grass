package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.UserDto;
import com.sprint.mission.discodeit.dto.request.BinaryContentCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserStatusUpdateRequest;
import com.sprint.mission.discodeit.dto.request.UserUpdateRequest;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.UserStatusService;
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

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name= "User", description = "User API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserStatusService userStatusService;

    @Operation(
            summary = "전체 User 목록 조회",
            description = "시스템에 등록된 모든 사용자(UserDto)의 목록을 반환합니다.",
            operationId = "findAll"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User 목록 조회 성공",
                    content = @Content(
                            mediaType = "*/*",
                            array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @Operation(
            summary = "User 등록",
            description = "multipart/form-data로 userCreateRequest(JSON)과 profile(선택, 이미지 파일)을 업로드합니다.",
            operationId = "create"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User가 성공적으로 생성됨",
                    content = @Content(
                            mediaType = "*/*",
                            schema = @Schema(implementation = User.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "같은 email 또는 username를 사용하는 User가 이미 존재함",
                    content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(value = "User with email {email} already exists")
                    )
            )
    })
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> create(
            @RequestPart("userCreateRequest")
            @Schema(implementation = UserCreateRequest.class)
            UserCreateRequest userCreateRequest,

            @RequestPart(value = "profile", required = false)
            @Parameter(description = "User 프로필 이미지")
            @Schema(type = "string", format = "binary")
            MultipartFile profile
    ) {
        Optional<BinaryContentCreateRequest> profileRequest = Optional.ofNullable(profile)
                .flatMap(this::resolveProfileRequest);
        User createdUser = userService.create(userCreateRequest, profileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @Operation(
            summary = "User 정보 수정",
            description = "multipart/form-data로 User 정보와 프로필 이미지를 수정합니다.",
            operationId = "update"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User 정보가 성공적으로 수정됨",
                    content = @Content(mediaType = "*/*",
                            schema = @Schema(implementation = User.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User를 찾을 수 없음",
                    content = @Content(mediaType = "*/*",
                            examples = @ExampleObject(value = "User with id {userId} not found"))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "같은 email 또는 username을 사용하는 User가 이미 존재함",
                    content = @Content(mediaType = "*/*",
                            examples = @ExampleObject(value = "User with email {newEmail} already exists"))
            )
    })
    @PatchMapping(path = "/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> update(
            @PathVariable("userId") UUID userId,
            @RequestPart("userUpdateRequest")UserUpdateRequest userUpdateRequest,
            @RequestPart(value = "profile", required = false) MultipartFile profile
            ){
        Optional<BinaryContentCreateRequest> profileRequest = Optional.ofNullable(profile)
                .flatMap(this::resolveProfileRequest);
        User updatedUser = userService.update(userId, userUpdateRequest, profileRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }


    @Operation(
            summary = "User 삭제",
            description = "userId에 해당하는 사용자를 삭제합니다.",
            operationId = "delete"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User가 성공적으로 삭제됨"),
            @ApiResponse(
                    responseCode = "404",
                    description = "User를 찾을 수 없음",
                    content = @Content(
                            mediaType = "*/*",
                            examples = @ExampleObject(value = "User with id {id} not found")
                    )
            )
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable("userId") UUID userId
    ) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "User 온라인 상태 업데이트",
            description = "특정 User의 온라인 상태(UserStatus)를 업데이트합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User 온라인 상태가 성공적으로 업데이트됨",
                    content = @Content(schema = @Schema(implementation = UserStatus.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 User의 UserStatus를 찾을 수 없음",
                    content = @Content(mediaType = "text/plain",
                            examples = @ExampleObject(value = "UserStatus with userId {userId} not found"))
            )
    })
    @PatchMapping(value = "/{userId}/userStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserStatus> updateUserStatusByUserId(
            @PathVariable ("userId") UUID userId,
            @Valid @RequestBody UserStatusUpdateRequest request
            ) {
        UserStatus updatedUserStatus  = userStatusService.updateByUserId(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserStatus);
    }


    private Optional<BinaryContentCreateRequest> resolveProfileRequest(MultipartFile profileFile) {
        if(profileFile == null || profileFile.isEmpty()) {
            return Optional.empty();
        } else {
            try {
                BinaryContentCreateRequest binaryContentCreateRequest = new BinaryContentCreateRequest(
                        profileFile.getOriginalFilename(),
                        profileFile.getContentType(),
                        profileFile.getBytes()
                );
                return Optional.of(binaryContentCreateRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
