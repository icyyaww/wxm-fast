package com.wxm.msfast.base.common.constant;

/**
 * Token的Key常量
 *
 * @author ruoyi
 */
public class TokenConstants {
    /**
     * 令牌自定义标识
     */
    public static final String AUTHENTICATION = "Authorization";

    /**
     * 令牌前缀
     */
    public static final String PREFIX = "Bearer ";

    /**
     * 令牌秘钥
     */
    public final static String SECRET = "abcdefghijklmnopqrstuvwxyz";

    /**
     * token有效期（分钟）
     */
    public final static Integer EXPIRATION = 3;

    /**
     * token刷新时间（分钟）
     */
    public final static Integer REFRESH_TIME = 1;

}
