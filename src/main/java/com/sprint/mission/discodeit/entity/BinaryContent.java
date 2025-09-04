package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sprint.mission.discodeit.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "binary_contents")
@Getter @SuperBuilder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "BinaryContent")
public class BinaryContent extends BaseEntity {

    @Schema(description = "파일명")
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Schema(description = "파일 크기(Byte)", format = "int64")
    @Column(name = "size", nullable = false)
    private Long size;

    @Schema(description = "콘텐츠 타입")
    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    public BinaryContent(String fileName, Long size, String contentType) {
        this.fileName = fileName;
        this.size = size;
        this.contentType = contentType;
    }
}
