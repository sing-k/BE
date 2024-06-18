package com.project.singk.domain.member.infrastructure;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberStatisticsEntity is a Querydsl query type for MemberStatisticsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberStatisticsEntity extends EntityPathBase<MemberStatisticsEntity> {

    private static final long serialVersionUID = 526910989L;

    public static final QMemberStatisticsEntity memberStatisticsEntity = new QMemberStatisticsEntity("memberStatisticsEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> totalActivityScore = createNumber("totalActivityScore", Integer.class);

    public final NumberPath<Integer> totalReview = createNumber("totalReview", Integer.class);

    public final NumberPath<Integer> totalReviewScore = createNumber("totalReviewScore", Integer.class);

    public QMemberStatisticsEntity(String variable) {
        super(MemberStatisticsEntity.class, forVariable(variable));
    }

    public QMemberStatisticsEntity(Path<? extends MemberStatisticsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberStatisticsEntity(PathMetadata metadata) {
        super(MemberStatisticsEntity.class, metadata);
    }

}

