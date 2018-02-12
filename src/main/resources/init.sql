CREATE DATABASE IF NOT EXISTS MOBILEOPERATOR
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;


USE MOBILEOPERATOR;

CREATE TABLE IF NOT EXISTS Tariffs (
  id   INT          NOT NULL AUTO_INCREMENT,
  price INT NOT NULL,
  PRIMARY KEY (Id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Options (
  id   INT          NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  value INT NOT NULL,
  PRIMARY KEY (id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Tariffs_Options(
  tariff_id int NOT NULL,
  option_id int NOT NULL,
  FOREIGN KEY (tariff_id) REFERENCES Tariffs (Id),
  FOREIGN KEY (option_id) REFERENCES Options (Id)
)
  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Clients(
  id INT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  birth DATE NOT NULL,
  passport_data VARCHAR(1000) NOT NULL,
  address VARCHAR(200) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  role VARCHAR(20) NOT NULL,
  password VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)

)
ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Contracts(
  id INT NOT NULL AUTO_INCREMENT,
  number VARCHAR(100) NOT NULL,
  tariff_id INT NOT NULL,
  client_id INT NOT NULL,
  is_active BOOLEAN NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (client_id) REFERENCES Clients(id),
  FOREIGN KEY (tariff_id) REFERENCES Tariffs(id)
)

  ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Contract_Option(
  contract_id INT NOT NULL,
  option_id INT NOT NULL,
  FOREIGN KEY (contract_id) REFERENCES Contracts (Id),
  FOREIGN KEY (option_id) REFERENCES Options (Id)

)

ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS Options_Compatibility(
  first_option_id INT NOT NULL,
  second_option_id INT NOT NULL,
  FOREIGN KEY (first_option_id) REFERENCES Options (Id),
  FOREIGN KEY (second_option_id) REFERENCES Options (Id)
)

  ENGINE = InnoDB;