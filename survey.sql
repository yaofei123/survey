/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.20.89
 Source Server Type    : MySQL
 Source Server Version : 50625
 Source Host           : 10.0.20.89
 Source Database       : survey

 Target Server Type    : MySQL
 Target Server Version : 50625
 File Encoding         : utf-8

 Date: 04/20/2016 07:09:44 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `answer_sheet`
-- ----------------------------
DROP TABLE IF EXISTS `answer_sheet`;
CREATE TABLE `answer_sheet` (
  `id` int(11) NOT NULL,
  `survey_id` int(11) DEFAULT NULL,
  `result` varchar(8000) DEFAULT NULL,
  `post_date` datetime DEFAULT NULL,
  `user_ip` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `config`
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL,
  `site_name` varchar(500) DEFAULT NULL,
  `site_url` varchar(500) DEFAULT NULL,
  `is_open` int(1) DEFAULT NULL,
  `close_word` varchar(1000) DEFAULT NULL,
  `copyright` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `guest_book`
-- ----------------------------
DROP TABLE IF EXISTS `guest_book`;
CREATE TABLE `guest_book` (
  `id` int(11) NOT NULL,
  `guest_user` varchar(50) DEFAULT NULL,
  `guest_content` varchar(5000) DEFAULT NULL,
  `guest_phone` varchar(20) DEFAULT NULL,
  `guest_qq` varchar(20) DEFAULT NULL,
  `guest_email` varchar(100) DEFAULT NULL,
  `guest_post_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `link`
-- ----------------------------
DROP TABLE IF EXISTS `link`;
CREATE TABLE `link` (
  `id` int(11) NOT NULL,
  `url` varchar(500) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `img` varchar(500) DEFAULT NULL,
  `info` varchar(1000) DEFAULT NULL,
  `is_lock` int(11) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `survey_id` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `head` varchar(1000) DEFAULT NULL,
  `body` varchar(8000) DEFAULT NULL,
  `result` varchar(1000) DEFAULT NULL,
  `img` varchar(1000) DEFAULT NULL,
  `jdtz` varchar(1000) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `survey`
-- ----------------------------
DROP TABLE IF EXISTS `survey`;
CREATE TABLE `survey` (
  `id` int(11) NOT NULL,
  `templet_id` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `describe` varchar(500) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `img` varchar(1000) DEFAULT NULL,
  `ip_repeat` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `ip_limit_type` varchar(10) DEFAULT NULL,
  `ip_range` varchar(2000) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `is_open` int(11) DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL,
  `is_audited` int(11) DEFAULT NULL,
  `hits` int(11) DEFAULT NULL,
  `use_hits` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `templet`
-- ----------------------------
DROP TABLE IF EXISTS `templet`;
CREATE TABLE `templet` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `top` varchar(8000) DEFAULT NULL,
  `body` varchar(7500) DEFAULT NULL,
  `bottom` varchar(6000) DEFAULT NULL,
  `default` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `text`
-- ----------------------------
DROP TABLE IF EXISTS `text`;
CREATE TABLE `text` (
  `id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `context` varchar(8000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
