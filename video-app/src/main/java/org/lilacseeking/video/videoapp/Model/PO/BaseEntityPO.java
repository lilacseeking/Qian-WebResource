package org.lilacseeking.video.videoapp.Model.PO;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @Auther: lilacseeking
 * @Date: 2018/8/23 22:25
 * @Description:基本字段表
 * 此表为基本字段表，是数据库中其他数据表的子表。
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntityPO implements Serializable {

    /**
     * 记录编号
     */
    @Id
    @Column(name="id", nullable=false)
    @GeneratedValue(strategy= GenerationType.AUTO)
    public Long id ;
    /**
     * 系统唯一码
     */
    @Column(name="guid", nullable=false, length = 50)
    public String guid = UUID.randomUUID().toString().replace("-","").toLowerCase();
    /**
     * 创建时间
     */
    @Column(name="gmt_create", nullable=false)
    public Date gmtCreate = new Date();
    /**
     * 创建人
     */
    @Column(name="creater", nullable=false)
    public Long creater = 1L;
    /**
     * 修改时间
     */
    @Column(name="gmt_modify")
    public Date gmtModify;
    /**
     * 修改人
     */
    @Column(name="modifier")
    public Long modifier ;
    /**
     * 删除时间
     */
    @Column(name="gmt_delete")
    public Date gmtDelete ;
    /**
     * 删除人
     */
    @Column(name="deleter")
    public Long deleter;
    /**
     * 版本号
     */
    @Column(name="version", nullable=false)
    public Integer version = 0;
    /**
     * 归档
     */
    @Column(name="achieve", nullable=false)
    public Integer achieve = 0;

}
