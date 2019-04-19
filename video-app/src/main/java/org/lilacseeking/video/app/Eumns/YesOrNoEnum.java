package org.lilacseeking.video.app.Eumns;

/**
 * @Auther: lilacseeking
 * @Date: 2018/12/17 21:55
 * @Description:
 */
public enum YesOrNoEnum {

    Y("YES","是"),
    N("NO","否"),
    NULL("NULL","空");

    public String code;
    public String name;

    YesOrNoEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
