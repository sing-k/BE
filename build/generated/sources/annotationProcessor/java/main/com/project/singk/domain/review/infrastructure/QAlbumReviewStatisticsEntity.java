package com.project.singk.domain.review.infrastructure;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAlbumReviewStatisticsEntity is a Querydsl query type for AlbumReviewStatisticsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAlbumReviewStatisticsEntity extends EntityPathBase<AlbumReviewStatisticsEntity> {

    private static final long serialVersionUID = -358139934L;

    public static final QAlbumReviewStatisticsEntity albumReviewStatisticsEntity = new QAlbumReviewStatisticsEntity("albumReviewStatisticsEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    public final NumberPath<Double> averageScore = createNumber("averageScore", Double.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> femaleCount = createNumber("femaleCount", Integer.class);

    public final NumberPath<Integer> femaleTotalScore = createNumber("femaleTotalScore", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maleCount = createNumber("maleCount", Integer.class);

    public final NumberPath<Integer> maleTotalScore = createNumber("maleTotalScore", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> score1Count = createNumber("score1Count", Integer.class);

    public final NumberPath<Integer> score2Count = createNumber("score2Count", Integer.class);

    public final NumberPath<Integer> score3Count = createNumber("score3Count", Integer.class);

    public final NumberPath<Integer> score4Count = createNumber("score4Count", Integer.class);

    public final NumberPath<Integer> score5Count = createNumber("score5Count", Integer.class);

    public final NumberPath<Integer> totalReviewer = createNumber("totalReviewer", Integer.class);

    public final NumberPath<Integer> totalScore = createNumber("totalScore", Integer.class);

    public QAlbumReviewStatisticsEntity(String variable) {
        super(AlbumReviewStatisticsEntity.class, forVariable(variable));
    }

    public QAlbumReviewStatisticsEntity(Path<? extends AlbumReviewStatisticsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAlbumReviewStatisticsEntity(PathMetadata metadata) {
        super(AlbumReviewStatisticsEntity.class, metadata);
    }

}

