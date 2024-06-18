package com.project.singk.domain.member.infrastructure;

import com.project.singk.domain.member.domain.MemberStatistics;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "MEMBER_STATISTICS")
@Getter
@NoArgsConstructor
public class MemberStatisticsEntity extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ColumnDefault("0")
    @Column(name = "total_activity_score")
    private int totalActivityScore;

    @ColumnDefault("0")
    @Column(name = "total_review")
    private int totalReview;

    @ColumnDefault("0")
    @Column(name = "total_review_score")
    private int totalReviewScore;

    @Builder
    public MemberStatisticsEntity(Long id, int totalActivityScore, int totalReview, int totalReviewScore) {
        this.id = id;
        this.totalActivityScore = totalActivityScore;
        this.totalReview = totalReview;
        this.totalReviewScore = totalReviewScore;
    }

    public static MemberStatisticsEntity from(MemberStatistics statistics) {
        return MemberStatisticsEntity.builder()
                .id(statistics.getId())
                .totalActivityScore(statistics.getTotalActivityScore())
                .totalReview(statistics.getTotalReview())
                .totalReviewScore(statistics.getTotalReviewScore())
                .build();
    }

    public MemberStatistics toModel() {
        return MemberStatistics.builder()
                .id(this.id)
                .totalActivityScore(this.totalActivityScore)
                .totalReview(this.totalReview)
                .totalReviewScore(this.totalReviewScore)
                .build();
    }
}
