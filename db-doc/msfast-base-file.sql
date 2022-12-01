SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for msf_file
-- ----------------------------
DROP TABLE IF EXISTS `msf_file`;
CREATE TABLE `msf_file`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `creator` int NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modifyer` int NULL DEFAULT NULL COMMENT '修改者',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `del_flag` int NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  `version` int NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  `original` bit(1) NULL DEFAULT b'1' COMMENT '是否是原图',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件路径',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件名称',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '文件状态 TEMP-临时文件 SAVED-已保存的文件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '文件' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;