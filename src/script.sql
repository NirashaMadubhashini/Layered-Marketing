DROP DATABASE IF EXISTS `Layered-Marketing`;
CREATE DATABASE IF NOT EXISTS `Layered-Marketing`;
SHOW DATABASES ;
USE `Layered-Marketing`;

#-------------------
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
   customerId VARCHAR(15),
   title VARCHAR(15),
   name VARCHAR(45) NOT NULL DEFAULT 'Unknown',
   address VARCHAR(15),
   city VARCHAR(15),
   postalCode VARCHAR(15),
   Province VARCHAR(15),
   CONSTRAINT PRIMARY KEY (CustomerId)
);
SHOW TABLES ;
DESCRIBE Customer;

#---------------------
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
   orderId VARCHAR(15),
   itemCode VARCHAR(15),
   customerId VARCHAR(15),
   cost DOUBLE,
   orderDate DATE,
   orderTime VARCHAR(15),
   CONSTRAINT PRIMARY KEY (orderId),
   CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE,
   CONSTRAINT FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE `Order`;

#------------------------
DROP TABLE IF EXISTS OrderDetail;
CREATE TABLE IF NOT EXISTS OrderDetail(
   orderDetailId VARCHAR(15),
   orderDetailItemCode VARCHAR(15),
   orderDetailQty INT,
   price DOUBLE ,
   discount DOUBLE,
   CONSTRAINT PRIMARY KEY (orderDetailItemCode, orderDetailId),
   CONSTRAINT FOREIGN KEY (orderDetailId) REFERENCES `Order`(orderId) ON DELETE CASCADE ON UPDATE CASCADE
);
SHOW TABLES ;
DESCRIBE OrderDetail;

#------------------------
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
   itemCode VARCHAR(15),
   description VARCHAR(15),
   packSize VARCHAR(15),
   qtyOnHand INT DEFAULT 0,
   unitPrice DOUBLE DEFAULT 0.00,
   CONSTRAINT PRIMARY KEY (itemCode)
);
SHOW TABLES ;
DESCRIBE Item;

#------------------------

DROP TABLE IF EXISTS CustomerOrderDetail;
CREATE TABLE IF NOT EXISTS CustomerOrderDetail(
   CustomerName VARCHAR(45),
   orderId VARCHAR(15),
   itemCode VARCHAR(15),
   description VARCHAR(15),
   packSize VARCHAR(15),
   qty INT,
   unitPrice DOUBLE ,
   cost DOUBLE,
   date DATE,
   time VARCHAR(15),
   CONSTRAINT PRIMARY KEY (orderId, itemCode)
);
SHOW TABLES ;
DESCRIBE CustomerOrderDetail;

