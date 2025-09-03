package com.sprint.mission.discodeit.storage;

import com.sprint.mission.discodeit.config.FileConfig;
import com.sprint.mission.discodeit.dto.data.BinaryContentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinaryContentStorageImpl implements BinaryContentStorage {

    private final FileConfig fileConfig;

    @Override
    public UUID put(UUID id, byte[] bytes) {
        File dir = fileConfig.getBinaryContentUploadDirFile();
        File dest = new File(dir, id.toString());

        try (FileOutputStream fos = new FileOutputStream(dest)) {
            fos.write(bytes);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패: " + dest.getAbsolutePath(), e);
        }
        return id;
    }

    @Override
    public InputStream get(UUID id) {
        File dir = fileConfig.getBinaryContentUploadDirFile();
        File dest = new File(dir, id.toString());

        if (!dest.exists()) {
            throw new RuntimeException("파일이 존재하지 않습니다: " + id);
        }

        try {
            return new FileInputStream(dest);
        } catch (IOException e) {
            throw new RuntimeException("파일 읽기 실패: " + dest.getAbsolutePath(), e);
        }
    }

    @Override
    public ResponseEntity<?> download(BinaryContentDto binaryContentDto) {
        try {
            InputStream inputStream = get(binaryContentDto.id());
            InputStreamResource resource = new InputStreamResource(inputStream);

            // 파일명 설정 (null이면 ID 사용)
            String fileName = binaryContentDto.fileName() != null ?
                    binaryContentDto.fileName() :
                    binaryContentDto.id().toString();

            // Content-Type 설정 (기본값: application/octet-stream)
            String contentType = binaryContentDto.contentType() != null ?
                    binaryContentDto.contentType() :
                    "application/octet-stream";

            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + fileName + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, contentType);

            // 파일 크기가 있으면 Content-Length 헤더 추가
            if (binaryContentDto.size() != null) {
                responseBuilder.header(HttpHeaders.CONTENT_LENGTH,
                        String.valueOf(binaryContentDto.size()));
            }

            return responseBuilder.body(resource);

        } catch (Exception e) {
            log.error("파일 다운로드 실패: {}", binaryContentDto.id(), e);
            return ResponseEntity.notFound().build(); // throw 제거하고 return만
        }
    }
}
