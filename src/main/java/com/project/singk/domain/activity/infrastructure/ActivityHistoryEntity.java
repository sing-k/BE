package com.project.singk.domain.activity.infrastructure;

import com.project.singk.domain.activity.domain.ActivityHistory;
import com.project.singk.domain.activity.domain.ActivityType;
import com.project.singk.domain.member.infrastructure.MemberEntity;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ACTIVITY_HISTORYS", indexes = {
        @Index(name = "idx_activity_historys_created_at", columnList = "createdAt DESC"),
})
@Getter
@NoArgsConstructor
public class ActivityHistoryEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "activity_type")
    @Enumerated(EnumType.STRING)
	private ActivityType type;

	@Column(name = "activity_score")
	private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @Builder
    public ActivityHistoryEntity(Long id, ActivityType type, int score, MemberEntity member) {
        this.id = id;
        this.type = type;
        this.score = score;
        this.member = member;
    }

    public static ActivityHistoryEntity from (ActivityHistory activity) {
        return ActivityHistoryEntity.builder()
                .id(activity.getId())
                .type(activity.getType())
                .score(activity.getScore())
                .member(MemberEntity.from(activity.getMember()))
                .build();
    }

    public ActivityHistory toModel() {
        return ActivityHistory.builder()
                .id(this.id)
                .type(this.type)
                .score(this.score)
                .member(this.member.toModel())
                .createdAt(this.getCreatedAt())
                .build();
    }
}
