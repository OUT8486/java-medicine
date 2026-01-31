-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: medicine
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `customer_id` varchar(20) NOT NULL COMMENT '客户编号，VARCHAR类型主键',
  `name` varchar(50) NOT NULL COMMENT '客户姓名/企业名称',
  `type` varchar(20) NOT NULL COMMENT '客户类型（个人/企业）',
  `contact_phone` char(11) DEFAULT NULL COMMENT '联系电话，11位手机号',
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户信息表，存储药品购买客户的基础信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `drug`
--

DROP TABLE IF EXISTS `drug`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `drug` (
  `drug_id` varchar(20) NOT NULL COMMENT '药品编号，VARCHAR类型主键',
  `generic_name` varchar(50) NOT NULL COMMENT '药品通用名',
  `approval_no` varchar(50) NOT NULL COMMENT '药品批准文号，唯一标识',
  `dosage_form` varchar(40) NOT NULL COMMENT '剂型（片剂/胶囊/注射液等）',
  `specification` varchar(30) NOT NULL COMMENT '药品规格（如5mg/片）',
  `unit` varchar(30) NOT NULL COMMENT '计量单位（片/盒/瓶等）',
  `purchase_price` decimal(6,2) DEFAULT NULL COMMENT '采购单价，保留2位小数',
  `retail_price` decimal(6,2) DEFAULT NULL COMMENT '零售单价，保留2位小数',
  `manufacturer_id` varchar(20) DEFAULT NULL COMMENT '生产厂家编号，关联manufacturer表',
  PRIMARY KEY (`drug_id`),
  UNIQUE KEY `approval_no` (`approval_no`),
  KEY `manufacturer_id` (`manufacturer_id`),
  CONSTRAINT `drug_ibfk_1` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`manufacturer_id`),
  CONSTRAINT `chk_drug_purchase_price` CHECK (((`purchase_price` > 0) and (`purchase_price` <= 9999.99))),
  CONSTRAINT `chk_retail_price` CHECK ((`retail_price` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品信息表，存储药品基础信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `drug_information`
--

DROP TABLE IF EXISTS `drug_information`;
/*!50001 DROP VIEW IF EXISTS `drug_information`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `drug_information` AS SELECT 
 1 AS `generic_name`,
 1 AS `dosage_form`,
 1 AS `specification`,
 1 AS `unit`,
 1 AS `retail_price`,
 1 AS `validity_date`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employee_id` varchar(20) NOT NULL COMMENT '员工编号，VARCHAR类型主键',
  `name` varchar(50) NOT NULL COMMENT '员工姓名',
  `post` varchar(50) NOT NULL COMMENT '岗位（采购/销售/库管/审核等）',
  PRIMARY KEY (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工信息表，存储企业员工基础信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `inventory_id` varchar(20) NOT NULL COMMENT '库存记录编号，VARCHAR类型主键',
  `drug_id` varchar(20) DEFAULT NULL COMMENT '药品编号，关联drug表',
  `warehouse_id` varchar(20) DEFAULT NULL COMMENT '仓库编号，关联warehouse表',
  `batch_no` varchar(50) NOT NULL COMMENT '药品批次号',
  `quantity` int NOT NULL COMMENT '库存数量，整数',
  `validity_date` char(10) NOT NULL COMMENT '有效期，格式YYYY-MM-DD',
  PRIMARY KEY (`inventory_id`),
  KEY `drug_id` (`drug_id`),
  KEY `warehouse_id` (`warehouse_id`),
  CONSTRAINT `inventory_ibfk_1` FOREIGN KEY (`drug_id`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `inventory_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`),
  CONSTRAINT `chk_inventory_quantity` CHECK ((`quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存表，存储各仓库药品的库存信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `manufacturer`
--

DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacturer` (
  `manufacturer_id` varchar(20) NOT NULL COMMENT '厂家编号，VARCHAR类型主键',
  `name` varchar(30) NOT NULL COMMENT '生产厂家名称',
  `credit_code` char(18) DEFAULT NULL COMMENT '统一社会信用代码，18位',
  PRIMARY KEY (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='药品生产厂家表，存储厂家基础信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `purchase_information`
--

DROP TABLE IF EXISTS `purchase_information`;
/*!50001 DROP VIEW IF EXISTS `purchase_information`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `purchase_information` AS SELECT 
 1 AS `po_id`,
 1 AS `supplier_id`,
 1 AS `po_date`,
 1 AS `employee_id`,
 1 AS `audit_status`,
 1 AS `drug_id`,
 1 AS `quantity`,
 1 AS `price`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `purchaseorder`
--

DROP TABLE IF EXISTS `purchaseorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchaseorder` (
  `po_id` varchar(20) NOT NULL COMMENT '采购订单编号，VARCHAR类型主键',
  `supplier_id` varchar(20) DEFAULT NULL COMMENT '供应商编号，关联supplier表',
  `po_date` char(10) NOT NULL COMMENT '采购日期，格式YYYY-MM-DD',
  `employee_id` varchar(20) DEFAULT NULL COMMENT '经办人编号，关联employee表',
  `audit_status` tinyint(1) DEFAULT NULL COMMENT '审核状态（0未审核/1已审核）',
  PRIMARY KEY (`po_id`),
  KEY `supplier_id` (`supplier_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `purchaseorder_ibfk_1` FOREIGN KEY (`supplier_id`) REFERENCES `supplier` (`supplier_id`),
  CONSTRAINT `purchaseorder_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购订单表，存储药品采购订单主信息';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `trigger_purchaseorder_before_delete` BEFORE DELETE ON `purchaseorder` FOR EACH ROW BEGIN
    DELETE FROM purchaseorderitem WHERE purchaseorderitem.po_id=OLD.po_id;
    DELETE FROM warehousein WHERE warehousein.po_id=OLD.po_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `purchaseorderitem`
--

DROP TABLE IF EXISTS `purchaseorderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchaseorderitem` (
  `poi_id` varchar(20) NOT NULL COMMENT '采购订单项编号，VARCHAR类型主键',
  `po_id` varchar(20) DEFAULT NULL COMMENT '采购订单编号，关联purchaseorder表',
  `drug_id` varchar(20) DEFAULT NULL COMMENT '药品编号，关联drug表',
  `quantity` int NOT NULL COMMENT '采购数量，整数',
  `price` decimal(6,2) NOT NULL COMMENT '采购单价，保留2位小数',
  PRIMARY KEY (`poi_id`),
  KEY `po_id` (`po_id`),
  KEY `drug_id` (`drug_id`),
  CONSTRAINT `purchaseorderitem_ibfk_1` FOREIGN KEY (`po_id`) REFERENCES `purchaseorder` (`po_id`),
  CONSTRAINT `purchaseorderitem_ibfk_2` FOREIGN KEY (`drug_id`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `chk_purchaseorderitem_price` CHECK (((`price` > 0) and (`price` <= 9999.99))),
  CONSTRAINT `chk_purchaseorderitem_quantity` CHECK ((`quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购订单项表，存储每个采购订单的药品明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `sale_information`
--

DROP TABLE IF EXISTS `sale_information`;
/*!50001 DROP VIEW IF EXISTS `sale_information`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `sale_information` AS SELECT 
 1 AS `so_id`,
 1 AS `customer_id`,
 1 AS `so_date`,
 1 AS `employee_id`,
 1 AS `drug_id`,
 1 AS `quantity`,
 1 AS `price`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `salesorder`
--

DROP TABLE IF EXISTS `salesorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salesorder` (
  `so_id` varchar(20) NOT NULL COMMENT '销售订单编号，VARCHAR类型主键',
  `customer_id` varchar(20) DEFAULT NULL COMMENT '客户编号，关联customer表',
  `so_date` char(10) NOT NULL COMMENT '销售日期，格式YYYY-MM-DD',
  `employee_id` varchar(20) DEFAULT NULL COMMENT '经办人编号，关联employee表',
  PRIMARY KEY (`so_id`),
  KEY `customer_id` (`customer_id`),
  KEY `employee_id` (`employee_id`),
  CONSTRAINT `salesorder_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`customer_id`),
  CONSTRAINT `salesorder_ibfk_2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单表，存储药品销售订单主信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `salesorderitem`
--

DROP TABLE IF EXISTS `salesorderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `salesorderitem` (
  `soi_id` varchar(20) NOT NULL COMMENT '销售订单项编号，VARCHAR类型主键',
  `so_id` varchar(20) DEFAULT NULL COMMENT '销售订单编号，关联salesorder表',
  `drug_id` varchar(20) DEFAULT NULL COMMENT '药品编号，关联drug表',
  `quantity` int NOT NULL COMMENT '销售数量，整数',
  `price` decimal(6,2) DEFAULT NULL COMMENT '销售单价，保留2位小数',
  `batch_no` varchar(50) NOT NULL COMMENT '药品批次号',
  PRIMARY KEY (`soi_id`),
  KEY `so_id` (`so_id`),
  KEY `drug_id` (`drug_id`),
  CONSTRAINT `salesorderitem_ibfk_1` FOREIGN KEY (`so_id`) REFERENCES `salesorder` (`so_id`),
  CONSTRAINT `salesorderitem_ibfk_2` FOREIGN KEY (`drug_id`) REFERENCES `drug` (`drug_id`),
  CONSTRAINT `chk_salesorderitem_price` CHECK (((`price` > 0) and (`price` <= 9999.99))),
  CONSTRAINT `chk_salesorderitem_quantity` CHECK ((`quantity` > 0))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单项表，存储每个销售订单的药品明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplier_id` varchar(20) NOT NULL COMMENT '供应商编号，VARCHAR类型主键',
  `name` varchar(50) NOT NULL COMMENT '供应商名称',
  `contact_phone` char(11) DEFAULT NULL COMMENT '联系电话，11位手机号',
  `status` tinyint(1) DEFAULT NULL COMMENT '合作状态（0停用/1启用）',
  PRIMARY KEY (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商表，存储药品采购供应商信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` varchar(50) NOT NULL COMMENT '用户账号',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '用户密码',
  `role` varchar(50) DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表，存储每个用户的账号密码信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouse` (
  `warehouse_id` varchar(20) NOT NULL COMMENT '仓库编号，VARCHAR类型主键',
  `name` varchar(50) NOT NULL COMMENT '仓库名称',
  `location` varchar(30) DEFAULT NULL COMMENT '仓库地址/位置',
  PRIMARY KEY (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仓库表，存储药品存储仓库信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `warehousein`
--

DROP TABLE IF EXISTS `warehousein`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehousein` (
  `wi_id` varchar(20) NOT NULL COMMENT '入库单编号，VARCHAR类型主键',
  `po_id` varchar(20) DEFAULT NULL COMMENT '采购订单编号，关联purchaseorder表',
  `warehouse_id` varchar(20) DEFAULT NULL COMMENT '仓库编号，关联warehouse表',
  `in_date` char(10) NOT NULL COMMENT '入库日期，格式YYYY-MM-DD',
  `batch_no` varchar(50) NOT NULL COMMENT '药品批次号',
  `validity_date` char(10) NOT NULL COMMENT '有效期，格式YYYY-MM-DD',
  PRIMARY KEY (`wi_id`),
  KEY `po_id` (`po_id`),
  KEY `warehouse_id` (`warehouse_id`),
  CONSTRAINT `warehousein_ibfk_1` FOREIGN KEY (`po_id`) REFERENCES `purchaseorder` (`po_id`),
  CONSTRAINT `warehousein_ibfk_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`warehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='入库表，存储药品采购入库记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping routines for database 'medicine'
--
/*!50003 DROP FUNCTION IF EXISTS `TOTAL_PRICE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `TOTAL_PRICE`(new_customer_id VARCHAR(20)) RETURNS decimal(6,2)
    READS SQL DATA
BEGIN
    DECLARE total DECIMAL(6,2) DEFAULT 0.00;
    SELECT COALESCE(SUM(quantity*price), 0) INTO total 
    FROM salesorderitem 
    WHERE salesorderitem.so_id IN (SELECT so_id FROM salesorder WHERE salesorder.customer_id=new_customer_id);
    return total;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `get_drug_price` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_drug_price`(IN q_drug_id VARCHAR(20),
OUT out_price DECIMAL(6,2))
BEGIN
    SELECT retail_price INTO out_price FROM drug WHERE drug_id=q_drug_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `purchase` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `purchase`(
    IN n_drug_id VARCHAR(20),
    IN n_batch_no VARCHAR(50),
    IN n_quantity INT,
    IN n_po_date CHAR(10),
    IN n_validity_date CHAR(10),
    IN n_supplier_id VARCHAR(20),
    IN n_employee_id VARCHAR(20),
    IN n_audit_status BOOLEAN,
    IN n_warehouse_id VARCHAR(20)
)
BEGIN
    SET @new_po_id = CONCAT('PO', (SELECT COUNT(po_id)+1 FROM purchaseorder));
    INSERT INTO purchaseorder (po_id,supplier_id,po_date,employee_id,audit_status) 
    VALUES
    (@new_po_id,n_supplier_id,n_po_date,n_employee_id,n_audit_status);

    SET @new_poi_id = CONCAT('PI',(SELECT COUNT(poi_id)+1 FROM purchaseorderitem));
    SET @price = (SELECT purchase_price FROM drug WHERE drug_id=n_drug_id);
    INSERT INTO purchaseorderitem(poi_id,po_id,drug_id,quantity,price) 
    VALUES
    (@new_poi_id,@new_po_id,n_drug_id,n_quantity,@price);

    SET @new_inventory_id = CONCAT('IN',(SELECT COUNT(inventory_id)+1 FROM inventory));
    SET @ttt_drug_id=(SELECT drug_id FROM inventory WHERE drug_id=n_drug_id);
    IF @ttt_drug_id IS NULL THEN
    INSERT INTO inventory(inventory_id,drug_id,warehouse_id,batch_no,quantity,validity_date) 
    VALUES
    (@new_inventory_id,n_drug_id,n_warehouse_id,n_batch_no,n_quantity,n_validity_date);
    ELSE
    UPDATE inventory 
    SET quantity=quantity+n_quantity
    WHERE drug_id=n_drug_id;
    END IF;

    SET @new_warehousein_id = CONCAT('WI',(SELECT COUNT(warehouse_id)+1 FROM warehousein));
    INSERT INTO warehousein(wi_id,po_id,warehouse_id,in_date,batch_no,validity_date) 
    VALUES
    (@new_warehousein_id,@new_po_id,n_warehouse_id,n_po_date,n_batch_no,n_validity_date);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `drug_information`
--

/*!50001 DROP VIEW IF EXISTS `drug_information`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `drug_information` AS select `d`.`generic_name` AS `generic_name`,`d`.`dosage_form` AS `dosage_form`,`d`.`specification` AS `specification`,`d`.`unit` AS `unit`,`d`.`retail_price` AS `retail_price`,`i`.`validity_date` AS `validity_date` from (`drug` `d` join `inventory` `i` on((`d`.`drug_id` = `i`.`drug_id`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `purchase_information`
--

/*!50001 DROP VIEW IF EXISTS `purchase_information`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `purchase_information` AS select `po`.`po_id` AS `po_id`,`po`.`supplier_id` AS `supplier_id`,`po`.`po_date` AS `po_date`,`po`.`employee_id` AS `employee_id`,`po`.`audit_status` AS `audit_status`,`poi`.`drug_id` AS `drug_id`,`poi`.`quantity` AS `quantity`,`poi`.`price` AS `price` from (`purchaseorder` `po` join `purchaseorderitem` `poi` on((`po`.`po_id` = `poi`.`po_id`))) order by `po`.`po_date` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `sale_information`
--

/*!50001 DROP VIEW IF EXISTS `sale_information`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_unicode_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `sale_information` AS select `sa`.`so_id` AS `so_id`,`sa`.`customer_id` AS `customer_id`,`sa`.`so_date` AS `so_date`,`sa`.`employee_id` AS `employee_id`,`sai`.`drug_id` AS `drug_id`,`sai`.`quantity` AS `quantity`,`sai`.`price` AS `price` from (`salesorder` `sa` join `salesorderitem` `sai` on((`sa`.`so_id` = `sai`.`so_id`))) order by `sa`.`so_date` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-31 11:09:18
