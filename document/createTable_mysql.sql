CREATE TABLE `serverinfo` (
  `serverInfoSeq` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) NOT NULL,
  `charset` varchar(255) NOT NULL,
  `driver` varchar(255) NOT NULL,
  `host` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `port` varchar(255) NOT NULL,
  `schemaName` varchar(255) NOT NULL,
  PRIMARY KEY (`serverInfoSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;