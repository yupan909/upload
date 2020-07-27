package com.java.controller;

import com.alibaba.fastjson.JSON;
import com.java.bean.Person;
import com.java.util.ExcelUtils;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * excel导入导出
 *
 * @author yupan
 * @date 2020-06-23 14:28
 */
@Controller
public class ExcelController {

    /**
     * 导出模版
     */
    @RequestMapping(value = "exportTemplate", method = RequestMethod.GET)
    @ResponseBody
    public void exportExcelTemplate(HttpServletResponse response) throws Exception {
        List<Person> personList = new ArrayList<>();
        Person person = new Person();
        personList.add(person);
        ExcelUtils.exportExcel(personList, Person.class,"人员表","人员信息模版", response);
    }

    /**
     * 导出
     */
    @ResponseBody
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response) throws Exception {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Person person = new Person();
            person.setName("张三" + i);
            person.setMobile("1304274420" + i);
            person.setAge(i + 10);
            personList.add(person);
        }
        ExcelUtils.exportExcel(personList, Person.class,"人员表","人员信息表", response);
    }

    /**
     * 导入
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    @ResponseBody
    public void importExcel(@RequestParam("file") MultipartFile file) throws Exception {
        List<Person> personList = ExcelUtils.importExcel(file, 1,1, Person.class);
        System.out.println("导入成功:" + JSON.toJSONString(personList));
    }

}
