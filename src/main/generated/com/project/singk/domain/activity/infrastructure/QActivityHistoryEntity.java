package com.project.singk.domain.activity.infrastructure;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QActivityHistoryEntity is a Querydsl query type for ActivityHistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QActivityHistoryEntity extends EntityPathBase<ActivityHistoryEntity> {

    private static final long serialVersionUID = 511365862L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QActivityHistoryEntity activityHistoryEntity = new QActivityHistoryEntity("activityHistoryEntity");

    public final com.project.singk.global.domain.QBaseTimeEntity _super = new com.project.singk.global.domain.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.project.singk.domain.member.infrastructure.QMemberEntity member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final EnumPath<com.project.singk.domain.activity.domain.ActivityType> type = createEnum("type", com.project.singk.domain.activity.domain.ActivityType.class);

    public QActivityHistoryEntity(String variable) {
        this(ActivityHistoryEntity.class, forVariable(variable), INITS);
    }

    public QActivityHistoryEntity(Path<? extends ActivityHistoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QActivityHistoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QActivityHistoryEntity(PathMetadata metadata, PathInits inits) {
        this(ActivityHistoryEntity.class, metadata, inits);
    }

    public QActivityHistoryEntity(Class<? extends ActivityHistoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.project.singk.domain.member.infrastructure.QMemberEntity(forProperty("member"), inits.get("member")) : null;
    }

}

