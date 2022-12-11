SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS table_name;
CREATE TABLE table_name (
  id int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  creator int(32) NULL DEFAULT NULL COMMENT '创建者',
  create_time datetime(0) NOT NULL COMMENT '创建时间',
  modifyer int(32) NULL DEFAULT NULL COMMENT '修改者',
  modify_time datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  del_flag int(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  version int(8)  NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '备注' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;


