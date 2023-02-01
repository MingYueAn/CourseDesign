/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50559
Source Host           : localhost:3306
Source Database       : db_book

Target Server Type    : MYSQL
Target Server Version : 50559
File Encoding         : 65001

Date: 2020-04-10 22:47:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_book`
-- ----------------------------
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookName` varchar(20) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `bookDesc` varchar(1000) DEFAULT NULL,
  `bookTypeId` int(11) DEFAULT NULL,
  `isflag` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_book` (`bookTypeId`),
  CONSTRAINT `FK_t_book` FOREIGN KEY (`bookTypeId`) REFERENCES `t_booktype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES ('12', 'java ', 'xx', '女', '200', 'java牛逼', '5', '0');
INSERT INTO `t_book` VALUES ('20', 'javaMe', '我', '男', '200', '啥事', '5', '0');
INSERT INTO `t_book` VALUES ('21', '从入门到大神', '方', '男', '200', '大神成才之路', '8', '0');

-- ----------------------------
-- Table structure for `t_booktype`
-- ----------------------------
DROP TABLE IF EXISTS `t_booktype`;
CREATE TABLE `t_booktype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bookTypeName` varchar(20) DEFAULT NULL,
  `bookTypeDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_booktype
-- ----------------------------
INSERT INTO `t_booktype` VALUES ('5', 'java', 'java非常掉123');
INSERT INTO `t_booktype` VALUES ('8', 'C语言', '123');
INSERT INTO `t_booktype` VALUES ('11', '爱情类别', '爱情就是整天哭');

-- ----------------------------
-- Table structure for `t_borrow`
-- ----------------------------
DROP TABLE IF EXISTS `t_borrow`;
CREATE TABLE `t_borrow` (
  `bid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `bookid` int(11) DEFAULT NULL,
  `btime` date DEFAULT NULL,
  `rtime` date DEFAULT NULL,
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_borrow
-- ----------------------------
INSERT INTO `t_borrow` VALUES ('5', '3', '11', '2020-04-09', '2020-04-09');
INSERT INTO `t_borrow` VALUES ('6', '3', '12', '2020-04-09', '2020-04-09');
INSERT INTO `t_borrow` VALUES ('7', '3', '11', '2020-04-09', '2020-04-09');
INSERT INTO `t_borrow` VALUES ('8', '5', '11', '2020-04-09', '2020-04-09');
INSERT INTO `t_borrow` VALUES ('9', '5', '12', '2020-04-09', '2020-04-09');
INSERT INTO `t_borrow` VALUES ('10', '4', '12', '2020-04-09', '2020-04-10');
INSERT INTO `t_borrow` VALUES ('11', '3', '11', '2020-04-09', '2020-04-09');
INSERT INTO `t_borrow` VALUES ('12', '4', '15', '2020-04-10', '2020-04-10');
INSERT INTO `t_borrow` VALUES ('13', '7', '21', '2020-04-10', '2020-04-10');

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `userid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('2', 'zy', 'zy123', '1');
INSERT INTO `t_user` VALUES ('3', '张三', '123456', '0');
INSERT INTO `t_user` VALUES ('4', '王五', '123', '0');
INSERT INTO `t_user` VALUES ('5', '赵六', '123', '0');
INSERT INTO `t_user` VALUES ('6', '发哥', '123', '0');
INSERT INTO `t_user` VALUES ('7', '钱七', '123456', '0');
