package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVideoCoursePO is a Querydsl query type for VideoCoursePO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVideoCoursePO extends EntityPathBase<VideoCoursePO> {

    private static final long serialVersionUID = 1967710357L;

    public static final QVideoCoursePO videoCoursePO = new QVideoCoursePO("videoCoursePO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    //inherited
    public final NumberPath<Long> deleter = _super.deleter;

    public final StringPath description = createString("description");

    public final NumberPath<Double> discount = createNumber("discount", Double.class);

    //inherited
    public final DateTimePath<java.util.Date> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.util.Date> gmtDelete = _super.gmtDelete;

    //inherited
    public final DateTimePath<java.util.Date> gmtModify = _super.gmtModify;

    //inherited
    public final StringPath guid = _super.guid;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isFree = createBoolean("isFree");

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath name = createString("name");

    public final StringPath tags = createString("tags");

    public final NumberPath<Long> teacher = createNumber("teacher", Long.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QVideoCoursePO(String variable) {
        super(VideoCoursePO.class, forVariable(variable));
    }

    public QVideoCoursePO(Path<? extends VideoCoursePO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoCoursePO(PathMetadata metadata) {
        super(VideoCoursePO.class, metadata);
    }

}

