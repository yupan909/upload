package com.java.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * todo
 *
 * @author yupan
 * @date 2020-07-27 11:16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUtilsTest {

    /**
     * 查询出入库列表
     */
    @Test
    public void deleteFile(){
        FileUtils.deleteFile("ff3260724127454880d82510dcb3a9b4.jpeg");
    }
}
