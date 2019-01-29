package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QFriendList is a Querydsl query type for FriendList
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFriendList extends EntityPathBase<FriendList> {

    private static final long serialVersionUID = -880701252L;

    public static final QFriendList friendList = new QFriendList("friendList");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    //inherited
    public final NumberPath<Long> deleter = _super.deleter;

    public final NumberPath<Long> friendId = createNumber("friendId", Long.class);

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

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QFriendList(String variable) {
        super(FriendList.class, forVariable(variable));
    }

    public QFriendList(Path<? extends FriendList> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFriendList(PathMetadata metadata) {
        super(FriendList.class, metadata);
    }

}

