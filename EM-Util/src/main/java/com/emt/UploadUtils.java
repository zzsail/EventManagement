package com.emt;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 上传图片工具类
 */
public class UploadUtils {

    public static String uploadImage(MultipartFile multipartFile){

        try {
            String realfilename = multipartFile.getOriginalFilename();

            String imgSuffix = realfilename.substring(realfilename.lastIndexOf("."));

            String newFilename = UUID.randomUUID() +imgSuffix;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = dateFormat.format(new Date());
            //上传到哪个路径下
            File targetPath = new File("C:\\Code\\Vue\\EventManagement-Front\\src\\assets\\upload" , datePath);
            if (!targetPath.exists()){
                targetPath.mkdirs();
            }
            File targetFilename = new File(targetPath, newFilename);
            multipartFile.transferTo(targetFilename);
            return "upload/"+datePath+"/"+newFilename;//资源映射路径
        } catch (IOException e) {
            e.printStackTrace();
            return "失败！";
        }
    }

}