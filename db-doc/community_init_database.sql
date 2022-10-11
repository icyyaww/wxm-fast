SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS fr_user;
CREATE TABLE fr_user (
                         id int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
                         creator int(32) NULL DEFAULT NULL COMMENT '创建者',
                         create_time datetime(0) NOT NULL COMMENT '创建时间',
                         modifyer int(32) NULL DEFAULT NULL COMMENT '修改者',
                         modify_time datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
                         del_flag int(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
                         version int(8)  NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
                         phone varchar(20) NOT NULL COMMENT '登陆账号',
                         password varchar(255) NOT NULL COMMENT '账号密码',
                         head_portrait varchar(255) NULL COMMENT '头像',
                         nick_name varchar(255) NOT NULL COMMENT '昵称',
                         birthday datetime(0) NOT NULL COMMENT '出生日期',
                         lon varchar(50) NULL COMMENT '经度',
                         lat varchar(50) NULL COMMENT '纬度',
                         city varchar(50) NULL COMMENT '城市',
                         gender varchar(10) NOT NULL COMMENT '性别',
                         PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
