SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS wxm_role;
CREATE TABLE wxm_role (
  id int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  creator int(32) NULL DEFAULT NULL COMMENT '创建者',
  create_time datetime(0) NOT NULL COMMENT '创建时间',
  modifyer int(32) NULL DEFAULT NULL COMMENT '修改者',
  modify_time datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  del_flag int(1) NOT NULL DEFAULT 0 COMMENT '删除标志（0代表存在 1代表删除）',
  version int(8)  NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
  role_name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  description varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色描述',
  is_admin bool not null default true COMMENT '是否管理员',
  is_preset bool not null default true COMMENT '是否预置（预置角色不允许编辑与修改）',
  enable bool not null default true COMMENT '是否启用',
  sort varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO wxm_role(creator, create_time, modifyer, modify_time, del_flag, version, role_name, description, is_admin, is_preset, enable, sort) VALUES(NULL, now(), NULL, NULL, 0, 0, '管理员', '系统内置管理员', 1, 1, 1, '0');
