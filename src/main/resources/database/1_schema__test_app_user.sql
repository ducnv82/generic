CREATE DATABASE  IF NOT EXISTS `test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `test`;
-- MySQL dump 10.13  Distrib 5.6.13, for Win32 (x86)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.5.19

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `app_user`
--

DROP TABLE IF EXISTS `app_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `city` varchar(50) DEFAULT NULL,
  `country` varchar(100) DEFAULT NULL,
  `postal_code` varchar(15) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `email` varchar(255) NOT NULL,
  `account_enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_hint` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `version` int(11) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `created_by` varchar(32) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `deleted` bit(1) DEFAULT NULL,
  `modified_by` varchar(32) DEFAULT NULL,
  `modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_user`
--

LOCK TABLES `app_user` WRITE;
/*!40000 ALTER TABLE `app_user` DISABLE KEYS */;
INSERT INTO `app_user` VALUES (-3,'\0','\0','','Denver','US','80210','CO','\0','two_roles_user@appfuse.org','','Two Roles','User','$2a$10$bH/ssqW8OhkTlIso9/yakubYODUOmh.6m5HEJvcBq3t3VdBh7ebqO','Not a female kitty.','','two_roles_user',1,'http://raibledesigns.com','script','2014-01-10 14:50:36','\0','script','2014-01-10 14:50:36'),(-2,'\0','\0','','Denver','US','80210','CO','\0','matt@raibledesigns.com','','Matt','Raible','$2a$10$bH/ssqW8OhkTlIso9/yakubYODUOmh.6m5HEJvcBq3t3VdBh7ebqO','Not a female kitty.','','admin',1,'http://raibledesigns.com','script','2014-01-10 14:50:36','\0','script','2014-01-10 14:50:36'),(-1,'\0','\0','','Denver','US','80210','CO','\0','matt_raible@yahoo.com','','Tomcat','User','$2a$10$CnQVJ9bsWBjMpeSKrrdDEeuIptZxXrwtI6CZ/OgtNxhIgpKxXeT9y','A male kitty.','','user',1,'http://tomcat.apache.org','script','2014-01-10 14:50:36','\0','script','2014-01-10 14:50:36');
/*!40000 ALTER TABLE `app_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-01-10 14:58:09
