/*
Navicat MySQL Data Transfer

Source Server         : 112.74.183.177
Source Server Version : 50718
Source Host : 112.74.183.177:3306
Source Database : tio_site

Target Server Type : MYSQL
Target Server Version : 50718
File Encoding : 65001

Date: 2017-07-27 09:05:35
*/

SET  FOREIGN_KEY_CHECKS = 0 ;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP  TABLE  IF  EXISTS  `blog` ;
CREATE  TABLE  `blog`  (
  `id`  int ( 11 )  NOT  NULL  AUTO_INCREMENT ,
  `loginname`  varchar ( 32 )  NOT  NULL ,
