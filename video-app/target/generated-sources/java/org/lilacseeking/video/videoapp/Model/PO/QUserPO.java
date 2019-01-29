package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserPO is a Querydsl query type for UserPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserPO extends EntityPathBase<UserPO> {

    private static final long serialVersionUID = -1071366294L;

    public static final QUserPO userPO = new QUserPO("userPO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    public final DateTimePath<java.util.Date> birthday = createDateTime("birthday", java.util.Date.class);

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    public final NumberPath<Integer> defaultUser = createNumber("defaultUser", Integer.class);

    //inherited
    public final NumberPath<Long> deleter = _super.deleter;

    public final StringPath email = createString("email");

    public final NumberPath<Integer> gender = createNumber("gender", Integer.class);

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

    public final StringPath mobile = createString("mobile");

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath username = createString("username");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public final StringPath yanzhi = createString("yanzhi");

    public QUserPO(String variable) {
        super(UserPO.class, forVariable(variable));
    }

    public QUserPO(Path<? extends UserPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserPO(PathMetadata metadata) {
        super(UserPO.class, metadata);
    }

}

