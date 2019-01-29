package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTeacherPO is a Querydsl query type for TeacherPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeacherPO extends EntityPathBase<TeacherPO> {

    private static final long serialVersionUID = -1949894399L;

    public static final QTeacherPO teacherPO = new QTeacherPO("teacherPO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    public final StringPath degreeFileName = createString("degreeFileName");

    public final StringPath degreeNo = createString("degreeNo");

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

    public final StringPath idCardFileName = createString("idCardFileName");

    public final StringPath idCardNo = createString("idCardNo");

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath personalResume = createString("personalResume");

    public final StringPath researchArea = createString("researchArea");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath verifyStatus = createString("verifyStatus");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath workExprience = createString("workExprience");

    public QTeacherPO(String variable) {
        super(TeacherPO.class, forVariable(variable));
    }

    public QTeacherPO(Path<? extends TeacherPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeacherPO(PathMetadata metadata) {
        super(TeacherPO.class, metadata);
    }

}

