CREATE DATABASE IF NOT EXISTS mybuy;
USE mybuy;

CREATE TABLE IF NOT EXISTS helloworld (
      id INT AUTO_INCREMENT PRIMARY KEY,
      helloworld VARCHAR(255) NOT NULL
);

INSERT INTO helloworld (id, helloworld) VALUES (1, 'test full connection')
ON DUPLICATE KEY UPDATE helloworld = VALUES(helloworld);
