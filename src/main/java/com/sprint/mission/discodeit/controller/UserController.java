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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// UserController는 Http 요청을 받아 응답을 반환하는 역할만 수행
// 따라서 실제 비즈니스 로직은 UserService에 의존한다.

@RequiredArgsConstructor // Lombok으로 스프링에서 DI(의존성 주입)의 방법 중에 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션
@RestController
@RequestMapping("/api/users") // 들어온 요청을 특정 메서드와 매핑
public class UserController {
    private final UserService userService;
    private final UserStatusService userStatusService;

    // @RequestMapping : consumes 속성은 요청헤더 Content-type의 값을 제한하겠다는 의미
    // MULTIPART_FORM_DATA_VALUE : 웹에서 파일 업로드할 때 주로 쓰이는 요청 형식. 폼 데이터(예: username=홍길동)뿐만 아니라 파일까지 함께 전송
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<User> create(
            // @RequestPart : multipart 요청에서 파일이나 JSON 같은 파트를 꺼낼 때 사용
            @RequestPart("userCreateRequest") UserCreateRequest userCreateRequest, //Json
            @RequestPart(value = "profile", required = false) MultipartFile profile //multipart
    ) {
        Optional<BinaryContentCreateRequest> profileRequest = Optional.ofNullable(profile)
                .flatMap(this::resolveProfileRequest);
        User createdUser = userService.create(userCreateRequest, profileRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
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

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(
            @PathVariable("userId") UUID userId
    ) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } // 응답 데이터에 아무런 정보가 없을 때에는 No Content를 의미하는 204 상태코드를 사용합니다.

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PatchMapping(value = "/{userId}/userStatus", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserStatus> updateUserStatusByUserId(
            @PathVariable ("userId") UUID userId,
            @RequestBody UserStatusUpdateRequest request // Body의 데이터 추출
            ) {
        UserStatus updatedUserStatus  = userStatusService.updateByUserId(userId, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUserStatus);
    }

}
