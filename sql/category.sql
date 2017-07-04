/*
 Navicat Premium Data Transfer

 Source Server         : 10.211.55.36
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : 10.211.55.36
 Source Database       : itcast

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 06/30/2017 10:40:28 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `category`
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` varchar(32) NOT NULL,
  `cname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `category`
-- ----------------------------
BEGIN;
INSERT INTO `category` VALUES ('1', '手机数码'), ('172934bd636d485c98fd2d3d9cccd409', '运动户外'), ('2', '电脑办公'), ('3', '家具家居'), ('4', '鞋靴箱包'), ('5', '图书音像'), ('59f56ba6ccb84cb591c66298766b83b5', 'aaaa'), ('6', '母婴孕婴'), ('afdba41a139b4320a74904485bdb7719', '汽车用品');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
