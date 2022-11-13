package com.wxm.msfast.base.file.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wxm.msfast.base.common.entity.BaseEntity;
import com.wxm.msfast.base.file.common.enums.FileStatusEnum;
import lombok.Data;


/**
 * 备注
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 23:52:07
 */
@Data
@TableName(value = "msf_file", autoResultMap = true)
public class MsfFileEntity extends BaseEntity {

    /**
     * 是否是原图
     */
    @TableField("original")
    private Boolean original;
    /**
     * 文件路径
     */
    @TableField("url")
    private String url;
    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件状态 TEMP-临时文件 SAVED-已保存的文件
     */
    @TableField("status")
    private FileStatusEnum status;

}
