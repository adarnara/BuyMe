CREATE DATABASE IF NOT EXISTS mybuy;
USE mybuy;


CREATE TABLE  IF NOT EXISTS EndUser (
                         User_id INT AUTO_INCREMENT,
                         endUser_login VARCHAR(15) NOT NULL UNIQUE,
                         email_address VARCHAR(50) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL UNIQUE,
                         isBuyer BOOLEAN DEFAULT FALSE,
                         isSeller BOOLEAN DEFAULT FALSE,
                         BidAlert TEXT,
                         PRIMARY KEY(User_id)
);
