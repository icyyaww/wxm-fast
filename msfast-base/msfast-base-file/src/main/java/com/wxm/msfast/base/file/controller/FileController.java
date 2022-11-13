package com.wxm.msfast.base.file.controller;

import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.base.file.rest.response.FileResponse;
import com.wxm.msfast.base.file.service.IFileService;
import com.wxm.msfast.base.file.service.MsfFileService;
import com.wxm.msfast.base.file.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("file")
@Api(tags = "文件系统")
@Slf4j
public class FileController {

    @Autowired
    private IFileService sysFileService;

    /**
     * 文件上传请求
     */
    @PostMapping("/upload")
    @ApiOperation(value = "文件上传")
    @AuthIgnore
    public R<FileResponse> upload(@RequestPart MultipartFile file) throws Exception {

        try {
            // 上传并返回访问地址
            String url = sysFileService.uploadFile(file);
            FileResponse sysFile = new FileResponse();
            sysFile.setName(FileUtils.getName(url));
            sysFile.setUrl(url);
            return R.ok(sysFile);
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw e;
        }
    }

    /**
     * 下载文件
     *
     * @param filename
     * @param response
     */
    @GetMapping("/download")
    @ApiOperation(value = "文件下载")
    public void download(@RequestParam String filename, HttpServletResponse response) throws Exception {

        try {
            this.sysFileService.download(filename, response);
        } catch (Exception e) {
            log.error("下载文件失败", e);
            throw e;
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "文件删除")
    public R delete(@RequestParam String filename) throws Exception {

        try {
            this.sysFileService.deleteFile(filename);
            return R.ok();
        } catch (Exception e) {
            log.error("文件删除失败", e);
            throw e;
        }
    }

}
