/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : salary_management_system

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 05/08/2021 17:45:37
*/

SET NAMES utf8mb4;

-- ----------------------------
-- Table structure for rule
-- ----------------------------
DROP TABLE IF EXISTS `rule`;
CREATE TABLE `rule`  (
  `rule_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tax_level` int UNSIGNED NULL DEFAULT NULL COMMENT '个税等级',
  `rule_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '规则种类',
  `range_upper` decimal(8, 2) UNSIGNED NULL DEFAULT NULL COMMENT '收入上限',
  `range_lower` decimal(8, 2) UNSIGNED NOT NULL COMMENT '收入下限',
  `rate` decimal(4, 3) UNSIGNED NOT NULL COMMENT '缴纳比例',
  `reduction` decimal(8, 2) UNSIGNED NULL DEFAULT NULL COMMENT '速算扣除数',
  PRIMARY KEY (`rule_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of rule
-- ----------------------------
INSERT INTO `rule` VALUES (1, 1, 'tax', 36000.00, 0.00, 0.030, 0.00);
INSERT INTO `rule` VALUES (2, 2, 'tax', 144000.00, 36000.00, 0.100, 2520.00);
INSERT INTO `rule` VALUES (3, 3, 'tax', 300000.00, 144000.00, 0.200, 16920.00);
INSERT INTO `rule` VALUES (4, 4, 'tax', 420000.00, 300000.00, 0.250, 31920.00);
INSERT INTO `rule` VALUES (5, 5, 'tax', 660000.00, 420000.00, 0.300, 52920.00);
INSERT INTO `rule` VALUES (6, 6, 'tax', 960000.00, 660000.00, 0.350, 85920.00);
INSERT INTO `rule` VALUES (7, 7, 'tax', NULL, 960000.00, 0.450, 181920.00);
INSERT INTO `rule` VALUES (8, NULL, 'pension', 17880.75, 3321.60, 0.080, NULL);
INSERT INTO `rule` VALUES (9, NULL, 'medicare', 17880.75, 3576.00, 0.020, NULL);
INSERT INTO `rule` VALUES (10, NULL, 'unemploy', 17880.75, 3321.60, 0.005, NULL);
INSERT INTO `rule` VALUES (11, NULL, 'housing', 29335.00, 2010.00, 0.120, NULL);



DROP TABLE IF EXISTS `deduction_info`;
CREATE TABLE `deduction_info`  (
  `deduction_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employee_id` int UNSIGNED NOT NULL COMMENT '工号',
  `child_deduction` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '子女教育',
  `pro_study_deduction` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '继续教育',
  `illness_deduction` decimal(7, 2) UNSIGNED ZEROFILL NOT NULL COMMENT '大病医疗',
  `housing_loan_deduction` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '住房贷款利息',
  `housing_rent_deduction` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '住房租金',
  `parent_deduction` int(10) UNSIGNED ZEROFILL NOT NULL COMMENT '赡养老人',
  PRIMARY KEY (`deduction_id`) USING BTREE,
  INDEX `employee_id`(`employee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS `employee_info`;
CREATE TABLE `employee_info`  (
  `employee_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '工号',
  `employee_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '员工姓名',
  `bank_account` char(19) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '银行账号',
  `pre_tax_salary` decimal(10, 2) UNSIGNED NOT NULL COMMENT '税前工资',
  `phone_num` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '手机号',
  `mail_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱地址',
  PRIMARY KEY (`employee_id`) USING BTREE,
  INDEX `pre_tax_salary`(`pre_tax_salary`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;



DROP TABLE IF EXISTS `salary_payment_record`;
CREATE TABLE `salary_payment_record`  (
  `pay_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `employee_id` int UNSIGNED NOT NULL COMMENT '工号',
  `status` tinyint(2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '工资发放状态',
  `pre_tax_salary` decimal(20, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '应发工资',
  `final_salary` decimal(20, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '实发工资',
  `tax` decimal(15, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '缴税额',
  `deduction` decimal(8, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '个税专项扣除额',
  `pension_insur` decimal(6, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '养老保险扣缴额',
  `medicare_insur` decimal(6, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '医疗保险扣缴额',
  `housing_provident` decimal(6, 2) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '公积金扣缴额',
  `unemploy_insur` decimal(6, 2) UNSIGNED NULL DEFAULT NULL COMMENT '失业保险扣缴额',
  `rule` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '计算规则',
  `pay_date` datetime NULL DEFAULT NULL COMMENT '工资发放时间',
  `pay_month` varchar(7) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '工资月份',
  PRIMARY KEY (`pay_id`) USING BTREE,
  INDEX `employee_id_2`(`employee_id`) USING BTREE,
  INDEX `pre_tax_salary`(`pre_tax_salary`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;


