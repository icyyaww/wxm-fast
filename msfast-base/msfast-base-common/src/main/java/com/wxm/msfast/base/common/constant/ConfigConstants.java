package com.wxm.msfast.base.common.constant;

import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.SpringUtils;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-24 15:36
 **/
@Component
@Data
@RefreshScope
public class ConfigConstants {

    @Value("${wxmfast.config.auth.redis-enable:false}")
    private Boolean redisEnable;

    @Value("${wxmfast.config.auth.many-online:false}")
    private Boolean manyOnline;

    @Value("${wxmfast.config.auth.authorization:Authorization}")
    private String authorization;

    @Value("${wxmfast.config.auth.prefix:Bearer }")
    private String prefix;

    @Value("${wxmfast.config.auth.secret:abcdefghijklmnopqrstuvwxyz}")
    private String secret;

    @Value("${wxmfast.config.auth.expiration:30}")
    private Long expiration;

    @Value("${wxmfast.config.auth.refresh:5}")
    private Long refresh;

    @Value("${wxmfast.config.auth.wxapplet.appId:}")
    private String wxAppletAppId;

    @Value("${wxmfast.config.auth.wxapplet.secret:}")
    private String wxAppletSecret;

    @Value("${wxmfast.config.file.max-size:50}")
    private Long fileMaxSize;

    @Value("${wxmfast.config.file.name-length:100}")
    private int fileNameLength;

    @Value("${wxmfast.config.file.temp-time:30}")
    private int fileTempTime;

    @Value("${wxmfast.config.file.static-path:}")
    private String fileStaticPath;

    @Value("${wxmfast.config.websocket.port:8888}")
    private int webSocketPort;

    @Value("${wxmfast.config.websocket.heartbeat-time:30}")
    private int heartBeatTime;

    @Value("${wxmfast.config.websocket.resend-time:600}")
    private Long resendTime;

    @Value("${wxmfast.config.websocket.only-one:false}")
    private Boolean onlyOne;

    @Value("${wxmfast.config.role.username:admin}")
    private String userName;

    @Value("${wxmfast.config.role.password:123456}")
    private String passWord;


    /**
     * @Description: 是否启用redis 默认false
     */
    public static Boolean AUTH_REDIS_ENABLE() {
        return SpringUtils.getBean(ConfigConstants.class).getRedisEnable();
    }

    /**
     * @Description: 是否支持多人在线 默认false
     */
    public static Boolean AUTH_MANY_ONLINE() {
        return SpringUtils.getBean(ConfigConstants.class).getManyOnline();
    }

    /**
     * 令牌自定义标识
     */
    public static String AUTHENTICATION() {
        return SpringUtils.getBean(ConfigConstants.class).getAuthorization();
    }

    /**
     * 令牌前缀
     */
    public static String PREFIX() {
        return SpringUtils.getBean(ConfigConstants.class).getPrefix();
    }

    /**
     * 令牌秘钥
     */
    public static String SECRET() {

        return SpringUtils.getBean(ConfigConstants.class).getSecret();
    }

    /**
     * token有效期（分钟）
     */
    public static Long EXPIRATION() {
        return SpringUtils.getBean(ConfigConstants.class).getExpiration();
    }

    /**
     * token失效刷新时间（分钟）
     */
    public static Long REFRESH() {
        return SpringUtils.getBean(ConfigConstants.class).getRefresh();
    }

    /**
     * 文件上传最大大小
     */
    public static Long FILE_MAX_SIZE() {
        return SpringUtils.getBean(ConfigConstants.class).getFileMaxSize();
    }

    /**
     * @Description: 文件名长度
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/13 上午10:12
     */
    public static int FILE_NAME_LENGTH() {
        return SpringUtils.getBean(ConfigConstants.class).getFileNameLength();
    }

    /*
     * @Author wanglei
     * @Description  上传文件临时保存时间 到期后会被删除 分钟
     * @Date 21:26 2022/11/14
     * @Param
     * @return
     **/
    public static int FILE_TEMP_TIME() {
        return SpringUtils.getBean(ConfigConstants.class).getFileTempTime();
    }

    /*
     * @Author wanglei
     * @Description  文件静态路径
     * @Date 15:29 2022/12/15
     * @Param
     * @return
     **/
    public static String FILE_STATIC_PATH() {
        return SpringUtils.getBean(ConfigConstants.class).getFileStaticPath();
    }

    /**
     * @Description: websocket端口号
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/13 上午10:13
     */
    public static int WEB_SOCKET_PORT() {
        return SpringUtils.getBean(ConfigConstants.class).getWebSocketPort();
    }

    /**
     * @Description: websocket心跳检测时间
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/13 上午10:20
     */
    public static int HEART_BEAT_TIME() {
        return SpringUtils.getBean(ConfigConstants.class).getHeartBeatTime();
    }

    /**
     * @Description: 消息重新发送保持时间
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2023/2/28 下午3:36
     */
    public static Long RESEND_TIME() {
        return SpringUtils.getBean(ConfigConstants.class).getResendTime();
    }

    /*
     * @Author wanglei
     * @Description  websocket 一个主机只能允许一个连接
     * @Date 15:14 2022/12/5
     * @Param
     * @return
     **/
    public static Boolean ONLY_ONE() {
        return SpringUtils.getBean(ConfigConstants.class).getOnlyOne();
    }


    /*
     * @Author wanglei
     * @Description  微信小程序 appid
     * @Date 15:15 2022/12/5
     * @Param
     * @return
     **/
    public static String WX_APPLET_APPID() {
        String appid = SpringUtils.getBean(ConfigConstants.class).getWxAppletAppId();
        if (StringUtils.isBlank(appid)) {
            throw new JrsfException(BaseExceptionEnum.APPID_ISEMPTY);
        }
        return appid;
    }

    /*
     * @Author wanglei
     * @Description  微信小程序Secret
     * @Date 15:15 2022/12/5
     * @Param
     * @return
     **/
    public static String WX_APPLET_SECRET() {

        String secret = SpringUtils.getBean(ConfigConstants.class).getWxAppletSecret();
        if (StringUtils.isBlank(secret)) {
            throw new JrsfException(BaseExceptionEnum.SECRET_ISEMPTY);
        }
        return secret;
    }

    public static String ROLE_ADMIN_USER_NAME() {
        return SpringUtils.getBean(ConfigConstants.class).getUserName();
    }

    public static String ROLE_ADMIN_PASSWORD() {
        return SpringUtils.getBean(ConfigConstants.class).getPassWord();
    }
}
