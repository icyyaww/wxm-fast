package com.wxm.msfast.base.file.service.impl;

import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.file.config.MinioConfig;
import com.wxm.msfast.base.file.exception.FileExceptionEnum;
import com.wxm.msfast.base.file.service.IFileService;
import com.wxm.msfast.base.file.service.MsfFileService;
import com.wxm.msfast.base.file.utils.DelayTaskProducer;
import com.wxm.msfast.base.file.utils.FileUploadUtils;
import com.wxm.msfast.base.file.utils.FileUtils;
import io.minio.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;

/**
 * Minio 文件存储
 *
 * @author ruoyi
 */
@Primary
@Service
public class MinioFileServiceImpl implements IFileService {

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private MinioClient client;

    @Autowired
    private MsfFileService fileService;

    /**
     * 本地文件上传接口
     *
     * @param file 上传的文件
     * @return 访问地址
     * @throws Exception
     */
    @Override
    public String uploadFile(MultipartFile file) throws Exception {

        String filePath = FileUploadUtils.extractFilename(file);
        PutObjectArgs args = PutObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object(filePath)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build();
        client.putObject(args);
        String url = minioConfig.getEndpoint() + "/" + minioConfig.getBucketName() + "/" + filePath;
        //保存文件 此时文件为临时文件 会被定期删除
        fileService.saveFile(url, filePath, FileUtils.getName(file.getOriginalFilename()));
        return url;
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
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }
    }

    /**
     * @param filePath
     * @Description: 删除文件
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 下午3:39
     */
    @Override
    public void deleteFile(String filePath) throws Exception {
        client.removeObject(
                RemoveObjectArgs.builder().bucket(minioConfig.getBucketName()).object(filePath).build());
    }

    @Override
    public String staticUpload(MultipartFile file, String path) {
        String staticPath = FileUtils.getPath(path) + File.separator + file.getOriginalFilename();
        String uploadpath = ConfigConstants.FILE_STATIC_PATH() + File.separator + staticPath;

        File fileOld = new File(uploadpath);
        if (fileOld.exists()) {
            throw new JrsfException(FileExceptionEnum.File_Exists_Exception);
        }
        mkdirs(uploadpath);
        //上传
        try {
            file.transferTo(new File(uploadpath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staticPath.replaceAll("\\\\", "/");
    }

    @Override
    public Boolean staticDelete(String path) {
       return FileUtils.deleteFile(ConfigConstants.FILE_STATIC_PATH() + File.separator + FileUtils.getPath(path));
    }

    private void mkdirs(String uploadpath) {
        File file1 = new File(uploadpath);
        File parent = file1.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
    }
}
