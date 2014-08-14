/*
Navicat MySQL Data Transfer

Source Server         : localhost 71
Source Server Version : 50141
Source Host           : localhost:3306
Source Database       : basewar

Target Server Type    : MYSQL
Target Server Version : 50141
File Encoding         : 65001

Date: 2014-08-13 18:00:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `data_dict`
-- ----------------------------
DROP TABLE IF EXISTS `data_dict`;
CREATE TABLE `data_dict` (
  `ddid` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号',
  `pddid` int(11) NOT NULL DEFAULT '0' COMMENT '父记录编号',
  `name` varchar(20) NOT NULL COMMENT '数据项目名',
  `itemkey` varchar(30) NOT NULL COMMENT '数据项目关键字，由它追溯字典里的位置，同一父结点下不能重复',
  `itemvalue` varchar(10) NOT NULL COMMENT '数据项目的值',
  `dept` varchar(100) DEFAULT NULL COMMENT '数据项目描述',
  `ordinal` tinyint(2) DEFAULT '0' COMMENT '排序',
  `deleted` tinyint(2) DEFAULT '0',
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ddid`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_dict
-- ----------------------------
INSERT INTO `data_dict` VALUES ('12', '0', '系统语言', 'systemlanguage', '0', '用于区分客户端的系统语言', '2', '0', '2014-04-14 09:50:32');
INSERT INTO `data_dict` VALUES ('13', '12', '简体中文', 'zh-CN', '1', 'simplified-chinese', '1', '0', '2014-03-18 13:47:02');
INSERT INTO `data_dict` VALUES ('16', '12', '俄语', 'ru', '4', 'russian', '4', '0', '2014-03-19 11:39:55');
INSERT INTO `data_dict` VALUES ('17', '12', '德语', 'de', '5', 'german', '5', '0', '2014-03-19 11:40:01');
INSERT INTO `data_dict` VALUES ('18', '12', '法语', 'fr', '6', 'french', '6', '0', '2014-03-18 13:47:32');
INSERT INTO `data_dict` VALUES ('19', '12', '西班牙语', 'es', '7', 'espana', '7', '0', '2014-03-18 13:47:39');
INSERT INTO `data_dict` VALUES ('37', '0', '音频类型', 'musictypes', '0', '音频资源的类别', '3', '0', '2014-04-14 09:50:45');
INSERT INTO `data_dict` VALUES ('38', '37', '普通音乐', 'general_music', '0', '', '1', '0', '2014-03-24 15:17:49');
INSERT INTO `data_dict` VALUES ('39', '37', '故事音频——经典童话', 'story_classic_tale', '1', '', '2', '0', '2014-03-24 15:23:22');
INSERT INTO `data_dict` VALUES ('40', '37', '故事音频——团结友谊', 'story_friendship', '2', '', '3', '0', '2014-03-24 15:20:18');
INSERT INTO `data_dict` VALUES ('41', '37', '故事音频——生活习惯', 'story_habit', '3', '', '4', '0', '2014-03-24 15:20:44');
INSERT INTO `data_dict` VALUES ('42', '37', '故事音频——成语故事', 'story_idiom', '4', '', '5', '0', '2014-03-24 15:21:23');
INSERT INTO `data_dict` VALUES ('43', '37', '故事音频——科学知识', 'story_science', '5', '', '6', '0', '2014-03-24 15:22:11');
INSERT INTO `data_dict` VALUES ('44', '37', '故事音频——温馨家庭', 'story_family', '6', '', '7', '0', '2014-03-24 15:22:34');
INSERT INTO `data_dict` VALUES ('47', '0', '资源收免费类型', 'res_types', '0', '激活码对应的类别，类别与能访问到的资源有关', '5', '0', '2014-08-13 09:00:18');
INSERT INTO `data_dict` VALUES ('48', '47', '官方收费', 'official_res', 'a1', '官方收费资源', '1', '0', '2014-05-14 16:43:02');
INSERT INTO `data_dict` VALUES ('49', '47', '某客户收费', 'guangz_res', 'b1', '某客户的收费资源', '2', '0', '2014-05-14 16:43:09');
INSERT INTO `data_dict` VALUES ('51', '47', '免费', 'free_res', 'a0', '免费资源', '0', '0', '2014-05-14 16:42:55');
INSERT INTO `data_dict` VALUES ('52', '0', '平台类别', 'platform', '0', '平台区分，比如ios和android的区分', '6', '0', '2014-05-12 14:55:30');
INSERT INTO `data_dict` VALUES ('53', '52', 'ipad', 'ipad', '0', 'ios平台', '0', '0', '2014-07-09 10:16:37');
INSERT INTO `data_dict` VALUES ('54', '52', 'android', 'android', '1', 'android平台', '1', '0', '2014-05-14 17:50:25');
INSERT INTO `data_dict` VALUES ('56', '12', '英语', 'en', '32', '', '32', '0', '2014-07-29 06:13:25');
INSERT INTO `data_dict` VALUES ('59', '12', '繁体', 'zh-TW', '23', '', '23', '0', '2014-07-29 06:12:59');
INSERT INTO `data_dict` VALUES ('60', '0', '收费策略', 'cost', '0', '收费或免费', '7', '0', '2014-06-10 09:25:04');
INSERT INTO `data_dict` VALUES ('61', '60', '免费', 'free', '0', '免费', '0', '0', '2014-06-10 09:26:05');
INSERT INTO `data_dict` VALUES ('62', '60', '收费', 'notfree', '1', '收费', '1', '0', '2014-06-10 09:26:26');
INSERT INTO `data_dict` VALUES ('63', '52', 'iphone', 'iphone', '2', 'iphone', '2', '0', '2014-07-09 10:15:54');
INSERT INTO `data_dict` VALUES ('64', '0', 's1_music_type', 's1_music_type', '0', 's1音乐类型定义', '8', '0', '2014-07-30 08:37:36');
INSERT INTO `data_dict` VALUES ('65', '64', '儿童音乐', 'children_music', '1', '', '1', '0', '2014-07-30 08:19:50');
INSERT INTO `data_dict` VALUES ('66', '0', 's1_music_age', 's1_music_age', '0', 's1音乐年龄段定义', '9', '0', '2014-07-30 08:04:40');
INSERT INTO `data_dict` VALUES ('67', '0', 's1_music_tag', 's1_music_tag', '0', 's1音乐标签定义', '10', '0', '2014-07-30 08:06:10');
INSERT INTO `data_dict` VALUES ('68', '64', '童话故事', 'story', '2', '', '2', '0', '2014-07-30 08:20:30');
INSERT INTO `data_dict` VALUES ('69', '64', '动物百科', 'animal', '3', '', '3', '0', '2014-07-30 08:20:50');
INSERT INTO `data_dict` VALUES ('70', '64', '国学经典', 'classic', '4', '', '4', '0', '2014-07-30 08:21:07');

-- ----------------------------
-- Table structure for `data_dict_kv`
-- ----------------------------
DROP TABLE IF EXISTS `data_dict_kv`;
CREATE TABLE `data_dict_kv` (
  `ddid` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录编号，自增',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '数据项中文别名',
  `itemkey` varchar(30) NOT NULL COMMENT '数据项关键名',
  `itemvalue` varchar(100) NOT NULL COMMENT '数据项的值',
  `dept` varchar(100) DEFAULT NULL COMMENT '描述',
  `used` tinyint(2) NOT NULL DEFAULT '1' COMMENT '停用标记，0未使用，1使用中，默认1',
  PRIMARY KEY (`ddid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of data_dict_kv
-- ----------------------------
INSERT INTO `data_dict_kv` VALUES ('1', 'asdf', 'asd', 'fa2', '1', '1');

-- ----------------------------
-- Table structure for `ds_music`
-- ----------------------------
DROP TABLE IF EXISTS `ds_music`;
CREATE TABLE `ds_music` (
  `mid` int(12) NOT NULL AUTO_INCREMENT,
  `mname` varchar(30) NOT NULL,
  `mfname` varchar(20) DEFAULT NULL,
  `size` mediumint(6) NOT NULL DEFAULT '0',
  `author` varchar(30) DEFAULT NULL,
  `performer` varchar(30) DEFAULT NULL,
  `restype` char(2) NOT NULL DEFAULT 'a0',
  `source` char(2) NOT NULL DEFAULT 's1',
  `favourites` int(4) NOT NULL DEFAULT '0',
  `coverpic` varchar(20) DEFAULT NULL,
  `lyric` varchar(20) DEFAULT NULL,
  `recsupport` tinyint(2) NOT NULL DEFAULT '0',
  `deleted` tinyint(2) NOT NULL DEFAULT '1',
  `lasttime` datetime NOT NULL,
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ds_music
-- ----------------------------
INSERT INTO `ds_music` VALUES ('1', '外婆的澎泊湾', '14072572ed90d3.mp3', '3869', 'tyc', 'tao', 'a0', 's1', '0', '1408098f39c550.png', '140731eeb782ac.lrc', '0', '0', '2014-08-13 09:23:41');
INSERT INTO `ds_music` VALUES ('2', 'bbbb211adfadfadfadf', '140802758afcb4.mp3', '3', '', '', 'a0', 's1', '0', null, '140731246d1584.lrc', '0', '1', '2014-08-08 03:53:15');
INSERT INTO `ds_music` VALUES ('3', 'ADFADSF', null, '0', null, null, 'a0', 's1', '0', null, null, '0', '1', '2014-07-31 06:04:41');
INSERT INTO `ds_music` VALUES ('4', '测试', null, '0', '', '', 'a0', 's1', '0', null, null, '0', '1', '2014-07-31 06:40:58');
INSERT INTO `ds_music` VALUES ('5', '彩色的毛衣', '14080179373e6b.mp3', '0', '', '', 'a0', 's1', '0', '1408013daacc33.jpg', null, '0', '0', '2014-08-01 15:10:21');
INSERT INTO `ds_music` VALUES ('6', '盲人摸象', '140801896d15bf.mp3', '0', '', '', 'a0', 's1', '0', '140801610d32e8.jpg', null, '1', '0', '2014-08-07 03:32:03');
INSERT INTO `ds_music` VALUES ('7', '海马爸爸生宝宝', '14080129f524b9.mp3', '0', '', '', 'a0', 's1', '0', '14080106d7ef7f.jpg', null, '0', '0', '2014-08-01 02:19:14');
INSERT INTO `ds_music` VALUES ('8', '找朋友', '140801546cf12e.mp3', '0', '', '', 'a0', 's1', '0', '140801a5f50f74.jpg', null, '1', '0', '2014-08-07 03:32:32');
INSERT INTO `ds_music` VALUES ('9', '我最爱的人', '140804c808505f.mp3', '0', 'king', 'king', 'a0', 's1', '0', '140804c6501710.png', '1408049da00c78.lrc', '0', '0', '2014-08-07 08:20:23');
INSERT INTO `ds_music` VALUES ('10', '拔萝卜', '1408048dad29ec.mp3', '0', '', '', 'a0', 's1', '0', '1408041fe75560.jpg', null, '0', '0', '2014-08-07 08:21:14');
INSERT INTO `ds_music` VALUES ('11', '大象的长鼻子', '140804de371b1c.mp3', '0', 'king', 'king', 'a0', 's1', '0', '1408044f23fdc4.jpg', null, '0', '0', '2014-08-07 08:21:54');
INSERT INTO `ds_music` VALUES ('12', '我是一粒米', '140806fd823141.mp3', '0', 'king', 'king', 'a0', 's1', '0', '140806910d4bae.png', null, '0', '0', '2014-08-07 08:22:28');
INSERT INTO `ds_music` VALUES ('13', '小二哥', '140806917432ef.mp3', '0', 'king', 'king', 'a0', 's1', '0', '1408062569911b.png', null, '0', '0', '2014-08-07 08:23:46');
INSERT INTO `ds_music` VALUES ('14', '呼儿嘿呦', '14080689a41ed9.mp3', '0', 'king', 'king', 'a0', 's1', '0', '140806ad0ea318.png', null, '0', '0', '2014-08-07 08:24:13');
INSERT INTO `ds_music` VALUES ('15', '新年到', '1408060c7d61d7.mp3', '0', 'king', 'king', 'a0', 's1', '0', '140806b2990126.png', null, '0', '0', '2014-08-07 08:24:50');
INSERT INTO `ds_music` VALUES ('16', '过家家', '140806344e9f7d.mp3', '0', 'king', 'king', 'a0', 's1', '0', '14080646406255.png', null, '0', '0', '2014-08-07 08:25:23');
INSERT INTO `ds_music` VALUES ('17', '大力水手', '140806b4e89425.mp3', '0', 'king', 'king', 'a0', 's1', '0', '14080675629ac2.png', null, '0', '0', '2014-08-07 08:26:15');
INSERT INTO `ds_music` VALUES ('18', 'gfsfg1', null, '0', '', '', 'a0', 's1', '0', null, null, '0', '1', '2014-08-09 07:15:04');

-- ----------------------------
-- Table structure for `ds_music_language`
-- ----------------------------
DROP TABLE IF EXISTS `ds_music_language`;
CREATE TABLE `ds_music_language` (
  `mlid` int(12) NOT NULL AUTO_INCREMENT,
  `lid` tinyint(2) NOT NULL,
  `mid` int(12) NOT NULL,
  `deleted` tinyint(2) NOT NULL DEFAULT '0',
  `lasttime` datetime NOT NULL,
  PRIMARY KEY (`mlid`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ds_music_language
-- ----------------------------
INSERT INTO `ds_music_language` VALUES ('1', '4', '1', '0', '2014-07-30 11:09:47');
INSERT INTO `ds_music_language` VALUES ('2', '6', '1', '0', '2014-07-30 11:09:47');
INSERT INTO `ds_music_language` VALUES ('3', '1', '1', '0', '2014-07-30 11:08:19');
INSERT INTO `ds_music_language` VALUES ('4', '1', '2', '0', '2014-07-30 13:14:13');
INSERT INTO `ds_music_language` VALUES ('5', '1', '3', '0', '2014-07-31 06:04:41');
INSERT INTO `ds_music_language` VALUES ('6', '1', '4', '0', '2014-07-31 06:21:21');
INSERT INTO `ds_music_language` VALUES ('7', '1', '5', '0', '2014-08-01 02:10:40');
INSERT INTO `ds_music_language` VALUES ('8', '1', '6', '0', '2014-08-01 02:13:44');
INSERT INTO `ds_music_language` VALUES ('9', '1', '7', '0', '2014-08-01 02:17:53');
INSERT INTO `ds_music_language` VALUES ('10', '1', '8', '0', '2014-08-01 02:20:52');
INSERT INTO `ds_music_language` VALUES ('11', '1', '9', '0', '2014-08-04 06:49:37');
INSERT INTO `ds_music_language` VALUES ('12', '1', '10', '0', '2014-08-04 09:21:44');
INSERT INTO `ds_music_language` VALUES ('13', '1', '11', '0', '2014-08-04 09:25:45');
INSERT INTO `ds_music_language` VALUES ('14', '1', '12', '0', '2014-08-06 03:07:41');
INSERT INTO `ds_music_language` VALUES ('15', '1', '13', '0', '2014-08-06 03:09:36');
INSERT INTO `ds_music_language` VALUES ('16', '1', '14', '0', '2014-08-06 03:11:15');
INSERT INTO `ds_music_language` VALUES ('17', '1', '15', '0', '2014-08-06 03:12:22');
INSERT INTO `ds_music_language` VALUES ('18', '1', '16', '0', '2014-08-06 03:13:20');
INSERT INTO `ds_music_language` VALUES ('19', '1', '17', '0', '2014-08-06 03:14:16');
INSERT INTO `ds_music_language` VALUES ('20', '7', '1', '1', '2014-08-09 06:37:53');
INSERT INTO `ds_music_language` VALUES ('21', '7', '18', '0', '2014-08-09 07:14:55');

-- ----------------------------
-- Table structure for `ds_music_type`
-- ----------------------------
DROP TABLE IF EXISTS `ds_music_type`;
CREATE TABLE `ds_music_type` (
  `mtypeid` int(12) NOT NULL AUTO_INCREMENT,
  `mid` int(12) NOT NULL,
  `typeid` tinyint(2) NOT NULL,
  `deleted` tinyint(2) NOT NULL DEFAULT '0',
  `lasttime` datetime NOT NULL,
  PRIMARY KEY (`mtypeid`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ds_music_type
-- ----------------------------
INSERT INTO `ds_music_type` VALUES ('1', '4', '2', '0', '2014-07-31 06:40:38');
INSERT INTO `ds_music_type` VALUES ('2', '4', '3', '0', '2014-07-31 06:40:58');
INSERT INTO `ds_music_type` VALUES ('3', '4', '1', '0', '2014-07-31 06:39:46');
INSERT INTO `ds_music_type` VALUES ('4', '4', '4', '0', '2014-07-31 06:40:47');
INSERT INTO `ds_music_type` VALUES ('5', '1', '1', '0', '2014-07-31 06:41:37');
INSERT INTO `ds_music_type` VALUES ('6', '2', '1', '0', '2014-07-31 07:36:09');
INSERT INTO `ds_music_type` VALUES ('7', '2', '2', '1', '2014-07-31 07:38:12');
INSERT INTO `ds_music_type` VALUES ('8', '5', '2', '0', '2014-08-01 02:10:40');
INSERT INTO `ds_music_type` VALUES ('9', '6', '4', '0', '2014-08-01 02:13:44');
INSERT INTO `ds_music_type` VALUES ('10', '7', '3', '0', '2014-08-01 02:17:53');
INSERT INTO `ds_music_type` VALUES ('11', '8', '1', '0', '2014-08-01 02:20:52');
INSERT INTO `ds_music_type` VALUES ('12', '9', '1', '0', '2014-08-04 06:49:37');
INSERT INTO `ds_music_type` VALUES ('13', '10', '1', '0', '2014-08-04 09:21:44');
INSERT INTO `ds_music_type` VALUES ('14', '11', '3', '0', '2014-08-04 09:25:45');
INSERT INTO `ds_music_type` VALUES ('15', '12', '1', '0', '2014-08-06 03:07:41');
INSERT INTO `ds_music_type` VALUES ('16', '13', '1', '0', '2014-08-06 03:09:36');
INSERT INTO `ds_music_type` VALUES ('17', '14', '1', '0', '2014-08-06 03:11:15');
INSERT INTO `ds_music_type` VALUES ('18', '15', '1', '0', '2014-08-06 03:12:22');
INSERT INTO `ds_music_type` VALUES ('19', '16', '1', '0', '2014-08-06 03:13:20');
INSERT INTO `ds_music_type` VALUES ('20', '17', '1', '0', '2014-08-06 03:14:16');
INSERT INTO `ds_music_type` VALUES ('21', '18', '1', '0', '2014-08-09 07:14:55');
INSERT INTO `ds_music_type` VALUES ('22', '1', '2', '0', '2014-08-13 09:23:41');

-- ----------------------------
-- Table structure for `logs`
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message` text COLLATE utf8_unicode_ci NOT NULL,
  `lasttime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3765 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of logs
-- ----------------------------

-- ----------------------------
-- Table structure for `manager`
-- ----------------------------
DROP TABLE IF EXISTS `manager`;
CREATE TABLE `manager` (
  `name` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager
-- ----------------------------
INSERT INTO `manager` VALUES ('admin', 'admin');
