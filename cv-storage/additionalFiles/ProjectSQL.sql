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
  `EMAIL_ADRESS` varchar(255) DEFAULT NULL,
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
  `CANDIDATE_ID` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`KNOWLEDGE_ID`),
  
  CONSTRAINT `FK_KNOWLEDGE_CANDIDATE` FOREIGN KEY (`CANDIDATE_ID`) REFERENCES `candidate` (`ID`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  `cv_storage`.`known_technologies` (
  `Knowledge_KNOWLEDGE_ID` varchar(255) NOT NULL,
  `KNOWN_TECHNOLOGIES` varchar(255) DEFAULT NULL,
  
  CONSTRAINT `FK_TECHNOLOG_KNOWLEDGE` FOREIGN KEY (`Knowledge_KNOWLEDGE_ID`) REFERENCES `knowledge` (`KNOWLEDGE_ID`)
  ON DELETE NO ACTION
  ON UPDATE CASCADE
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT IGNORE INTO `cv_storage`.`candidate` (ID, FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DATE, GENDER) 
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



INSERT IGNORE INTO `cv_storage`.`knowledge` (KNOWLEDGE_ID, CANDIDATE_ID) 
VALUES 
('daeccd79-692b-11eb-8bfa-a08cfda726f3','3cbd2faf-691c-11eb-8bfa-a08cfda726f3'),
('e30c5a2e-692b-11eb-8bfa-a08cfda726f3','354b0bdb-691e-11eb-8bfa-a08cfda726f3'),
('eae362c7-692b-11eb-8bfa-a08cfda726f3','c0ad2f85-6920-11eb-8bfa-a08cfda726f3');



INSERT IGNORE INTO `cv_storage`.`KNOWN_TECHNOLOGIES` (Knowledge_KNOWLEDGE_ID, KNOWN_TECHNOLOGIES) 
VALUES 
('daeccd79-692b-11eb-8bfa-a08cfda726f3','Git'),
('daeccd79-692b-11eb-8bfa-a08cfda726f3','Spring Boot'),
('daeccd79-692b-11eb-8bfa-a08cfda726f3','HTML'),
('e30c5a2e-692b-11eb-8bfa-a08cfda726f3','Git'),
('e30c5a2e-692b-11eb-8bfa-a08cfda726f3','Java EE'),
('e30c5a2e-692b-11eb-8bfa-a08cfda726f3','Java Core'),
('eae362c7-692b-11eb-8bfa-a08cfda726f3','Maven'),
('eae362c7-692b-11eb-8bfa-a08cfda726f3','REST'),
('eae362c7-692b-11eb-8bfa-a08cfda726f3','Spring');

INSERT IGNORE INTO `cv_storage`.`skype` (skype_ID,SKYPE_USER_NAME, CANDIDATE_ID) 
VALUES
('120e7b5a-693c-11eb-8bfa-a08cfda726f3','ivanko','354b0bdb-691e-11eb-8bfa-a08cfda726f3');
