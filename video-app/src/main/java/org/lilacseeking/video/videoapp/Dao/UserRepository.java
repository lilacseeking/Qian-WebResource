package org.lilacseeking.video.videoapp.Dao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.lilacseeking.video.videoapp.Model.PO.QUserPO;
import org.lilacseeking.video.videoapp.Model.PO.UserPO;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Repository
public class UserRepository extends AbstractRepository<UserPO> {

    QUserPO qUserPO = QUserPO.userPO;
    /**
     * 保存或更新
     * @return
     */
    @Transactional
    public UserPO saveOrUpdate(UserPO userPO){
        return entityManager.merge(userPO);
    }

    /**
     * 分页查询
     * @param page
     * @return
     */
    public Page list(Page page, String filter) throws ParseException {
//        // 参数组装
        JSONObject value = JSONObject.parseObject(filter);
        String name = value.getString("name");
        String email = value.getString("email");
        String mobile = value.getString("mobile");
        String username = value.getString("username");
        Date birthday = StringUtils.isBlank(value.getString("birthday"))?null:new SimpleDateFormat("yyyy-MM-dd").parse(value.getString("birthday"));
        JSONArray gmtCreateList = value.getJSONArray("gmtCreateList");
        Integer gender =  StringUtils.isBlank(value.getString("gender"))?null:Integer.valueOf(value.getString("gender"));
        Integer age =  StringUtils.isBlank(value.getString("age"))?null:Integer.valueOf(value.getString("age"));
        Integer achieve =  StringUtils.isBlank(value.getString("achieve"))?null:Integer.valueOf(value.getString("achieve"));
        JPAQueryBase query = new JPAQuery<>(entityManager).from(qUserPO);
        // 姓名查询
        if (!StringUtils.isBlank(name)){
            query.where(qUserPO.name.eq(name));
        }
        // 手机号查询
        if (!StringUtils.isBlank(mobile)){
            query.where(qUserPO.mobile.eq(mobile));
        }
        // 邮箱查询
        if (!StringUtils.isBlank(email)){
            query.where(qUserPO.email.eq(email));
        }
        // 用户名
        if (!StringUtils.isBlank(username)){
            query.where(qUserPO.username.eq(username));
        }
        // 出生日期
        if (birthday !=null){
            query.where(qUserPO.birthday.eq(birthday));
        }
        // 创建日期
        if (null != gmtCreateList && gmtCreateList.size()>0){
            query.where(qUserPO.gmtCreate.in(new SimpleDateFormat("yyyy-MM-dd").parse(gmtCreateList.get(0).toString())
                    ,new SimpleDateFormat("yyyy-MM-dd").parse(gmtCreateList.get(1).toString())));
        }
        // 性别查询
        if (gender != null){
            query.where(qUserPO.gender.eq(gender));
        }
        // 年龄查询
        if (age != null){
            query.where(qUserPO.age.eq(age));
        }
        // 归档查询
        if (achieve != null){
            query.where(qUserPO.achieve.eq(achieve));
        }
        int count = (int)query.fetchCount();//总记录数
        query.limit(page.getRows());//每页记录数
        query.offset(page.getRows() * (page.getCurrentPage() - 1));//偏移量
        List<UserPO> userPOList = query.fetch();
        //组装返回参数
        page.setCount(count);
        page.setResultList(userPOList);
        return page;
    }

    /**
     * 删除用户单个用户，数据库不保留数据
     * @return
     */
    @Transactional
    public Integer delete(UserPO userPO){
        UserPO userPODelete = entityManager.find(UserPO.class, userPO);
        entityManager.remove(userPODelete);
        return 1;
    }

    /**
     * 封存用户
     * @param userPO
     * @return
     */
    public Integer achieve(UserPO userPO){
        return null;
    }

    public UserPO getUserPOByMobile(String mobile){
        JPAQueryBase query = new JPAQuery(entityManager).from(qUserPO);
        query.where(qUserPO.mobile.eq(mobile));
        UserPO userPO = (UserPO)query.fetchFirst();
        return userPO;
    }
}
