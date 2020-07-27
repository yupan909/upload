package com.java.bean;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * todo
 *
 * @author yupan
 * @date 2020-06-23 13:51
 */
public class Person {

    /**
     * 姓名
     */
    @Excel(name = "姓名", orderNum = "0")
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄", orderNum = "1")
    private Integer age;

    /**
     * 手机号
     */
    @Excel(name = "手机号", orderNum = "2", width = 20.0D)
    private String mobile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
