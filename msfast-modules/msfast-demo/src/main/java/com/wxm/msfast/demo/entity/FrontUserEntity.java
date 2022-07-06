package com.wxm.msfast.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-06 18:07:55
 */
@Data
@TableName("front_user")
public class FrontUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId
    private Integer id;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改者
     */
    private String modifyer;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableLogic
    private Integer delFlag;
    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;

}
