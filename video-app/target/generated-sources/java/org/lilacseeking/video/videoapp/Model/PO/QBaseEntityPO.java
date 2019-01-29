package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseEntityPO is a Querydsl query type for BaseEntityPO
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseEntityPO extends EntityPathBase<BaseEntityPO> {

    private static final long serialVersionUID = 746115955L;

    public static final QBaseEntityPO baseEntityPO = new QBaseEntityPO("baseEntityPO");

    public final NumberPath<Integer> achieve = createNumber("achieve", Integer.class);

    public final NumberPath<Long> creater = createNumber("creater", Long.class);

    public final NumberPath<Long> deleter = createNumber("deleter", Long.class);

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtDelete = createDateTime("gmtDelete", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModify = createDateTime("gmtModify", java.util.Date.class);

    public final StringPath guid = createString("guid");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> modifier = createNumber("modifier", Long.class);

    public final NumberPath<Integer> version = createNumber("version", Integer.class);

    public QBaseEntityPO(String variable) {
        super(BaseEntityPO.class, forVariable(variable));
    }

    public QBaseEntityPO(Path<? extends BaseEntityPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEntityPO(PathMetadata metadata) {
        super(BaseEntityPO.class, metadata);
    }

}

