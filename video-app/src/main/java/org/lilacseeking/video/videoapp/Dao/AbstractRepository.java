package org.lilacseeking.video.videoapp.Dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 通用抽象Repository操作类
 *
 * @param <T>
 */
public abstract class AbstractRepository<T> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * persist
     *
     * @param object
     */
    public void persist(T object) {
        entityManager.persist(object);
    }

    /**
     * merge
     *
     * @param object
     */
    public T merge(T object) {
        return entityManager.merge(object);
    }

    /**
     * remove
     *
     * @param object
     */
    public void remove(T object) {
        entityManager.remove(object);
    }

    /**
     * 批量录入
     *
     * @param list
     */
    public void batchInsert(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                entityManager.persist(list.get(i));
            }
            if (i % 24 == 0 || i == (list.size() - 1)) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    /**
     * 批量更新
     *
     * @param list
     */
    public void batchUpdate(List<T> list) {
        entityManager.flush();
        entityManager.clear();
        for (int i = 0; i < list.size(); i++) {
            entityManager.merge(list.get(i));
            if (i % 24 == 0 || i == (list.size() - 1)) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    /**
     * 批量删除
     *
     * @param list
     */
    public void batchDelete(List<T> list) {
        entityManager.flush();
        entityManager.clear();
        for (int i = 0; i < list.size(); i++) {
            entityManager.remove(list.get(i));
            if (i % 24 == 0 || i == (list.size() - 1)) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

}
