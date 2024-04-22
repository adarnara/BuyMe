CREATE DATABASE IF NOT EXISTS mybuy;

USE mybuy;


CREATE TABLE IF NOT EXISTS CustomerRep (
    CustomerRep_ID INT AUTO_INCREMENT,
    CustomerRep_login VARCHAR(15) NOT NULL UNIQUE,
    email_address VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    PRIMARY KEY(CustomerRep_ID)
);

CREATE TABLE IF NOT EXISTS Admin (
    Admin_ID INT AUTO_INCREMENT,
    admin_login VARCHAR(15) NOT NULL UNIQUE,
    email_address VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    CustomerRep_ID INT,
    PRIMARY KEY(Admin_ID),
    FOREIGN KEY (CustomerRep_ID) REFERENCES CustomerRep(CustomerRep_ID)
);

DELIMITER $$

CREATE TRIGGER one_row BEFORE INSERT ON Admin
    FOR EACH ROW
BEGIN
    DECLARE admin_count INT;

    SELECT COUNT(*) INTO admin_count FROM Admin;

    IF admin_count >= 1 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error: There can only be one admin.';
    END IF;
END$$

DELIMITER ;



CREATE TABLE IF NOT EXISTS Items (
    Item_ID INT AUTO_INCREMENT,
    brand VARCHAR(25),
    name VARCHAR(50),
    color_variants VARCHAR(50),
    PRIMARY KEY(Item_ID)
);

CREATE TABLE IF NOT EXISTS EndUser (
    User_Id INT AUTO_INCREMENT,
    endUser_login VARCHAR(15) NOT NULL UNIQUE,
    email_address VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    salt VARCHAR(255) NOT NULL,
    user_type ENUM('buyer', 'seller') NOT NULL DEFAULT 'buyer',
    BidAlert TEXT,
    PRIMARY KEY(User_Id)
);

CREATE TABLE IF NOT EXISTS Question (
    question_ID INT AUTO_INCREMENT,
    answer_text TEXT,
    question_text TEXT,
    CustomerRep_ID INT NOT NULL,
    User_Id INT NOT NULL,
    PRIMARY KEY(question_ID),
    FOREIGN KEY (CustomerRep_ID) REFERENCES CustomerRep(CustomerRep_ID),
    FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id)
);

CREATE TABLE IF NOT EXISTS Auction (
    Auction_ID INT AUTO_INCREMENT,
    Current_Price DOUBLE,
    Auction_Closing_Date DATE,
    Auction_Closing_time TIME,
    Bid_Increment DOUBLE,
    Initial_Price DOUBLE,
    Minimum DOUBLE,
    Upper_Limit DOUBLE,
    Winner VARCHAR(50),
    User_Id INT NOT NULL,
    Item_ID INT NOT NULL,
    PRIMARY KEY(Auction_ID),
    FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID),
    FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id)
);

CREATE TABLE IF NOT EXISTS Bid (
    Bid_ID INT AUTO_INCREMENT,
    Bid_Date DATE,
    Bid_time TIME,
    Bid_Amount DOUBLE,
    Auction_ID INT NOT NULL,
    User_Id INT NOT NULL,
    PRIMARY KEY(Bid_ID),
    FOREIGN KEY (Auction_ID) REFERENCES Auction(Auction_ID),
    FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id)
);

CREATE TABLE IF NOT EXISTS ItemsSubattributes ( -- Might not need this as originally was weak entity set, can just add fields and values to Items
    Item_ID INT,
    subattribute VARCHAR(25),
    PRIMARY KEY (Item_ID, subattribute),
    FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID)
);

CREATE TABLE IF NOT EXISTS Posts (
    Item_ID INT,
    User_Id INT,
    PRIMARY KEY (Item_ID, User_Id),
    FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID),
    FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id)
);

CREATE TABLE IF NOT EXISTS Category (
    Category_ID INT AUTO_INCREMENT,
    Category_Name VARCHAR(50),
    Sub_Category_ID INT,
    Item_ID INT,
    PRIMARY KEY(Category_ID),
    FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID),
    FOREIGN KEY (Sub_Category_ID) REFERENCES Category(Category_ID)
);

-- inserting ONE admin into the database with hashed password and salt
-- actual password is "Admin" and "One_Admin" is username.
INSERT INTO Admin (admin_login, email_address, password, salt)
VALUES ("One_Admin", "onlyadmin@gmail.com", "e1d0253d7e5ce8c582aa07c01e5cdf6bbd4d97ed7edec1e3921d469e77b0ea7f", "9fcb340a561f0d91148e068d544d94de");

-- example values to test auction
INSERT INTO Items (Item_ID)
VALUES (1)

INSERT INTO Auction (Auction_ID, Auction_Closing_Date, Auction_Closing_Time, Bid_Increment, Initial_Price, User_Id, Item_ID)
VALUES (1, '2022-04-26 13:30:00', '2022-04-26 13:30:00', 2.00, 40.50, 1, 1)