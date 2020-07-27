package com.java.bean;

import java.util.List;

/**
 * 上传图片实体
 *
 * @author yupan
 * @date 2020-07-27 09:36
 */
public class UploadImg {

    /**
     * base64图片
     */
    private List<String> fileData;

    public List<String> getFileData() {
        return fileData;
    }

    public void setFileData(List<String> fileData) {
        this.fileData = fileData;
    }
}
