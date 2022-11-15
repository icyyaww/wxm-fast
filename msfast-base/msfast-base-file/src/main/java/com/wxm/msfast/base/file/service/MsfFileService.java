package com.wxm.msfast.base.file.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.file.entity.MsfFileEntity;

/**
 * 备注
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 23:52:07
 */
public interface MsfFileService extends IService<MsfFileEntity> {

    /*
     * @Author 保存临时文件
     * @Description
     * @Date 15:23 2022/11/13
     * @Param
     * @return
     **/
    void saveFile(String url,String filePath, String fileName);

    /*
     * @Author wanglei
     * @Description  临时文件持久化
     * @Date 20:36 2022/11/13
     * @Param
     * @return
     **/
    void changeTempFile(Object object);

    /*
     * @Author wanglei
     * @Description  临时文件持久化
     * @Date 20:36 2022/11/14
     * @Param
     * @return
     **/
    void changeTempFile(String url);

    /*
     * @Author wanglei
     * @Description  删除临时文件存储
     * @Date 20:38 2022/11/14
     * @Param
     * @return
     **/
    void deleteTempFile(String filePath,String url);

    /**
     * @Description: 删除文件 物理删除
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/9 下午3:39
     */
    void deleteFile(String filename) throws Exception;
}

