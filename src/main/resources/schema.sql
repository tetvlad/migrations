DROP TABLE IF EXISTS ORDERS;
DROP TABLE IF EXISTS CUSTOMERS;

CREATE TABLE CUSTOMERS
(
    id           INT IDENTITY(1,1) PRIMARY KEY,
    name         NVARCHAR(50) NOT NULL,
    surname      NVARCHAR(50) NOT NULL,
    age          INT,
    phone_number NVARCHAR(25)
);

CREATE TABLE ORDERS
(
    id           INT IDENTITY(1,1) PRIMARY KEY,
    date         DATETIME       NOT NULL,
    customer_id  INT            NOT NULL,
    product_name NVARCHAR(50) NOT NULL,
    amount       DECIMAL(10, 2) NOT NULL,
    CONSTRAINT FK_Orders_Customers FOREIGN KEY (customer_id) REFERENCES CUSTOMERS (id)
);

INSERT INTO CUSTOMERS (name, surname, age, phone_number)
VALUES (N'Alexey', N'Ivanov', 30, N'+79990000001'),
       (N'alexey', N'Petrov', 25, N'+79990000002'),
       (N'Maria', N'Sidorova', 28, N'+79990000003');

INSERT INTO ORDERS (date, customer_id, product_name, amount)
VALUES (GETDATE(), 1, N'Laptop', 75000),
       (GETDATE(), 1, N'Mouse', 1500),
       (GETDATE(), 2, N'Keyboard', 3500),
       (GETDATE(), 3, N'Monitor', 22000);