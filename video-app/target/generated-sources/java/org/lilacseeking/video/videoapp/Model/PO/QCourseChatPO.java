package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCourseChatPO is a Querydsl query type for CourseChatPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCourseChatPO extends EntityPathBase<CourseChatPO> {

    private static final long serialVersionUID = 1130527666L;

    public static final QCourseChatPO courseChatPO = new QCourseChatPO("courseChatPO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

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

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath text = createString("text");

    public final StringPath userId = createString("userId");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QCourseChatPO(String variable) {
        super(CourseChatPO.class, forVariable(variable));
    }

    public QCourseChatPO(Path<? extends CourseChatPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCourseChatPO(PathMetadata metadata) {
        super(CourseChatPO.class, metadata);
    }

}

