package com.java.util;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 文件上传工具类
 *
 * @author yupan
 * @date 2020-07-10 17:20
 */
public class FileUtils {

    private final static String UPLOAD_DIR = "upload";

    private final static String IMG_PNG = "data:image/png;base64,";
    private final static String IMG_JPG = "data:image/jpg;base64,";
    private final static String IMG_JPEG = "data:image/jpeg;base64,";
    private final static String IMG_GIF = "data:image/gif;base64,";

    /**
     * 获取文件的完整目录路径
     * @return
     */
    public static String getFilePath(String fileName) {
        try {
            ApplicationHome h = new ApplicationHome(FileUtils.class);
            if (h != null) {
                // 获取jar包所在目录路径
                File jarF = h.getSource();
                String jarPath = jarF.getParentFile().toString();
                StringBuilder sb = new StringBuilder(jarPath);
                sb.append(File.separator);
                sb.append(UPLOAD_DIR);
                sb.append(File.separator);
                sb.append(fileName);
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 上传
     */
    public static String upload(MultipartFile file) throws Exception {
        if (file == null) {
            return null;
        }
        // 生成的文件名
        String fileName =  getUUID() + getSuffix(file.getOriginalFilename());
        // 文件保存路径
        String realPath = getFilePath(fileName);
        File dest = new File(realPath);
        createFileDir(dest);
        //保存文件
        file.transferTo(dest);
        return fileName;
    }

    /**
     * 下载
     */
    public static void download(String fileName, String filePath, HttpServletResponse response) throws Exception {
        if (filePath == null) {
            return;
        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "multipart/form-data");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 文件保存路径
        String realPath = getFilePath(filePath);
        File file = new File(realPath);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            os = response.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = is.read(buffer);
            while(len != -1) {
                os.write(buffer, 0, len);
                len = is.read(buffer);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                os.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * base64图片上传
     */
    public static String upload(String base64) throws Exception {
        if (base64 == null) {
            return null;
        }
        // 获取图片类型
        String fileType = null;
        if (base64.contains(IMG_PNG)) {
            base64 = base64.replace(IMG_PNG, "");
            fileType = ".png";
        } else if (base64.contains(IMG_JPG)) {
            base64 = base64.replace(IMG_JPG, "");
            fileType = ".jpg";
        } else if (base64.contains(IMG_JPEG)) {
            base64 = base64.replace(IMG_JPEG, "");
            fileType = ".jpeg";
        } else if (base64.contains(IMG_GIF)) {
            base64 = base64.replace(IMG_GIF, "");
            fileType = ".gif";
        }

        if (fileType == null) {
            return null;
        }

        // 生成的文件名
        String fileName = getUUID() + fileType;
        // 文件保存路径
        String realPath = getFilePath(fileName);
        File dest = new File(realPath);
        createFileDir(dest);

        // Base64解码
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] bytes = base64Decoder.decodeBuffer(base64);
        // 调整异常数据
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {
                bytes[i] += 256;
            }
        }
        // 保存图片
        OutputStream out = new FileOutputStream(dest);
        out.write(bytes);
        out.flush();
        out.close();
        return fileName;
    }

    /**
     * 根据图片名称删除图片
     */
    public static void deleteFile(String fileName) {
        if (fileName == null) {
            return;
        }
        // 文件保存路径
        String realPath = getFilePath(fileName);
        File dest = new File(realPath);
        dest.delete();
    }

    /**
     * 判断文件父目录是否存在，不存在则创建
     */
    private static void createFileDir(File dest) {
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }
    }

    /**
     * 获取文件后缀
     */
    private static String getSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 随机生成id
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
