package org.lilacseeking.video.videoapp.Repository.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.querydsl.jpa.JPAQueryBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.lilacseeking.video.videoapp.Model.DTO.UserBasicDTO;
import org.lilacseeking.video.videoapp.Repository.AbstractRepository;
import org.lilacseeking.video.videoapp.Model.PO.QUserPO;
import org.lilacseeking.video.videoapp.Model.PO.UserPO;
import org.lilacseeking.video.videoapp.Utils.Page;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        query.where(qUserPO.achieve.eq(0));
        // 姓名查询
        if (!StringUtils.isBlank(name)){
            query.where(qUserPO.name.like("%" + name + "%"));
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
            query.where(qUserPO.username.like("%" + username + "%"));
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
        query.orderBy(qUserPO.gmtCreate.desc());
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

    /**
     * 根据手机号或用户名或邮箱获取用户信息
     * @param param
     * @return
     */
    public UserPO getUserByMobileOrEmailOrUsername(String param){
        JPAQueryBase query = new JPAQuery(entityManager).from(qUserPO);
        query.where(qUserPO.achieve.eq(0));
        // 根据手机号获取用户信息
        if (match("0?(13|14|15|18|17)[0-9]{9}",param)){
            query.where(qUserPO.mobile.eq(param));
        }
        // 根据邮箱获取用户信息
        else if (match("\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}",param)){
            query.where(qUserPO.email.eq(param));
        } else {
            query.where(qUserPO.username.eq(param));
        }
        UserPO userPO = (UserPO)query.fetchFirst();
        return userPO;
    }

    /**
     * 正则匹配
     * @param regex
     * @param str
     * @return
     */
    private boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    /**
     * 获取用户信息
     * @param userDTO
     * @return
     */
    public UserPO getUserInfo(UserBasicDTO userDTO){
        JPAQuery query = new JPAQuery<>(entityManager).from(qUserPO);
        query.where(qUserPO.id.eq(userDTO.getId()));
        query.where(qUserPO.achieve.eq(0));
        return (UserPO)query.fetchFirst();
    }
}
