--liquibase formatted sql

--changeset aarrieta-it001:1 runOnChange:true
--comment: DDL for table Users
CREATE TABLE Users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  firstname varchar(100) NOT NULL,
  lastname varchar(100) NOT NULL,
  role varchar(20) NOT NULL default 'USER',
  active smallint(6) NOT NULL DEFAULT '1',
  deleted smallint(6) default '0',
  CONSTRAINT Users_PK PRIMARY KEY (id),
  CONSTRAINT Users_UK1 UNIQUE KEY (username)
);
INSERT INTO Users (username, firstname, lastname, `role`, active, deleted)
VALUES('admin@blossom.com', 'ADMIN', 'BLOSSOM', 'ADMIN', 1, 0) ON DUPLICATE KEY UPDATE username=username;
--rollback drop table Users;

--changeset aarrieta-it001:2 runOnChange:true
--comment: DDL for tabla Products
CREATE TABLE Products (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  code varchar(25) NOT NULL,
  name varchar(100) NOT NULL,
  category varchar(100) NOT NULL,
  brand varchar(20) NOT NULL,
  price numeric(15,2) NOT NULL,
  active smallint(6) NOT NULL DEFAULT '1',
  deleted smallint(6) default '0',
  CONSTRAINT Products_PK PRIMARY KEY (id),
  CONSTRAINT Products_UK1 UNIQUE KEY (code)
);
--rollback drop table Products;

--changeset aarrieta-it001:3 runOnChange:true
--comment: Data for tabla Products
INSERT INTO Products(code, name, category, brand, price, active, deleted)
VALUES('AAA', 'Product name for AAA', 'CATEGORY_A', 'BRAND_A', 9.90, 1, 0) ON DUPLICATE KEY UPDATE code=code;
INSERT INTO Products(code, name, category, brand, price, active, deleted)
VALUES('BBB', 'Product name for BBB', 'CATEGORY_B', 'BRAND_B', 5.90, 1, 0) ON DUPLICATE KEY UPDATE code=code;
INSERT INTO Products(code, name, category, brand, price, active, deleted)
VALUES('CCC', 'Product name for CCC', 'CATEGORY_C', 'BRAND_C', 6.90, 1, 0) ON DUPLICATE KEY UPDATE code=code;
INSERT INTO Products(code, name, category, brand, price, active, deleted)
VALUES('DDD', 'Product name for DDD', 'CATEGORY_D', 'BRAND_C', 20.50, 1, 0) ON DUPLICATE KEY UPDATE code=code;
INSERT INTO Products(code, name, category, brand, price, active, deleted)
VALUES('AAZ', 'Product name for AAZ', 'CATEGORY_C', 'BRAND_B', 14.35, 1, 0) ON DUPLICATE KEY UPDATE code=code;
--rollback delete from Products;


--changeset aarrieta-it001:4 runOnChange:true
--comment: DDL for tabla Orders
CREATE TABLE Orders (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  idfkuser bigint(20) NOT NULL,
  dateorder datetime NOT NULL,
  confirmed smallint(6) default '0',
  CONSTRAINT Order_PK PRIMARY KEY (id),
  CONSTRAINT Order_FK1 FOREIGN KEY (idfkuser) REFERENCES Users(id)
);
CREATE TABLE OrdersDetail (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  idfkorder bigint(20),
  idfkproduct bigint(20) NOT NULL,
  quantity numeric(15,2) NOT NULL,
  price numeric(15,2) NOT NULL,
  CONSTRAINT OrderDetail_PK PRIMARY KEY (id),
  CONSTRAINT OrderDetail_FK1 FOREIGN KEY (idfkorder) REFERENCES Orders(id),
  CONSTRAINT OrderDetail_FK2 FOREIGN KEY (idfkproduct) REFERENCES Products(id)
);
--rollback drop table OrdersDetail;
--rollback drop table Orders;