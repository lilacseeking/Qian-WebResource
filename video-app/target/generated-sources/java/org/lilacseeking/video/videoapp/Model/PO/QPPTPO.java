package org.lilacseeking.video.videoapp.Model.PO;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPPTPO is a Querydsl query type for PPTPO
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPPTPO extends EntityPathBase<PPTPO> {

    private static final long serialVersionUID = -1564258541L;

    public static final QPPTPO pPTPO = new QPPTPO("pPTPO");

    public final QBaseEntityPO _super = new QBaseEntityPO(this);

    //inherited
    public final NumberPath<Integer> achieve = _super.achieve;

    //inherited
    public final NumberPath<Long> creater = _super.creater;

    //inherited
    public final NumberPath<Long> deleter = _super.deleter;

    public final StringPath description = createString("description");

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

    public final StringPath isUse = createString("isUse");

    //inherited
    public final NumberPath<Long> modifier = _super.modifier;

    public final StringPath name = createString("name");

    public final StringPath url = createString("url");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QPPTPO(String variable) {
        super(PPTPO.class, forVariable(variable));
    }

    public QPPTPO(Path<? extends PPTPO> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPPTPO(PathMetadata metadata) {
        super(PPTPO.class, metadata);
    }

}

