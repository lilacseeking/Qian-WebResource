package org.lilacseeking.video.infrastructure.utils;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @author lilacseeking
 * @date 2018/7/22 18:20
 */
public class Page<T> {

    /**当前页*/
    private int currentPage;

    /**每页大小*/
    private int rows;
    /**总页数*/
    private int countPage;
    /**总记录数*/
    private int count;
    /**启始记录*/
    private List<T> resultList;

    private int  firstResult;
    public static Map resultSuccess= ImmutableMap.of("result","success");
    public static Map resultFailure= ImmutableMap.of("result","failure");
    public static Map addResultSuccess(Object value){
        return  ImmutableMap.of("result","success","message",value);
    }
    public static Map addResultFailure(Object value){
    	if(value==null) {
    	    value="系统出错了！";
        }
        return ImmutableMap.of("result","failure","message",value);
    }

    public Page() {}

    public Page(int currentPage, int rows) {
        this.currentPage = currentPage;
        this.rows = rows;
        this.firstResult=(currentPage-1)*rows;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCountPage() {
        return countPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        if(count%rows!=0){
            this.countPage=(count/rows)+1;
        }else {
            this.countPage=count/rows;
        }
    }

    public int getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(int firstResult) {
        this.firstResult = firstResult;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public Page setResultList(List<T> resultList) {
        this.resultList = resultList;
        return this;
    }
}
