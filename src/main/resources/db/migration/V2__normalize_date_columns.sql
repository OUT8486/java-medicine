-- 将日期字符串列规范为 DATE 类型。
-- 先清洗非法/空日期，避免 ALTER 失败；非法日期统一置为 1970-01-01。
UPDATE purchaseorder SET po_date = '1970-01-01'
  WHERE po_date IS NULL OR STR_TO_DATE(po_date, '%Y-%m-%d') IS NULL;
UPDATE salesorder SET so_date = '1970-01-01'
  WHERE so_date IS NULL OR STR_TO_DATE(so_date, '%Y-%m-%d') IS NULL;
UPDATE warehousein SET in_date = '1970-01-01'
  WHERE in_date IS NULL OR STR_TO_DATE(in_date, '%Y-%m-%d') IS NULL;
UPDATE warehousein SET validity_date = '1970-01-01'
  WHERE validity_date IS NULL OR STR_TO_DATE(validity_date, '%Y-%m-%d') IS NULL;
UPDATE inventory SET validity_date = '1970-01-01'
  WHERE validity_date IS NULL OR STR_TO_DATE(validity_date, '%Y-%m-%d') IS NULL;

ALTER TABLE purchaseorder MODIFY po_date DATE NOT NULL;
ALTER TABLE salesorder MODIFY so_date DATE NOT NULL;
ALTER TABLE warehousein MODIFY in_date DATE NOT NULL, MODIFY validity_date DATE NOT NULL;
ALTER TABLE inventory MODIFY validity_date DATE NOT NULL;
