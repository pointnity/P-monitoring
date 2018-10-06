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
  `pwd`  varchar ( 64 )  NOT  NULL ,
  `salt`  varchar ( 16 )  NOT  NULL ,
  `nick`  varchar ( 16 )  NOT  NULL ,
  `avatar`  varchar ( 64 )  CHARACTER  SET  utf8  NOT  NULL ,
  ip` `  VARCHAR ( 16 )  the CHARACTER  the SET  UTF8  the NOT  NULL  the COMMENT  'registered IP' ,
  `createtime`  timestamp  NOT  NULL  DEFAULT  CURRENT_TIMESTAMP  ON  UPDATE  CURRENT_TIMESTAMP ,
  `status`  tinyint ( 2 )  NOT  NULL  DEFAULT  '1'  COMMENT  'User status, 1: Normal, 2: Logout, 3: Pulled black' ,
  PRIMARY  KEY  ( `id` ),
  KEY  `loginname`  ( `loginname` , `pwd` )
)  ENGINE = the InnoDB  the DEFAULT  the CHARSET = utf8mb4 ;

-- ----------------------------
-- Records of blog
-- ----------------------------

-- ----------------------------
-- Table structure for donate
-- ----------------------------
DROP  TABLE  IF  EXISTS  `donate` ;
CREATE  TABLE  `donate`  (
  `id`  int ( 11 )  NOT  NULL  AUTO_INCREMENT ,
  `amount`  double  NOT  NULL ,
  `name`  varchar ( 16 )  NOT  NULL ,
  `url`  varchar ( 128 )  DEFAULT  NULL  COMMENT  'user or company url' ,
  `time`  timestamp  NOT  NULL  DEFAULT  CURRENT_TIMESTAMP ,
  `way`  varchar ( 32 )  DEFAULT  NULL ,
  `remark`  varchar ( 256 )  DEFAULT  NULL ,
  leavemsg` `  VARCHAR ( 1024 )  the DEFAULT  NULL  the COMMENT  'message' ,
  `myremark`  varchar ( 255 )  DEFAULT  NULL  COMMENT  'Give the author's own comments, do not display ' ,
  PRIMARY  KEY  ( `id` )
)  ENGINE = the InnoDB  the AUTO_INCREMENT = 46 is  the DEFAULT  the CHARSET = utf8mb4 ;

-- ----------------------------
