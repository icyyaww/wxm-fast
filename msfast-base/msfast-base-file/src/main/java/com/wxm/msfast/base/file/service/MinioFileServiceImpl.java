package com.wxm.msfast.base.file.service;


import com.wxm.msfast.base.file.config.MinioConfig;
import com.wxm.msfast.base.file.utils.FileUploadUtils;
import io.minio.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Service
public class MinioFileServiceImpl implements IFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {
        String fileName = FileUploadUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(fileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        return minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + fileName;
    }

    /**
     * @param filename
     * @param response
     * @Description: 文件下载
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 上午11:08
     */
    @Override
    public void download(String filename, HttpServletResponse response) throws Exception {
        InputStream in = null;
        try {
            //获取对象信息
            StatObjectResponse stat = client.statObject(
                    StatObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filename).build());
            response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(filename, "utf-8"));
            //文件下载
            in = client.getObject(GetObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filename).build());
            IOUtils.copy(in, response.getOutputStream());
        }  finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }
}
