package com.project.singk.domain.alarm.infrastructure.entity;

import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="ALARM")
@Getter
@NoArgsConstructor
public class AlarmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="content")
    private String content;

    @Column(name="isRead", nullable = false)
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity receiver;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false)
    private AlarmType type;

    @Builder
    public AlarmEntity(Long id, AlarmType type, MemberEntity receiver, Boolean isRead, String content) {
        this.id = id;
        this.type = type;
        this.receiver = receiver;
        this.isRead = isRead;
        this.content = content;
    }

    public static AlarmEntity from(Alarm alarm){
        return AlarmEntity.builder()
                .id(alarm.getId())
                .content(alarm.getContent())
                .isRead(alarm.getIsRead())
                .type(alarm.getType())
                .receiver(MemberEntity.from(alarm.getReceiver()))
                .build();
    }

    public Alarm toModel(){
        return  Alarm.builder()
                .id(this.id)
                .content(this.content)
                .isRead(this.isRead)
                .type(this.type)
                .receiver(this.receiver.toModel())
                .build();
    }

}
