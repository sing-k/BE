package com.project.singk.domain.review.domain;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.project.singk.domain.review.domain.QAlbumReviewStatistics is a Querydsl Projection type for AlbumReviewStatistics
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QAlbumReviewStatistics extends ConstructorExpression<AlbumReviewStatistics> {

    private static final long serialVersionUID = -369497216L;

    public QAlbumReviewStatistics(com.querydsl.core.types.Expression<Long> id, com.querydsl.core.types.Expression<Integer> totalReviewer, com.querydsl.core.types.Expression<Integer> totalScore, com.querydsl.core.types.Expression<Double> averageScore, com.querydsl.core.types.Expression<Integer> score1Count, com.querydsl.core.types.Expression<Integer> score2Count, com.querydsl.core.types.Expression<Integer> score3Count, com.querydsl.core.types.Expression<Integer> score4Count, com.querydsl.core.types.Expression<Integer> score5Count, com.querydsl.core.types.Expression<Integer> maleCount, com.querydsl.core.types.Expression<Integer> maleTotalScore, com.querydsl.core.types.Expression<Integer> femaleCount, com.querydsl.core.types.Expression<Integer> femaleTotalScore, com.querydsl.core.types.Expression<java.time.LocalDateTime> modifiedAt) {
        super(AlbumReviewStatistics.class, new Class<?>[]{long.class, int.class, int.class, double.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, int.class, java.time.LocalDateTime.class}, id, totalReviewer, totalScore, averageScore, score1Count, score2Count, score3Count, score4Count, score5Count, maleCount, maleTotalScore, femaleCount, femaleTotalScore, modifiedAt);
    }

}

