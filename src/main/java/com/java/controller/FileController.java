package com.java.controller;

import com.java.bean.BaseResult;
import com.java.bean.UploadImg;
import com.java.util.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 文件上传
 *
 * @author yupan
 * @date 2020-07-10 17:32
 */
@RestController
public class FileController {

    /**
     * 上传图片
     */
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    public BaseResult uploadImg(@RequestParam("fileImg") MultipartFile file) throws Exception{
        String fileName = FileUtils.upload(file);
        return new BaseResult(fileName);
    }

    /**
     * 读取图片
     */
    @RequestMapping(value = "getImg/{imgName}", method = RequestMethod.GET)
    public void getImg(@PathVariable("imgName") String imgName, HttpServletResponse response){
        //设置发送到客户端的响应内容类型
        response.setContentType("image/*");

        FileInputStream in = null;
        ServletOutputStream out = null;
        try {
            // 读取图片实际路径
            String realPath = FileUtils.getFilePath(imgName);
            in = new FileInputStream(realPath);
            out = response.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024];
            while((count = in.read(buffer)) != -1) {
                out.write(buffer, 0 , count);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 删除图片
     */
    @RequestMapping(value = "delImg/{imgName}", method = RequestMethod.GET)
    public BaseResult delImg(@PathVariable("imgName") String imgName){
        FileUtils.deleteFile(imgName);
        return BaseResult.successResult();
    }

    /**
     * 上传Base64图片
     */
    @RequestMapping(value = "uploadBase64Img", method = RequestMethod.POST)
    public BaseResult uploadBase64Img(@RequestBody UploadImg uploadImg) throws Exception {
        List<String> fileData = uploadImg.getFileData();
        if (fileData != null) {
            for (int i = 0; i < fileData.size(); i++) {
                String fileName = FileUtils.upload(fileData.get(i));
                System.out.println("上传第" + (i+1) + "张图片成功：" + fileName);
            }
        }
        return BaseResult.successResult();
    }

    /**
     * 上传文件
     */
    @RequestMapping(value = "uploadFile", method = RequestMethod.POST)
    public BaseResult uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws Exception {
        String fileName = FileUtils.upload(file);
        System.out.println("OriginalFilename: " + file.getOriginalFilename());
        System.out.println("fileName: " + fileName);
        System.out.println("id:" + id);
        return BaseResult.successResult();
    }

    /**
     * 下载文件
     */
    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public BaseResult downloadFile(HttpServletResponse response) throws Exception {
        FileUtils.download("易酒批入职offer余攀0320.pdf", "a2991b5af077413691b2af3a098a887a.pdf", response);
        return null;
    }
}
