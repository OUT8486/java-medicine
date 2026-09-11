SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS manufacturer (
  manufacturer_id varchar(20) NOT NULL,
  name varchar(30) NOT NULL,
  credit_code char(18) DEFAULT NULL,
  PRIMARY KEY (manufacturer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS warehouse (
  warehouse_id varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  location varchar(30) DEFAULT NULL,
  PRIMARY KEY (warehouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS supplier (
  supplier_id varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  contact_phone char(11) DEFAULT NULL,
  status tinyint(1) DEFAULT NULL,
  PRIMARY KEY (supplier_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS employee (
  employee_id varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  post varchar(50) NOT NULL,
  PRIMARY KEY (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS customer (
  customer_id varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  type varchar(20) NOT NULL,
  contact_phone char(11) DEFAULT NULL,
  PRIMARY KEY (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS drug (
  drug_id varchar(20) NOT NULL,
  generic_name varchar(50) NOT NULL,
  approval_no varchar(50) NOT NULL,
  dosage_form varchar(40) NOT NULL,
  specification varchar(30) NOT NULL,
  unit varchar(30) NOT NULL,
  purchase_price decimal(6,2) DEFAULT NULL,
  retail_price decimal(6,2) DEFAULT NULL,
  manufacturer_id varchar(20) DEFAULT NULL,
  PRIMARY KEY (drug_id),
  UNIQUE KEY approval_no (approval_no),
  KEY manufacturer_id (manufacturer_id),
  CONSTRAINT drug_ibfk_1 FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (manufacturer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS purchaseorder (
  po_id varchar(20) NOT NULL,
  supplier_id varchar(20) DEFAULT NULL,
  po_date char(10) NOT NULL,
  employee_id varchar(20) DEFAULT NULL,
  audit_status tinyint(1) DEFAULT NULL,
  PRIMARY KEY (po_id),
  KEY supplier_id (supplier_id),
  KEY employee_id (employee_id),
  CONSTRAINT purchaseorder_ibfk_1 FOREIGN KEY (supplier_id) REFERENCES supplier (supplier_id),
  CONSTRAINT purchaseorder_ibfk_2 FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS purchaseorderitem (
  poi_id varchar(20) NOT NULL,
  po_id varchar(20) DEFAULT NULL,
  drug_id varchar(20) DEFAULT NULL,
  quantity int NOT NULL,
  price decimal(6,2) NOT NULL,
  PRIMARY KEY (poi_id),
  KEY po_id (po_id),
  KEY drug_id (drug_id),
  CONSTRAINT purchaseorderitem_ibfk_1 FOREIGN KEY (po_id) REFERENCES purchaseorder (po_id),
  CONSTRAINT purchaseorderitem_ibfk_2 FOREIGN KEY (drug_id) REFERENCES drug (drug_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS warehousein (
  wi_id varchar(20) NOT NULL,
  po_id varchar(20) DEFAULT NULL,
  warehouse_id varchar(20) DEFAULT NULL,
  in_date char(10) NOT NULL,
  batch_no varchar(50) NOT NULL,
  validity_date char(10) NOT NULL,
  PRIMARY KEY (wi_id),
  KEY po_id (po_id),
  KEY warehouse_id (warehouse_id),
  CONSTRAINT warehousein_ibfk_1 FOREIGN KEY (po_id) REFERENCES purchaseorder (po_id),
  CONSTRAINT warehousein_ibfk_2 FOREIGN KEY (warehouse_id) REFERENCES warehouse (warehouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS inventory (
  inventory_id varchar(20) NOT NULL,
  drug_id varchar(20) DEFAULT NULL,
  warehouse_id varchar(20) DEFAULT NULL,
  batch_no varchar(50) NOT NULL,
  quantity int NOT NULL,
  validity_date char(10) NOT NULL,
  PRIMARY KEY (inventory_id),
  KEY drug_id (drug_id),
  KEY warehouse_id (warehouse_id),
  CONSTRAINT inventory_ibfk_1 FOREIGN KEY (drug_id) REFERENCES drug (drug_id),
  CONSTRAINT inventory_ibfk_2 FOREIGN KEY (warehouse_id) REFERENCES warehouse (warehouse_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS salesorder (
  so_id varchar(20) NOT NULL,
  customer_id varchar(20) DEFAULT NULL,
  so_date char(10) NOT NULL,
  employee_id varchar(20) DEFAULT NULL,
  PRIMARY KEY (so_id),
  KEY customer_id (customer_id),
  KEY employee_id (employee_id),
  CONSTRAINT salesorder_ibfk_1 FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
  CONSTRAINT salesorder_ibfk_2 FOREIGN KEY (employee_id) REFERENCES employee (employee_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS salesorderitem (
  soi_id varchar(20) NOT NULL,
  so_id varchar(20) DEFAULT NULL,
  drug_id varchar(20) DEFAULT NULL,
  quantity int NOT NULL,
  price decimal(6,2) DEFAULT NULL,
  batch_no varchar(50) NOT NULL,
  PRIMARY KEY (soi_id),
  KEY so_id (so_id),
  KEY drug_id (drug_id),
  CONSTRAINT salesorderitem_ibfk_1 FOREIGN KEY (so_id) REFERENCES salesorder (so_id),
  CONSTRAINT salesorderitem_ibfk_2 FOREIGN KEY (drug_id) REFERENCES drug (drug_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS users (
  user_id varchar(50) NOT NULL,
  user_name varchar(50) NOT NULL,
  password varchar(255) NOT NULL,
  role varchar(50) DEFAULT NULL,
  PRIMARY KEY (user_id),
  UNIQUE KEY username (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
