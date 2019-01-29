package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVideoContentPO is a Querydsl query type for VideoContentPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVideoContentPO extends EntityPathBase<VideoContentPO> {

    private static final long serialVersionUID = -1003447715L;

    public static final QVideoContentPO videoContentPO = new QVideoContentPO("videoContentPO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    public final StringPath chapterDescription = createString("chapterDescription");

    public final StringPath chapterId = createString("chapterId");

    public final StringPath chapterNane = createString("chapterNane");

    public final NumberPath<Long> courseId = createNumber("courseId", Long.class);

    public final StringPath courseName = createString("courseName");

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    //inherited
    public final NumberPath<Long> deleter = _super.deleter;

    public final StringPath fileName = createString("fileName");

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

    public final EnumPath<org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum> isFree = createEnum("isFree", org.lilacseeking.video.videoapp.Eumns.YesOrNoEnum.class);

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath time = createString("time");

    public final StringPath url = createString("url");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath views = createString("views");

    public QVideoContentPO(String variable) {
        super(VideoContentPO.class, forVariable(variable));
    }

    public QVideoContentPO(Path<? extends VideoContentPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVideoContentPO(PathMetadata metadata) {
        super(VideoContentPO.class, metadata);
    }

}

