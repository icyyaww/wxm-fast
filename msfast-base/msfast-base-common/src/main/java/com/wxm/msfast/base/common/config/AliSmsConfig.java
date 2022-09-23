package com.wxm.msfast.base.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Minio 配置信息
 *
 * @author ruoyi
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alisms")
public class AliSmsConfig {

    private String accessKeyId;

    private String secret;

    private String signName;

}
