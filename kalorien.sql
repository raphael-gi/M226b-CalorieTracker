-- MariaDB dump 10.19  Distrib 10.4.22-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: kalorien
-- ------------------------------------------------------
-- Server version	10.4.22-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `benutzer`
--

DROP TABLE IF EXISTS `benutzer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `benutzer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Benutzername` varchar(90) NOT NULL,
  `Passwort` varchar(90) NOT NULL,
  `dark` tinyint(1) NOT NULL DEFAULT 0,
  `gender` tinyint(2) NOT NULL,
  `age` tinyint(150) NOT NULL,
  `gewicht` float NOT NULL,
  `groesse` float NOT NULL,
  `muskel` tinyint(1) NOT NULL DEFAULT 0,
  `bulk` tinyint(2) NOT NULL DEFAULT 0,
  `sprache` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `benutzer`
--

LOCK TABLES `benutzer` WRITE;
/*!40000 ALTER TABLE `benutzer` DISABLE KEYS */;
INSERT INTO `benutzer` VALUES (8,'f','21dc09be5c176dc2f0e02c01bb7f70f00b310cbed0957959cb30ff6b03fe31d7',1,2,13,73,184,0,1,1);
/*!40000 ALTER TABLE `benutzer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mahlzeit`
--

DROP TABLE IF EXISTS `mahlzeit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mahlzeit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(90) NOT NULL,
  `kalorien` int(11) NOT NULL,
  `carb` int(11) NOT NULL,
  `protein` int(11) NOT NULL,
  `fat` int(11) NOT NULL,
  `ben` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mahlzeit`
--

LOCK TABLES `mahlzeit` WRITE;
/*!40000 ALTER TABLE `mahlzeit` DISABLE KEYS */;
INSERT INTO `mahlzeit` VALUES (23,'t',61,2,2,5,2),(24,'f',78,3,3,6,2),(25,'Ei',102,6,6,6,2),(26,'Ei',230,10,25,10,8);
/*!40000 ALTER TABLE `mahlzeit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mmm`
--

DROP TABLE IF EXISTS `mmm`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mmm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mahl` int(11) NOT NULL,
  `port` float NOT NULL,
  `kalorien` int(11) NOT NULL,
  `carb` float NOT NULL,
  `protein` float NOT NULL,
  `fat` float NOT NULL,
  `datum` date NOT NULL,
  `ben` int(11) NOT NULL,
  `mahlzeit` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mmm`
--

LOCK TABLES `mmm` WRITE;
/*!40000 ALTER TABLE `mmm` DISABLE KEYS */;
INSERT INTO `mmm` VALUES (93,24,1,51,3,3,3,'2022-03-25',2,3),(105,25,0,0,0,0,0,'2022-03-26',2,3),(110,23,9,549,18,18,45,'2022-03-29',2,1);
/*!40000 ALTER TABLE `mmm` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-04-10 18:46:01
