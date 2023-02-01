/*
 Navicat MySQL Data Transfer

 Source Server         : mysql5
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : weibo

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 01/02/2023 22:18:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for attentionfans
-- ----------------------------
DROP TABLE IF EXISTS `attentionfans`;
CREATE TABLE `attentionfans`  (
  `attention_id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '关注者账号',
  `fans_id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '粉丝账号'
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of attentionfans
-- ----------------------------
INSERT INTO `attentionfans` VALUES ('123', '123');

-- ----------------------------
-- Table structure for personal
-- ----------------------------
DROP TABLE IF EXISTS `personal`;
CREATE TABLE `personal`  (
  `id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '个人账号',
  `password` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '个人密码',
  `power` varchar(5) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '访客' COMMENT '个人权限（访客、管理员）',
  `login_success` varchar(5) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '否' COMMENT '是否登录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of personal
-- ----------------------------
INSERT INTO `personal` VALUES ('123', '123456', '访客', '否');
INSERT INTO `personal` VALUES ('admin', 'admin', '管理员', '否');

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor`  (
  `id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '访客账号',
  `visitor_name` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '访客昵称',
  `visitor_sex` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '访客性别',
  `visitor_birthday` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '访客生日',
  `visitor_yes_no` varchar(20) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL DEFAULT '是' COMMENT '是否登录',
  PRIMARY KEY (`id`, `visitor_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of visitor
-- ----------------------------
INSERT INTO `visitor` VALUES ('123', '用户123', '请编辑', '请编辑', '否');

-- ----------------------------
-- Table structure for weibo
-- ----------------------------
DROP TABLE IF EXISTS `weibo`;
CREATE TABLE `weibo`  (
  `weibo_id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '微博编号',
  `writer_id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '作者账号',
  `reader_id` varchar(50) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '读者账号',
  `weibo_content` varchar(1000) CHARACTER SET gbk COLLATE gbk_chinese_ci NOT NULL COMMENT '微博内容',
  PRIMARY KEY (`weibo_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = gbk COLLATE = gbk_chinese_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of weibo
-- ----------------------------
INSERT INTO `weibo` VALUES ('123.1', '123', '123', '123');
INSERT INTO `weibo` VALUES ('123.2', '123', '123', '你好时间');

SET FOREIGN_KEY_CHECKS = 1;
