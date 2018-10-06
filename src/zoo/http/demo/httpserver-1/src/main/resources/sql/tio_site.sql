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
-- Records of donate
-- ----------------------------
INSERT  INTO  `donate`  VALUES  ( '1' ,  '20' ,  'Ma Wenlong ' ,  null ,  '2016-12-13 10:02:10 ' ,  'Code Cloud ' ,  'qq:237903488 ' ,  'Thank you for your open source Project! I hope to give more detailed documentation to my qq is 237903488' ,  null );
INSERT  INTO  `donate`  VALUES  ( '2' ,  '10' ,  'bad kid' ,  null ,  '2016-12-13 10:19:37' ,  'code cloud' ,  null ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '3' ,  '10' ,  'draper' ,  'https://gitee.com/websterlu' ,  '2016-12-13 10:20:35' ,  'Code Cloud' ,  ' Qq:10558813' ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '4' ,  '20' ,  ' Lily ' ,  null ,  '2016-12-13 10:25:16 ' ,  'Code Cloud ' ,  'Nanjing Yu Yeteng Information Technology Co., Ltd., It is also a colleague of the author who has provided server support for t-io for a long time ,  'to keep up' ,  null );
INSERT  INTO  `donate`  VALUES  ( '5' ,  '5' ,  'Titanium Walnut' ,  'https://gitee.com/wu1g119' ,  '2016-12-22 15:00:53' ,  'Code Cloud' ,  null ,  'Although not used but it looks very good' ,  null );
INSERT  INTO  `donate`  VALUES  ( '6' ,  '5' ,  'love confused meallon' ,  'https://gitee.com/meallon' ,  '2016-12-22 17:07:16' ,  'code Cloud' ,  'qq: 376487342, former colleague' ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '7' ,  '10' ,  'lihc super brother' ,  'https://gitee.com/lihc2015' ,  '2017-01-02 17:58:17' ,  'code cloud' ,  null ,  'Thank you for your open source project! Learn NIO from you, what do you need to pay attention to when designing and developing systems like IM' ,  null );
INSERT  INTO  `donate`  VALUES  ( '8' ,  '5' ,  'WhatAKitty' ,  'https://gitee.com/wustart' ,  '2017-01-08 00:07:37' ,  'Code Cloud' ,  null ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '9' ,  '5' ,  'mahengyang' ,  'https://gitee.com/enyo' ,  '2017-01-14 20:35:21' ,  'code cloud' ,  null ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '10' ,  '10' ,  'commonrpc' ,  'https://gitee.com/284520459' ,  '2017-01-19 16:55:37' ,  'Code Cloud' ,  ' Commonrpc author' ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '11' ,  '5' ,  'wilsonbrant' ,  'https://gitee.com/wilsonbrant' ,  '2017-02-04 15:10:48' ,  'code cloud' ,  null ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '12' ,  '10' ,  'small room' ,  null ,  '2017-02-07 14:14:00 ' ,  'code cloud ' ,  'qq:2667624395' ,  'Thank you for your Open source project, thank you for your selfless efforts!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '13' ,  '10' ,  'YY Guardian Angel YY' ,  'https://gitee.com/yyljlyy' ,  '2017-02-15 00:13:31' ,  'Code Cloud ' ,  'This friend tried to read the tio source code, and donated many times, the author is a little embarrassed ' ,  'Thank you for your open source project!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '14' ,  '100' ,  'donors request anonymous' ,  '' ,  '2017-03-19 10:02:53' ,  'code cloud' ,  'donors request anonymity, thanks This classmate, the author has privately commented on the donation information ' ,  'open source is not easy to thank open source' ,  'qq: 787702029, Zhang Xiaofan, a white whistle, https://gitee.com/SJRSB' );
INSERT  INTO  `donate`  VALUES  ( '15' ,  '188' ,  'YY guardian angel YY' ,  'https://gitee.com/yyljlyy' ,  '2017-03-26 09:33:48' ,  'Code Cloud ' ,  'This friend tried to read the tio source code, and donated many times, the author is a little embarrassed ' ,  'Let the talent-aio get better and better!' ,  null );
INSERT  INTO  `donate`  VALUES  ( '16' ,  '100' ,  'wandering' ,  null ,  '2017-04-03 13:16:10' ,  'code cloud' ,  null ,  'Thank you for your open source project!' ,  Null );
INSERT  INTO  `donate`  VALUES  ( '17' ,  '58' ,  'YY guardian angel YY' ,  'https://gitee.com/yyljlyy' ,  '2017-04-05 23:22:43' ,  'Code Cloud ' ,  ' This friend tried to read the tio source code, and donated many times, the author is a little embarrassed ' ,  'action support tio' ,  null );
The INSERT  the INTO  `donate`  the VALUES  ( '18 is' ,  '10' ,  'wizard 007' ,  'https://gitee.com/null_346_8382' ,  '2017-04-07 13:03:38' ,  'code cloud' ,  'qq:270249250' ,  'Thank you for your open source project! ' ,  null );
INSERT  INTO  `donate`  VALUES  ( '19' ,  '10' ,  'Ye Zhaoliang' ,  null ,  '2017-04-16 14:03:09' ,  'Code Cloud' ,  'qq:977962857' ,  'Thank you for your Open source project! ' ,  null );
INSERT  INTO  `donate`  VALUES  ( '20' ,  '6' ,  'Unknown' ,  'mailto:tywo45@163.com' ,  '2017-01-09 11:34:45' ,  'WeChat QR code collection' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '21' ,  '15' ,  'Unknown' ,  'mailto:tywo45@163.com' ,  '2017-04-07 17:40:09' ,  'WeChat QR code collection' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '22' ,  '20' ,  'KevinBlandy' ,  null ,  '2017-05-03 00:18:24' ,  'WeChat QR code collection' ,  'qq:747692844' ,  ' ' ,  null );
INSERT  INTO  `donate`  VALUES  ( '23' ,  '100' ,  'Unknown' ,  'mailto:tywo45@163.com' ,  '2017-05-05 09:34:28' ,  'WeChat QR code collection' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
The INSERT  the INTO  `donate`  the VALUES  ( '24' ,  '20 is' ,  'unknown' ,  'mailto: tywo45@163.com' ,  '2017-05-05 10:11:29' ,  'micro-channel two-dimensional code receivable' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '25' ,  '128' ,  'orpherus' ,  'https://my.oschina.net/u/3239976' ,  '2017-05-07 19:24:09' ,  'WeChat QR code collection ' ,  'The latter double help tio correct a misunderstanding' ,  'after yii and vue, the third domestic base library I think is good, small donation 128 to encourage. ' ,  null );
INSERT  INTO  `donate`  VALUES  ( '26' ,  '66' ,  'Unknown' ,  'mailto:tywo45@163.com' ,  '2017-05-09 10:10:29' ,  'WeChat QR code collection' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '27' ,  '50' ,  'He Yongbo' ,  'http://www.citis.cc' ,  '2017-05-16 11:09:30' ,  'WeChat QR code Receipt ' ,  'qq: 834659942, company address: Room 702, Building 1, Xintiandi Building, Min'an Road, Jiangdong District, Ningbo, Zhejiang, China. Preparing to use tio httpserver. ' ,  'tio. Tan. Java instant messaging framework' ,  null );
The INSERT  the INTO  `donate`  the VALUES  ( '28' ,  '10' ,  'unknown' ,  'mailto: tywo45@163.com' ,  '2017-05-25 10:01:31' ,  'micro-channel two-dimensional code receivable' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '29' ,  '66' ,  'Unknown' ,  'mailto:tywo45@163.com' ,  '2017-06-13 16:12:31' ,  'WeChat QR code collection' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '30' ,  '5' ,  'Unknown' ,  'mailto:tywo45@163.com' ,  '2017-06-14 17:24:35' ,  'WeChat QR code collection' ,  'The author would like to know who you are! Because the author does not want Lei Feng to suffer, for your own reasonable interests, please feedback in time, thank you!' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '31' ,  '66' ,  'donors request anonymous' ,  '' ,  '2017-06-16 22:26:13' ,  'WeChat QR code collection' ,  'donor Anonymous, thank the classmate, the author has privately commented on the donation information ' ,  '' ,  '203025368, Mo Yan' );
INSERT  INTO  `donate`  VALUES  ( '32' ,  '600' ,  'Xian Xin' ,  'https://my.oschina.net/u/1168184' ,  '2017-05-11 17:26:24' ,  ' Alipay' ,  'layui author, sage is too polite, after I donated layui, I also donated ' ,  'support t-io' ,  null );
INSERT  INTO  `donate`  VALUES  ( '33' ,  '88' ,  'artisan' ,  'https://gitee.com/openWolf/gopush' ,  '2017-03-18 07:31:15' ,  'WeChat red envelope' ,  'gopush author' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '34' ,  '58.88' ,  'YY guardian angel YY' ,  'https://gitee.com/yyljlyy' ,  '2017-03-02 19:09:24' ,  'WeChat red envelope ' ,  ' the friend tio try to read the source code, and donated many times, the last of a little embarrassed ' ,  ' framework write too overbearing! Like anger. I read emotionally, long-lost feeling. ' ,  Null );
INSERT  INTO  `donate`  VALUES  ( '35' ,  '68' ,  ' null ,  '2017-03-18 00:14:44' ,  'WeChat red envelope' ,  'qq:171707767' ,  'hard work , little caution ' ,  null );
INSERT  INTO  `donate`  VALUES  ( '36' ,  '50' ,  '(,  null ,  '2017-04-25 18:41:18' ,  'Alipay' ,  'qq:253044990' ,  'Tan The main refueling, lam Xiaotao' ,  null );
INSERT  INTO  `donate`  VALUES  ( '37' ,  '42' ,  'donors request anonymous' ,  null ,  '2017-04-25 18:08:10 ' ,  'WeChat QR code collection' ,  'donor request Anonymous, thank the classmate, the author has privately commented on the donation information ' ,  '' ,  'He Xi' );
INSERT  INTO  `donate`  VALUES  ( '38' ,  '59' ,  'Mu Yu' ,  null ,  '2017-04-23 15:50:04' ,  'Alipay' ,  'qq:18332024, book enterprise service in advance' ,  'Support t-io development' ,  'Advance booking enterprise service' );
INSERT  INTO  `donate`  VALUES  ( '39' ,  '168' ,  'Xian Xin' ,  'https://my.oschina.net/u/1168184' ,  '2017-06-13 23:18:57' ,  ' Alipay' ,  'layui author, sage is too polite, after donating layui on my code cloud, I also added a rebate' ,  'finally find a place to return a gift' ,  'Xianxin is too polite, I donated layui on the code cloud, also Price increase rebate ' );
INSERT  INTO  `donate`  VALUES  ( '40' ,  '29' ,  'pull' ,  null ,  '2017-05-08 21:55:40' ,  'Alipay' ,  'QQ: 237 809 796' ,  'feelings help () Tired and tired, have a cup of coffee' ,  'jf' );
INSERT  INTO  `donate`  VALUES  ( '41' ,  '9' ,   null ,  '2017-05-09 09:18:14' ,  'Alipay' ,  'qq:18332024' ,  'donation' ,  null );
INSERT  INTO  `donate`  VALUES  ( '42' ,  '20' ,  'KevinBlandy' ,  null ,  '2017-05-03 14:22:35' ,  'Alipay' ,  'qq:747692844' ,  '' ,  null );
INSERT  INTO  `donate`  VALUES  ( '43' ,  '6' ,  '   '' ,  '2017-04-28 11:04:48' ,  'WeChat QR code collection' ,  'qq:409413474 The project server I went online uses the tio framework, as the server side and the temperature control device (client) for socket communication, the client is the hardware device of the client side, has its own protocol implementation, so no tio, tio is used. Helped me achieve automatic reconnection and heartbeat detection, so basically nothing can be done. On the line for two months, has been very stable. ' ,  null ,  null );
INSERT  INTO  `donate`  VALUES  ( '45' ,  '8' ,  '  null ,  '2017-05-04 10:18:25' ,  null ,  'qq:244627250' ,  null ,  null );

-- ----------------------------
-- Table structure for user
