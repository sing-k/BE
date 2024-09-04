package com.project.singk.domain.alarm.infrastructure.entity;

import com.project.singk.domain.alarm.domain.Alarm;
import com.project.singk.domain.alarm.domain.AlarmType;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.global.domain.BaseTimeEntity;
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
public class AlarmEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name="type", nullable = false)
    private AlarmType type;

    @ManyToOne
    @JoinColumn(name = "sender_id", updatable = false, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", updatable = false, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MemberEntity receiver;

    @JoinColumn(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name="is_read", nullable = false)
    private boolean isRead;

    @Builder
    public AlarmEntity(Long id, AlarmType type, MemberEntity sender, MemberEntity receiver, Long targetId, boolean isRead) {
        this.id = id;
        this.type = type;
        this.sender = sender;
        this.receiver = receiver;
        this.targetId = targetId;
        this.isRead = isRead;
    }

    public static AlarmEntity from(Alarm alarm){
        return AlarmEntity.builder()
                .id(alarm.getId())
                .type(alarm.getType())
                .sender(MemberEntity.from(alarm.getSender()))
                .receiver(MemberEntity.from(alarm.getReceiver()))
                .targetId(alarm.getTargetId())
                .isRead(alarm.isRead())
                .build();
    }

    public Alarm toModel(){
        return  Alarm.builder()
                .id(this.id)
                .type(this.type)
                .receiver(this.receiver.toModel())
                .sender(this.sender.toModel())
                .isRead(this.isRead)
                .createdAt(this.getCreatedAt())
                .build();
    }

}
