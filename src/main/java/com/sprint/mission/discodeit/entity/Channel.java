package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "channels")
@Getter @SuperBuilder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "Channel")
public class Channel extends BaseUpdatableEntity {

    @Enumerated(EnumType.STRING)
    @Schema(description = "채널 타입(PUBLIC/PRIVATE)", allowableValues = {"PUBLIC", "PRIVATE"})
    @Column(name = "type", nullable = false, length = 10)
    private ChannelType type;

    @Schema(description = "채널명")
    @Column(name = "name", length = 100)
    private String name;

    @Schema(description = "채널 설명")
    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(
            mappedBy = "channel",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messageList = new ArrayList<>();

    @OneToMany(
            mappedBy = "channel",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ReadStatus> readStatusList = new ArrayList<>();

    public Channel(ChannelType type, String name, String description) {
        this.type = type;
        this.name = name;
        this.description = description;
    }

    public void update(String newName, String newDescription) {
        if (newName != null && !newName.equals(this.name)) {
            this.name = newName;
        }
        if (newDescription != null && !newDescription.equals(this.description)) {
            this.description = newDescription;
        }
    }
}
