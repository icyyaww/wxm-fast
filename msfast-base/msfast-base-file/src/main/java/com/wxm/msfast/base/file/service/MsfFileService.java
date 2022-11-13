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
    void saveFile(String url, String fileName);
}

