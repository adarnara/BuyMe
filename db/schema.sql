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
                                     FOREIGN KEY (CustomerRep_ID) REFERENCES CustomerRep(CustomerRep_ID) ON UPDATE CASCADE ON DELETE SET NULL
);


-- run with option-x on MACOS on dbeaver


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


-- Make sure for Category/Items you are using MySQL 5.6 above and you are using InnoDB which is the Engine supporting FULLTEXT indexes.

CREATE TABLE IF NOT EXISTS Category (
                                        Category_ID INT AUTO_INCREMENT,
                                        Category_Name VARCHAR(50),
                                        Parent_Category_ID INT,
                                        PRIMARY KEY (Category_ID),
                                        FULLTEXT (Category_Name),
                                        FOREIGN KEY (Parent_Category_ID) REFERENCES Category(Category_ID) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;



CREATE TABLE IF NOT EXISTS Items (
                                     Item_ID INT AUTO_INCREMENT,
                                     brand VARCHAR(25),
                                     name VARCHAR(50),
                                     color_variants VARCHAR(50),
                                     Category_ID INT,
                                     image_url VARCHAR(255),
                                     PRIMARY KEY (Item_ID),
                                     FULLTEXT (brand, name),
                                     FOREIGN KEY (Category_ID) REFERENCES Category(Category_ID) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS EndUser (
                                       User_Id INT AUTO_INCREMENT,
                                       endUser_login VARCHAR(15) NOT NULL UNIQUE,
                                       email_address VARCHAR(50) NOT NULL UNIQUE,
                                       password VARCHAR(255) NOT NULL,
                                       salt VARCHAR(255) NOT NULL,
                                       user_type ENUM('buyer', 'seller') NOT NULL DEFAULT 'buyer',
                                       alert_threshold_price DOUBLE DEFAULT NULL,
                                       wants_auction_close_alert BOOLEAN NOT NULL DEFAULT '0',
                                       PRIMARY KEY(User_Id)
);

CREATE TABLE IF NOT EXISTS Question (
                                        question_ID INT AUTO_INCREMENT,
                                        answer_text TEXT,
                                        question_text TEXT,
                                        CustomerRep_ID INT NOT NULL,
                                        User_Id INT NOT NULL,
                                        PRIMARY KEY(question_ID),
                                        FOREIGN KEY (CustomerRep_ID) REFERENCES CustomerRep(CustomerRep_ID) ON UPDATE CASCADE ON DELETE SET NULL,
                                        FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Auction (

                                       Auction_ID INT AUTO_INCREMENT,
                                       Current_Price DOUBLE,
                                       Auction_Closing_Date DATE,
                                       Auction_Closing_time TIME,
                                       Bid_Increment DOUBLE,
                                       Initial_Price DOUBLE,
                                       Minimum DOUBLE,
                                       Winner VARCHAR(50),
                                       auction_status ENUM('active', 'completed', 'cancelled') NOT NULL DEFAULT 'active',
                                       User_Id INT NOT NULL,
                                       Item_ID INT NOT NULL,
                                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                       PRIMARY KEY(Auction_ID),
                                       FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID) ON UPDATE CASCADE ON DELETE SET NULL,
                                       FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id) ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS Bid (
                                   Bid_ID INT AUTO_INCREMENT,
                                   Bid_Date DATE DEFAULT CURRENT_DATE,
                                   Bid_Time TIME DEFAULT CURRENT_TIME,
                                   Bid_Amount DOUBLE,
                                   Auction_ID INT NOT NULL,
                                   User_Id INT NOT NULL,
                                   PRIMARY KEY(Bid_ID),
                                   FOREIGN KEY (Auction_ID) REFERENCES Auction(Auction_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                                   FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id) ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS ItemsSubattributes (
                                                  Item_ID INT,
                                                  subattribute VARCHAR(25),
                                                  PRIMARY KEY (Item_ID, subattribute),
                                                  FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS Posts (
                                     Item_ID INT,
                                     User_Id INT,
                                     PRIMARY KEY (Item_ID, User_Id),
                                     FOREIGN KEY (Item_ID) REFERENCES Items(Item_ID) ON UPDATE CASCADE ON DELETE CASCADE,
                                     FOREIGN KEY (User_Id) REFERENCES EndUser(User_Id) ON UPDATE CASCADE ON DELETE SET NULL
);



DELIMITER $$

DROP FUNCTION IF EXISTS `LEVENSHTEIN`$$
CREATE FUNCTION `LEVENSHTEIN`(`s1` VARCHAR(255) CHARACTER SET utf8, `s2` VARCHAR(255) CHARACTER SET utf8)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE s1_len, s2_len, i, j, c, c_temp, cost INT;
    DECLARE s1_char CHAR;
    DECLARE cv0, cv1 VARBINARY(256);
    SET s1_len = CHAR_LENGTH(s1), s2_len = CHAR_LENGTH(s2), cv1 = 0x00, j = 1, i = 1, c = 0;
    IF s1 = s2 THEN
        RETURN 0;
    ELSEIF s1_len = 0 THEN
        RETURN s2_len;
    ELSEIF s2_len = 0 THEN
        RETURN s1_len;
    END IF;
    WHILE j <= s2_len DO
            SET cv1 = CONCAT(cv1, CHAR(j)),
                j = j + 1;
        END WHILE;
    WHILE i <= s1_len DO
            SET s1_char = SUBSTRING(s1, i, 1), c = i, cv0 = CHAR(i), j = 1;
            WHILE j <= s2_len DO
                    SET c = c + 1;
                    IF s1_char = SUBSTRING(s2, j, 1) THEN
                        SET cost = 0; ELSE SET cost = 1;
                    END IF;
                    SET c_temp = ORD(SUBSTRING(cv1, j, 1)) + cost;
                    IF c > c_temp THEN SET c = c_temp; END IF;
                    SET c_temp = ORD(SUBSTRING(cv1, j+1, 1)) + 1;
                    IF c > c_temp THEN
                        SET c = c_temp;
                    END IF;
                    SET cv0 = CONCAT(cv0, CHAR(c)), j = j + 1;
                END WHILE;
            SET cv1 = cv0, i = i + 1;
        END WHILE;
    RETURN c;
END$$

DROP FUNCTION IF EXISTS `LEVENSHTEIN_RATIO`$$
CREATE FUNCTION `LEVENSHTEIN_RATIO`(`s1` VARCHAR(255) CHARACTER SET utf8, `s2` VARCHAR(255) CHARACTER SET utf8)
    RETURNS INT
    DETERMINISTIC
BEGIN
    DECLARE s1_len, s2_len, max_len INT;
    SET s1_len = LENGTH(s1), s2_len = LENGTH(s2);
    IF s1_len > s2_len THEN
        SET max_len = s1_len;
    ELSE
        SET max_len = s2_len;
    END IF;
    RETURN ROUND((1 - LEVENSHTEIN(s1, s2) / max_len) * 100);
END$$

DELIMITER ;


SELECT LEVENSHTEIN_RATIO('Laptops', 'macboofdafdsfadsfsadffadsfadsfdsaffadsfadsfk pro 16-inch');



INSERT INTO Admin (admin_login, email_address, password, salt)
VALUES ("One_Admin", "onlyadmin@gmail.com", "e1d0253d7e5ce8c582aa07c01e5cdf6bbd4d97ed7edec1e3921d469e77b0ea7f", "9fcb340a561f0d91148e068d544d94de");