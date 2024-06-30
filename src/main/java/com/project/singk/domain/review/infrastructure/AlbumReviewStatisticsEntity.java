package com.project.singk.domain.review.infrastructure;

import com.project.singk.domain.album.infrastructure.entity.AlbumEntity;
import com.project.singk.domain.review.domain.AlbumReviewStatistics;
import com.project.singk.global.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "ALBUM_REVIEW_STATISTICS", indexes = {
        @Index(name = "idx_album_statistic_modified_at", columnList = "modifiedAt DESC"),
        @Index(name = "idx_album_statistic_total_reviewer", columnList = "totalReviewer DESC"),
        @Index(name = "idx_album_statistic_average_score", columnList = "averageScore DESC")
})
@Getter
@NoArgsConstructor
public class AlbumReviewStatisticsEntity extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @ColumnDefault("0")
    @Column(name = "total_reviewer")
    private int totalReviewer;

    @ColumnDefault("0")
    @Column(name = "total_score")
    private int totalScore;

    @ColumnDefault("0.0")
    @Column(name = "average_score")
    private double averageScore;

    @ColumnDefault("0")
    @Column(name = "score_1_count")
    private int score1Count;

    @ColumnDefault("0")
    @Column(name = "score_2_count")
    private int score2Count;

    @ColumnDefault("0")
    @Column(name = "score_3_count")
    private int score3Count;

    @ColumnDefault("0")
    @Column(name = "score_4_count")
    private int score4Count;

    @ColumnDefault("0")
    @Column(name = "score_5_count")
    private int score5Count;

    @ColumnDefault("0")
    @Column(name = "male_count")
    private int maleCount;

    @ColumnDefault("0")
    @Column(name = "male_total_score")
    private int maleTotalScore;

    @ColumnDefault("0")
    @Column(name = "female_count")
    private int femaleCount;

    @ColumnDefault("0")
    @Column(name = "female_total_score")
    private int femaleTotalScore;

    @Builder
    public AlbumReviewStatisticsEntity(Long id, int totalReviewer, int totalScore, double averageScore, int score1Count, int score2Count, int score3Count, int score4Count, int score5Count, int maleCount, int maleTotalScore, int femaleCount, int femaleTotalScore, AlbumEntity album) {
        this.id = id;
        this.totalReviewer = totalReviewer;
        this.totalScore = totalScore;
        this.averageScore = averageScore;
        this.score1Count = score1Count;
        this.score2Count = score2Count;
        this.score3Count = score3Count;
        this.score4Count = score4Count;
        this.score5Count = score5Count;
        this.maleCount = maleCount;
        this.maleTotalScore = maleTotalScore;
        this.femaleCount = femaleCount;
        this.femaleTotalScore = femaleTotalScore;
    }

    public static AlbumReviewStatisticsEntity from (AlbumReviewStatistics statistics) {
        return AlbumReviewStatisticsEntity.builder()
                .id(statistics.getId())
                .totalReviewer(statistics.getTotalReviewer())
                .totalScore(statistics.getTotalScore())
                .averageScore(statistics.calculateAverage(
                        statistics.getTotalScore(),
                        statistics.getTotalReviewer()
                ))
                .score1Count(statistics.getScore1Count())
                .score2Count(statistics.getScore2Count())
                .score3Count(statistics.getScore3Count())
                .score4Count(statistics.getScore4Count())
                .score5Count(statistics.getScore5Count())
                .maleCount(statistics.getMaleCount())
                .maleTotalScore(statistics.getMaleTotalScore())
                .femaleCount(statistics.getFemaleCount())
                .femaleTotalScore(statistics.getFemaleTotalScore())
                .build();
    }

    public AlbumReviewStatistics toModel() {
        return AlbumReviewStatistics.builder()
                .id(this.id)
                .totalReviewer(this.totalReviewer)
                .totalScore(this.totalScore)
                .averageScore(this.averageScore)
                .score1Count(this.score1Count)
                .score2Count(this.score2Count)
                .score3Count(this.score3Count)
                .score4Count(this.score4Count)
                .score5Count(this.score5Count)
                .maleCount(this.maleCount)
                .maleTotalScore(this.maleTotalScore)
                .femaleCount(this.femaleCount)
                .femaleTotalScore(this.femaleTotalScore)
                .modifiedAt(this.getModifiedAt())
                .build();
    }

}
