package com.sprint.mission.discodeit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sprint.mission.discodeit.entity.base.BaseUpdatableEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "messages")
@Getter @SuperBuilder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Schema(name = "Message")
public class Message extends BaseUpdatableEntity {

    @Schema(description = "메시지 내용")
    @Column(name = "content", columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "channel_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_messages_channel")
    )
    private Channel channel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "author_id",
            foreignKey = @ForeignKey(name = "fk_messages_author")
    )
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "message_attachments",
            joinColumns = @JoinColumn(
                    name = "message_id",
                    foreignKey = @ForeignKey(name = "fk_ma_message")
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "attachment_id",
                    foreignKey = @ForeignKey(name = "fk_ma_attachment")
            )
    )
    private List<BinaryContent> attachments = new ArrayList<>();

    public void update(String newContent, List<BinaryContent> newAttachments) {
        if (newContent != null && !newContent.equals(this.content)) {
            this.content = newContent;
        }
        if (newAttachments != null && !newAttachments.equals(this.attachments)) {
            this.attachments.addAll(newAttachments);
        }
    }
}
