CREATE DATABASE IF NOT EXISTS `cv_storage`
CHARACTER SET utf8
COLLATE utf8_general_ci;
            
CREATE TABLE IF NOT EXISTS  `cv_storage`.`candidate` (
  `ID` varchar(255) NOT NULL,
  `BIRTH_DATE` date DEFAULT NULL,
  `FIRST_NAME` varchar(255) DEFAULT NULL,
  `GENDER` varchar(255) DEFAULT NULL,
  `LAST_NAME` varchar(255) DEFAULT NULL,
  `MIDDLE_NAME` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;            



CREATE TABLE IF NOT EXISTS  `cv_storage`. `email` (
  `EMAIL_ID` varchar(255) NOT NULL,
  `EMAIL_ADDRESS` varchar(255) DEFAULT NULL,
  `CANDIDATE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`EMAIL_ID`),
  
  CONSTRAINT `FK_EMAIL_CANDIDATE` FOREIGN KEY (`CANDIDATE_ID`) REFERENCES `candidate` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS  `cv_storage`.`phone` (
  `PHONE_ID` varchar(255) NOT NULL,
  `PHONE_NUMBER` varchar(255) DEFAULT NULL,
  `CANDIDATE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PHONE_ID`),
  
  CONSTRAINT `FK_PHONE_CANDIDATE` FOREIGN KEY (`CANDIDATE_ID`) REFERENCES `candidate` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS  `cv_storage`.`site` (
  `SITE_ID` varchar(255) NOT NULL,
  `SITE_URL` varchar(255) DEFAULT NULL,
  `CANDIDATE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SITE_ID`),
  
  CONSTRAINT `FK_SITE_CANDIDATE` FOREIGN KEY (`CANDIDATE_ID`) REFERENCES `candidate` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS  `cv_storage`.`skype` (
  `SKYPE_ID` varchar(255) NOT NULL,
  `SKYPE_USER_NAME` varchar(255) DEFAULT NULL,
  `CANDIDATE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SKYPE_ID`),
  
  CONSTRAINT `FK_SKYPE_CANDIDATE` FOREIGN KEY (`CANDIDATE_ID`) REFERENCES `candidate` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS  `cv_storage`.`knowledge` (
  `KNOWLEDGE_ID` varchar(255) NOT NULL,
  `KNOWLEDGE_NAME` varchar(255) NOT NULL,
  `CANDIDATE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KNOWLEDGE_ID`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE  IF NOT EXISTS `cv_storage`.`candidate_knowledge` (
  `KNOWLEDGE_ID` varchar(255) NOT NULL,
  `CANDIDATE_ID` varchar(255) NOT NULL,
  PRIMARY KEY (`CANDIDATE_ID`,`KNOWLEDGE_ID`),
  CONSTRAINT `FK_CANDIDATE_KNOWLEDGE` FOREIGN KEY (`KNOWLEDGE_ID`) REFERENCES `knowledge` (`KNOWLEDGE_ID`),
  CONSTRAINT `FK_KNOWLEDGE_CANDIDATE` FOREIGN KEY (`CANDIDATE_ID`) REFERENCES `candidate` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT IGNORE INTO `cv_storage`.`candidate` (ID,LAST_NAME, FIRST_NAME, MIDDLE_NAME,  BIRTH_DATE, GENDER)
VALUES 
		('3cbd2faf-691c-11eb-8bfa-a08cfda726f3', 'Петров', 'Петр', 'Петрович', '1986-12-12', 'МУЖЧИНА'),
        ('354b0bdb-691e-11eb-8bfa-a08cfda726f3', 'Иванов', 'Иван', 'Иванович', '1997-04-04', 'МУЖЧИНА'),
        ('c0ad2f85-6920-11eb-8bfa-a08cfda726f3', 'Морская', 'Мария', 'Васильевна', '1999-11-07', 'ЖЕНЩИНА');
        
 
INSERT IGNORE INTO `cv_storage`.`email` (EMAIL_ID, EMAIL_ADRESS, CANDIDATE_ID) 
VALUES 
('9b51248c-6921-11eb-8bfa-a08cfda726f3','petrovich@gmail.com','3cbd2faf-691c-11eb-8bfa-a08cfda726f3');



INSERT IGNORE INTO `cv_storage`.`phone` (PHONE_ID, PHONE_NUMBER, CANDIDATE_ID) 
VALUES 
('373fad84-6923-11eb-8bfa-a08cfda726f3','+375(29)123-45-67','3cbd2faf-691c-11eb-8bfa-a08cfda726f3'),
('e60f7a0f-6923-11eb-8bfa-a08cfda726f3','+375(29)87-65-43','354b0bdb-691e-11eb-8bfa-a08cfda726f3'),
('1aeba390-6924-11eb-8bfa-a08cfda726f3','+375(29)999-99-99','c0ad2f85-6920-11eb-8bfa-a08cfda726f3');


    
INSERT IGNORE INTO `cv_storage`.`site` (SITE_ID, SITE_URL, CANDIDATE_ID) 
VALUES 
('dc72a1ea-6928-11eb-8bfa-a08cfda726f3','http://github.com/petya','3cbd2faf-691c-11eb-8bfa-a08cfda726f3'),
('e2e136fb-6928-11eb-8bfa-a08cfda726f3','http://github.com/vanya','354b0bdb-691e-11eb-8bfa-a08cfda726f3'),
('e985628e-6928-11eb-8bfa-a08cfda726f3','https://www.linkedin.com/in/mariya/','c0ad2f85-6920-11eb-8bfa-a08cfda726f3');



INSERT IGNORE INTO `cv_storage`.`knowledge` (KNOWLEDGE_ID, KNOWLEDGE_NAME) 
VALUES 
('daeccd79-692b-11eb-8bfa-a08cfda726f3','Git'),
('e30c5a2e-692b-11eb-8bfa-a08cfda726f3','Spring Boot'),
('eae362c7-692b-11eb-8bfa-a08cfda726f3','HTML'),
('39236a99-7746-11eb-9a09-a08cfda726f3','Java EE'),
('21d6ad01-7747-11eb-9a09-a08cfda726f3','Java Core'),
('32696068-7747-11eb-9a09-a08cfda726f3','Maven'),
('46850deb-7747-11eb-9a09-a08cfda726f3','REST'),
('5043b27d-7747-11eb-9a09-a08cfda726f3','Spring');




INSERT IGNORE INTO `cv_storage`.`candidate_knowledge` (KNOWLEDGE_ID, CANDIDATE_ID) 
VALUES 
('daeccd79-692b-11eb-8bfa-a08cfda726f3','3cbd2faf-691c-11eb-8bfa-a08cfda726f3'),
('e30c5a2e-692b-11eb-8bfa-a08cfda726f3','3cbd2faf-691c-11eb-8bfa-a08cfda726f3'),
('eae362c7-692b-11eb-8bfa-a08cfda726f3','3cbd2faf-691c-11eb-8bfa-a08cfda726f3'),
('daeccd79-692b-11eb-8bfa-a08cfda726f3','354b0bdb-691e-11eb-8bfa-a08cfda726f3'),
('39236a99-7746-11eb-9a09-a08cfda726f3','354b0bdb-691e-11eb-8bfa-a08cfda726f3'),
('21d6ad01-7747-11eb-9a09-a08cfda726f3','354b0bdb-691e-11eb-8bfa-a08cfda726f3'),
('32696068-7747-11eb-9a09-a08cfda726f3','c0ad2f85-6920-11eb-8bfa-a08cfda726f3'),
('46850deb-7747-11eb-9a09-a08cfda726f3','c0ad2f85-6920-11eb-8bfa-a08cfda726f3'),
('5043b27d-7747-11eb-9a09-a08cfda726f3','c0ad2f85-6920-11eb-8bfa-a08cfda726f3');

INSERT IGNORE INTO `cv_storage`.`skype` (skype_ID,SKYPE_USER_NAME, CANDIDATE_ID) 
VALUES
('120e7b5a-693c-11eb-8bfa-a08cfda726f3','ivanko','354b0bdb-691e-11eb-8bfa-a08cfda726f3');



