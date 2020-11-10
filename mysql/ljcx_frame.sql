/*
Navicat MySQL Data Transfer

Source Server         : 123
Source Server Version : 80018
Source Host           : localhost:3306
Source Database       : ljcx_frame

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2019-11-20 11:38:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account_log_login
-- ----------------------------
DROP TABLE IF EXISTS `account_log_login`;
CREATE TABLE `account_log_login` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '关联用户ID',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `login_time` datetime NOT NULL COMMENT '登录时间',
  `login_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录IP',
  `login_type` tinyint(1) unsigned NOT NULL COMMENT '登录类型(1.帐号登录，2.手机登录)',
  `is_success` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登录状态(1.成功,2.失败)',
  `memo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `usr_id_index` (`user_id`) USING BTREE,
  KEY `account_index` (`account`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='登录日志表';

-- ----------------------------
-- Records of account_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for account_send_msg
-- ----------------------------
DROP TABLE IF EXISTS `account_send_msg`;
CREATE TABLE `account_send_msg` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送对象(手机号码或邮箱)',
  `type` tinyint(1) unsigned NOT NULL COMMENT '对象类型',
  `msg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送信息',
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '验证码',
  `expires_time` datetime NOT NULL COMMENT '过期时间',
  `is_success` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '发送状态(1.成功2.失败)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发送时间(创建时间)',
  `is_delete` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `item_index` (`account`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='发送信息记录表';

-- ----------------------------
-- Records of account_send_msg
-- ----------------------------

-- ----------------------------
-- Table structure for account_sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `account_sys_permission`;
CREATE TABLE `account_sys_permission` (
  `id` int(1) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `type` int(1) NOT NULL COMMENT '类型 1菜单 2功能',
  `parent_id` int(1) NOT NULL COMMENT '父级编号',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单地址',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '菜单图标标识',
  `permission` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '授权标识，多个以,分隔',
  `status` int(1) NOT NULL DEFAULT '1' COMMENT '权限状态 1正常 2禁用',
  `memo` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `sort` int(1) NOT NULL DEFAULT '0' COMMENT '排序',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否删除 1未删除 2已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='后台菜单权限表';

-- ----------------------------
-- Records of account_sys_permission
-- ----------------------------
INSERT INTO `account_sys_permission` VALUES ('1', '系统管理', '1', '0', '', 'xe64f', '', '1', '系统设置', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('2', '用户管理', '1', '1', 'modules/user/user_index.html', 'xe605', '', '1', '用户设置', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('3', '用户添加', '2', '2', '', 'admin', 'sys:user:add', '1', '用户添加', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('4', '用户修改', '2', '2', '', 'admin', 'sys:user:update', '1', '用户修改', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('5', '用户删除', '2', '2', '', 'admin', 'sys:user:del', '1', '用户删除', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('6', '用户列表', '2', '2', '', 'admin', 'sys:user:query', '1', '用户列表', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('7', '用户授权', '2', '2', '', 'admin', 'sys:user:grant', '1', '用户授权', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('8', '角色管理', '1', '1', 'modules/user/role_index.html', 'xe6d3', '', '1', '角色设置', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('9', '角色添加', '2', '8', '', 'admin', 'sys:role:add', '1', '角色添加', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('10', '角色修改', '2', '8', '', 'admin', 'sys:role:update', '1', '角色修改', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('11', '角色删除', '2', '8', '', 'admin', 'sys:role:del', '1', '角色删除', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('12', '角色列表', '2', '8', '', 'admin', 'sys:role:query', '1', '角色列表', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('13', '权限管理', '1', '1', 'modules/user/right_index.html', 'xe6d3', '', '1', '权限设置', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('14', '权限添加', '2', '13', '', 'admin', 'sys:menu:add', '1', '权限添加', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('15', '权限修改', '2', '13', '', 'admin', 'sys:menu:update', '1', '权限修改', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('16', '权限删除', '2', '13', '', 'admin', 'sys:menu:del', '1', '权限删除', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('17', '权限列表', '2', '13', '', 'admin', 'sys:menu:query', '1', '权限列表', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('19', '字典管理', '1', '1', 'modules/sys/dict_index.html', 'xe6d3', '', '1', '字典管理', '0', '2019-11-08 15:27:19', '2019-11-08 15:27:19', '1');
INSERT INTO `account_sys_permission` VALUES ('20', '字典添加', '2', '19', '', '', 'sys:dic:add', '1', '字典添加', '0', '2019-11-08 15:27:52', '2019-11-08 15:27:52', '1');
INSERT INTO `account_sys_permission` VALUES ('21', '字典修改', '2', '19', '', '', 'sys:dic:update', '1', '字典修改', '0', '2019-11-08 15:30:00', '2019-11-08 15:30:00', '1');
INSERT INTO `account_sys_permission` VALUES ('22', '字典删除', '2', '19', '', '', 'sys:dic:del', '1', '字典删除', '0', '2019-11-08 15:30:30', '2019-11-08 15:30:30', '1');
INSERT INTO `account_sys_permission` VALUES ('23', '字典列表', '2', '19', '', '', 'sys:dic:query', '1', '字典列表', '0', '2019-11-08 15:30:49', '2019-11-08 15:30:49', '1');
INSERT INTO `account_sys_permission` VALUES ('24', '团队管理', '1', '0', '', 'xe64f', '', '1', '团队管理', '0', '2019-05-23 16:11:49', '2019-10-29 11:36:44', '1');
INSERT INTO `account_sys_permission` VALUES ('30', '团队列表', '1', '24', 'modules/team/list_index.html', 'xe64f', '', '1', '团队列表', '0', '2019-11-13 14:03:21', '2019-11-13 14:03:21', '1');
INSERT INTO `account_sys_permission` VALUES ('31', '列表添加', '2', '30', '', 'admin', 'team:list:add', '1', '列表添加', '0', '2019-11-13 14:03:21', '2019-11-13 14:03:21', '1');
INSERT INTO `account_sys_permission` VALUES ('32', '列表修改', '2', '30', '', 'admin', 'team:list:update', '1', '列表修改', '0', '2019-11-13 14:03:21', '2019-11-13 14:03:21', '1');
INSERT INTO `account_sys_permission` VALUES ('33', '列表删除', '2', '30', '', 'admin', 'team:list:del', '1', '列表删除', '0', '2019-11-13 14:03:21', '2019-11-13 14:03:21', '1');
INSERT INTO `account_sys_permission` VALUES ('34', '列表查询', '2', '30', '', 'admin', 'team:list:query', '1', '列表查询', '0', '2019-11-13 14:03:21', '2019-11-13 14:03:21', '1');
INSERT INTO `account_sys_permission` VALUES ('40', '团队管理', '1', '24', 'modules/team/equipment_index.html', 'xe64f', '', '1', '设备库', '0', '2019-11-13 14:03:22', '2019-11-13 14:03:22', '1');
INSERT INTO `account_sys_permission` VALUES ('41', '设备添加', '2', '40', '', 'admin', 'team:equipment:add', '1', '设备添加', '0', '2019-11-13 14:03:22', '2019-11-13 14:03:22', '1');
INSERT INTO `account_sys_permission` VALUES ('42', '设备修改', '2', '40', '', 'admin', 'team:equipment:update', '1', '设备修改', '0', '2019-11-13 14:03:23', '2019-11-13 14:03:23', '1');
INSERT INTO `account_sys_permission` VALUES ('43', '设备删除', '2', '40', '', 'admin', 'team:equipment:del', '1', '设备删除', '0', '2019-11-13 14:03:23', '2019-11-13 14:03:23', '1');
INSERT INTO `account_sys_permission` VALUES ('44', '设备查询', '2', '40', '', 'admin', 'team:equipment:query', '1', '设备查询', '0', '2019-11-13 14:03:23', '2019-11-13 14:03:23', '1');
INSERT INTO `account_sys_permission` VALUES ('45', '数据管理', '1', '0', '', 'xe605', '', '1', '数据管理', '0', '2019-11-18 17:08:30', '2019-11-18 17:08:30', '1');
INSERT INTO `account_sys_permission` VALUES ('50', '飞行区域', '1', '45', 'modules/data/flyarea.html', 'xe605', '', '1', '飞行区域', '0', '2019-11-18 17:05:15', '2019-11-18 17:05:15', '1');
INSERT INTO `account_sys_permission` VALUES ('51', '飞行区域添加', '2', '50', '', '', 'data:flyarea:add', '1', '飞行区域添加', '0', '2019-11-18 17:05:15', '2019-11-18 17:05:15', '1');
INSERT INTO `account_sys_permission` VALUES ('52', '飞行区域修改', '2', '50', '', '', 'data:flyarea:update', '1', '飞行区域修改', '0', '2019-11-18 17:05:16', '2019-11-18 17:05:16', '1');
INSERT INTO `account_sys_permission` VALUES ('53', '飞行区域删除', '2', '50', '', '', 'data:flyarea:del', '1', '飞行区域删除', '0', '2019-11-18 17:05:16', '2019-11-18 17:05:16', '1');
INSERT INTO `account_sys_permission` VALUES ('54', '飞行区域列表', '2', '50', '', '', 'data:flyarea:query', '1', '飞行区域列表', '0', '2019-11-18 17:05:16', '2019-11-18 17:05:16', '1');
INSERT INTO `account_sys_permission` VALUES ('55', '全景图', '1', '45', 'modules/data/panorama.html', 'xe605', '', '1', '全景图', '0', '2019-11-18 17:06:21', '2019-11-18 17:06:21', '1');
INSERT INTO `account_sys_permission` VALUES ('56', '全景图添加', '2', '55', '', '', 'data:panorama:add', '1', '全景图添加', '0', '2019-11-18 17:06:21', '2019-11-18 17:06:21', '1');
INSERT INTO `account_sys_permission` VALUES ('57', '全景图修改', '2', '55', '', '', 'data:panorama:update', '1', '全景图修改', '0', '2019-11-18 17:06:22', '2019-11-18 17:06:22', '1');
INSERT INTO `account_sys_permission` VALUES ('58', '全景图删除', '2', '55', '', '', 'data:panorama:del', '1', '全景图删除', '0', '2019-11-18 17:06:22', '2019-11-18 17:06:22', '1');
INSERT INTO `account_sys_permission` VALUES ('59', '全景图列表', '2', '55', '', '', 'data:panorama:query', '1', '全景图列表', '0', '2019-11-18 17:06:22', '2019-11-18 17:06:22', '1');
INSERT INTO `account_sys_permission` VALUES ('60', '现场上报', '1', '45', 'modules/data/scenereport.html', 'xe605', '', '1', '现场上报', '0', '2019-11-18 17:06:56', '2019-11-18 17:06:56', '1');
INSERT INTO `account_sys_permission` VALUES ('61', '现场上报添加', '2', '60', '', '', 'data:scenereport:add', '1', '现场上报添加', '0', '2019-11-18 17:06:57', '2019-11-18 17:06:57', '1');
INSERT INTO `account_sys_permission` VALUES ('62', '现场上报修改', '2', '60', '', '', 'data:scenereport:update', '1', '现场上报修改', '0', '2019-11-18 17:06:57', '2019-11-18 17:06:57', '1');
INSERT INTO `account_sys_permission` VALUES ('63', '现场上报删除', '2', '60', '', '', 'data:scenereport:del', '1', '现场上报删除', '0', '2019-11-18 17:06:57', '2019-11-18 17:06:57', '1');
INSERT INTO `account_sys_permission` VALUES ('64', '现场上报列表', '2', '60', '', '', 'data:scenereport:query', '1', '现场上报列表', '0', '2019-11-18 17:06:57', '2019-11-18 17:06:57', '1');
INSERT INTO `account_sys_permission` VALUES ('66', '文件上传与下载', '1', '1', 'modules/sys/file_index.html', 'xe605', '', '1', '文件上传与下载', '0', '2019-11-19 12:19:05', '2019-11-19 12:19:05', '1');
INSERT INTO `account_sys_permission` VALUES ('67', '文件上传与下载添加', '2', '66', '', '', 'sys:file:add', '1', '附件列表添加', '0', '2019-11-19 14:44:22', '2019-11-19 14:44:22', '1');
INSERT INTO `account_sys_permission` VALUES ('68', '文件上传与下载修改', '2', '66', '', '', 'sys:file:update', '1', '附件列表修改', '0', '2019-11-19 14:44:22', '2019-11-19 14:44:22', '1');
INSERT INTO `account_sys_permission` VALUES ('69', '文件上传与下载删除', '2', '66', '', '', 'sys:file:del', '1', '附件列表删除', '0', '2019-11-19 14:44:22', '2019-11-19 14:44:22', '1');
INSERT INTO `account_sys_permission` VALUES ('70', '文件上传与下载列表', '2', '66', '', '', 'sys:file:query', '1', '附件列表列表', '0', '2019-11-19 14:44:22', '2019-11-19 14:44:22', '1');
INSERT INTO `account_sys_permission` VALUES ('71', '任务管理', '1', '0', '', 'xe605', '', '1', '任务管理', '0', '2019-11-19 18:10:20', '2019-11-19 18:10:20', '1');
INSERT INTO `account_sys_permission` VALUES ('72', '任务列表', '1', '71', 'modules/task/list.html', 'xe605', '', '1', '任务列表', '0', '2019-11-19 18:10:21', '2019-11-19 18:10:21', '1');
INSERT INTO `account_sys_permission` VALUES ('73', '任务列表添加', '2', '72', '', '', 'task:list:add', '1', '任务列表添加', '0', '2019-11-19 18:10:21', '2019-11-19 18:10:21', '1');
INSERT INTO `account_sys_permission` VALUES ('74', '任务列表修改', '2', '72', '', '', 'task:list:update', '1', '任务列表修改', '0', '2019-11-19 18:10:21', '2019-11-19 18:10:21', '1');
INSERT INTO `account_sys_permission` VALUES ('75', '任务列表删除', '2', '72', '', '', 'task:list:del', '1', '任务列表删除', '0', '2019-11-19 18:10:21', '2019-11-19 18:10:21', '1');
INSERT INTO `account_sys_permission` VALUES ('76', '任务列表列表', '2', '72', '', '', 'task:list:query', '1', '任务列表列表', '0', '2019-11-19 18:10:22', '2019-11-19 18:10:22', '1');

-- ----------------------------
-- Table structure for account_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `account_sys_role`;
CREATE TABLE `account_sys_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `memo` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `sort` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统角色表';

-- ----------------------------
-- Records of account_sys_role
-- ----------------------------
INSERT INTO `account_sys_role` VALUES ('1', '超级管理员', '超级管理员', '3', '2017-05-01 13:25:39', '2017-10-05 21:59:18', '1');
INSERT INTO `account_sys_role` VALUES ('2', 'APP用户', '使用APP', '2', '2017-08-01 21:47:31', '2017-10-05 21:59:26', '1');
INSERT INTO `account_sys_role` VALUES ('9', '团队管理员', '团队管理员', '0', '2019-11-20 10:15:00', '2019-11-20 10:15:00', '1');
INSERT INTO `account_sys_role` VALUES ('10', '普通用户', '普通用户', '2', '2019-11-20 10:40:15', '2019-11-20 10:40:15', '1');

-- ----------------------------
-- Table structure for account_sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `account_sys_role_permission`;
CREATE TABLE `account_sys_role_permission` (
  `role_id` int(11) unsigned NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`,`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限关系表';

-- ----------------------------
-- Records of account_sys_role_permission
-- ----------------------------
INSERT INTO `account_sys_role_permission` VALUES ('1', '1');
INSERT INTO `account_sys_role_permission` VALUES ('1', '2');
INSERT INTO `account_sys_role_permission` VALUES ('1', '3');
INSERT INTO `account_sys_role_permission` VALUES ('1', '4');
INSERT INTO `account_sys_role_permission` VALUES ('1', '5');
INSERT INTO `account_sys_role_permission` VALUES ('1', '6');
INSERT INTO `account_sys_role_permission` VALUES ('1', '7');
INSERT INTO `account_sys_role_permission` VALUES ('1', '8');
INSERT INTO `account_sys_role_permission` VALUES ('1', '9');
INSERT INTO `account_sys_role_permission` VALUES ('1', '10');
INSERT INTO `account_sys_role_permission` VALUES ('1', '11');
INSERT INTO `account_sys_role_permission` VALUES ('1', '12');
INSERT INTO `account_sys_role_permission` VALUES ('1', '13');
INSERT INTO `account_sys_role_permission` VALUES ('1', '14');
INSERT INTO `account_sys_role_permission` VALUES ('1', '15');
INSERT INTO `account_sys_role_permission` VALUES ('1', '16');
INSERT INTO `account_sys_role_permission` VALUES ('1', '17');
INSERT INTO `account_sys_role_permission` VALUES ('1', '19');
INSERT INTO `account_sys_role_permission` VALUES ('1', '20');
INSERT INTO `account_sys_role_permission` VALUES ('1', '21');
INSERT INTO `account_sys_role_permission` VALUES ('1', '22');
INSERT INTO `account_sys_role_permission` VALUES ('1', '23');
INSERT INTO `account_sys_role_permission` VALUES ('1', '24');
INSERT INTO `account_sys_role_permission` VALUES ('1', '30');
INSERT INTO `account_sys_role_permission` VALUES ('1', '31');
INSERT INTO `account_sys_role_permission` VALUES ('1', '32');
INSERT INTO `account_sys_role_permission` VALUES ('1', '33');
INSERT INTO `account_sys_role_permission` VALUES ('1', '34');
INSERT INTO `account_sys_role_permission` VALUES ('1', '40');
INSERT INTO `account_sys_role_permission` VALUES ('1', '41');
INSERT INTO `account_sys_role_permission` VALUES ('1', '42');
INSERT INTO `account_sys_role_permission` VALUES ('1', '43');
INSERT INTO `account_sys_role_permission` VALUES ('1', '44');
INSERT INTO `account_sys_role_permission` VALUES ('1', '45');
INSERT INTO `account_sys_role_permission` VALUES ('1', '50');
INSERT INTO `account_sys_role_permission` VALUES ('1', '51');
INSERT INTO `account_sys_role_permission` VALUES ('1', '52');
INSERT INTO `account_sys_role_permission` VALUES ('1', '53');
INSERT INTO `account_sys_role_permission` VALUES ('1', '54');
INSERT INTO `account_sys_role_permission` VALUES ('1', '55');
INSERT INTO `account_sys_role_permission` VALUES ('1', '56');
INSERT INTO `account_sys_role_permission` VALUES ('1', '57');
INSERT INTO `account_sys_role_permission` VALUES ('1', '58');
INSERT INTO `account_sys_role_permission` VALUES ('1', '59');
INSERT INTO `account_sys_role_permission` VALUES ('1', '60');
INSERT INTO `account_sys_role_permission` VALUES ('1', '61');
INSERT INTO `account_sys_role_permission` VALUES ('1', '62');
INSERT INTO `account_sys_role_permission` VALUES ('1', '63');
INSERT INTO `account_sys_role_permission` VALUES ('1', '64');
INSERT INTO `account_sys_role_permission` VALUES ('1', '66');
INSERT INTO `account_sys_role_permission` VALUES ('1', '67');
INSERT INTO `account_sys_role_permission` VALUES ('1', '68');
INSERT INTO `account_sys_role_permission` VALUES ('1', '69');
INSERT INTO `account_sys_role_permission` VALUES ('1', '70');
INSERT INTO `account_sys_role_permission` VALUES ('1', '71');
INSERT INTO `account_sys_role_permission` VALUES ('1', '72');
INSERT INTO `account_sys_role_permission` VALUES ('1', '73');
INSERT INTO `account_sys_role_permission` VALUES ('1', '74');
INSERT INTO `account_sys_role_permission` VALUES ('1', '75');
INSERT INTO `account_sys_role_permission` VALUES ('1', '76');
INSERT INTO `account_sys_role_permission` VALUES ('2', '1');
INSERT INTO `account_sys_role_permission` VALUES ('2', '2');
INSERT INTO `account_sys_role_permission` VALUES ('2', '3');
INSERT INTO `account_sys_role_permission` VALUES ('2', '4');
INSERT INTO `account_sys_role_permission` VALUES ('2', '5');
INSERT INTO `account_sys_role_permission` VALUES ('2', '6');
INSERT INTO `account_sys_role_permission` VALUES ('2', '7');
INSERT INTO `account_sys_role_permission` VALUES ('2', '8');
INSERT INTO `account_sys_role_permission` VALUES ('2', '9');
INSERT INTO `account_sys_role_permission` VALUES ('2', '10');
INSERT INTO `account_sys_role_permission` VALUES ('2', '11');
INSERT INTO `account_sys_role_permission` VALUES ('2', '12');
INSERT INTO `account_sys_role_permission` VALUES ('2', '13');
INSERT INTO `account_sys_role_permission` VALUES ('2', '14');
INSERT INTO `account_sys_role_permission` VALUES ('2', '15');
INSERT INTO `account_sys_role_permission` VALUES ('2', '16');
INSERT INTO `account_sys_role_permission` VALUES ('2', '17');
INSERT INTO `account_sys_role_permission` VALUES ('2', '19');
INSERT INTO `account_sys_role_permission` VALUES ('2', '20');
INSERT INTO `account_sys_role_permission` VALUES ('2', '21');
INSERT INTO `account_sys_role_permission` VALUES ('2', '22');
INSERT INTO `account_sys_role_permission` VALUES ('2', '23');
INSERT INTO `account_sys_role_permission` VALUES ('2', '24');
INSERT INTO `account_sys_role_permission` VALUES ('2', '30');
INSERT INTO `account_sys_role_permission` VALUES ('2', '31');
INSERT INTO `account_sys_role_permission` VALUES ('2', '32');
INSERT INTO `account_sys_role_permission` VALUES ('2', '33');
INSERT INTO `account_sys_role_permission` VALUES ('2', '34');
INSERT INTO `account_sys_role_permission` VALUES ('2', '40');
INSERT INTO `account_sys_role_permission` VALUES ('2', '41');
INSERT INTO `account_sys_role_permission` VALUES ('2', '42');
INSERT INTO `account_sys_role_permission` VALUES ('2', '43');
INSERT INTO `account_sys_role_permission` VALUES ('2', '44');
INSERT INTO `account_sys_role_permission` VALUES ('2', '45');
INSERT INTO `account_sys_role_permission` VALUES ('2', '50');
INSERT INTO `account_sys_role_permission` VALUES ('2', '51');
INSERT INTO `account_sys_role_permission` VALUES ('2', '52');
INSERT INTO `account_sys_role_permission` VALUES ('2', '53');
INSERT INTO `account_sys_role_permission` VALUES ('2', '54');
INSERT INTO `account_sys_role_permission` VALUES ('2', '55');
INSERT INTO `account_sys_role_permission` VALUES ('2', '56');
INSERT INTO `account_sys_role_permission` VALUES ('2', '57');
INSERT INTO `account_sys_role_permission` VALUES ('2', '58');
INSERT INTO `account_sys_role_permission` VALUES ('2', '59');
INSERT INTO `account_sys_role_permission` VALUES ('2', '60');
INSERT INTO `account_sys_role_permission` VALUES ('2', '61');
INSERT INTO `account_sys_role_permission` VALUES ('2', '62');
INSERT INTO `account_sys_role_permission` VALUES ('2', '63');
INSERT INTO `account_sys_role_permission` VALUES ('2', '64');
INSERT INTO `account_sys_role_permission` VALUES ('2', '66');
INSERT INTO `account_sys_role_permission` VALUES ('2', '67');
INSERT INTO `account_sys_role_permission` VALUES ('2', '68');
INSERT INTO `account_sys_role_permission` VALUES ('2', '69');
INSERT INTO `account_sys_role_permission` VALUES ('2', '70');
INSERT INTO `account_sys_role_permission` VALUES ('2', '71');
INSERT INTO `account_sys_role_permission` VALUES ('2', '72');
INSERT INTO `account_sys_role_permission` VALUES ('2', '73');
INSERT INTO `account_sys_role_permission` VALUES ('2', '74');
INSERT INTO `account_sys_role_permission` VALUES ('2', '75');
INSERT INTO `account_sys_role_permission` VALUES ('2', '76');
INSERT INTO `account_sys_role_permission` VALUES ('7', '1');
INSERT INTO `account_sys_role_permission` VALUES ('7', '2');
INSERT INTO `account_sys_role_permission` VALUES ('7', '3');
INSERT INTO `account_sys_role_permission` VALUES ('7', '4');

-- ----------------------------
-- Table structure for account_sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `account_sys_role_user`;
CREATE TABLE `account_sys_role_user` (
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户角色关系表';

-- ----------------------------
-- Records of account_sys_role_user
-- ----------------------------
INSERT INTO `account_sys_role_user` VALUES ('1', '1');
INSERT INTO `account_sys_role_user` VALUES ('41', '2');
INSERT INTO `account_sys_role_user` VALUES ('42', '2');
INSERT INTO `account_sys_role_user` VALUES ('43', '2');
INSERT INTO `account_sys_role_user` VALUES ('44', '2');
INSERT INTO `account_sys_role_user` VALUES ('53', '1');

-- ----------------------------
-- Table structure for account_user_account
-- ----------------------------
DROP TABLE IF EXISTS `account_user_account`;
CREATE TABLE `account_user_account` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '关联用户ID',
  `account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '账号',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '帐号类型 1.普通账号，2.手机帐号，3.邮箱帐号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '盐值',
  `last_login_ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '最后一次登录IP',
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登录时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间（创建时间）',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态 1.正常，2.删除',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `usr_id_index` (`user_id`) USING BTREE,
  KEY `account_password_index` (`account`,`password`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户账号表';

-- ----------------------------
-- Records of account_user_account
-- ----------------------------
INSERT INTO `account_user_account` VALUES ('53', '1', '18573519915', '1', 'eda1421e6516105f310d8a0f2005e143', 'b379', '', '2019-10-25 11:04:43', '2019-10-25 11:04:43', '2019-10-25 11:04:43', '1');

-- ----------------------------
-- Table structure for account_user_base
-- ----------------------------
DROP TABLE IF EXISTS `account_user_base`;
CREATE TABLE `account_user_base` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `type` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '用户类型(1.超级管理员,2.普通用户)',
  `header_url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '账户头像',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态（1.启用，2.禁用）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `is_delete` tinyint(1) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `username_index` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户基础表';

-- ----------------------------
-- Records of account_user_base
-- ----------------------------
INSERT INTO `account_user_base` VALUES ('1', 'rY1eOeX', '超级管理员', '', '', '1', '', '1', '2019-05-29 23:37:35', '2019-05-29 23:37:35', '1');
INSERT INTO `account_user_base` VALUES ('41', '64G8D66', '小兰', '', '', '1', '', '1', '2019-05-29 23:45:05', '2019-05-29 23:45:05', '1');
INSERT INTO `account_user_base` VALUES ('42', 'CVW5UT3', '小溪', '', '', '1', '', '1', '2019-05-30 19:42:20', '2019-05-30 19:42:20', '1');
INSERT INTO `account_user_base` VALUES ('43', '09g95BK', '三儿', '', '', '1', '', '1', '2019-06-03 20:24:32', '2019-06-03 20:24:32', '1');
INSERT INTO `account_user_base` VALUES ('44', 'n592qAM', '二五', '', '', '1', '', '1', '2019-06-03 20:24:44', '2019-06-03 20:24:44', '1');
INSERT INTO `account_user_base` VALUES ('45', '9aon4XZ', '一期', '', '', '1', '', '1', '2019-06-03 20:24:57', '2019-06-03 20:24:57', '1');
INSERT INTO `account_user_base` VALUES ('46', 'tkU81nl', '过儿', '', '', '1', '', '1', '2019-06-03 20:25:08', '2019-06-03 20:25:08', '1');
INSERT INTO `account_user_base` VALUES ('47', 'Bm24MS2', '狒狒', '', '', '1', '', '1', '2019-06-03 20:25:16', '2019-06-03 20:25:16', '1');
INSERT INTO `account_user_base` VALUES ('48', '1qO2aFR', '兔子', '', '', '1', '', '1', '2019-06-03 20:25:24', '2019-06-03 20:25:24', '1');
INSERT INTO `account_user_base` VALUES ('49', 'y4zA0FC', '冯总', '', '', '1', '', '1', '2019-06-03 20:25:37', '2019-06-03 20:25:37', '1');
INSERT INTO `account_user_base` VALUES ('50', 'd25h478', '巴铁', '', '', '1', '', '1', '2019-06-03 20:25:49', '2019-06-03 20:25:49', '1');
INSERT INTO `account_user_base` VALUES ('51', 'S8T4069', '伺服', '', '', '1', '', '1', '2019-06-03 20:25:55', '2019-06-03 20:25:55', '1');
INSERT INTO `account_user_base` VALUES ('52', 'ym1993A', '壳子', '', '', '1', '', '1', '2019-06-03 20:26:00', '2019-06-03 20:26:00', '1');

-- ----------------------------
-- Table structure for account_user_security
-- ----------------------------
DROP TABLE IF EXISTS `account_user_security`;
CREATE TABLE `account_user_security` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `usr_id` bigint(20) unsigned NOT NULL COMMENT '关联用户ID',
  `question_first` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第一个问题',
  `answer_first` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第一个答案',
  `question_second` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第二个问题',
  `answer_second` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第二个答案',
  `question_third` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三个问题',
  `answer_third` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '第三个答案',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `usr_id_index` (`usr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户安全表';

-- ----------------------------
-- Records of account_user_security
-- ----------------------------

-- ----------------------------
-- Table structure for ljcx_car_info
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_car_info`;
CREATE TABLE `ljcx_car_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `last_location` varchar(255) NOT NULL DEFAULT '',
  `create_user` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备车信息表';

-- ----------------------------
-- Records of ljcx_car_info
-- ----------------------------
INSERT INTO `ljcx_car_info` VALUES ('1', '小车车', '123,123', '1', '2019-11-18 14:23:44', '2019-11-18 14:23:44', '2');
INSERT INTO `ljcx_car_info` VALUES ('2', '123123', '', '1', '2019-11-18 14:23:05', '2019-11-18 14:23:05', '2');
INSERT INTO `ljcx_car_info` VALUES ('3', '222', '', '1', '2019-11-18 15:06:27', '2019-11-18 15:06:27', '1');

-- ----------------------------
-- Table structure for ljcx_fly_area
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_fly_area`;
CREATE TABLE `ljcx_fly_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL COMMENT '类型',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `create_user` bigint(20) NOT NULL COMMENT '上传人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='飞行区域';

-- ----------------------------
-- Records of ljcx_fly_area
-- ----------------------------
INSERT INTO `ljcx_fly_area` VALUES ('1', '1', '666', '1', '2019-11-18 20:04:08', '2');
INSERT INTO `ljcx_fly_area` VALUES ('2', '1', '123', '1', '2019-11-18 20:04:19', '1');

-- ----------------------------
-- Table structure for ljcx_panorama
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_panorama`;
CREATE TABLE `ljcx_panorama` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL COMMENT '名称',
  `url` varchar(255) NOT NULL COMMENT '图片地址',
  `lng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '上传人横坐标',
  `lat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '上传人纵坐标',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '地址',
  `is_share` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否共享，1.共享，2.不共享',
  `create_user` bigint(20) NOT NULL COMMENT '上传人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='全景图';

-- ----------------------------
-- Records of ljcx_panorama
-- ----------------------------
INSERT INTO `ljcx_panorama` VALUES ('1', '123', '2', '', '', '222', '1', '1', '2019-11-18 20:35:27', '1');
INSERT INTO `ljcx_panorama` VALUES ('2', '', '', '', '', '', '1', '1', '2019-11-18 20:47:26', '2');
INSERT INTO `ljcx_panorama` VALUES ('3', '666666', '666666', '', '', '111', '2', '1', '2019-11-18 20:47:51', '1');

-- ----------------------------
-- Table structure for ljcx_scene_report
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_scene_report`;
CREATE TABLE `ljcx_scene_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '服务类型',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '内容',
  `lng` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '横坐标',
  `lat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '纵坐标',
  `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
  `create_user` bigint(20) NOT NULL COMMENT '上传人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='现场上报';

-- ----------------------------
-- Records of ljcx_scene_report
-- ----------------------------
INSERT INTO `ljcx_scene_report` VALUES ('1', '1', '1232231231', '', '', '123123', '1', '2019-11-19 10:07:08', '2');
INSERT INTO `ljcx_scene_report` VALUES ('2', '1', '的撒的', '', '', '阿斯顿', '1', '2019-11-19 10:07:30', '1');

-- ----------------------------
-- Table structure for ljcx_task
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_task`;
CREATE TABLE `ljcx_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `team_id` bigint(20) NOT NULL COMMENT '团队ID',
  `name` varchar(255) NOT NULL COMMENT '名称',
  `type` tinyint(4) NOT NULL COMMENT '任务类型',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '任务内容',
  `create_user` bigint(20) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务列表';

-- ----------------------------
-- Records of ljcx_task
-- ----------------------------
INSERT INTO `ljcx_task` VALUES ('1', '5', '得到的', '1', '666', '1', '2019-11-19 20:44:10', '1');
INSERT INTO `ljcx_task` VALUES ('2', '4', 'adasdad21321qwdasdasd3213sadaasdasds', '1', '12312', '1', '2019-11-20 09:59:42', '2');
INSERT INTO `ljcx_task` VALUES ('3', '4', '11122', '1', '3123', '1', '2019-11-20 09:59:42', '2');

-- ----------------------------
-- Table structure for ljcx_task_records
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_task_records`;
CREATE TABLE `ljcx_task_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `task_id` bigint(20) NOT NULL COMMENT '任务ID',
  `perform_user` bigint(20) NOT NULL COMMENT '执行用户',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '结束时间',
  `complete_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '完成状态（0.未完成，1.正在进行，2.已完成）',
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='任务记录';

-- ----------------------------
-- Records of ljcx_task_records
-- ----------------------------
INSERT INTO `ljcx_task_records` VALUES ('1', '1', '1', '2019-11-19 20:46:19', '2019-11-20 20:46:09', '1', '1');

-- ----------------------------
-- Table structure for ljcx_team_info
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_team_info`;
CREATE TABLE `ljcx_team_info` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '管理员ID',
  `name` varchar(255) NOT NULL COMMENT '姓名',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '描述',
  `create_user` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) NOT NULL DEFAULT '1' COMMENT '删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='团队信息表';

-- ----------------------------
-- Records of ljcx_team_info
-- ----------------------------
INSERT INTO `ljcx_team_info` VALUES ('1', '1', '12', '21321', '1', '2019-11-15 11:08:21', '2');
INSERT INTO `ljcx_team_info` VALUES ('2', '42', '撒旦', '撒大苏打', '1', '2019-11-18 11:14:49', '2');
INSERT INTO `ljcx_team_info` VALUES ('3', '41', '123顶顶顶顶', '多读点', '1', '2019-11-18 14:31:17', '2');
INSERT INTO `ljcx_team_info` VALUES ('4', '44', '大苏打123', '实打实123', '1', '2019-11-18 17:53:55', '1');
INSERT INTO `ljcx_team_info` VALUES ('5', '42', '123', '21321', '1', '2019-11-15 14:33:42', '1');
INSERT INTO `ljcx_team_info` VALUES ('6', '42', '123', '21321', '1', '2019-11-18 17:55:59', '2');
INSERT INTO `ljcx_team_info` VALUES ('7', '1', '撒打算', '231', '1', '2019-11-18 11:14:49', '2');
INSERT INTO `ljcx_team_info` VALUES ('8', '42', '顶顶顶顶', 'ddd', '1', '2019-11-18 17:55:45', '1');

-- ----------------------------
-- Table structure for ljcx_team_relationship
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_team_relationship`;
CREATE TABLE `ljcx_team_relationship` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `team_id` bigint(20) NOT NULL COMMENT '团队ID',
  `m_id` bigint(20) NOT NULL,
  `m_type` tinyint(4) NOT NULL,
  `create_user` bigint(20) NOT NULL COMMENT '创建用户',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='团队关系表';

-- ----------------------------
-- Records of ljcx_team_relationship
-- ----------------------------
INSERT INTO `ljcx_team_relationship` VALUES ('3', '3', '1', '1', '1', '2019-11-15 18:39:55');
INSERT INTO `ljcx_team_relationship` VALUES ('9', '5', '1', '2', '1', '2019-11-18 11:02:32');
INSERT INTO `ljcx_team_relationship` VALUES ('10', '7', '1', '2', '1', '2019-11-18 11:03:12');
INSERT INTO `ljcx_team_relationship` VALUES ('14', '3', '2', '1', '1', '2019-11-18 14:31:02');
INSERT INTO `ljcx_team_relationship` VALUES ('15', '3', '4', '1', '1', '2019-11-18 14:31:02');
INSERT INTO `ljcx_team_relationship` VALUES ('17', '4', '4', '1', '1', '2019-11-18 14:36:11');
INSERT INTO `ljcx_team_relationship` VALUES ('44', '4', '1', '1', '1', '2019-11-20 10:02:13');
INSERT INTO `ljcx_team_relationship` VALUES ('45', '4', '2', '1', '1', '2019-11-20 10:02:13');
INSERT INTO `ljcx_team_relationship` VALUES ('46', '4', '5', '1', '1', '2019-11-20 10:02:13');

-- ----------------------------
-- Table structure for ljcx_uav_info
-- ----------------------------
DROP TABLE IF EXISTS `ljcx_uav_info`;
CREATE TABLE `ljcx_uav_info` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `speed` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '速度',
  `high` decimal(10,0) NOT NULL DEFAULT '0' COMMENT '高度',
  `course` varchar(255) NOT NULL DEFAULT '' COMMENT '航向',
  `electricity` int(11) NOT NULL DEFAULT '100' COMMENT '电池',
  `network` varchar(255) NOT NULL DEFAULT '' COMMENT '网络',
  `create_user` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='无人机信息表';

-- ----------------------------
-- Records of ljcx_uav_info
-- ----------------------------
INSERT INTO `ljcx_uav_info` VALUES ('1', '丹妹3号', '0', '0', '', '100', '', '1', '2019-11-18 15:03:10', '2019-11-18 15:03:10', '1');
INSERT INTO `ljcx_uav_info` VALUES ('2', '123', '0', '0', '', '100', '', '1', '2019-11-18 12:17:36', '2019-11-18 12:17:36', '1');
INSERT INTO `ljcx_uav_info` VALUES ('3', '', '0', '0', '', '100', '', '1', '2019-11-18 14:20:52', '2019-11-18 14:20:52', '2');
INSERT INTO `ljcx_uav_info` VALUES ('4', '多读点', '0', '0', '', '100', '', '1', '2019-11-18 12:30:17', '2019-11-18 12:30:17', '1');
INSERT INTO `ljcx_uav_info` VALUES ('5', '丹妹2号', '0', '0', '', '100', '', '1', '2019-11-18 15:00:17', '2019-11-18 15:00:17', '1');

-- ----------------------------
-- Table structure for sys_dic
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic`;
CREATE TABLE `sys_dic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL COMMENT '编码',
  `name` varchar(255) NOT NULL,
  `memo` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `sort` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dic
-- ----------------------------
INSERT INTO `sys_dic` VALUES ('1', 'sex', '性别', '', '0', '2019-11-11 18:27:17', '1');

-- ----------------------------
-- Table structure for sys_dic_desc
-- ----------------------------
DROP TABLE IF EXISTS `sys_dic_desc`;
CREATE TABLE `sys_dic_desc` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `id` int(11) NOT NULL,
  `code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `memo` varchar(255) NOT NULL DEFAULT '',
  `sort` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_user` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典详情表';

-- ----------------------------
-- Records of sys_dic_desc
-- ----------------------------
INSERT INTO `sys_dic_desc` VALUES ('1', '1', 'sex', '男', '', '0', '2019-11-11 15:30:07', '1');
INSERT INTO `sys_dic_desc` VALUES ('2', '2', 'sex', '女', '', '0', '2019-11-11 15:30:14', '1');
INSERT INTO `sys_dic_desc` VALUES ('3', '1', '2222', '123', '111', '11', '2019-11-11 18:25:05', '1');
INSERT INTO `sys_dic_desc` VALUES ('4', '3', 'sex', '111', '2222', '333', '2019-11-11 17:26:58', '1');

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `m_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联id',
  `m_src` varchar(255) NOT NULL DEFAULT '' COMMENT '关联来源',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件名称',
  `size` bigint(11) NOT NULL COMMENT '文件大小',
  `file_path` varchar(255) NOT NULL COMMENT '上传文件路径',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `suffix` varchar(255) NOT NULL COMMENT '文件后缀名',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='附件列表';

-- ----------------------------
-- Records of sys_file
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) NOT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=397 DEFAULT CHARSET=utf8 COMMENT='系统日志表';


-- ------------------------------------
 2019 / 11 / 20 >>>>>>>>>>>>>>>>>>>>>
-- ------------------------------------

-- ------------------------------------
 2019 / 11 / 25 >>>>>>>>>>>>>>>>>>>>>
-- ------------------------------------
alter table account_user_base add column total_hours decimal comment '总时长' not null default 0;
alter table account_user_base add column total_mileage decimal comment '总里程' not null default 0;
alter table account_user_base add column user_sign varchar(255) comment '用户签名' not null default "";
alter table account_user_base add column sign_expire_time datetime comment '签名过期时间';

-- ------------------------------------
 2019 / 11 / 28 >>>>>>>>>>>>>>>>>>>>>
-- ------------------------------------

alter table ljcx_uav_info add column `no` varchar(64) comment '序列号' not null;
alter table ljcx_uav_info add column `model` varchar(64) comment '型号' not null default "";
alter table ljcx_uav_info add column `status` varchar(64) comment '实时状态1.下线，2.上线，3.异常' not null default 1;
alter table ljcx_car_info add column `no` varchar(64) comment '序列号' not null;
alter table ljcx_car_info add column `model` varchar(64) comment '型号' not null default "";
alter table ljcx_car_info add column `status` varchar(64) comment '实时状态，1.下线，2.上线，3.异常' not null default 1;
alter table ljcx_scene_report drop column `type`
alter table ljcx_scene_report add column `team_id` bigint comment '团队ID' not null;