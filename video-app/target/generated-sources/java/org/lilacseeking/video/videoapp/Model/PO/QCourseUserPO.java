package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseUserPO is a Querydsl query type for CourseUserPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourseUserPO extends EntityPathBase<CourseUserPO> {

    private static final long serialVersionUID = 1656128357L;

    public static final QCourseUserPO courseUserPO = new QCourseUserPO("courseUserPO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    public final StringPath completion = createString("completion");

    public final StringPath contentsId = createString("contentsId");

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    //inherited
    public final NumberPath<Long> deleter = _super.deleter;

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

    public final StringPath isFinish = createString("isFinish");

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath userId = createString("userId");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCourseUserPO(String variable) {
        super(CourseUserPO.class, forVariable(variable));
    }

    public QCourseUserPO(Path<? extends CourseUserPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseUserPO(PathMetadata metadata) {
        super(CourseUserPO.class, metadata);
    }

}

