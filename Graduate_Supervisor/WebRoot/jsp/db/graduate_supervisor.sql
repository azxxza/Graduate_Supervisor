/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50027
Source Host           : localhost:3306
Source Database       : graduate_supervisor

Target Server Type    : MYSQL
Target Server Version : 50027
File Encoding         : 65001

Date: 2015-10-15 20:30:46
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `info_student_basic`
-- ----------------------------
DROP TABLE IF EXISTS `info_student_basic`;
CREATE TABLE `info_student_basic` (
  `s_id` varchar(255) NOT NULL,
  `s_name` varchar(255) default NULL,
  `s_sex` varchar(2) default NULL,
  PRIMARY KEY  (`s_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of info_student_basic
-- ----------------------------

-- ----------------------------
-- Table structure for `info_student_score`
-- ----------------------------
DROP TABLE IF EXISTS `info_student_score`;
CREATE TABLE `info_student_score` (
  `id` int(11) NOT NULL auto_increment,
  `s_c_name` varchar(255) default NULL,
  `s_c_type` varchar(255) default NULL,
  `s_c_credit` double default NULL,
  `s_c_score` double default NULL,
  `y_t_id` int(11) default NULL,
  `s_id` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of info_student_score
-- ----------------------------

-- ----------------------------
-- Table structure for `info_teacher_basic`
-- ----------------------------
DROP TABLE IF EXISTS `info_teacher_basic`;
CREATE TABLE `info_teacher_basic` (
  `t_work_id` varchar(255) NOT NULL,
  `t_name` varchar(255) default NULL,
  `t_sex` varchar(2) default NULL,
  PRIMARY KEY  (`t_work_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of info_teacher_basic
-- ----------------------------
INSERT INTO `info_teacher_basic` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for `logic_student_volunteer`
-- ----------------------------
DROP TABLE IF EXISTS `logic_student_volunteer`;
CREATE TABLE `logic_student_volunteer` (
  `id` int(11) NOT NULL auto_increment,
  `s_id` varchar(255) default NULL,
  `t_work_id` varchar(255) default NULL,
  `s_t_volunteer` varchar(255) default NULL,
  `s_remark` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logic_student_volunteer
-- ----------------------------

-- ----------------------------
-- Table structure for `logic_teacher_student`
-- ----------------------------
DROP TABLE IF EXISTS `logic_teacher_student`;
CREATE TABLE `logic_teacher_student` (
  `id` int(11) NOT NULL auto_increment,
  `t_work_id` varchar(255) default NULL,
  `s_id` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of logic_teacher_student
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_round_open_time`
-- ----------------------------
DROP TABLE IF EXISTS `sys_round_open_time`;
CREATE TABLE `sys_round_open_time` (
  `id` int(11) NOT NULL auto_increment,
  `r_t_start_time` varchar(255) default NULL,
  `r_t_end_time` varchar(255) default NULL,
  `r_t_round` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_round_open_time
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL auto_increment,
  `s_user_name` varchar(255) character set utf8 default NULL,
  `s_user_password` varchar(255) character set utf8 default NULL,
  `s_user_role_id` int(11) default NULL,
  `s_foreign_id` int(11) default NULL COMMENT '参考外键',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', '1', '1', '1', null);
INSERT INTO `sys_user` VALUES ('2', '2', '2', '2', null);
INSERT INTO `sys_user` VALUES ('3', '3', '3', '3', null);

-- ----------------------------
-- Table structure for `sys_user_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_log`;
CREATE TABLE `sys_user_log` (
  `id` int(11) NOT NULL auto_increment,
  `u_l_times` int(11) default NULL,
  `u_id` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_log
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_volunteer_open_time`
-- ----------------------------
DROP TABLE IF EXISTS `sys_volunteer_open_time`;
CREATE TABLE `sys_volunteer_open_time` (
  `id` int(11) NOT NULL auto_increment,
  `v_t_start_time` datetime default NULL,
  `v_t_end_time` datetime default NULL,
  `r_t_round` int(11) default NULL,
  `r_is_end` char(255) default NULL,
  `v_volunteer` int(11) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_volunteer_open_time
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_year_term`
-- ----------------------------
DROP TABLE IF EXISTS `sys_year_term`;
CREATE TABLE `sys_year_term` (
  `id` int(11) NOT NULL auto_increment,
  `year` varchar(255) default NULL,
  `term` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_year_term
-- ----------------------------
