SET foreign_key_checks = 0;

CREATE TABLE `A_kongji` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `tcode` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `id` varchar(8) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `tcode` (`tcode`)
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=euckr;

CREATE TABLE `Biz_tb1` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(10) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `show_yn` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`),
  UNIQUE KEY `listnum` (`listnum`,`twmark`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=euckr;

CREATE TABLE `F_dealer1` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer11` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=54923 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer11_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer1_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer2` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=9226 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer2_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer3` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=126747 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer3_B01` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=39060 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer3_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer4` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=16367 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer4_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer5` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=305 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer5_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer6` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=377 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer6_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer7` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer7_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer8` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1202 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer8_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer9` (
  `number` int(8) NOT NULL AUTO_INCREMENT,
  `listnum` int(8) DEFAULT '0',
  `twmark` varchar(20) DEFAULT NULL,
  `title` varchar(100) NOT NULL DEFAULT '',
  `view` int(11) DEFAULT '0',
  `content` mediumtext,
  `user_type` char(1) DEFAULT NULL,
  `html` char(1) DEFAULT NULL,
  `file` varchar(50) DEFAULT NULL,
  `ip` varchar(15) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `reply` int(3) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`number`),
  KEY `listnum` (`listnum`),
  KEY `twmark` (`twmark`),
  KEY `title` (`title`),
  KEY `id` (`id`),
  KEY `date` (`date`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3830 DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `F_dealer9_reply` (
  `number` int(11) NOT NULL DEFAULT '0',
  `dep_no` int(11) NOT NULL DEFAULT '0',
  `content` mediumtext,
  `ip` varchar(20) DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `crypt_key` varchar(13) DEFAULT NULL,
  `input_date` datetime DEFAULT NULL,
  `singo` char(1) DEFAULT NULL,
  PRIMARY KEY (`number`,`dep_no`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `Search_AdvertiseContents` (
  `nAdvertiseContentsSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '광고컨텐츠고유코드',
  `sAdvertiseContentsTitle` varchar(100) NOT NULL DEFAULT '' COMMENT '광고컨텐츠타이틀',
  `sAdvertiseContentsState` varchar(1) NOT NULL DEFAULT '' COMMENT '광고컨텐츠사용여부 YN',
  `emAdvertiseContentsType` enum('T','B') NOT NULL DEFAULT 'T' COMMENT '광고컨텐츠타입 T: 텍스트플러스 B:브랜딩보드',
  `dtPeriodStart` date NOT NULL DEFAULT '0000-00-00' COMMENT '시작일',
  `dtPeriodEnd` date NOT NULL DEFAULT '0000-00-00' COMMENT '종료일',
  `dtInputDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  `sInputUserID` varchar(20) NOT NULL DEFAULT '' COMMENT '등록자ID',
  `sInputUserName` varchar(30) NOT NULL DEFAULT '' COMMENT '등록자명',
  PRIMARY KEY (`nAdvertiseContentsSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=euckr COMMENT='광고컨텐츠';

CREATE TABLE `Search_AdvertiseContentsDetail` (
  `nAdvertiseContentsDetailSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '광고컨텐츠상세내용고유코드',
  `nAdvertiseContentsSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '광고컨텐츠고유코드',
  `sAdvertiseContentsName` varchar(100) DEFAULT NULL COMMENT '광고컨텐츠명',
  `sAdvertiseContentsExplain` varchar(255) DEFAULT NULL COMMENT '광고컨텐츠설명',
  `sAdvertiseContentsRepresentURL` varchar(255) DEFAULT NULL COMMENT '광고컨텐츠대표URL',
  `sAdvertiseContentsConnectURL` varchar(255) NOT NULL DEFAULT '' COMMENT '광고컨텐츠연결URL',
  `nOrderSeq` tinyint(3) unsigned DEFAULT NULL COMMENT '광고컨텐츠정렬',
  PRIMARY KEY (`nAdvertiseContentsDetailSeq`),
  KEY `AdvertiseContentsDetail1` (`nAdvertiseContentsSeq`),
  CONSTRAINT `AdvertiseContentsDetail1` FOREIGN KEY (`nAdvertiseContentsSeq`) REFERENCES `Search_AdvertiseContents` (`nAdvertiseContentsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=227 DEFAULT CHARSET=euckr COMMENT='광고컨텐츠상세내용';

CREATE TABLE `Search_AdvertiseContentsKeyword` (
  `nAdvertiseContentsSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '광고컨텐츠고유코드',
  `nKeywordSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '광고컨텐츠키워드고유코드',
  PRIMARY KEY (`nKeywordSeq`),
  KEY `AdvertiseContentsKeyword1` (`nAdvertiseContentsSeq`),
  CONSTRAINT `AdvertiseContentsKeyword1` FOREIGN KEY (`nAdvertiseContentsSeq`) REFERENCES `Search_AdvertiseContents` (`nAdvertiseContentsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='광고컨텐츠키워드';

CREATE TABLE `Search_ThemeProduct` (
  `nThemeProductSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '검색기획컨텐츠상품고유코드',
  `nSeasonThemeSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '기획컨텐츠고유번호',
  `nThemeProductType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '상품타입 1.Danawa 2. 협력사',
  `sCmpnySeq` varchar(6) NOT NULL DEFAULT '' COMMENT '상품타입고유코드',
  `nCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '다나와 대분류 고유번호',
  `sProductSeq` varchar(100) NOT NULL DEFAULT '' COMMENT '상품고유코드',
  `nThemeProductDetailCheck` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상품상세정보입력체크 1.기존DB값사용 2.수동입력대체값사용',
  `nOrder` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '정렬',
  PRIMARY KEY (`nThemeProductSeq`)
) ENGINE=MyISAM AUTO_INCREMENT=2948 DEFAULT CHARSET=euckr COMMENT='검색기획컨텐츠 상품';

CREATE TABLE `Search_ThemeProductDetail` (
  `nThemeProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '검색기획컨텐츠상품고유코드',
  `sThemeProductName` varchar(100) DEFAULT NULL COMMENT '대체상품명',
  `sThemeProductImgUrl` varchar(255) DEFAULT NULL COMMENT '대체이미지',
  `nThemeProductPrice` int(10) unsigned DEFAULT NULL COMMENT '대체가격',
  `sThemeProductUrl` varchar(255) DEFAULT NULL COMMENT '대체url',
  PRIMARY KEY (`nThemeProductSeq`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='검색기획컨텐츠상품상세정보';

CREATE TABLE `Search_ad` (
  `adPK` int(11) NOT NULL AUTO_INCREMENT,
  `Keyword` varchar(30) NOT NULL,
  `ProdCode` varchar(255) NOT NULL,
  `Exposure` varchar(1) NOT NULL,
  `cate` varchar(5) NOT NULL,
  `random` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`adPK`),
  KEY `PK` (`adPK`)
) ENGINE=MyISAM AUTO_INCREMENT=362 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_admatch` (
  `adPK` int(11) NOT NULL,
  `ProdcodePK` int(11) NOT NULL,
  KEY `AdPK` (`adPK`),
  KEY `ProdcodePK` (`ProdcodePK`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_allbulk_status` (
  `volume` varchar(3) DEFAULT NULL,
  `status` varchar(1) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_bulkstatus` (
  `status` varchar(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_keyword` (
  `keywordPK` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(30) NOT NULL,
  PRIMARY KEY (`keywordPK`),
  KEY `PK` (`keywordPK`),
  KEY `Keyword` (`keyword`)
) ENGINE=MyISAM AUTO_INCREMENT=5947 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_keywordmatch` (
  `keywordPK` int(11) NOT NULL,
  `matchingKEY` int(11) NOT NULL,
  `matchingTBL` varchar(15) NOT NULL,
  KEY `keywordPK` (`keywordPK`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_price_watcher_log` (
  `logid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `table_pk1` varchar(100) NOT NULL DEFAULT '',
  `table_pk2` varchar(100) DEFAULT NULL,
  `table_pk3` varchar(100) DEFAULT NULL,
  `table_pk4` varchar(100) DEFAULT NULL,
  `table_pk5` varchar(100) DEFAULT NULL,
  `dml_type` char(2) NOT NULL DEFAULT '',
  `log_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `refresh_flag` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`logid`),
  KEY `refresh_flag` (`refresh_flag`)
) ENGINE=MyISAM AUTO_INCREMENT=1210 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_price_watcher_log_v1` (
  `logid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `table_pk1` varchar(100) NOT NULL DEFAULT '',
  `table_pk2` varchar(100) DEFAULT NULL,
  `table_pk3` varchar(100) DEFAULT NULL,
  `table_pk4` varchar(100) DEFAULT NULL,
  `table_pk5` varchar(100) DEFAULT NULL,
  `dml_type` char(2) NOT NULL DEFAULT '',
  `log_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `refresh_flag` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`logid`),
  KEY `refresh_flag` (`refresh_flag`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_price_watcher_log_v2` (
  `logid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `table_pk1` varchar(100) NOT NULL DEFAULT '',
  `table_pk2` varchar(100) DEFAULT NULL,
  `table_pk3` varchar(100) DEFAULT NULL,
  `table_pk4` varchar(100) DEFAULT NULL,
  `table_pk5` varchar(100) DEFAULT NULL,
  `dml_type` char(2) NOT NULL DEFAULT '',
  `log_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `refresh_flag` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`logid`),
  KEY `refresh_flag` (`refresh_flag`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_recommend_kwd` (
  `rk_pid` int(11) NOT NULL AUTO_INCREMENT,
  `kwd` varchar(50) NOT NULL,
  `rk_kwd` varchar(100) NOT NULL,
  PRIMARY KEY (`rk_pid`),
  KEY `kwd` (`kwd`)
) ENGINE=MyISAM AUTO_INCREMENT=433 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_search_kwd` (
  `sk_pid` int(11) NOT NULL AUTO_INCREMENT,
  `kwd` varchar(50) NOT NULL DEFAULT '',
  `cate1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cate2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cate3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cate4` smallint(5) unsigned NOT NULL DEFAULT '0',
  `depth` tinyint(1) NOT NULL,
  `cate_name` varchar(30) NOT NULL,
  `method` varchar(100) DEFAULT NULL,
  `pn_search` char(1) DEFAULT NULL,
  `subkwd` varchar(50) DEFAULT NULL,
  `sAddKeyword` varchar(100) DEFAULT NULL COMMENT '검색어 추가',
  `sExceptionKeyword` varchar(100) DEFAULT NULL COMMENT '검색어 제외',
  PRIMARY KEY (`sk_pid`),
  UNIQUE KEY `kwd` (`kwd`),
  KEY `cate1` (`cate1`),
  KEY `cate2` (`cate2`),
  KEY `cate3` (`cate3`),
  KEY `cate4` (`cate4`),
  KEY `subkwd` (`subkwd`)
) ENGINE=MyISAM AUTO_INCREMENT=418193 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_seasonTheme` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `subject` varchar(100) NOT NULL,
  `title` varchar(100) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  `theme_state` varchar(1) NOT NULL,
  `plan_state` varchar(1) NOT NULL,
  `view_part` varchar(1) NOT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `period_s` date DEFAULT '0000-00-00',
  `period_e` date DEFAULT '0000-00-00',
  `input_d` date DEFAULT NULL,
  `banner_img` varchar(255) DEFAULT NULL,
  `banner_url` varchar(255) DEFAULT NULL,
  `input_id` varchar(20) NOT NULL DEFAULT '',
  `input_name` varchar(30) NOT NULL DEFAULT '',
  PRIMARY KEY (`seq`)
) ENGINE=MyISAM AUTO_INCREMENT=649 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_special` (
  `specialPK` int(11) NOT NULL AUTO_INCREMENT,
  `Keyword` varchar(30) NOT NULL,
  `ProdCode` varchar(255) NOT NULL,
  `Exposure` varchar(1) NOT NULL,
  `cate` varchar(5) NOT NULL,
  `random` varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`specialPK`),
  KEY `PK` (`specialPK`)
) ENGINE=MyISAM AUTO_INCREMENT=909 DEFAULT CHARSET=euckr;

CREATE TABLE `Search_specialmatch` (
  `specialPK` int(11) NOT NULL,
  `ProdcodePK` int(11) NOT NULL,
  KEY `SpecialPK` (`specialPK`),
  KEY `ProdcodePK` (`ProdcodePK`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `Search_synonym` (
  `synonymSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '동의어고유번호',
  `synonymName` varchar(100) NOT NULL COMMENT '동의어명',
  PRIMARY KEY (`synonymSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=32119 DEFAULT CHARSET=euckr COMMENT='동의어정보';

CREATE TABLE `Search_synonymMapping` (
  `synonymMappingSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '동의어매핑고유번호',
  `headSynonymSeq` int(10) unsigned NOT NULL COMMENT '대표동의어SEQ',
  `synonymSeq` int(10) unsigned NOT NULL COMMENT '동의어SEQ',
  `expandCode` tinyint(2) unsigned NOT NULL DEFAULT '0' COMMENT '동의어타입, 0:양방향, 1:단방향',
  PRIMARY KEY (`synonymMappingSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=37365 DEFAULT CHARSET=euckr COMMENT='동의어 매핑요청 정보';

CREATE TABLE `Search_synonymRequest` (
  `synonymRequestSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '동의어요청고유번호',
  `userID` char(15) NOT NULL COMMENT '요청자 ID',
  `userName` char(10) NOT NULL COMMENT '요청자명',
  `requestDate` datetime NOT NULL COMMENT '요청일시',
  `requestContent` varchar(255) DEFAULT NULL COMMENT '요청내용',
  `comment` varchar(255) DEFAULT NULL COMMENT '설명',
  `statusCode` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '처리상태, 0:요청, 1:처리중, 2:보류, 3:완료',
  PRIMARY KEY (`synonymRequestSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=1084 DEFAULT CHARSET=euckr COMMENT='동의어요청정보';

CREATE TABLE `Search_tBrandLink` (
  `nBrandLinkSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '브랜드 링크 고유키',
  `sBrandTitle` varchar(100) NOT NULL COMMENT '브랜드 링크 타이틀',
  `sBrandExposure` char(1) NOT NULL DEFAULT 'N' COMMENT '브랜드 링크 노출여부',
  `dtCreateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY (`nBrandLinkSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=euckr COMMENT='브랜드 링크 정보';

CREATE TABLE `Search_tServiceSynonymKeyword` (
  `nServiceSynonymKeywordSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '서비스별 동의어 키워드 매칭정보',
  `nKeywordSeq` int(10) NOT NULL COMMENT 'keyword table pk',
  `nServiceType` tinyint(3) unsigned NOT NULL COMMENT '서비스 구분 1:브랜드링크, 2:스폰서배너링크, 3:바로가기..',
  `nServiceLinkSeq` int(10) unsigned NOT NULL COMMENT '서버스별 링크 고유키',
  PRIMARY KEY (`nServiceSynonymKeywordSeq`),
  UNIQUE KEY `idxUMLinkInfo` (`nKeywordSeq`,`nServiceLinkSeq`,`nServiceType`)
) ENGINE=InnoDB AUTO_INCREMENT=34073 DEFAULT CHARSET=euckr COMMENT='서비스별 동의어 키워드 매칭 정보';

CREATE TABLE `Search_tShortcutLink` (
  `nShortcutLinkSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '바로가기 정보 고유키',
  `sShortcutLinkTitle` varchar(100) NOT NULL COMMENT '바로가기 타이틀',
  `sShortcutLinkExposure` char(1) NOT NULL DEFAULT 'N' COMMENT '바로가기 노출여부',
  `sShortcutLinkDirectYN` char(1) NOT NULL DEFAULT 'N' COMMENT '바로가기 이동여부',
  `sShortcutLinkUrl` varchar(200) NOT NULL COMMENT '바로가기 URL',
  `sShortcutLinkDirectUrl` varchar(200) DEFAULT NULL COMMENT '바로가기 이동 URL',
  `dtCreateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY (`nShortcutLinkSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=euckr COMMENT='바로가기 정보';

CREATE TABLE `Search_tSponsorLink` (
  `nSponsorLinkSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '스폰서배너 링크 고유키',
  `sSponsorLinkTitle` varchar(100) NOT NULL COMMENT '스폰서배너 타이틀',
  `sSponsorLinkExposure` char(1) NOT NULL DEFAULT 'N' COMMENT '스폰서배너 노출여부',
  `sSponsorLinkAdvertise` text NOT NULL COMMENT '스폰서배너 광고 html',
  `dtCreateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY (`nSponsorLinkSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=euckr COMMENT='스폰서배너 광고 정보';

CREATE TABLE `Search_tThemePlan` (
  `nSeasonThemeSeq` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '기획전컨텐츠고유번호',
  `nPlanSeq` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '기획전고유번호',
  `nThemePlanOrder` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '정렬',
  PRIMARY KEY (`nSeasonThemeSeq`,`nPlanSeq`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='기획전링크테이블';

CREATE TABLE `Search_themeTextLink` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `theme` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `state` varchar(1) NOT NULL,
  `content_0` varchar(255) DEFAULT NULL,
  `content_1` varchar(255) DEFAULT NULL,
  `content_2` varchar(255) DEFAULT NULL,
  `content_3` varchar(255) DEFAULT NULL,
  `content_4` varchar(255) DEFAULT NULL,
  `content_5` varchar(255) DEFAULT NULL,
  `content_6` varchar(255) DEFAULT NULL,
  `content_7` varchar(255) DEFAULT NULL,
  `content_8` varchar(255) DEFAULT NULL,
  `content_9` varchar(255) DEFAULT NULL,
  `content_10` varchar(255) DEFAULT NULL,
  `content_11` varchar(255) DEFAULT NULL,
  `content_12` varchar(255) DEFAULT NULL,
  `content_13` varchar(255) DEFAULT NULL,
  `content_14` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `theme` (`theme`)
) ENGINE=MyISAM AUTO_INCREMENT=556 DEFAULT CHARSET=euckr;

CREATE TABLE `category_for_search_v1` (
  `site` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_n1` varchar(100) NOT NULL DEFAULT '',
  `cate_n2` varchar(100) DEFAULT NULL,
  `cate_n3` varchar(100) DEFAULT NULL,
  `cate_n4` varchar(100) DEFAULT NULL,
  `depth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cate_c_path` varchar(150) NOT NULL DEFAULT '',
  `cate_n_path` varchar(200) NOT NULL DEFAULT '',
  `disp_yn` char(1) NOT NULL DEFAULT 'Y',
  `virtual_yn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`site`,`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='색인별 통합용 카테고리1';

CREATE TABLE `category_for_search_v2` (
  `site` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_n1` varchar(100) NOT NULL DEFAULT '',
  `cate_n2` varchar(100) DEFAULT NULL,
  `cate_n3` varchar(100) DEFAULT NULL,
  `cate_n4` varchar(100) DEFAULT NULL,
  `depth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `cate_c_path` varchar(150) NOT NULL DEFAULT '',
  `cate_n_path` varchar(200) NOT NULL DEFAULT '',
  `disp_yn` char(1) NOT NULL DEFAULT 'Y',
  `virtual_yn` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`site`,`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='색인별 통합용 카테고리2';

CREATE TABLE `content_kwd` (
  `sk_pid` int(11) NOT NULL AUTO_INCREMENT,
  `kwd` varchar(50) NOT NULL DEFAULT '',
  `cate1` smallint(5) NOT NULL DEFAULT '0',
  `cate2` smallint(5) NOT NULL DEFAULT '0',
  `cate3` smallint(5) NOT NULL DEFAULT '0',
  `cate4` smallint(5) NOT NULL DEFAULT '0',
  `depth` tinyint(1) NOT NULL,
  `cate_name` varchar(30) NOT NULL,
  `method` varchar(100) DEFAULT NULL,
  `pn_search` char(1) DEFAULT NULL,
  PRIMARY KEY (`sk_pid`),
  KEY `cate1` (`cate1`),
  KEY `cate2` (`cate2`),
  KEY `cate3` (`cate3`),
  KEY `cate4` (`cate4`)
) ENGINE=InnoDB AUTO_INCREMENT=5976 DEFAULT CHARSET=euckr;

CREATE TABLE `coupon_elec` (
  `cpnid` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `target_url` text,
  `regdate` datetime DEFAULT NULL,
  `moddate` datetime DEFAULT NULL,
  PRIMARY KEY (`cpnid`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=euckr;

CREATE TABLE `fastcatWatcher` (
  `logid` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `table_pk1` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `table_pk2` mediumint(8) unsigned DEFAULT NULL,
  `table_pk3` mediumint(8) unsigned DEFAULT NULL,
  `dml_type` char(2) NOT NULL DEFAULT '',
  `log_date` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_name` varchar(100) NOT NULL DEFAULT '',
  `db_name` varchar(100) NOT NULL DEFAULT '',
  `refresh_flag` char(1) NOT NULL DEFAULT '',
  PRIMARY KEY (`logid`),
  KEY `table_pk1` (`table_pk1`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `goods_keyword` (
  `keyword_c` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `keyword_n` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`keyword_c`),
  KEY `idx_cate_c` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB AUTO_INCREMENT=187 DEFAULT CHARSET=euckr;

CREATE TABLE `goods_keyword_synonym` (
  `sn` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `keyword_c` int(10) unsigned NOT NULL DEFAULT '0',
  `synonym` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`sn`),
  KEY `idx_keyword_c` (`keyword_c`)
) ENGINE=InnoDB AUTO_INCREMENT=247 DEFAULT CHARSET=euckr;

CREATE TABLE `kw_klog` (
  `logid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `table_pk1` varchar(100) NOT NULL DEFAULT '',
  `table_pk2` varchar(100) DEFAULT NULL,
  `table_pk3` varchar(100) DEFAULT NULL,
  `table_pk4` varchar(100) DEFAULT NULL,
  `table_pk5` varchar(100) DEFAULT NULL,
  `dml_type` char(2) NOT NULL DEFAULT '',
  `log_date` date NOT NULL DEFAULT '0000-00-00',
  `refresh_date` date DEFAULT NULL,
  `refresh_flag` char(1) NOT NULL DEFAULT '',
  `user_name` varchar(100) NOT NULL DEFAULT '',
  `db_name` varchar(100) NOT NULL DEFAULT '',
  `table_name` varchar(100) NOT NULL DEFAULT '',
  `view_name` varchar(100) NOT NULL DEFAULT '',
  `samekey_logid` int(11) DEFAULT NULL,
  PRIMARY KEY (`logid`)
) ENGINE=InnoDB AUTO_INCREMENT=419815 DEFAULT CHARSET=euckr;

CREATE TABLE `kw_tablemap` (
  `mapid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `db_user_name` varchar(100) NOT NULL DEFAULT '',
  `db_name` varchar(100) NOT NULL DEFAULT '',
  `db_table_name` varchar(100) NOT NULL DEFAULT '',
  `db_table_alias` varchar(100) NOT NULL DEFAULT '',
  `db_view_name` varchar(100) NOT NULL DEFAULT '',
  `ts_table_name` varchar(100) NOT NULL DEFAULT '',
  `db_table_key_column_name` varchar(100) NOT NULL DEFAULT '',
  `db_table_key_column_type` varchar(100) NOT NULL DEFAULT '',
  `db_view_key_column_name` varchar(100) NOT NULL DEFAULT '',
  `ts_table_key_column_name` varchar(100) NOT NULL DEFAULT '',
  `db_view_column_list` text NOT NULL,
  `ts_table_column_list` text NOT NULL,
  `db_derived_column_list` text NOT NULL,
  `ts_derived_column_list` text NOT NULL,
  PRIMARY KEY (`mapid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=euckr;

CREATE TABLE `postcode` (
  `do` varchar(5) DEFAULT NULL,
  `city` varchar(15) DEFAULT NULL,
  `dong` varchar(25) NOT NULL DEFAULT '',
  `lee` varchar(40) NOT NULL DEFAULT '',
  `doseo` varchar(10) DEFAULT NULL,
  `ho1` varchar(20) DEFAULT NULL,
  `ho2` varchar(20) DEFAULT NULL,
  `postcode` varchar(6) NOT NULL DEFAULT '',
  `postcode_old` varchar(6) DEFAULT NULL,
  KEY `ipostcode_dong` (`dong`),
  KEY `ipostcode_lee` (`lee`),
  KEY `ipostcode_postcode` (`postcode`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `prod_cut_request` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prod_c` int(10) unsigned NOT NULL DEFAULT '0',
  `member_id` varchar(20) NOT NULL DEFAULT '',
  `request_date_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `launch_date_d` date NOT NULL DEFAULT '0000-00-00',
  `reject_date_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ip_n` varchar(50) DEFAULT NULL,
  `status_c` enum('N','L','Y','R','U') NOT NULL DEFAULT 'N',
  `content_m` mediumtext,
  `memo_m` mediumtext,
  `accept_comment_n` smallint(5) unsigned NOT NULL DEFAULT '0',
  `reject_comment_n` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `prod_c_unique` (`prod_c`)
) ENGINE=InnoDB AUTO_INCREMENT=226223 DEFAULT CHARSET=euckr COMMENT='단종요청';

CREATE TABLE `prod_cut_request_comment` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `request_seq` int(10) unsigned NOT NULL DEFAULT '0',
  `comment_type_c` enum('Y','N','D') NOT NULL DEFAULT 'Y',
  `member_id` varchar(20) NOT NULL DEFAULT '',
  `date_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ip_n` varchar(50) DEFAULT NULL,
  `content_m` text,
  PRIMARY KEY (`seq`),
  KEY `request_seq` (`request_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=euckr COMMENT='단종요청 의견';

CREATE TABLE `search_kwd` (
  `sk_pid` int(11) NOT NULL AUTO_INCREMENT,
  `kwd` varchar(50) NOT NULL DEFAULT '',
  `cate1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate4` int(10) unsigned NOT NULL DEFAULT '0',
  `depth` tinyint(1) NOT NULL,
  `cate_name` varchar(30) NOT NULL,
  `method` varchar(100) DEFAULT NULL,
  `pn_search` char(1) DEFAULT NULL,
  PRIMARY KEY (`sk_pid`),
  UNIQUE KEY `kwd` (`kwd`),
  KEY `cate1` (`cate1`),
  KEY `cate2` (`cate2`),
  KEY `cate3` (`cate3`),
  KEY `cate4` (`cate4`)
) ENGINE=InnoDB AUTO_INCREMENT=407464 DEFAULT CHARSET=euckr;

CREATE TABLE `sponsor_code` (
  `loc` varchar(10) NOT NULL DEFAULT '',
  `cat` varchar(50) NOT NULL DEFAULT '',
  `seq` char(3) NOT NULL DEFAULT '',
  `ban_size` varchar(100) DEFAULT NULL,
  `file_size` varchar(50) DEFAULT NULL,
  `order_seq` char(1) DEFAULT NULL,
  PRIMARY KEY (`loc`,`cat`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `sponsor_hist` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '0',
  `loc` varchar(4) NOT NULL DEFAULT '',
  `cat` varchar(50) NOT NULL DEFAULT '',
  `rolling` char(3) NOT NULL DEFAULT '',
  `fr_date` date NOT NULL DEFAULT '0000-00-00',
  `to_date` date NOT NULL DEFAULT '0000-00-00',
  `period` char(2) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `discount` int(11) DEFAULT NULL,
  `comment` mediumtext,
  `progress` char(1) DEFAULT NULL,
  `banner` char(1) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `i_frame_content` mediumtext,
  `input_date` date DEFAULT NULL,
  PRIMARY KEY (`cmpny_c`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `sponsor_new` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `sPassword` varchar(6) NOT NULL DEFAULT '' COMMENT '비밀번호',
  `name` varchar(50) DEFAULT NULL,
  `name_email` varchar(50) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `hphone` varchar(20) DEFAULT NULL,
  `tot_price` int(11) DEFAULT NULL,
  `pay` char(1) DEFAULT NULL,
  `comment` mediumtext,
  `r_file` varchar(50) DEFAULT NULL,
  `e_file` varchar(50) DEFAULT NULL,
  `c_file` varchar(50) DEFAULT NULL,
  `agency` char(1) DEFAULT NULL,
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원고유키',
  PRIMARY KEY (`cmpny_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr PACK_KEYS=1;

CREATE TABLE `standardPC_AS` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_seq` int(10) unsigned DEFAULT NULL,
  `name_n` varchar(20) DEFAULT NULL,
  `phone_no` varchar(20) DEFAULT NULL,
  `mobile_no` varchar(20) DEFAULT NULL,
  `zipcode` varchar(7) DEFAULT NULL,
  `address` varchar(80) DEFAULT NULL,
  `desc_m` mediumtext,
  `as_center` varchar(40) DEFAULT NULL,
  `as_status_s` varchar(20) DEFAULT NULL,
  `as_booking_seq` varchar(20) DEFAULT NULL,
  `input_d` datetime DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `member_seq` (`member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=euckr;

CREATE TABLE `standardPC_coupon` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_seq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '구매자 시퀀스',
  `sale_member_seq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '판매자 시퀀스',
  `coupon_no` varchar(20) DEFAULT NULL,
  `coupon_secret_no` varchar(20) DEFAULT NULL,
  `standard_pc_c` varchar(4) DEFAULT NULL,
  `cmpny_n` varchar(20) DEFAULT NULL,
  `cmpny_phone_no` varchar(20) DEFAULT NULL,
  `cmpny_address` varchar(160) DEFAULT NULL,
  `buyPCdate_d` datetime DEFAULT NULL,
  `buyPCname_n` varchar(40) DEFAULT NULL,
  `input_d` datetime DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `member_seq` (`member_seq`),
  KEY `sale_member_seq` (`sale_member_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=9673 DEFAULT CHARSET=euckr;

CREATE TABLE `standardPC_singo` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `standard_pc_c` varchar(4) DEFAULT NULL,
  `cmpny_n` varchar(20) DEFAULT NULL,
  `cmpny_phone_no` varchar(20) DEFAULT NULL,
  `writer_member_seq` int(10) unsigned DEFAULT NULL,
  `writer_name` varchar(20) DEFAULT NULL,
  `title_n` tinytext,
  `desc_m` mediumtext,
  `input_d` datetime DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=euckr;

CREATE TABLE `standardPC_total` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `member_seq` int(10) unsigned DEFAULT NULL,
  `standard_pc_c` varchar(4) DEFAULT NULL,
  `cmpny_n` varchar(20) DEFAULT NULL,
  `count_date_d` date DEFAULT NULL,
  `a_m_q` int(10) unsigned DEFAULT NULL,
  `b_m_q` int(10) unsigned DEFAULT NULL,
  `c_m_q` int(10) unsigned DEFAULT NULL,
  `d_m_q` int(10) unsigned DEFAULT NULL,
  `e_m_q` int(10) unsigned DEFAULT NULL,
  `f_m_q` int(10) unsigned DEFAULT NULL,
  `g_m_q` int(10) unsigned DEFAULT NULL,
  `h_m_q` int(10) unsigned DEFAULT NULL,
  `i_m_q` int(10) unsigned DEFAULT NULL,
  `j_m_q` int(10) unsigned DEFAULT NULL,
  `a_a_q` int(10) unsigned DEFAULT NULL,
  `b_a_q` int(10) unsigned DEFAULT NULL,
  `c_a_q` int(10) unsigned DEFAULT NULL,
  `d_a_q` int(10) unsigned DEFAULT NULL,
  `e_a_q` int(10) unsigned DEFAULT NULL,
  `f_a_q` int(10) unsigned DEFAULT NULL,
  `g_a_q` int(10) unsigned DEFAULT NULL,
  `h_a_q` int(10) unsigned DEFAULT NULL,
  `i_a_q` int(10) unsigned DEFAULT NULL,
  `j_a_q` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `standard_pc_c` (`standard_pc_c`)
) ENGINE=InnoDB AUTO_INCREMENT=351 DEFAULT CHARSET=euckr;

CREATE TABLE `stat_hot_day` (
  `stat_hot_day_seq` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '일별 인기순위 자동증가값',
  `basic_date` date NOT NULL DEFAULT '0000-00-00' COMMENT '통계수집일',
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '대분류고유키',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '중분류고유키',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '소분류고유키',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '세분류고유키',
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '상품고유키',
  `gubun_c` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '통계구분 1:판매량, 2:클릭수, 3:업체수...',
  `count_q` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '통계치',
  PRIMARY KEY (`stat_hot_day_seq`),
  UNIQUE KEY `uidx_basic_date` (`basic_date`,`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`,`prod_c`,`gubun_c`),
  KEY `idxMBasicDateProductSeq` (`basic_date`,`prod_c`)
) ENGINE=InnoDB AUTO_INCREMENT=869654946 DEFAULT CHARSET=euckr COMMENT='일별 인기순위 산출 자료 통계 정보';

CREATE TABLE `stat_hot_day_balance` (
  `balance_seq` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '일별 인기순위 관리자 조정 자동증가값',
  `stat_hot_day_seq` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '일별 인기순위 고유키',
  `balance_count_q` mediumint(8) NOT NULL DEFAULT '0' COMMENT '증감 통계치',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '증감분 입력시간',
  `member_id` varchar(20) NOT NULL DEFAULT '' COMMENT '증감분 입력ID',
  PRIMARY KEY (`balance_seq`),
  KEY `idx_stat_hot_day_seq` (`stat_hot_day_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=1814 DEFAULT CHARSET=euckr COMMENT='일별 인기순위 관리자 조정 통계 정보';

CREATE TABLE `stat_sale_monthly` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `basic_date` date NOT NULL DEFAULT '0000-00-00',
  `minprice` int(10) unsigned NOT NULL DEFAULT '0',
  `avgprice` int(10) unsigned NOT NULL DEFAULT '0',
  `sale_count` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `sale_price` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`prod_c`,`basic_date`),
  KEY `basicdate_idx` (`basic_date`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `stat_sale_weekly` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `basic_date` date NOT NULL DEFAULT '0000-00-00',
  `minprice` int(10) unsigned NOT NULL DEFAULT '0',
  `avgprice` int(10) unsigned NOT NULL DEFAULT '0',
  `sale_count` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `sale_price` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`prod_c`,`basic_date`),
  KEY `basicdate_idx` (`basic_date`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tAccount` (
  `nAccountSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '통합결제정보 고유키',
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '결제주문번호',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '결제회원고유번호',
  `sMemberID` varchar(20) NOT NULL DEFAULT '' COMMENT '결제회원ID',
  `nUseServiceType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '통합결제 서비스분류 1:비즈광고..추가예정',
  `nAmount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '결제금액',
  `sPayMethodType` char(1) NOT NULL DEFAULT 'C' COMMENT '결제수단 C:카드, V:가상계좌',
  `sAccountStatus` char(1) NOT NULL DEFAULT 'W' COMMENT '결제상태 W:대기, A:승인, C:취소',
  `sBuyerEmail` varchar(40) NOT NULL DEFAULT '' COMMENT '구매자 Email',
  `dtInputDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '입력일',
  `dtInputTime` time NOT NULL DEFAULT '00:00:00' COMMENT '입력시간',
  `dtTradeDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '거래승인완료일',
  PRIMARY KEY (`nAccountSeq`),
  UNIQUE KEY `uidxsOrderNumber` (`sOrderNumber`) USING BTREE,
  KEY `idxdtInputDate` (`dtInputDate`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2554 DEFAULT CHARSET=utf8 COMMENT='통합결제 정보목록';

CREATE TABLE `tAccountCardInfo` (
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '결제주문번호',
  `sCardCode` char(4) NOT NULL DEFAULT '0000' COMMENT '결제카드코드',
  `sCardName` varchar(32) NOT NULL DEFAULT '' COMMENT '결제카드명',
  `sApprovalNumber` char(8) NOT NULL DEFAULT '' COMMENT '결제승인번호',
  PRIMARY KEY (`sOrderNumber`),
  CONSTRAINT `fkAccount2` FOREIGN KEY (`sOrderNumber`) REFERENCES `tAccount` (`sOrderNumber`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='통합결제 카드 정보';

CREATE TABLE `tAccountLog` (
  `nAccountLogSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '통합결제 로그정보 고유키',
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '결제주문번호',
  `sTradeNumber` varchar(14) NOT NULL DEFAULT '' COMMENT '결제거래번호',
  `sPayMethodType` char(1) NOT NULL DEFAULT 'C' COMMENT '결제수단 C:카드, V:가상계좌',
  `sTradeStatus` char(1) NOT NULL DEFAULT 'N' COMMENT '거래상태 A:입금(가상계좌) or 정상(신용카드), B:취소, C: 관리자취소(가상계좌만)',
  `sResultCode` char(4) NOT NULL DEFAULT '0000' COMMENT '결제 결과 전송코드',
  `dtInputDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '입력일자',
  `dtTradeDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '거래승인일자',
  PRIMARY KEY (`nAccountLogSeq`),
  KEY `fkAccount3` (`sOrderNumber`),
  CONSTRAINT `fkAccount3` FOREIGN KEY (`sOrderNumber`) REFERENCES `tAccount` (`sOrderNumber`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2892 DEFAULT CHARSET=utf8 COMMENT='통합결제 로그정보';

CREATE TABLE `tAccountLogVirtualDetail` (
  `nAccountLogSeq` int(10) unsigned NOT NULL COMMENT '통합결제 로그정보 고유키',
  `sNoticeID` varchar(20) NOT NULL DEFAULT '' COMMENT '결제통보ID',
  `nResultAmount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '결제 결과 전송 금액',
  `nResultReturnCode` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '결제 결과 전송 처리 리턴값 0:처리성공, 2:입금액틀림, 3:처리실패',
  PRIMARY KEY (`nAccountLogSeq`),
  CONSTRAINT `fkAccount4` FOREIGN KEY (`nAccountLogSeq`) REFERENCES `tAccountLog` (`nAccountLogSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='통합결제 로그별 가상계좌 상세 정보';

CREATE TABLE `tAccountVirtualInfo` (
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '결제주문번호',
  `sBankCode` char(4) NOT NULL DEFAULT '0000' COMMENT '입금은행코드명 ex)BK01',
  `sBankName` varchar(16) NOT NULL DEFAULT '' COMMENT '입금은행명',
  `sBankUseName` varchar(50) NOT NULL DEFAULT '' COMMENT '예금자명',
  `sBankInputName` varchar(50) NOT NULL DEFAULT '' COMMENT '입금자명',
  `sAccountNumber` varchar(20) NOT NULL DEFAULT '' COMMENT '가상계좌번호',
  PRIMARY KEY (`sOrderNumber`),
  CONSTRAINT `fkAccount1` FOREIGN KEY (`sOrderNumber`) REFERENCES `tAccount` (`sOrderNumber`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='통합결제 가상계좌 정보';

CREATE TABLE `tAdverInfoChangeRequest` (
  `nAdverInfoChangeRequestSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유키',
  `nTaxBillManagerSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '처리자고유키',
  `sCompanyCode` varchar(20) NOT NULL DEFAULT '0' COMMENT '업체고유키',
  `sRequestType` char(1) NOT NULL DEFAULT 'A' COMMENT '변경요청타입 A:기본정보 B:사업자정보..',
  `sRequestContent` text NOT NULL COMMENT '요청내역',
  `sStatus` char(1) NOT NULL DEFAULT 'N' COMMENT '처리상태 Y:처리완료 N:미처리',
  `dtInputDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '요청일자',
  `dtUpdateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '처리일자',
  PRIMARY KEY (`nAdverInfoChangeRequestSeq`),
  KEY `fkTaxBill1` (`nTaxBillManagerSeq`),
  CONSTRAINT `fkTaxBill1` FOREIGN KEY (`nTaxBillManagerSeq`) REFERENCES `tTaxBillManager` (`nTaxBillManagerSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=euckr COMMENT='광고주 정보 변경 요청 내역 테이블';

CREATE TABLE `tAdverManager` (
  `nAdverManagerSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유키',
  `sCompanyCode` varchar(20) NOT NULL DEFAULT '' COMMENT '업체코드',
  `sManagerName` varchar(50) NOT NULL DEFAULT '' COMMENT '업체담당자명',
  `sPhoneNumber` varchar(20) NOT NULL DEFAULT '' COMMENT '연락처',
  `sMobileNumber` varchar(20) NOT NULL DEFAULT '' COMMENT '핸드폰',
  `sEmail` varchar(50) NOT NULL DEFAULT '' COMMENT '이메일',
  `sEtcMemo` text COMMENT '참고사항',
  `sBaseEmailYN` char(1) NOT NULL DEFAULT 'N' COMMENT '기본메일설정 Y:설정,N:설정안함',
  `dtCreateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '입력일자',
  PRIMARY KEY (`nAdverManagerSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=2559 DEFAULT CHARSET=euckr COMMENT='광고주 담당자 정보 테이블';

CREATE TABLE `tAdvertiseStatisticsDayCount` (
  `nDaySeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '일별 누적 고유키',
  `nADServiceSeq` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '광고 상품 서비스 고유키',
  `nAdvertiseSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '광고별 고유키',
  `nClickCount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '클릭수',
  `nViewCount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '노출수',
  `sCreateDate` char(8) NOT NULL DEFAULT '00000000' COMMENT '등록날짜',
  PRIMARY KEY (`nDaySeq`),
  KEY `fkAdvertiseStatisticsDayCount1` (`nADServiceSeq`),
  CONSTRAINT `fkAdvertiseStatisticsDayCount1` FOREIGN KEY (`nADServiceSeq`) REFERENCES `tAdvertiseStatisticsService` (`nADServiceSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=126737 DEFAULT CHARSET=utf8 COMMENT='일별 광고 상품 누적 수';

CREATE TABLE `tAdvertiseStatisticsService` (
  `nADServiceSeq` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '광고상품 서비스 고유키',
  `sADServiceName` varchar(40) DEFAULT NULL COMMENT '서비스명',
  PRIMARY KEY (`nADServiceSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='광고상품 서비스';

CREATE TABLE `tAdvertiseStatisticsTermCount` (
  `nTermCountSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '기간 누적 고유키',
  `nADServiceSeq` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '광고상품 서비스 고유키',
  `nAdvertiseSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '광고별 고유키',
  `nClickCount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '클릭수',
  `nViewCount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '노출수',
  `sStartDate` char(8) NOT NULL DEFAULT '00000000' COMMENT '시작일',
  `sEndDate` char(8) NOT NULL DEFAULT '00000000' COMMENT '종료일',
  PRIMARY KEY (`nTermCountSeq`),
  KEY `fkAdvertiseStatisticsTermCount1` (`nADServiceSeq`),
  CONSTRAINT `fkAdvertiseStatisticsTermCount1` FOREIGN KEY (`nADServiceSeq`) REFERENCES `tAdvertiseStatisticsService` (`nADServiceSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5115 DEFAULT CHARSET=utf8 COMMENT='기간별 광고상품 누적수';

CREATE TABLE `tAttribute` (
  `nAttributeSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '속성고유코드',
  `sAttributeName` varchar(30) NOT NULL DEFAULT '' COMMENT '속성명칭',
  `sAttributeUnit` varchar(30) NOT NULL DEFAULT '' COMMENT '속성단위',
  `sAttributeUnitPosition` char(1) NOT NULL DEFAULT 'R' COMMENT '속성단위위치정보 F:앞, R:뒤',
  `nDescriptionListSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '용어설명게시글고유번호 dbBoard.tNormalList 고유번호',
  `sSimpleDescriptionYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '간략설명포함유무 Y:포함, N:불포함',
  `sMultiSelectType` char(1) NOT NULL DEFAULT 'S' COMMENT '속성값다중선택여부 M:다중, S:단일',
  PRIMARY KEY (`nAttributeSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=20030 DEFAULT CHARSET=euckr COMMENT='속성정보';

CREATE TABLE `tAttributeValue` (
  `nAttributeValueSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '속성값고유코드',
  `nAttributeSeq` int(10) unsigned NOT NULL COMMENT '속성고유코드',
  `sAttributeValueName` varchar(60) NOT NULL DEFAULT '' COMMENT '속성값명칭',
  `nDescriptionListSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '용어설명게시글고유번호 dbBoard.tNormalList 고유번호',
  `sDisplayYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '노출여부 Y:노출함, N:노출안함(단종)',
  `nDisplaySort` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출순서',
  PRIMARY KEY (`nAttributeValueSeq`),
  KEY `fkAttribute3` (`nAttributeSeq`),
  CONSTRAINT `fkAttribute3` FOREIGN KEY (`nAttributeSeq`) REFERENCES `tAttribute` (`nAttributeSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=124355 DEFAULT CHARSET=euckr COMMENT='속성값정보';

CREATE TABLE `tBizAdvertiseAccount` (
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '주문번호',
  `nPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '결제금액',
  `nAccountResult` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '결제결과(0:실패, 1:성공)',
  `nPaymentType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '결제방법',
  `nTaxBill` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '세금계산서 고유번호',
  `sCompanyCode` varchar(20) NOT NULL DEFAULT '' COMMENT '컴퍼니 코드',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL DEFAULT '00:00:00' COMMENT '등록시',
  `sPaymentTypeEtc` varchar(25) NOT NULL DEFAULT '' COMMENT '결제방법 참조정보',
  PRIMARY KEY (`sOrderNumber`),
  KEY `idxMAccountsCompany` (`sCompanyCode`,`nTaxBill`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='비즈광고 결제정보';

CREATE TABLE `tBizAdvertiseCart` (
  `nCartSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '장바구니 고유번호',
  `nProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품정보 고유번호',
  `nBuyerMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '구매자 고유번호',
  `dtCreateDateTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일시',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상태(0:결재대기, 1:결제완료, 2:결제실패)',
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '주문번호',
  `nProductCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품코드 (eldanawa tProd 상품코드)',
  PRIMARY KEY (`nCartSeq`),
  KEY `idxnProductSeq` (`nProductSeq`),
  CONSTRAINT `fkBizAdvertise2` FOREIGN KEY (`nProductSeq`) REFERENCES `tBizAdvertiseProduct` (`nProductSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2367 DEFAULT CHARSET=utf8 COMMENT='비즈광고 장바구니';

CREATE TABLE `tBizAdvertiseProduct` (
  `nProductSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '비즈광고상품 고유번호',
  `nProductIcon` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상품아이콘',
  `sProductName` varchar(30) NOT NULL DEFAULT '' COMMENT '광고 상품명',
  `nCategory1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리1',
  `nCategory2` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리2',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '등록자 고유번호',
  `nPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가격',
  `nProductType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상품타입 (1:스페셜, 2:플러스, 3:대량판매 패키지-제조사, 4:대량판매 일반-상품코드)',
  `nSort` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '노출우선순위',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상태(0:판매중지, 1:판매중 )',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL DEFAULT '00:00:00' COMMENT '등록시',
  `nCategory3` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리3',
  `nCategory4` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리4',
  PRIMARY KEY (`nProductSeq`),
  KEY `idxMnState` (`nCategory1`,`nCategory2`,`nCategory3`,`nCategory4`,`nProductType`,`nSort`)
) ENGINE=InnoDB AUTO_INCREMENT=6646 DEFAULT CHARSET=utf8 COMMENT='비즈광고 상품정보';

CREATE TABLE `tBizAdvertiseProductLog` (
  `nProductLogSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '비즈광고상품 고유번호',
  `nProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '비즈광고상품 고유번호',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상태(0:판매중지, 1:판매중 )',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '등록자 고유번호',
  `sMemo` varchar(200) DEFAULT NULL COMMENT '간략내용',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL DEFAULT '00:00:00' COMMENT '등록시',
  PRIMARY KEY (`nProductLogSeq`),
  KEY `idxnProductSeq` (`nProductSeq`),
  CONSTRAINT `fkBizAdvertise1` FOREIGN KEY (`nProductSeq`) REFERENCES `tBizAdvertiseProduct` (`nProductSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12420 DEFAULT CHARSET=utf8 COMMENT='비즈광고 상품정보';

CREATE TABLE `tBizAdvertiseSale` (
  `nSaleSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '상품구매정보 고유번호',
  `nProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품정보 고유번호',
  `nProductCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품코드',
  `nMakerCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '제조사코드',
  `dtStartDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '적용 시작일',
  `dtEndDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '적용 종료일',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상태(0:대기, 1: 승인)',
  `nBuyerMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '구매자 고유번호',
  `nPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가격',
  `sOrderNumber` varchar(40) NOT NULL DEFAULT '' COMMENT '주문번호',
  PRIMARY KEY (`nSaleSeq`),
  KEY `idxsOrderNumber` (`sOrderNumber`),
  KEY `idxMnProductSeq` (`nState`,`nProductSeq`,`dtStartDate`,`dtEndDate`),
  CONSTRAINT `fkBizAdvertise3` FOREIGN KEY (`sOrderNumber`) REFERENCES `tBizAdvertiseAccount` (`sOrderNumber`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19684 DEFAULT CHARSET=utf8 COMMENT='비즈광고 상품구매정보';

CREATE TABLE `tBizAdvertiseSaleAddInformation` (
  `nAddInfoSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '부가정보 고유키',
  `nSaleSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '비즈광고 상품 구매정보 고유키',
  `sCompanyName` varchar(30) NOT NULL DEFAULT '' COMMENT '회사명',
  `sGuideText` varchar(500) NOT NULL DEFAULT '' COMMENT '안내문구',
  `sPhoneNumber` varchar(50) NOT NULL DEFAULT '' COMMENT '전화번호',
  `sLinkURL` varchar(300) NOT NULL DEFAULT '' COMMENT '연결URL',
  PRIMARY KEY (`nAddInfoSeq`),
  KEY `idxnSaleSeq` (`nSaleSeq`) USING BTREE,
  CONSTRAINT `fkBizAdvertise5` FOREIGN KEY (`nSaleSeq`) REFERENCES `tBizAdvertiseSale` (`nSaleSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8291 DEFAULT CHARSET=utf8 COMMENT='비즈광고 상품 구매 추가 정보';

CREATE TABLE `tBizAdvertiseSaleAdminRecommend` (
  `nSaleSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '비즈광고 상품 구매정보 고유키',
  `sTitle` varchar(100) DEFAULT NULL COMMENT '제목',
  `sURL` varchar(255) DEFAULT NULL COMMENT '링크',
  PRIMARY KEY (`nSaleSeq`),
  CONSTRAINT `fktBizAdvertiseSaleAdminRecommend1` FOREIGN KEY (`nSaleSeq`) REFERENCES `tBizAdvertiseSale` (`nSaleSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='관리자 상품 추천정보';

CREATE TABLE `tBizAdvertiseSaleLog` (
  `nSaleSeqLog` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '상품구매정보 로그 고유번호',
  `nSaleSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품구매정보 고유번호',
  `nProductCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품코드',
  `nMakerCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '제조사코드',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상태(0:대기, 1: 승인)',
  `nAdminMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '구매자 고유번호',
  `sMemo` varchar(200) DEFAULT NULL COMMENT '간략내용',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL DEFAULT '00:00:00' COMMENT '등록시',
  PRIMARY KEY (`nSaleSeqLog`),
  KEY `idxnSaleSeq` (`nSaleSeq`),
  CONSTRAINT `fkBizAdvertise4` FOREIGN KEY (`nSaleSeq`) REFERENCES `tBizAdvertiseSale` (`nSaleSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31441 DEFAULT CHARSET=utf8 COMMENT='비즈광고 상품구매정보 로그';

CREATE TABLE `tBizAdvertiseSalePlusLink` (
  `nSaleSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품구매정보 고유번호',
  `sCompanyCode` varchar(6) NOT NULL DEFAULT '' COMMENT '업체코드',
  `sSellerID` varchar(50) NOT NULL DEFAULT '' COMMENT '판매자 아이디',
  `sLInkURL` varchar(200) NOT NULL DEFAULT '' COMMENT '링크URL',
  `sGoodsName` varchar(200) NOT NULL DEFAULT '' COMMENT '상품명',
  `nDeliveryPriceType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '배송비 여부(1:유료, 2:무료, 3:유/무료)',
  `nDeliveryPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '배송비',
  `nPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가격',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상태(1:승인, 2:미승인)',
  `dtCreate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일시',
  `dtUpdate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일시',
  `sLinkProductCode` varchar(100) NOT NULL DEFAULT '' COMMENT '업체상품코드',
  PRIMARY KEY (`nSaleSeq`),
  CONSTRAINT `fkBizAdvertise6` FOREIGN KEY (`nSaleSeq`) REFERENCES `tBizAdvertiseSale` (`nSaleSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='플러스 링크 추가정보';

CREATE TABLE `tCategoryAttribute` (
  `nCategoryAttributeSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '카테고리별속성연결정보고유코드',
  `nCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리고유코드',
  `nAttributeSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '속성고유코드',
  `sBasicSearchYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '기본검색노출유무 Y:포함, N:불포함',
  `sDisplayYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  `nDisplaySort` smallint(5) unsigned NOT NULL DEFAULT '1' COMMENT '노출순서',
  PRIMARY KEY (`nCategoryAttributeSeq`),
  UNIQUE KEY `idxUMCategoryAttribute` (`nCategorySeq`,`nAttributeSeq`),
  KEY `idxMnCategoryListSort` (`nCategorySeq`,`nDisplaySort`,`sDisplayYN`),
  KEY `fkAttribute5` (`nAttributeSeq`),
  CONSTRAINT `fkAttribute4` FOREIGN KEY (`nCategorySeq`) REFERENCES `tcate` (`cate_c`) ON DELETE CASCADE,
  CONSTRAINT `fkAttribute5` FOREIGN KEY (`nAttributeSeq`) REFERENCES `tAttribute` (`nAttributeSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=605929 DEFAULT CHARSET=euckr COMMENT='카테고리별 속성연결정보';

CREATE TABLE `tCategoryAttributeOption` (
  `nCategoryAttributeSeq` int(10) unsigned NOT NULL COMMENT '카테고리별속성연결정보고유코드',
  `sOptionType` enum('I') NOT NULL COMMENT '카테고리별속성연결정보옵션타입 (I:이미지등록정보)',
  PRIMARY KEY (`nCategoryAttributeSeq`),
  CONSTRAINT `fkCategoryAttributeOption1` FOREIGN KEY (`nCategoryAttributeSeq`) REFERENCES `tCategoryAttribute` (`nCategoryAttributeSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='카테고리별 속성연결정보 옵션';

CREATE TABLE `tCategoryAttributeValueDisplay` (
  `nAttributeValueDisplaySeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '카테고리별속성값노출정보고유코드',
  `nCategoryAttributeSeq` int(10) unsigned NOT NULL COMMENT '카테고리별속성연결정보고유코드',
  `nAttributeValueSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '속성값고유코드',
  `nMainDisplaySort` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '카테고리주요노출순서',
  PRIMARY KEY (`nAttributeValueDisplaySeq`),
  UNIQUE KEY `idxUMCategoryAttributeValue` (`nCategoryAttributeSeq`,`nAttributeValueSeq`),
  KEY `fkAttribute11` (`nAttributeValueSeq`),
  CONSTRAINT `fkAttribute10` FOREIGN KEY (`nCategoryAttributeSeq`) REFERENCES `tCategoryAttribute` (`nCategoryAttributeSeq`) ON DELETE CASCADE,
  CONSTRAINT `fkAttribute11` FOREIGN KEY (`nAttributeValueSeq`) REFERENCES `tAttributeValue` (`nAttributeValueSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10777 DEFAULT CHARSET=euckr COMMENT='카테고리별 속성값노출정보';

CREATE TABLE `tCategoryKeyword` (
  `nCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리 고유키',
  `sKeyword` varchar(255) NOT NULL DEFAULT '' COMMENT '카테고리별 검색 키워드 (,) 구분자 사용',
  PRIMARY KEY (`nCategorySeq`),
  CONSTRAINT `fkCategory1` FOREIGN KEY (`nCategorySeq`) REFERENCES `tcate` (`cate_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tChartToppingPenalty` (
  `nChartToppingPenaltySeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '인기순위패널티고유번호',
  `nPenaltyType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '패널티구분 1:상품고유번호, 2:제조사고유번호...',
  `nPenaltySeq` int(10) unsigned NOT NULL DEFAULT '1' COMMENT '패널티별 고유번호',
  `nCategorySeq1` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '대분류 코드',
  `nCategorySeq2` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '중분류 코드',
  `nCategorySeq3` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '소분류 코드',
  `nCategorySeq4` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '세분류 코드',
  `dtStartDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '적용시작일',
  `dtEndDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '적용종료일',
  `nStatus` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '상태값 1:정상, 2:삭제..',
  `nPenaltyValue` tinyint(3) NOT NULL DEFAULT '0' COMMENT '패널티값 1-100 사이값으로 음수사용가능',
  PRIMARY KEY (`nChartToppingPenaltySeq`),
  KEY `idxMnPenaltyType` (`nPenaltyType`,`dtStartDate`,`dtEndDate`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=euckr COMMENT='인기순위패널티정보';

CREATE TABLE `tChartToppingPenaltyLog` (
  `nChartToppingPenaltyLogSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '인기순위패널티로그고유번호',
  `nChartToppingPenaltySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '인기순위패널티고유번호',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원고유번호',
  `nStatus` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '상태값 1:정상, 2:삭제..',
  `sMemberIP` varchar(15) NOT NULL DEFAULT '' COMMENT '작성자 IP',
  `dtCreateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '입력일자',
  `sContents` varchar(255) NOT NULL DEFAULT '' COMMENT '상세내역',
  PRIMARY KEY (`nChartToppingPenaltyLogSeq`),
  KEY `fkChartTopping1` (`nChartToppingPenaltySeq`),
  CONSTRAINT `fkChartTopping1` FOREIGN KEY (`nChartToppingPenaltySeq`) REFERENCES `tChartToppingPenalty` (`nChartToppingPenaltySeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=euckr COMMENT='인기순위패널티로그정보';

CREATE TABLE `tCmpnyProductCardPriceInfo` (
  `nProductSeq` mediumint(8) unsigned NOT NULL COMMENT '상품고유키',
  `nCardAveragePrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카드평균가',
  `nCardMinimumPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카드최저가',
  PRIMARY KEY (`nProductSeq`),
  CONSTRAINT `fkCmpnyProductCardPriceInfo1` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='업체카드가격정보';

CREATE TABLE `tCmpnyRequestSetting` (
  `nCmpnyRequestSettingtSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '시퀀스',
  `sCmpnyID` varchar(6) NOT NULL DEFAULT '' COMMENT '협력사 ID',
  `sClass` varchar(8) NOT NULL DEFAULT '' COMMENT '요청 종류',
  `emInstantSetting` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '즉시변경여부',
  `sCurrentSetting` varchar(8) NOT NULL DEFAULT '' COMMENT '현재 설정',
  `sRequestSetting` varchar(8) NOT NULL DEFAULT '' COMMENT '변경될 설정',
  `dtRequestDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '요청일',
  `dtAcceptDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '접수일',
  `emApproval` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '요청 승인 여부',
  PRIMARY KEY (`nCmpnyRequestSettingtSeq`),
  KEY `idxMsCmpnyID` (`sCmpnyID`,`sClass`)
) ENGINE=InnoDB AUTO_INCREMENT=2011 DEFAULT CHARSET=euckr COMMENT='협력사 정보 수정 요청 기록 테이블';

CREATE TABLE `tCmpnyRequestSettingDetail` (
  `nCmpnyRequestSettingtSeq` int(10) unsigned NOT NULL COMMENT 'tCmpnyRequestSetting 테이블 스퀀스 값',
  `sCurrentSettingDetail` text NOT NULL COMMENT '현재 설정 상세 정보',
  `sRequestSettingDetail` text NOT NULL COMMENT '변경될 설정 상세 정보',
  PRIMARY KEY (`nCmpnyRequestSettingtSeq`),
  CONSTRAINT `fkCmpnyRequestSetting` FOREIGN KEY (`nCmpnyRequestSettingtSeq`) REFERENCES `tCmpnyRequestSetting` (`nCmpnyRequestSettingtSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='협력사 정보 수정 요청 상세 기록 테이블';

CREATE TABLE `tCmpnyTaxRequest` (
  `nCmpnyTaxRequestSeq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `WorkHistorySeq` int(10) unsigned NOT NULL DEFAULT '0',
  `sCompany` varchar(6) NOT NULL DEFAULT '',
  `sCmpnyName` varchar(50) NOT NULL DEFAULT '',
  `sOwnerName` varchar(8) NOT NULL DEFAULT '',
  `sRegistNumber` varchar(16) NOT NULL DEFAULT '000-00-00000',
  `sCondition` varchar(16) NOT NULL DEFAULT '',
  `sItem` varchar(16) NOT NULL DEFAULT '',
  `sTaxPost` varchar(16) NOT NULL DEFAULT '',
  `sTaxAddress1` varchar(32) NOT NULL DEFAULT '',
  `sTaxAddress2` varchar(32) NOT NULL DEFAULT '',
  `sRcvPost` varchar(16) NOT NULL DEFAULT '',
  `sRcvAddress1` varchar(32) NOT NULL DEFAULT '',
  `sRcvAddress2` varchar(32) NOT NULL DEFAULT '',
  `sEmail` varchar(32) NOT NULL DEFAULT '',
  `emBsnRstDoc` enum('Y','N') NOT NULL DEFAULT 'N',
  `dtRequestDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `dtAcceptDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sMallName` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`nCmpnyTaxRequestSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=825 DEFAULT CHARSET=euckr COMMENT='세금 계산서 수정 요청 테이블';

CREATE TABLE `tConcern` (
  `seq` int(8) unsigned NOT NULL AUTO_INCREMENT,
  `conName` varchar(100) NOT NULL DEFAULT '',
  `conOpen` enum('Y','N') NOT NULL DEFAULT 'Y',
  `writeTime` datetime NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=455 DEFAULT CHARSET=euckr;

CREATE TABLE `tDerivativeMobilePhoneDescription` (
  `nDerivativeSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '파생상품 고유키',
  `sInstalmentPrice` varchar(100) DEFAULT NULL COMMENT '할부원금',
  `sSignPrice` varchar(100) DEFAULT NULL COMMENT '가입비',
  `sUSIMPrice` varchar(100) DEFAULT NULL COMMENT 'USIM비용',
  `sAddedService` varchar(100) DEFAULT NULL COMMENT '부가서비스 조건',
  `sDescription` text COMMENT '상세설명',
  PRIMARY KEY (`nDerivativeSeq`),
  CONSTRAINT `fkMobilePhone1` FOREIGN KEY (`nDerivativeSeq`) REFERENCES `tDerivativeService` (`nDerivativeSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='파생상품 핸드폰 연동몰 상세정보';

CREATE TABLE `tDerivativeService` (
  `nDerivativeSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '파생상품 고유키',
  `sCompanyCode` varchar(6) NOT NULL DEFAULT '' COMMENT '업체코드',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '상품코드',
  `sGoodsName` varchar(200) DEFAULT NULL COMMENT '상품명',
  `sApplicationURL` varchar(200) NOT NULL DEFAULT '' COMMENT '신청하기 URL',
  `nState` tinyint(3) NOT NULL DEFAULT '0' COMMENT '상태 1:노출, 2:비노출, 3:관리자제한',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '작성일',
  `dtCreateTime` time NOT NULL DEFAULT '00:00:00' COMMENT '작성시간',
  `nCntComment` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '댓글수',
  PRIMARY KEY (`nDerivativeSeq`),
  UNIQUE KEY `idxUnCompanyCode` (`sCompanyCode`,`nProductSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='파생상품 서비스 공용정보';

CREATE TABLE `tEasySearchGuide` (
  `nGuideSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가이드 질문 고유번호',
  `sQuestion` varchar(100) DEFAULT NULL COMMENT '질문',
  `nParentQuestionSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '부모 질문 고유키',
  PRIMARY KEY (`nGuideSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=3639 DEFAULT CHARSET=utf8 COMMENT='상품검색 가이드 질문';

CREATE TABLE `tEasySearchGuideAddOn` (
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  `sGuideCountTitle` varchar(50) DEFAULT NULL COMMENT '통계제목',
  `sGuideTip` varchar(100) DEFAULT NULL COMMENT '팁',
  PRIMARY KEY (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide3` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='추가정보';

CREATE TABLE `tEasySearchGuideAttribute` (
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  `nAttributeSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품 속성 고유키',
  PRIMARY KEY (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide5` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='연결속성';

CREATE TABLE `tEasySearchGuideAttributeValue` (
  `nGuideAttributeValueSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연결 속성값',
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  `nAttributeValueSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품 속성값 고유키',
  PRIMARY KEY (`nGuideAttributeValueSeq`),
  KEY `fkEasySearchGuide6` (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide6` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15327 DEFAULT CHARSET=utf8 COMMENT='연결 속성값';

CREATE TABLE `tEasySearchGuideBrand` (
  `nGuideBrandSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연결 브랜드 고유키',
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  `nBrandSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '브랜드 고유키',
  PRIMARY KEY (`nGuideBrandSeq`),
  KEY `fkEasySearchGuide7` (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide7` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=287 DEFAULT CHARSET=utf8 COMMENT='연결 브랜드';

CREATE TABLE `tEasySearchGuideCategory` (
  `nGuideCategorySeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가이드 카테고리 고유키',
  `nCategory1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '1차 카테고리',
  `nCategory2` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '2차 카테고리',
  `sDisplayYN` char(1) NOT NULL DEFAULT 'N' COMMENT '검색가이드 노출여부(Y:노출,N:비노출)',
  PRIMARY KEY (`nGuideCategorySeq`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='상품 검색 가이드 카테고리';

CREATE TABLE `tEasySearchGuideCount` (
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  `nCount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카운트',
  PRIMARY KEY (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide4` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카운트';

CREATE TABLE `tEasySearchGuideMaker` (
  `nGuideMakerSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연결 메이커 고유키',
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  `nMakerSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '메이커 고유키',
  PRIMARY KEY (`nGuideMakerSeq`),
  KEY `fkEasySearchGuide8` (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide8` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1023 DEFAULT CHARSET=utf8 COMMENT='연결 메이커';

CREATE TABLE `tEventIcon` (
  `nEventIconSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '상품별 이벤트아이콘 고유번호',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '상품고유코드',
  `nCategorySeq1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '대분류 코드',
  `nCategorySeq2` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '중분류 코드',
  `nCategorySeq3` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '소분류 코드',
  `nCategorySeq4` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '세분류 코드',
  `dtInputDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY (`nEventIconSeq`),
  KEY `idxCategoryInfo` (`nCategorySeq2`)
) ENGINE=InnoDB AUTO_INCREMENT=15767 DEFAULT CHARSET=utf8 COMMENT='상품별 이벤트아이콘 정보';

CREATE TABLE `tEventIconDetail` (
  `nEventIconDetailSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '상품별 이벤트아이콘 상세 고유번호',
  `nEventIconSeq` int(10) unsigned NOT NULL COMMENT '상품별 이벤트아이콘 고유번호',
  `nIconType` tinyint(3) NOT NULL DEFAULT '1' COMMENT 'Icon 구분 1:이벤트, 2:표준PC, 3:표준모니터, 4:표준노트북..',
  `sIconViewYN` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Icon 노출 여부 Y:노출함, N:노출안함',
  `dtStartDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '적용 시작일',
  `dtEndDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '적용 종료일',
  PRIMARY KEY (`nEventIconDetailSeq`),
  KEY `fkIcon1` (`nEventIconSeq`),
  CONSTRAINT `fkIcon1` FOREIGN KEY (`nEventIconSeq`) REFERENCES `tEventIcon` (`nEventIconSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=63018 DEFAULT CHARSET=utf8 COMMENT='상품별 이벤트아이콘 상세정보';

CREATE TABLE `tLinkEasySearchCategoryGuide` (
  `nGuideCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 카테고리 고유키',
  `nGuideSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가이드 질문 고유번호',
  PRIMARY KEY (`nGuideCategorySeq`,`nGuideSeq`),
  KEY `fkEasySearchGuide2` (`nGuideSeq`),
  CONSTRAINT `fkEasySearchGuide1` FOREIGN KEY (`nGuideCategorySeq`) REFERENCES `tEasySearchGuideCategory` (`nGuideCategorySeq`) ON DELETE CASCADE,
  CONSTRAINT `fkEasySearchGuide2` FOREIGN KEY (`nGuideSeq`) REFERENCES `tEasySearchGuide` (`nGuideSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품 검색 카테고리 가이드  연결';

CREATE TABLE `tLinkProductSamsung` (
  `sModelCode` varchar(50) NOT NULL DEFAULT '' COMMENT '모델코드',
  `nProductSeq` mediumint(3) unsigned NOT NULL DEFAULT '0' COMMENT '다나와상품코드',
  PRIMARY KEY (`sModelCode`,`nProductSeq`),
  KEY `fkProduct4` (`nProductSeq`),
  CONSTRAINT `fkProduct3` FOREIGN KEY (`sModelCode`) REFERENCES `tSamsungProduct` (`sModelCode`) ON DELETE CASCADE,
  CONSTRAINT `fkProduct4` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='다나와상품별 삼성제품연결정보';

CREATE TABLE `tLinkSlrclubProduct` (
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '다나와상품고유번호',
  `nSlrClubProductCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'SLRClub 상품고유번호',
  `dtProductInputDate` date NOT NULL DEFAULT '0000-00-00',
  PRIMARY KEY (`nProductSeq`),
  CONSTRAINT `fkSlrClub1` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='다나와상품별 SLRClub상품 매핑정보';

CREATE TABLE `tLocalMarket` (
  `nLocalMarketSeq` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '오프라인매장종류고유코드',
  `sLocalMarketName` varchar(30) NOT NULL DEFAULT '' COMMENT '매장이름',
  `sLocalMarketMessage` varchar(200) DEFAULT NULL COMMENT '매장별행사',
  PRIMARY KEY (`nLocalMarketSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=euckr COMMENT='오프라인매장종류';

CREATE TABLE `tLocalMarketCategory` (
  `nLocalMarketCategorySeq` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '오프라인매장별카테고리고유코드',
  `nLocalMarketSeq` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '오프라인매장종류고유코드',
  `nCategoryCode` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리고유코드',
  `nBrandCode` int(10) unsigned DEFAULT NULL COMMENT '브랜드고유코드',
  `nMakerCode` int(10) unsigned DEFAULT NULL COMMENT '제조사고유코드',
  PRIMARY KEY (`nLocalMarketCategorySeq`),
  KEY `fkLocalMarketCategory1` (`nLocalMarketSeq`),
  KEY `fkLocalMarketCategory2` (`nCategoryCode`),
  CONSTRAINT `fkLocalMarketCategory1` FOREIGN KEY (`nLocalMarketSeq`) REFERENCES `tLocalMarket` (`nLocalMarketSeq`) ON DELETE CASCADE,
  CONSTRAINT `fkLocalMarketCategory2` FOREIGN KEY (`nCategoryCode`) REFERENCES `tcate` (`cate_c`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=euckr COMMENT='오프라인매장별카테고리';

CREATE TABLE `tLocalMarketPlace` (
  `nLocalMarketPlaceSeq` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '오프라인매장지점고유코드',
  `nLocalMarketSeq` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '오프라인매장종류고유코드',
  `sLocalMarketPlaceName` varchar(100) NOT NULL DEFAULT '' COMMENT '지점이름',
  `sZipCode` varchar(7) NOT NULL DEFAULT '' COMMENT '지점우편번호',
  `sLocalMarketPlaceAddress` varchar(80) NOT NULL DEFAULT '' COMMENT '지점기본주소',
  `nLocalMarketPlaceLat` float(10,7) NOT NULL DEFAULT '0.0000000' COMMENT '지점좌표위도',
  `nLocalMarketPlaceLng` float(11,7) NOT NULL DEFAULT '0.0000000' COMMENT '지점좌표경도',
  PRIMARY KEY (`nLocalMarketPlaceSeq`),
  KEY `fkLocalMarketPlace1` (`nLocalMarketSeq`),
  KEY `idxZipCode` (`sZipCode`),
  KEY `idxLocalMarketPlaceAddress` (`sLocalMarketPlaceAddress`),
  CONSTRAINT `fkLocalMarketPlace1` FOREIGN KEY (`nLocalMarketSeq`) REFERENCES `tLocalMarket` (`nLocalMarketSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1160 DEFAULT CHARSET=euckr COMMENT='오프라인매장지점';

CREATE TABLE `tLocalMarketPlaceDetail` (
  `nLocalMarketPlaceSeq` smallint(5) unsigned NOT NULL COMMENT '오프라인매장지점고유코드',
  `sLocalMarketPlaceDetailAddress` varchar(100) NOT NULL DEFAULT '' COMMENT '지점상세주소',
  `sLocalMarketPlaceDetailHomepage` varchar(200) NOT NULL DEFAULT '' COMMENT '지점홈페이지',
  `sLocalMarketPlaceDetailPhoneNumber` varchar(20) NOT NULL DEFAULT '' COMMENT '지점전화번호',
  `sLocalMarketPlaceDetailBusinessHour` varchar(20) NOT NULL DEFAULT '' COMMENT '지점영업시간',
  `sLocalMarketPlaceDetailMessage` varchar(200) DEFAULT NULL COMMENT '지점행사',
  PRIMARY KEY (`nLocalMarketPlaceSeq`),
  CONSTRAINT `fkLocalMarketPlaceDetail1` FOREIGN KEY (`nLocalMarketPlaceSeq`) REFERENCES `tLocalMarketPlace` (`nLocalMarketPlaceSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='오프라인매장지점상세정보';

CREATE TABLE `tMappingCarInfo` (
  `nModelCode` smallint(3) NOT NULL COMMENT '업체측 모델코드',
  `nLineUp` smallint(4) NOT NULL DEFAULT '0' COMMENT '업체측 라인업',
  `nProductSeq` mediumint(8) unsigned NOT NULL COMMENT '다나와 상품코드',
  PRIMARY KEY (`nModelCode`,`nLineUp`,`nProductSeq`),
  KEY `idxnProductSeq` (`nProductSeq`),
  CONSTRAINT `fktMappingCarInfo` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tMonitorPriceLink` (
  `sCompanyCode` varchar(6) NOT NULL DEFAULT '' COMMENT '협력사 코드',
  `sLinkProductCode` varchar(100) NOT NULL DEFAULT '' COMMENT '협력사 상품코드',
  `sLinkProductName` varchar(255) NOT NULL DEFAULT '' COMMENT '협력사 상품명',
  `nPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가격',
  `sConfirmYN` char(1) NOT NULL DEFAULT 'N' COMMENT '관리자 확인 여부',
  `dtModifyDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '최종 수정일',
  PRIMARY KEY (`sCompanyCode`,`sLinkProductCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='링크상품의 상품명 변경, 최저가 변경 시 기존 데이터를 저장하는 테이블';

CREATE TABLE `tMonitorPriceLinkConfig` (
  `nCategorySeq1` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '대분류 카테고리 코드',
  `nCategorySeq2` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '중분류 카테고리 코드',
  `nCategorySeq3` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '소분류 카테고리 코드',
  `nCategorySeq4` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '세분류 카테고리 코드',
  `nRankingLimit` tinyint(3) unsigned DEFAULT '20' COMMENT '인기순위 범위 제한 값',
  PRIMARY KEY (`nCategorySeq1`,`nCategorySeq2`,`nCategorySeq3`,`nCategorySeq4`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='실시간 가격 갱신 시스템 구성 테이블';

CREATE TABLE `tMonitorPriceLinkMemo` (
  `sCompanyCode` varchar(6) NOT NULL COMMENT '협력사 코드',
  `sLinkProductCode` varchar(100) NOT NULL COMMENT '협력사 상품코드',
  `sMemo` text NOT NULL COMMENT '메모',
  `dtModifyDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '최종 수정일',
  PRIMARY KEY (`sCompanyCode`,`sLinkProductCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='링크상품에 대한 관리자 메모 저장 테이블';

CREATE TABLE `tNvidiaBenchData` (
  `nSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '증가값',
  `nMatchingSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '최적화PC매칭시퀀스',
  `nGpuProductCode` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'nvidia VGA 상품 코드',
  `sGpuProductName` varchar(100) NOT NULL DEFAULT '' COMMENT 'nvidia VGA 상품명',
  `nCpuProductCode` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'cpu 상품 코드',
  `sCpuProductName` varchar(100) NOT NULL DEFAULT '' COMMENT 'cpu 상품명',
  `n3DMarkVantage` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '3DMarkVantage벤치마킹값',
  `n3DMark` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '3DMark 벤치마킹값',
  `nPCMark` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'PCMark 벤치마킹값',
  `nGameMark1` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT 'WOW벤치마킹값',
  `nGameMark2` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '몬스터헌터데모벤치마킹값',
  `nGameMark3` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '데빌 메이 크라이 4 벤치마킹값',
  `isView` char(1) NOT NULL DEFAULT 'N' COMMENT '노출여부',
  PRIMARY KEY (`nSeq`),
  UNIQUE KEY `idxUMnCpuProductCode` (`nCpuProductCode`,`nGpuProductCode`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=euckr COMMENT='nvidia_opc 벤치마크 데이터';

CREATE TABLE `tPopularKeyword` (
  `nPopularKeywordSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '인기검색어고유번호',
  `sPopularKeywordName` varchar(50) NOT NULL DEFAULT '' COMMENT '키워드명',
  `sPopularKeywordStart` char(2) NOT NULL DEFAULT '' COMMENT '키워드초성',
  `nRanking` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '순위',
  `dtUpdateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '최근수정일',
  `sManualYN` char(1) NOT NULL DEFAULT 'N' COMMENT '수동관리여부 N:자동(D) Y:수동',
  `nSearchCount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '검색 횟수',
  PRIMARY KEY (`nPopularKeywordSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=437048 DEFAULT CHARSET=euckr COMMENT='인기검색어';

CREATE TABLE `tPopularKeywordManualProduct` (
  `nPopularKeywordProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '인기검색어상품고유코드',
  `sProductName` varchar(100) DEFAULT NULL COMMENT '상품명',
  `sProductUrl` varchar(300) DEFAULT NULL COMMENT '상품URL',
  `sProductImgUrl` varchar(300) DEFAULT NULL COMMENT '상품이미지URL',
  `nProductPrice` int(10) unsigned DEFAULT NULL COMMENT '상품가격',
  PRIMARY KEY (`nPopularKeywordProductSeq`),
  CONSTRAINT `fkPopularKeywordManualProduct1` FOREIGN KEY (`nPopularKeywordProductSeq`) REFERENCES `tPopularKeywordProduct` (`nPopularKeywordProductSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='인기검색어수동상품';

CREATE TABLE `tPopularKeywordProduct` (
  `nPopularKeywordProductSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '인기검색어상품고유코드',
  `nPopularKeywordSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '인기검색어고유번호',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '상품고유번호',
  `sShowListYN` char(1) NOT NULL DEFAULT 'N' COMMENT '리스트상단사용여부',
  `sListOrder` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '리스트노출순서',
  `sExternalReferenceYN` char(1) NOT NULL DEFAULT 'N' COMMENT '외부참조여부 N:내부(D) Y:외부',
  PRIMARY KEY (`nPopularKeywordProductSeq`),
  KEY `fkPopularKeywordProduct1` (`nPopularKeywordSeq`),
  KEY `fkPopularKeywordProduct2` (`nProductSeq`),
  CONSTRAINT `fkPopularKeywordProduct1` FOREIGN KEY (`nPopularKeywordSeq`) REFERENCES `tPopularKeyword` (`nPopularKeywordSeq`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkPopularKeywordProduct2` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5473201 DEFAULT CHARSET=euckr COMMENT='인기검색어상품';

CREATE TABLE `tPriceAffiliatePromotion` (
  `nPriceAffiliatePromotionSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '제휴 업체 가격별 프로모션 고유번호',
  `nPriceSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '업체가격고유번호',
  `nPromotionType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '프로모션구분 1:카드..',
  `sPromotionName` varchar(50) NOT NULL DEFAULT '' COMMENT '프로모션명',
  `nPromotionPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '프로모션 가격정보',
  PRIMARY KEY (`nPriceAffiliatePromotionSeq`),
  KEY `idxMProdID` (`nPriceSeq`,`nPromotionType`),
  CONSTRAINT `fkPricecompare4` FOREIGN KEY (`nPriceSeq`) REFERENCES `tprice_affiliate` (`nPriceSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2322164 DEFAULT CHARSET=euckr COMMENT='제휴 업체 가격별 프로모션 정보';

CREATE TABLE `tPricePromotion` (
  `nPricePromotionSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '컴/가전 업체 가격별 프로모션 고유번호',
  `nPriceSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '업체가격고유번호',
  `nPromotionType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '프로모션구분 1:카드..',
  `sPromotionName` varchar(50) NOT NULL DEFAULT '' COMMENT '프로모션명',
  `nPromotionPrice` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '프로모션 가격정보',
  PRIMARY KEY (`nPricePromotionSeq`),
  KEY `idxMProdID` (`nPriceSeq`,`nPromotionType`),
  CONSTRAINT `fkPricecompare3` FOREIGN KEY (`nPriceSeq`) REFERENCES `tprice` (`nPriceSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5797694 DEFAULT CHARSET=euckr COMMENT='컴/가전 업체 가격별 프로모션 정보';

CREATE TABLE `tProductAttributeValue` (
  `nProductAttributeValueSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '카테고리상품별속성값연결정보고유코드',
  `nProductCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품별카테고리정보고유고드',
  `nAttributeValueSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '속성값고유코드',
  `sAttributeValueDescription` varchar(50) DEFAULT NULL COMMENT '속성값상세설명',
  PRIMARY KEY (`nProductAttributeValueSeq`),
  UNIQUE KEY `idxUMProductCategoryAttributeValue` (`nProductCategorySeq`,`nAttributeValueSeq`),
  KEY `fkAttribute7` (`nAttributeValueSeq`),
  CONSTRAINT `fkAttribute6` FOREIGN KEY (`nProductCategorySeq`) REFERENCES `tProductCategory` (`nProductCategorySeq`) ON DELETE CASCADE,
  CONSTRAINT `fkAttribute7` FOREIGN KEY (`nAttributeValueSeq`) REFERENCES `tAttributeValue` (`nAttributeValueSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15415317 DEFAULT CHARSET=euckr COMMENT='카테고리상품별 속성값연결정보';

CREATE TABLE `tProductBoardCount` (
  `nProductBoardCountSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '자동증가 고유번호',
  `nProductCode` mediumint(8) unsigned NOT NULL COMMENT '상품고유번호',
  `nBoardSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '작성된 게시판 고유번호',
  `nWriteCnt` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '작성된 게시글 count',
  PRIMARY KEY (`nProductBoardCountSeq`),
  UNIQUE KEY `idxUMProductBoard` (`nProductCode`,`nBoardSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=105163 DEFAULT CHARSET=euckr COMMENT='상품별 게시판 글작성 카운트 테이블';

CREATE TABLE `tProductCategory` (
  `nProductCategorySeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '상품별카테고리정보고유고드',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '상품고유코드',
  `nCategorySeq1` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '대분류 코드',
  `nCategorySeq2` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '중분류 코드',
  `nCategorySeq3` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '소분류 코드',
  `nCategorySeq4` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '세분류 코드',
  `sOriginYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '원본상품여부',
  `stViewBit` set('c1','c2','c3','c4') DEFAULT NULL COMMENT '카테고리별상품노출여부',
  `nCntSave` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '인기순위 산출을 위한 점수',
  PRIMARY KEY (`nProductCategorySeq`),
  UNIQUE KEY `idxUMnProductSeq` (`nCategorySeq1`,`nCategorySeq2`,`nCategorySeq3`,`nCategorySeq4`,`nProductSeq`),
  KEY `idxMnCategorySort1` (`nCategorySeq2`,`stViewBit`),
  KEY `inxMnCategorySort2` (`nCategorySeq3`,`stViewBit`),
  KEY `inxMnCategorySort3` (`nCategorySeq4`,`stViewBit`),
  KEY `idxMnProductCategoryList1` (`nCategorySeq2`,`sOriginYN`),
  KEY `idxMnProductCategoryList2` (`nCategorySeq3`,`sOriginYN`),
  KEY `idxMnProductCategoryList3` (`nCategorySeq4`,`sOriginYN`),
  KEY `fkAttribute1` (`nProductSeq`),
  CONSTRAINT `fkAttribute1` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1868253 DEFAULT CHARSET=euckr COMMENT='상품별 카테고리정보';

CREATE TABLE `tProductDescription` (
  `nProductCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품별카테고리정보고유고드',
  `sSimpleDescription` varchar(500) NOT NULL DEFAULT '' COMMENT '상품간략설명',
  `sAddDescription` varchar(350) DEFAULT NULL COMMENT '상품추가설명',
  `sFixDescription` varchar(3000) DEFAULT NULL COMMENT '카테고리고정설명',
  PRIMARY KEY (`nProductCategorySeq`),
  CONSTRAINT `fkAttribute8` FOREIGN KEY (`nProductCategorySeq`) REFERENCES `tProductCategory` (`nProductCategorySeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='카테고리상품별 속성값상세정보';

CREATE TABLE `tProductDescriptionDictionary` (
  `nProductCategorySeq` int(10) unsigned NOT NULL COMMENT '상품별카테고리정보고유고드',
  `sSimpleDictionaryCode` varchar(500) NOT NULL DEFAULT '0' COMMENT '상품 간략설명 용어 사전 코드',
  PRIMARY KEY (`nProductCategorySeq`),
  CONSTRAINT `fktProductDescriptionDictionary1` FOREIGN KEY (`nProductCategorySeq`) REFERENCES `tProductDescription` (`nProductCategorySeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='카테고리상품별속성값상세정보 용어사전';

CREATE TABLE `tProductKC` (
  `nProductSeq` mediumint(8) unsigned NOT NULL COMMENT '상품코드(고유번호)',
  `sKCCode` varchar(30) NOT NULL DEFAULT '' COMMENT 'KC인증번호',
  PRIMARY KEY (`nProductSeq`),
  CONSTRAINT `fkProduct2` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='상품별 KC인증 정보';

CREATE TABLE `tProductMovie` (
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '다나와 대표상품 고유번호',
  `nMovieSeq` int(4) unsigned NOT NULL DEFAULT '0' COMMENT '동영상 고유번호',
  PRIMARY KEY (`nProductSeq`),
  CONSTRAINT `fkProduct1` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='다나와 대표상품별 연결 동영상 정보';

CREATE TABLE `tProductPriceCount` (
  `nProductSeq` mediumint(8) unsigned NOT NULL COMMENT '다나와상품고유번호',
  `nPricePartnerType` tinyint(3) NOT NULL DEFAULT '1' COMMENT '가격별 파트너 구분 1:제휴몰(goodscollection), 2:독립몰(goodscollection), 3:연동몰(eldanawa), 4:빌링시스템(dbBilling)..',
  `nMinPrice` int(10) unsigned DEFAULT '0' COMMENT '최저가',
  `nAvgPrice` int(10) unsigned DEFAULT '0' COMMENT '평균가',
  `nShopQ` int(10) unsigned DEFAULT '0' COMMENT '업체수',
  `dtUpdateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '최종수정일',
  PRIMARY KEY (`nProductSeq`,`nPricePartnerType`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='다나와 기준상품별 가격 카운트 정보';

CREATE TABLE `tProductVR` (
  `nProductSeq` mediumint(3) unsigned NOT NULL DEFAULT '0' COMMENT '상품코드(고유번호)',
  `sSwfDisplayYN` char(1) NOT NULL DEFAULT 'N' COMMENT 'SWF노출여부',
  `sGifDisplayYN` char(1) NOT NULL DEFAULT 'N' COMMENT 'GIF노출여부',
  `sSwfFileYN` char(1) NOT NULL DEFAULT 'N' COMMENT 'SWF등록여부',
  `sGifFileYN` char(1) NOT NULL DEFAULT 'N' COMMENT 'GIF등록여부',
  PRIMARY KEY (`nProductSeq`),
  CONSTRAINT `fkVR1` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='상품별 VR 매핑정보';

CREATE TABLE `tQrCode` (
  `nQrCodeSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'QR코드 고유번호',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '상품고유코드',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT 'QR코드 상태값(0:활성, 1:대기, 2:비활성...)',
  `sCreator` varchar(50) NOT NULL DEFAULT '' COMMENT 'QR코드 생성자',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '생성일자',
  `dtCreateTime` time NOT NULL DEFAULT '00:00:00' COMMENT '생성시간',
  PRIMARY KEY (`nQrCodeSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=8010 DEFAULT CHARSET=utf8 COMMENT='QR코드 정보';

CREATE TABLE `tQrCodeValue` (
  `nQrCodeSeq` int(10) unsigned NOT NULL COMMENT 'QR코드 고유번호',
  `sQrValue` varchar(7089) NOT NULL DEFAULT '' COMMENT 'QR코드 자체값',
  PRIMARY KEY (`nQrCodeSeq`),
  CONSTRAINT `fkQrCode1` FOREIGN KEY (`nQrCodeSeq`) REFERENCES `tQrCode` (`nQrCodeSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='QR코드 자체값 정보';

CREATE TABLE `tRealTimePriceUpdateLog` (
  `nRealTimePriceUpdateLogSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '실시간가격갱신로그 고유번호',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '다나와 대표상품 고유번호',
  `dtExecuteDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '실시간 가격갱신 시간',
  PRIMARY KEY (`nRealTimePriceUpdateLogSeq`),
  KEY `fkRealTimePriceUpdate` (`nProductSeq`),
  CONSTRAINT `fkRealTimePriceUpdate` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=500578 DEFAULT CHARSET=euckr COMMENT='실시간가격갱신로그정보';

CREATE TABLE `tRelationProduct` (
  `nRelationProductSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연관상품고유번호 ',
  `nRelationProductServiceSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '연관상품서비스고유번호',
  `nRelationProductMenuSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '연관상품메뉴고유번호',
  `nDisplayServiceType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출서비스구분 1:전체, 2:상품블로그, 3:잉크찾기..',
  `sRelationFormType` char(1) NOT NULL DEFAULT '1' COMMENT '연관형태구분 1:상품별, 2:카테고리별...',
  `nRelationFormValue` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '연관형태별상세값 (상품고유번호 또는 카테고리고유번호)',
  `sDisplayYN` char(1) NOT NULL DEFAULT '1' COMMENT '노출여부 Y:노출, N:숨김..',
  `nDisplayOrderby` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출정렬구분 1.인기상품순 2.신상품순..',
  PRIMARY KEY (`nRelationProductSeq`),
  KEY `idxMRelationForm` (`nRelationFormValue`,`sRelationFormType`,`nDisplayServiceType`),
  KEY `fktRelationProduct1` (`nRelationProductServiceSeq`),
  KEY `fktRelationProduct2` (`nRelationProductMenuSeq`),
  CONSTRAINT `fktRelationProduct1` FOREIGN KEY (`nRelationProductServiceSeq`) REFERENCES `tRelationProductService` (`nRelationProductServiceSeq`) ON DELETE CASCADE,
  CONSTRAINT `fktRelationProduct2` FOREIGN KEY (`nRelationProductMenuSeq`) REFERENCES `tRelationProductMenu` (`nRelationProductMenuSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3990 DEFAULT CHARSET=utf8 COMMENT='연관상품정보';

CREATE TABLE `tRelationProductMenu` (
  `nRelationProductMenuSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연관상품메뉴고유번호',
  `sMenuName` varchar(20) NOT NULL DEFAULT '' COMMENT '메뉴명',
  PRIMARY KEY (`nRelationProductMenuSeq`),
  UNIQUE KEY `idxUsMenuName` (`sMenuName`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8 COMMENT='연관상품메뉴정보';

CREATE TABLE `tRelationProductService` (
  `nRelationProductServiceSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연관상품서비스고유번호',
  `sRelationProductServiceName` varchar(50) NOT NULL DEFAULT '' COMMENT '연관상품서비스명',
  PRIMARY KEY (`nRelationProductServiceSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='연관상품서비스정보';

CREATE TABLE `tRelationProductTargetProduct` (
  `nRelationProductTargetProductSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연관상품별대상상품고유번호 ',
  `nRelationProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '연관상품고유번호',
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '다나와상품고유번호',
  `nDisplayServiceType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출서비스구분 1:전체, 2:상품블로그, 3:잉크찾기..',
  `sDisplayYN` char(1) NOT NULL DEFAULT '1' COMMENT '노출여부 Y:노출, N:숨김..',
  `nDisplaySeq` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출순서',
  PRIMARY KEY (`nRelationProductTargetProductSeq`),
  KEY `fktRelationProductTargetProduct1` (`nRelationProductSeq`),
  KEY `fktRelationProductTargetProduct2` (`nProductSeq`),
  CONSTRAINT `fktRelationProductTargetProduct1` FOREIGN KEY (`nRelationProductSeq`) REFERENCES `tRelationProduct` (`nRelationProductSeq`) ON DELETE CASCADE,
  CONSTRAINT `fktRelationProductTargetProduct2` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17069 DEFAULT CHARSET=utf8 COMMENT='연관상품별대상상품정보';

CREATE TABLE `tRelationProductTargetTerms` (
  `nRelationProductTargetTermsSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '연관상품별대상조건고유번호 ',
  `nRelationProductSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '연관상품고유번호',
  `nDisplayServiceType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출서비스구분 1:전체, 2:상품블로그, 3:잉크찾기..',
  `sDisplayYN` char(1) NOT NULL DEFAULT '1' COMMENT '노출여부 Y:노출, N:숨김..',
  `nTargetCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '대상카테고리고유번호',
  `nTermsType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '조건유형구분 1.제조사 2.브랜드 3.검색옵션 4. 검색옵션상세값, 5.가격...',
  `sTermsTypeValue` varchar(50) NOT NULL DEFAULT '' COMMENT '조건유형별 상세고유값 (가격범위 지정가능)',
  PRIMARY KEY (`nRelationProductTargetTermsSeq`),
  KEY `fktRelationProductTargetTerms1` (`nRelationProductSeq`),
  CONSTRAINT `fktRelationProductTargetTerms1` FOREIGN KEY (`nRelationProductSeq`) REFERENCES `tRelationProduct` (`nRelationProductSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='연관상품별대상조건정보';

CREATE TABLE `tSamsungProduct` (
  `sModelCode` varchar(50) NOT NULL DEFAULT '' COMMENT '모델코드',
  `sModelName` varchar(200) NOT NULL DEFAULT '' COMMENT '모델명',
  `sModelUrl` varchar(2048) NOT NULL DEFAULT '' COMMENT '모델URL',
  `sImgUrl` varchar(2048) NOT NULL DEFAULT '' COMMENT '이미지URL',
  `sLinkYN` char(1) NOT NULL DEFAULT 'N' COMMENT '링크여부 Y:링크됨, N:링크안됨',
  PRIMARY KEY (`sModelCode`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='삼성제휴상품정보';

CREATE TABLE `tSamsungProductMappingRequest` (
  `nSamsungProductMappingRequestSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '삼성제휴상품매핑요청고유번호',
  `sCompanyCode` varchar(6) NOT NULL DEFAULT '' COMMENT '제휴사업체코드',
  `sMappingRequestType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '매핑요청구분 1:뉴스, 2:쇼핑후기..',
  `sRequestProductName` varchar(50) NOT NULL DEFAULT '' COMMENT '요청제품명',
  `sLinkURL` varchar(200) DEFAULT '' COMMENT '상품링크 주소',
  `sRequestContent` varchar(200) DEFAULT '' COMMENT '요청내용',
  `dtInputDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '요청일자',
  PRIMARY KEY (`nSamsungProductMappingRequestSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=523 DEFAULT CHARSET=euckr COMMENT='삼성제휴상품매핑요청정보';

CREATE TABLE `tSearchLinkProductDisplay` (
  `nProductSeq` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '다나와상품고유번호',
  `sCompanyCode` varchar(6) NOT NULL DEFAULT '' COMMENT '협력사고유번호',
  `sLinkProdC` varchar(100) NOT NULL DEFAULT '' COMMENT '협력사상품고유번호',
  `sSearchLinkDisplayType` char(1) NOT NULL DEFAULT 'A' COMMENT '검색링크노출구분 A:링크추가, E:링크제외..',
  PRIMARY KEY (`nProductSeq`,`sLinkProdC`,`sCompanyCode`),
  CONSTRAINT `fkSearchLinkProductDisplay1` FOREIGN KEY (`nProductSeq`) REFERENCES `tprod` (`prod_c`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='검색링크 업체상품 노출 설정정보';

CREATE TABLE `tSellerAddonPriceLinkAnswerContent` (
  `nQuestionListSeq` int(10) unsigned NOT NULL COMMENT '가격갱신 요청 문의 고유키',
  `sContent` text COMMENT '내용',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '답변날짜',
  PRIMARY KEY (`nQuestionListSeq`),
  CONSTRAINT `fkSellerAddon3` FOREIGN KEY (`nQuestionListSeq`) REFERENCES `tSellerAddonPriceLinkQuestionList` (`nQuestionListSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 답변 내용';

CREATE TABLE `tSellerAddonPriceLinkDemandList` (
  `nDemandListSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가격갱신 요청 고유키',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원 고유키',
  `sMarketCode` varchar(10) DEFAULT '' COMMENT '마켓 코드',
  `sSellerID` varchar(15) DEFAULT '' COMMENT '셀러 아이디',
  `sLinkURL` varchar(200) DEFAULT '' COMMENT '상품링크 주소',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '요청날짜',
  `nState` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '진행상태(1:접수, 2:링크, 3:반려)',
  PRIMARY KEY (`nDemandListSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=1362 DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 요청 리스트';

CREATE TABLE `tSellerAddonPriceLinkNoticedContent` (
  `nNoticeListSeq` int(10) unsigned NOT NULL COMMENT '가격갱신 요청 공지사항 고유키',
  `sContent` text COMMENT '내용',
  PRIMARY KEY (`nNoticeListSeq`),
  CONSTRAINT `fkSellerAddon1` FOREIGN KEY (`nNoticeListSeq`) REFERENCES `tSellerAddonPriceLinkNoticedList` (`nNoticeListSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 공지사항 내용';

CREATE TABLE `tSellerAddonPriceLinkNoticedList` (
  `nNoticeListSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가격갱신 요청 공지사항 고유키',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원 고유키',
  `sTitle` varchar(100) NOT NULL DEFAULT '' COMMENT '제목',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록날짜',
  PRIMARY KEY (`nNoticeListSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 공지사항 리스트';

CREATE TABLE `tSellerAddonPriceLinkQuestionContent` (
  `nQuestionListSeq` int(10) unsigned NOT NULL COMMENT '가격갱신 요청 문의 고유키',
  `sContent` text COMMENT '내용',
  PRIMARY KEY (`nQuestionListSeq`),
  CONSTRAINT `fkSellerAddon2` FOREIGN KEY (`nQuestionListSeq`) REFERENCES `tSellerAddonPriceLinkQuestionList` (`nQuestionListSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 문의 내용';

CREATE TABLE `tSellerAddonPriceLinkQuestionList` (
  `nQuestionListSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가격갱신 요청 문의 고유키',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원 고유키',
  `nSection` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '섹션 구분(1:오류, 2:문의, 3:내용)',
  `sTitle` varchar(100) NOT NULL DEFAULT '' COMMENT '제목',
  `sAnswerYN` char(1) NOT NULL DEFAULT 'N' COMMENT '답변여부(Y:답변, N:없음)',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록날짜',
  PRIMARY KEY (`nQuestionListSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 문의 리스트';

CREATE TABLE `tSellerAddonPriceLinkUpdateList` (
  `nUpdateListSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가격갱신 고유키',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원 고유키',
  `sMarketCode` varchar(10) DEFAULT '' COMMENT '마켓 코드',
  `sSellerID` varchar(15) DEFAULT '' COMMENT '셀러 아이디',
  `nUpdateTime` tinyint(4) NOT NULL DEFAULT '0' COMMENT '갱신 요청 시간',
  `dtCreateDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록날짜',
  PRIMARY KEY (`nUpdateListSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 COMMENT='판매자 가격갱신 정보 리스트';

CREATE TABLE `tServiceAttributeDisplay` (
  `nServiceAttributeDisplaySeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '서비스별속성노출정보고유코드',
  `nAttributeSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '속성고유코드',
  `nServiceType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '서비스구분 1:연동몰, 2:바로PC..',
  `nDisplaySort` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '노출순서',
  PRIMARY KEY (`nServiceAttributeDisplaySeq`),
  UNIQUE KEY `idxUMnAttributeService` (`nAttributeSeq`,`nServiceType`),
  CONSTRAINT `fkAttribute9` FOREIGN KEY (`nAttributeSeq`) REFERENCES `tAttribute` (`nAttributeSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1305 DEFAULT CHARSET=euckr COMMENT='서비스별 속성노출정보';

CREATE TABLE `tTaxBillMailing` (
  `nTaxBillMailingSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유키',
  `nTaxBillManagerSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '메일발송 담당자 고유키',
  `sCompanyCode` varchar(20) NOT NULL DEFAULT '' COMMENT '업체고유키',
  `nTaxBillSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '세금계산서 고유키',
  `sEmailList` varchar(200) NOT NULL DEFAULT '' COMMENT '발송 Email 목록',
  `sSubject` varchar(150) NOT NULL DEFAULT '' COMMENT '발송제목',
  `sAddContent` text COMMENT '추가 입력사항',
  `dtInputDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '발송일자',
  `nSendMailType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '메일발송유형 1:수동발송, 2:자동미입금메일발송...',
  `nDmailSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'blog-master.dmail 관련 고유번호',
  PRIMARY KEY (`nTaxBillMailingSeq`),
  KEY `fkTaxBill2` (`nTaxBillManagerSeq`),
  KEY `idxMCompanyCode` (`sCompanyCode`,`nTaxBillSeq`),
  CONSTRAINT `fkTaxBill2` FOREIGN KEY (`nTaxBillManagerSeq`) REFERENCES `tTaxBillManager` (`nTaxBillManagerSeq`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11505 DEFAULT CHARSET=euckr COMMENT='세금계산서 메일 발송내역 테이블';

CREATE TABLE `tTaxBillManager` (
  `nTaxBillManagerSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유키',
  `nDepartmentCode` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '본부고유키',
  `nTeamCode` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '팀고유키',
  `sManagerName` varchar(50) NOT NULL DEFAULT '' COMMENT '담당자명',
  `sManagerID` varchar(20) NOT NULL DEFAULT '' COMMENT '담당자ID',
  `sPhoneNumber` varchar(15) NOT NULL DEFAULT '' COMMENT '연락처',
  `sExtNumber` varchar(10) DEFAULT NULL COMMENT '내선',
  `sEmail` varchar(50) NOT NULL DEFAULT '' COMMENT '이메일',
  `dtInputDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '입력일자',
  `dtUpdateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '수정일자',
  `sUsedYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용여부 Y:사용, N:미사용',
  PRIMARY KEY (`nTaxBillManagerSeq`),
  UNIQUE KEY `idxUMnDepartmentCode` (`nDepartmentCode`,`nTeamCode`,`sManagerID`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=euckr COMMENT='다나와 세금계산서 담당자 정보 테이블';

CREATE TABLE `tTaxTypeDetail` (
  `nTaxTypeDetailSeq` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '상세품목 고유번호',
  `sTaxTypeDetailName` varchar(40) DEFAULT NULL COMMENT '상세 품목 명',
  PRIMARY KEY (`nTaxTypeDetailSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='품목 상세 정보';

CREATE TABLE `tTaxTypeDetailRelation` (
  `nRelationSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '품목 상세 정보 연결 고유번호',
  `nTaxTypeSeq` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '품목 고유번호',
  `nTaxTypeDetailSeq` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '상세품목 고유번호',
  PRIMARY KEY (`nRelationSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='품목 과 품목 상세 정보 연결 정보';

CREATE TABLE `tVirtualAccount` (
  `nVirtualAccountSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가상계좌고유번호',
  `sVirtualAccountNumber` varchar(20) NOT NULL DEFAULT '' COMMENT '계좌번호',
  `emTypeYN` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '사용여부',
  PRIMARY KEY (`nVirtualAccountSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=euckr COMMENT='가상계좌 저장 테이블';

CREATE TABLE `tVirtualAccountCmpny` (
  `sCmpnyCode` varchar(6) NOT NULL DEFAULT '' COMMENT '업체코드',
  `nVirtualAccountSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가상계좌고유번호',
  PRIMARY KEY (`sCmpnyCode`),
  KEY `fkVirtualAccountCmpny` (`nVirtualAccountSeq`),
  CONSTRAINT `fkVirtualAccountCmpny` FOREIGN KEY (`nVirtualAccountSeq`) REFERENCES `tVirtualAccount` (`nVirtualAccountSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='가상계좌번호와 업체 매핑 정보 테이블';

CREATE TABLE `tVirtualCategoryList` (
  `nVirtualCategoryListSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '가상카테고리정보고유번호',
  `nVirtualCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가상카테고리고유번호',
  `nSourceCategorySeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '원본카테고리고유번호',
  `nVirtualType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '가상링크유형 1:카테고리, 2:속성값, 3:제조사, 4:브랜드',
  `nVirtualTypeSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '가상링크유형별고유코드(카테고리고유코드,속성값고유코드, 제조사코드, 브랜드코드 등)',
  PRIMARY KEY (`nVirtualCategoryListSeq`),
  KEY `idxMVirtualCategory` (`nVirtualCategorySeq`,`nSourceCategorySeq`),
  CONSTRAINT `fkAttribute2` FOREIGN KEY (`nVirtualCategorySeq`) REFERENCES `tcate` (`cate_c`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=153313 DEFAULT CHARSET=euckr COMMENT='가상카테고리정보';

CREATE TABLE `tWeddingCategory` (
  `nWeddingCategorySeq` smallint(5) unsigned NOT NULL AUTO_INCREMENT COMMENT '혼수 카테고리 고유키',
  `sCategoryName` varchar(30) NOT NULL DEFAULT '' COMMENT '카테고리 이름',
  `sDisplayYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '노출여부 (Y:노출, N:미노출)',
  `nSort` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '정렬순서',
  `sImageURL` varchar(100) NOT NULL DEFAULT '' COMMENT '이미지 주소',
  `sLinkURL` varchar(100) NOT NULL DEFAULT '' COMMENT '링크주소',
  PRIMARY KEY (`nWeddingCategorySeq`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='혼수 카테고리';

CREATE TABLE `tWeddingEstimation` (
  `nWeddingEstimationSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '견적서 고유키',
  `nWeddingCategorySeq` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '혼수 카테고리 고유키',
  `nWeddingEstimateListSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '견적리스트 고유키',
  `nGoodsSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '상품코드',
  `nGiftType` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '선물여부(1:내가구매, 2:선물받을것)',
  `dtCreate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  PRIMARY KEY (`nWeddingEstimationSeq`),
  KEY `fkWedding1` (`nWeddingCategorySeq`),
  KEY `fkWedding2` (`nWeddingEstimateListSeq`),
  CONSTRAINT `fkWedding1` FOREIGN KEY (`nWeddingCategorySeq`) REFERENCES `tWeddingCategory` (`nWeddingCategorySeq`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkWedding2` FOREIGN KEY (`nWeddingEstimateListSeq`) REFERENCES `tWeddingEstimationList` (`nWeddingEstimateListSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16653 DEFAULT CHARSET=utf8 COMMENT='혼수 견적내용';

CREATE TABLE `tWeddingEstimationList` (
  `nWeddingEstimateListSeq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '견적리스트 고유키',
  `sTitle` varchar(20) NOT NULL DEFAULT '' COMMENT '견적제목',
  `nMemberSeq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '회원고유키',
  `dtCreate` date NOT NULL DEFAULT '0000-00-00' COMMENT '등록일',
  PRIMARY KEY (`nWeddingEstimateListSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=17145 DEFAULT CHARSET=utf8 COMMENT='혼수 견적리스트';

CREATE TABLE `tZipCodeBusan` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='부산광역시';

CREATE TABLE `tZipCodeChungbuk` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='충청북도';

CREATE TABLE `tZipCodeChungnam` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='충청남도';

CREATE TABLE `tZipCodeDaegu` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='대구광역시';

CREATE TABLE `tZipCodeDaejeon` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='대전광역시';

CREATE TABLE `tZipCodeGangwon` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='강원도';

CREATE TABLE `tZipCodeGwangju` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='광주광역시';

CREATE TABLE `tZipCodeGyeongbuk` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='경상북도';

CREATE TABLE `tZipCodeGyeonggi` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='경기도';

CREATE TABLE `tZipCodeGyeongnam` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='경상남도';

CREATE TABLE `tZipCodeIncheon` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='인천광역시';

CREATE TABLE `tZipCodeJeju` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='제주특별자치도';

CREATE TABLE `tZipCodeJeonbuk` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='전라북도';

CREATE TABLE `tZipCodeJeonnam` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='전라남도';

CREATE TABLE `tZipCodeSejong` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='세종특별시';

CREATE TABLE `tZipCodeSeoul` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='서울특별시';

CREATE TABLE `tZipCodeUlsan` (
  `sSido` varchar(20) NOT NULL COMMENT '시,도',
  `sGugun` varchar(20) NOT NULL COMMENT '시,군,구',
  `sStreet` varchar(80) NOT NULL COMMENT '도로명',
  `nBuildingnum1` smallint(5) NOT NULL COMMENT '건물번호본번',
  `nBuildingnum2` smallint(5) NOT NULL COMMENT '건물번호부번',
  `sBuilding` varchar(40) DEFAULT NULL COMMENT '다량배달처명(건물명)',
  `sBubdong` varchar(20) DEFAULT NULL COMMENT '법정동명',
  `sZipcode` varchar(6) NOT NULL COMMENT '우편번호',
  KEY `idxMsSido` (`sSido`,`sGugun`,`sStreet`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='울산광역시';

CREATE TABLE `tas_cate` (
  `as_c` mediumint(9) NOT NULL DEFAULT '0',
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`as_c`,`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tas_desc` (
  `as_c` mediumint(9) NOT NULL AUTO_INCREMENT,
  `as_n` varchar(255) NOT NULL DEFAULT '',
  `post_n` varchar(7) NOT NULL DEFAULT '',
  `addr1_m` varchar(255) NOT NULL DEFAULT '',
  `addr2_m` varchar(255) NOT NULL DEFAULT '',
  `local_c1` smallint(6) NOT NULL DEFAULT '0',
  `local_c2` smallint(6) NOT NULL DEFAULT '0',
  `local_c3` smallint(6) NOT NULL DEFAULT '0',
  `phone_n` varchar(255) NOT NULL DEFAULT '',
  `url_m` varchar(255) NOT NULL DEFAULT '',
  `open_time_m` varchar(255) NOT NULL DEFAULT '',
  `email_n` varchar(255) NOT NULL DEFAULT '',
  `handling_prod_m` varchar(255) NOT NULL DEFAULT '',
  `etc_m` varchar(255) NOT NULL DEFAULT '',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modify_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`as_c`)
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=euckr;

CREATE TABLE `tas_opt` (
  `as_c` mediumint(9) NOT NULL DEFAULT '0',
  `type` enum('maker','brand','circul') NOT NULL DEFAULT 'maker',
  `data_c` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '제조사고유키, 브랜드고유키, 유통사고유키',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`as_c`,`type`,`data_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tbiz_ad_basket` (
  `number` int(10) NOT NULL AUTO_INCREMENT,
  `tempid` varchar(32) NOT NULL DEFAULT '',
  `sort_c` enum('C','P','L','N','M') NOT NULL,
  `id` varchar(20) DEFAULT NULL,
  `kind_c` varchar(6) NOT NULL DEFAULT '',
  `type_c` varchar(30) NOT NULL DEFAULT '',
  `showCate1` smallint(5) unsigned DEFAULT NULL,
  `showCate2` smallint(5) unsigned DEFAULT NULL,
  `el_cate_c1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c4` smallint(5) unsigned NOT NULL DEFAULT '0',
  `seq_c` smallint(5) unsigned DEFAULT NULL,
  `kikan` char(1) DEFAULT NULL,
  `price` int(10) unsigned DEFAULT NULL,
  `type_tit` varchar(40) DEFAULT NULL,
  `prod_c` varchar(30) NOT NULL DEFAULT '',
  `prod_n` tinytext,
  `el_cate_n` varchar(100) NOT NULL DEFAULT '',
  `el_prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `el_prod_n` tinytext NOT NULL,
  `maker_c` smallint(5) unsigned DEFAULT NULL,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cmpny_n` tinytext,
  `cmpny_c` varchar(6) DEFAULT NULL,
  `adtel_no` tinytext,
  `domain_n` tinytext,
  `domain_k` char(1) DEFAULT NULL,
  `desc_m` tinytext,
  `ordernum` varchar(40) DEFAULT NULL,
  `okdate` date DEFAULT NULL,
  `guide1` tinytext,
  `guide1_check` char(1) DEFAULT NULL,
  `guide1_link` tinytext,
  `guide2` tinytext,
  `guide2_check` char(1) DEFAULT NULL,
  `guide2_link` tinytext,
  `sAppStatus` char(1) NOT NULL DEFAULT 'W' COMMENT '결제상태 W:대기, A:완료',
  PRIMARY KEY (`number`),
  KEY `tempid` (`tempid`),
  KEY `kind_c` (`kind_c`),
  KEY `type_c` (`type_c`),
  KEY `seq_c` (`seq_c`),
  KEY `input_d` (`input_d`),
  KEY `ordernum` (`ordernum`),
  KEY `sort_c` (`sort_c`)
) ENGINE=InnoDB AUTO_INCREMENT=15227 DEFAULT CHARSET=euckr;

CREATE TABLE `tbiz_ad_main` (
  `no` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `sort_c` enum('C','P','L','N','M') NOT NULL,
  `kind_c` varchar(6) NOT NULL DEFAULT '',
  `type_c` varchar(30) NOT NULL DEFAULT '',
  `showCate1` smallint(5) unsigned DEFAULT NULL,
  `showCate2` smallint(5) unsigned DEFAULT NULL,
  `el_cate_c1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c4` smallint(5) unsigned NOT NULL DEFAULT '0',
  `seq_c` smallint(5) unsigned DEFAULT NULL,
  `fr_d` date NOT NULL DEFAULT '0000-00-00',
  `to_d` date NOT NULL DEFAULT '0000-00-00',
  `kikan` char(1) DEFAULT NULL,
  `price` int(10) unsigned DEFAULT NULL,
  `type_tit` varchar(40) DEFAULT NULL,
  `prod_c` varchar(30) DEFAULT NULL,
  `prod_n` tinytext,
  `el_cate_n` varchar(100) NOT NULL DEFAULT '',
  `el_prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `el_prod_n` tinytext NOT NULL,
  `maker_c` smallint(5) unsigned DEFAULT NULL,
  `id` varchar(20) DEFAULT NULL,
  `tempid` varchar(32) DEFAULT NULL,
  `ordernum` varchar(40) DEFAULT NULL,
  `cmpny_n` tinytext,
  `cmpny_c` varchar(6) DEFAULT NULL,
  `adtel_no` tinytext,
  `domain_n` tinytext,
  `domain_k` char(1) DEFAULT NULL,
  `desc_m` tinytext,
  `approval_c` char(1) DEFAULT 'A',
  `bigo` tinytext,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `deltxt` tinytext,
  `delchk` char(1) DEFAULT '',
  `deldate` datetime DEFAULT NULL,
  `guide1` tinytext,
  `guide1_check` char(1) DEFAULT NULL,
  `guide1_link` tinytext,
  `guide2` tinytext,
  `guide2_check` char(1) DEFAULT NULL,
  `guide2_link` tinytext,
  `biz_prod_type` char(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`no`),
  KEY `kind_c` (`kind_c`),
  KEY `type_c` (`type_c`),
  KEY `seq_c` (`seq_c`),
  KEY `tempid` (`tempid`),
  KEY `fr_d` (`fr_d`),
  KEY `to_d` (`to_d`),
  KEY `id` (`id`),
  KEY `ordernum` (`ordernum`),
  KEY `prod_c` (`prod_c`),
  KEY `delchk` (`delchk`),
  KEY `key_el_cate_c` (`el_cate_c1`,`el_cate_c2`),
  KEY `el_prod_c` (`el_prod_c`),
  KEY `key_sort_c` (`sort_c`,`el_cate_c1`,`el_cate_c2`),
  KEY `key_sort_c_2` (`sort_c`,`el_prod_c`)
) ENGINE=InnoDB AUTO_INCREMENT=6385 DEFAULT CHARSET=euckr;

CREATE TABLE `tbiz_ad_price` (
  `kind_c` varchar(6) NOT NULL DEFAULT '',
  `type_c` varchar(30) NOT NULL DEFAULT '',
  `el_cate_c1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c4` smallint(5) unsigned NOT NULL DEFAULT '0',
  `type_tit` varchar(50) DEFAULT NULL,
  `el_cate_n` varchar(100) NOT NULL DEFAULT '',
  `seq_c` smallint(5) unsigned NOT NULL DEFAULT '0',
  `price_q` int(10) DEFAULT NULL,
  `bigo` tinytext,
  `sort_c` enum('C','P','L','N','M') NOT NULL,
  KEY `kind_c` (`kind_c`,`type_c`,`seq_c`),
  KEY `el_cate_c1` (`el_cate_c1`,`el_cate_c2`,`seq_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tbiz_ad_settlement` (
  `ordernum` varchar(40) NOT NULL DEFAULT '',
  `tempid` varchar(32) NOT NULL DEFAULT '',
  `sort_c` enum('C','P','L','N','M') NOT NULL,
  `id` varchar(20) DEFAULT NULL,
  `card_authno` varchar(50) DEFAULT NULL,
  `settlement_price` int(11) DEFAULT NULL,
  `settlement_date` datetime DEFAULT NULL,
  `write_d` date DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`ordernum`,`sort_c`),
  KEY `tempid` (`tempid`),
  KEY `id` (`id`),
  KEY `sort_c` (`sort_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tboard` (
  `cmpny_c` varchar(20) NOT NULL,
  `board_seq` smallint(5) unsigned NOT NULL,
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `order_idx` mediumint(8) unsigned NOT NULL DEFAULT '16777215',
  `type_c` enum('T','H','B','E','C') NOT NULL DEFAULT 'T',
  `name_n` varchar(30) NOT NULL,
  `title_n` varchar(255) NOT NULL,
  `file_n` varchar(255) DEFAULT NULL,
  `content_m` text,
  `ip_n` varchar(15) NOT NULL,
  `member_id` varchar(15) DEFAULT NULL,
  `email_n` varchar(50) DEFAULT NULL,
  `pass_n` varchar(12) NOT NULL,
  `home_n` varchar(50) DEFAULT NULL,
  `secret_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `view_n` smallint(5) unsigned NOT NULL DEFAULT '0',
  `divide_n` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `date_d` datetime NOT NULL,
  `comment_n` smallint(5) unsigned NOT NULL DEFAULT '0',
  `notice_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`seq`),
  KEY `cmpny_c` (`cmpny_c`,`board_seq`),
  KEY `order_idx` (`order_idx`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=euckr COMMENT='게시판 데이터';

CREATE TABLE `tboard_comment` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `tboard_seq` int(10) unsigned NOT NULL,
  `pass_n` varchar(12) NOT NULL,
  `date_d` datetime NOT NULL,
  `member_id` varchar(15) DEFAULT NULL,
  `name_n` varchar(30) NOT NULL,
  `content_m` text,
  `ip_m` varchar(15) NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `tboard_seq` (`tboard_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='게시판 코멘트';

CREATE TABLE `tboard_config` (
  `cmpny_c` varchar(20) NOT NULL,
  `board_seq` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `name_n` varchar(50) NOT NULL,
  `skin_c` varchar(20) NOT NULL DEFAULT '기본형A',
  `denied_ip_m` text,
  `denied_word_m` text,
  `top_text_m` text,
  `bottom_text_m` text,
  `top_include_n` varchar(255) DEFAULT NULL,
  `bottom_include_n` varchar(255) DEFAULT NULL,
  `list_per_page_n` tinyint(3) unsigned NOT NULL DEFAULT '20',
  `page_per_screen_n` tinyint(3) unsigned NOT NULL DEFAULT '10',
  `title_length_n` tinyint(3) unsigned NOT NULL DEFAULT '80',
  `name_length_n` tinyint(3) unsigned NOT NULL DEFAULT '10',
  `new_article_period_n` tinyint(3) unsigned NOT NULL DEFAULT '3',
  `allow_list_c` set('A','M','G') NOT NULL DEFAULT 'G',
  `allow_form_c` set('A','M','G') NOT NULL DEFAULT 'G',
  `allow_view_c` set('A','M','G') NOT NULL DEFAULT 'G',
  `use_comment_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `allow_comment_c` set('A','M','G') NOT NULL DEFAULT 'G',
  `use_auto_link_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `use_divide_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `divide_list_n` varchar(255) DEFAULT NULL,
  `use_recommend_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `use_pds_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `denied_ext_n` varchar(255) DEFAULT NULL,
  `denied_filesize_n` int(10) unsigned DEFAULT NULL,
  `use_thumb_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `thumb_width_n` tinyint(3) unsigned DEFAULT NULL,
  `thumb_height_n` tinyint(3) unsigned DEFAULT NULL,
  `force_pds_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `use_secret_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `list_in_view_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `delete_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `iboard_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `iboard_limit_n` tinyint(3) unsigned NOT NULL DEFAULT '5',
  `iboard_length_n` tinyint(3) unsigned NOT NULL DEFAULT '25',
  PRIMARY KEY (`cmpny_c`,`board_seq`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='게시판 설정';

CREATE TABLE `tcate` (
  `cate_c` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `nGroupSeq` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `pcate_c` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_n` varchar(100) NOT NULL DEFAULT '',
  `cate_m` varchar(255) DEFAULT NULL,
  `depth` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `disp_seq` tinyint(3) unsigned DEFAULT NULL,
  `disp_yn` char(1) NOT NULL DEFAULT 'N',
  `box_size` enum('A','B') DEFAULT NULL,
  `box_ratio` smallint(5) unsigned DEFAULT NULL,
  `optPrice_m` varchar(100) DEFAULT NULL,
  `market_disp_yn` char(1) NOT NULL DEFAULT 'Y',
  `market_disp_seq` tinyint(3) unsigned DEFAULT NULL,
  `input_d` datetime NOT NULL,
  `modify_d` datetime NOT NULL,
  `virtual_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`cate_c`),
  KEY `tcate_pcate_c_idx` (`pcate_c`)
) ENGINE=InnoDB AUTO_INCREMENT=44981 DEFAULT CHARSET=euckr;

CREATE TABLE `tcate_brand` (
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `brand_c` int(10) unsigned NOT NULL DEFAULT '0',
  `brand_n` varchar(50) NOT NULL,
  `maker_c` int(10) unsigned NOT NULL DEFAULT '0',
  `view_seq` tinyint(3) NOT NULL,
  `disp_yn` enum('Y','N') NOT NULL,
  `admin_id` varchar(20) NOT NULL,
  `input_d` datetime NOT NULL,
  `modify_d` datetime NOT NULL,
  PRIMARY KEY (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`,`brand_c`),
  KEY `tcate_maker_cate_c2_idx` (`cate_c2`),
  KEY `tcate_maker_cate_c3_idx` (`cate_c3`),
  KEY `tcate_maker_cate_c4_idx` (`cate_c4`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcate_maker` (
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `maker_c` int(10) unsigned NOT NULL DEFAULT '0',
  `maker_n` varchar(50) NOT NULL,
  `view_seq` tinyint(3) NOT NULL,
  `disp_yn` enum('Y','N') NOT NULL,
  `admin_id` varchar(20) NOT NULL,
  `input_d` datetime NOT NULL,
  `modify_d` datetime NOT NULL,
  PRIMARY KEY (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`,`maker_c`),
  KEY `tcate_maker_cate_c2_idx` (`cate_c2`),
  KEY `tcate_maker_cate_c3_idx` (`cate_c3`),
  KEY `tcate_maker_cate_c4_idx` (`cate_c4`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcmpny` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `pass_c` varchar(6) NOT NULL DEFAULT '',
  `uclick_id_n` varchar(15) DEFAULT NULL,
  `cmpny_n` tinytext,
  `shop_n` tinytext,
  `owner_n` tinytext,
  `sec_mgr_n` tinytext,
  `counsel_n` tinytext,
  `email_n` tinytext,
  `domain_n` tinytext,
  `telemarket_n` tinytext,
  `phone_n` tinytext,
  `cphone_n` tinytext,
  `fax_n` tinytext,
  `post_n` tinytext,
  `addr1_m` tinytext,
  `addr2_m` tinytext,
  `sell_type_m` tinytext,
  `deliver_type_m` tinytext,
  `outer_url_m` tinytext,
  `open_time_m` tinytext,
  `off_day_m` tinytext,
  `smp_cmnt_m` tinytext,
  `dtl_cmnt_m` mediumtext,
  `tm_key` varchar(50) DEFAULT NULL,
  `input_d` datetime DEFAULT NULL,
  `gaib_d` date DEFAULT NULL,
  `fee_type` char(1) DEFAULT NULL,
  `mall_type` char(1) DEFAULT '1',
  `mall_cate` varchar(100) DEFAULT NULL,
  `mall_view_type` enum('R','S','T') NOT NULL DEFAULT 'R',
  `sell_type` char(1) DEFAULT NULL,
  `deliver_type` char(1) DEFAULT NULL,
  `vat_yn` char(1) DEFAULT 'N',
  `card_yn` char(1) DEFAULT 'N',
  `minab_yn` char(1) DEFAULT 'N',
  `uclick_minab_yn` char(1) DEFAULT 'Z',
  `escrow_yn` char(1) DEFAULT 'N',
  `escrow_direct_yn` char(1) DEFAULT 'N',
  `escrow_card_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `rgn_type` char(1) DEFAULT NULL,
  `loc_c` smallint(4) unsigned NOT NULL DEFAULT '0',
  `pr_point_q` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `pr_coupon_m` varchar(100) DEFAULT NULL,
  `pr_coupon_url_m` tinytext,
  `pr_nointerest_m` varchar(100) DEFAULT NULL,
  `pr_gift_m` varchar(100) DEFAULT NULL,
  `pr_desc_m` tinytext,
  `bank_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `uclick_yn` enum('Y','N','Z') NOT NULL DEFAULT 'N',
  `uclick_direct_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `uclick_card_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `escrow_id_n` varchar(20) DEFAULT NULL,
  `card_cmpny_n` varchar(30) DEFAULT NULL,
  `card_id_n` varchar(20) DEFAULT NULL,
  `prepare_yn` enum('Y','N','Z') NOT NULL DEFAULT 'Y',
  `counsel_phone_n` tinytext,
  `inner_mgr_n` tinytext,
  `inner_mgr_phone_n` tinytext,
  `inner_mgr_email_n` tinytext,
  `price_view_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `price_view_type` enum('A','B') NOT NULL DEFAULT 'B',
  `pc_cmpny_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `stdpc_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `fm_use_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `sponsor_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `gaibbi_yn` enum('Y','N') DEFAULT NULL,
  `manage_cost_q` int(10) unsigned DEFAULT NULL,
  `affil_id_n` varchar(20) DEFAULT NULL,
  `safe_trade_type` enum('전자보증','에스크로','보증일반','자체보호') NOT NULL DEFAULT '자체보호',
  `mall_propensity` set('p1','p2','p3','p4','p5','p6','p7','p8','p9','p10','d1','d2','e1','e2','e3','e4','e5','X') DEFAULT 'X',
  `nForceOutType` tinyint(3) NOT NULL DEFAULT '0' COMMENT '강제퇴점구분 0:일반, 1:강제퇴점예정, 3:강제퇴점완료',
  `dtForceOutDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '강제퇴점예정일',
  `nGrade` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '협력사등급 1:A+, 2:A, 3:B, 4:C, 5:D, 6:F, 7:F-',
  PRIMARY KEY (`cmpny_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcmpny_deliver` (
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `post_q` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modify_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`cmpny_c`,`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcmpny_prod` (
  `prod_c` mediumint(8) unsigned NOT NULL,
  `cmpny_c` varchar(6) NOT NULL,
  `link_prod_c` varchar(100) NOT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `manual_link_yn` enum('Y','N') DEFAULT 'N' COMMENT '색인제외여부',
  `search_way` char(1) DEFAULT '2',
  `search_prod_n` varchar(255) DEFAULT NULL,
  `keyword_n` varchar(255) DEFAULT NULL,
  `prod_status` enum('N','O','S') DEFAULT 'N',
  `kprice_s` int(10) unsigned DEFAULT '0',
  `kprice_e` int(10) unsigned DEFAULT '0',
  `nSearchUseCategoryDepth` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '검색 사용 카테고리 지정 범위값 0:무제한, 1:대, 2:중, 3:소, 4:세..',
  PRIMARY KEY (`prod_c`,`cmpny_c`),
  KEY `prod_cmpny_linkprod_index` (`prod_c`,`cmpny_c`,`link_prod_c`),
  KEY `cmpny_linkprod_index` (`cmpny_c`,`link_prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcmpny_tax` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `cmpny_n` tinytext,
  `owner_n` tinytext,
  `resno` tinytext,
  `eobtae` tinytext,
  `jongmok` tinytext,
  `tax_post_n` tinytext,
  `tax_addr1_m` tinytext,
  `tax_addr2_m` tinytext,
  `rcv_post_n` tinytext,
  `rcv_addr1_m` tinytext,
  `rcv_addr2_m` tinytext,
  `email_n` tinytext,
  `cmnt_m` text,
  `inner_cmnt_m` text,
  `nCompanyType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '기업분류 (0: 미분류, 1: 대기업, 2: 중소 용산기업)',
  PRIMARY KEY (`cmpny_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcmpny_trash` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `pass_c` varchar(6) NOT NULL DEFAULT '',
  `uclick_id_n` varchar(15) DEFAULT NULL,
  `cmpny_n` tinytext,
  `shop_n` tinytext,
  `owner_n` tinytext,
  `sec_mgr_n` tinytext,
  `counsel_n` tinytext,
  `email_n` tinytext,
  `domain_n` tinytext,
  `telemarket_n` tinytext,
  `phone_n` tinytext,
  `cphone_n` tinytext,
  `fax_n` tinytext,
  `post_n` tinytext,
  `addr1_m` tinytext,
  `addr2_m` tinytext,
  `sell_type_m` tinytext,
  `deliver_type_m` tinytext,
  `outer_url_m` tinytext,
  `open_time_m` tinytext,
  `off_day_m` tinytext,
  `smp_cmnt_m` tinytext,
  `dtl_cmnt_m` text,
  `tm_key` varchar(50) DEFAULT NULL,
  `input_d` datetime DEFAULT NULL,
  `gaib_d` date DEFAULT NULL,
  `fee_type` char(1) DEFAULT NULL,
  `mall_type` char(1) DEFAULT '1',
  `mall_view_type` enum('R','S','T') NOT NULL DEFAULT 'R',
  `sell_type` char(1) DEFAULT NULL,
  `deliver_type` char(1) DEFAULT NULL,
  `vat_yn` char(1) DEFAULT 'N',
  `card_yn` char(1) DEFAULT 'N',
  `minab_yn` char(1) DEFAULT 'N',
  `escrow_yn` char(1) DEFAULT 'N',
  `escrow_direct_yn` char(1) DEFAULT 'N',
  `escrow_card_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `rgn_type` char(1) DEFAULT NULL,
  `pr_point_q` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `pr_coupon_m` varchar(100) DEFAULT NULL,
  `pr_coupon_url_m` tinytext,
  `pr_nointerest_m` varchar(100) DEFAULT NULL,
  `pr_gift_m` varchar(100) DEFAULT NULL,
  `pr_desc_m` tinytext,
  `bank_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `uclick_yn` enum('Y','N','Z') NOT NULL DEFAULT 'N',
  `uclick_direct_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `uclick_card_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `escrow_id_n` varchar(20) DEFAULT NULL,
  `card_cmpny_n` varchar(30) DEFAULT NULL,
  `card_id_n` varchar(20) DEFAULT NULL,
  `prepare_yn` enum('Y','N','Z') NOT NULL DEFAULT 'Y',
  `counsel_phone_n` tinytext,
  `inner_mgr_n` tinytext,
  `inner_mgr_phone_n` tinytext,
  `inner_mgr_email_n` tinytext,
  `price_view_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `price_view_type` enum('A','B') NOT NULL DEFAULT 'B',
  `pc_cmpny_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `stdpc_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `sponsor_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `gaibbi_yn` enum('Y','N') DEFAULT NULL,
  `resno` tinytext,
  `trash_in_d` datetime DEFAULT NULL,
  `loc_c` smallint(4) unsigned NOT NULL DEFAULT '0',
  `nForceOutType` tinyint(3) NOT NULL DEFAULT '0' COMMENT '강제퇴점구분 0:일반, 1:강제퇴점예정, 3:강제퇴점완료',
  `dtForceOutDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '강제퇴점예정일',
  PRIMARY KEY (`cmpny_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tcontents_maker` (
  `contents_seq` smallint(6) NOT NULL AUTO_INCREMENT,
  `contents_n` varchar(100) NOT NULL,
  `drm_key` varchar(8) NOT NULL DEFAULT '(danawa)',
  `offer_yn` enum('Y','N') NOT NULL,
  PRIMARY KEY (`contents_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=euckr;

CREATE TABLE `tcontents_member` (
  `member_id` varchar(20) NOT NULL,
  `input_date_d` date NOT NULL DEFAULT '0000-00-00',
  `expire_date_d` date NOT NULL DEFAULT '0000-00-00',
  `status_c` enum('Y','N') NOT NULL DEFAULT 'Y',
  `member_seq` int(10) unsigned NOT NULL,
  `domain_n` mediumtext,
  `reseller_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `copy_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `memo_m` mediumtext,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='컨텐츠 구매자';

CREATE TABLE `tcontents_member_prodlist` (
  `member_id` varchar(20) NOT NULL,
  `prod_c` int(10) unsigned NOT NULL,
  `link_date_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `link_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `view_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `memo_m` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`member_id`,`prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='컨텐츠 구매자 상품내역';

CREATE TABLE `test_price` (
  `nPriceSeq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `link_prod_c` varchar(100) NOT NULL DEFAULT '',
  `link_prod_n` varchar(255) DEFAULT NULL,
  `price_q` int(10) unsigned NOT NULL DEFAULT '0',
  `post_q` mediumint(8) unsigned NOT NULL DEFAULT '16777215',
  `cnt_q` smallint(5) unsigned NOT NULL DEFAULT '65535',
  `disp_type` char(1) DEFAULT NULL,
  `disp_seq` tinyint(3) unsigned DEFAULT NULL,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`nPriceSeq`),
  UNIQUE KEY `idxUMProdC` (`prod_c`,`link_prod_c`,`cmpny_c`),
  KEY `idxMProdC` (`prod_c`,`cmpny_c`),
  KEY `idxMLinkProdC` (`link_prod_c`,`cmpny_c`),
  KEY `idxCmpnyC` (`cmpny_c`),
  KEY `idxMInputD` (`input_d`,`cmpny_c`)
) ENGINE=InnoDB AUTO_INCREMENT=102036672 DEFAULT CHARSET=euckr;

CREATE TABLE `tevent_icon` (
  `seq` mediumint(8) NOT NULL AUTO_INCREMENT,
  `prod_c` mediumint(8) NOT NULL,
  `cate_c1` smallint(5) NOT NULL,
  `cate_c2` smallint(5) NOT NULL,
  `cate_c3` smallint(5) NOT NULL,
  `cate_c4` smallint(5) NOT NULL,
  `icon1_show_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `icon1_start_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon1_end_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon1_desc_m` varchar(255) NOT NULL DEFAULT '',
  `icon2_show_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `icon2_start_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon2_end_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon2_desc_m` varchar(255) NOT NULL DEFAULT '',
  `icon_class` varchar(32) NOT NULL DEFAULT '',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `site_c` tinyint(3) NOT NULL,
  `icon3_show_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `icon3_start_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon3_end_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon3_desc_m` varchar(255) NOT NULL DEFAULT '',
  `icon4_show_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `icon4_start_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon4_end_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `icon4_desc_m` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`seq`),
  KEY `tevent_icon_prod_c_idx` (`prod_c`)
) ENGINE=InnoDB AUTO_INCREMENT=7241 DEFAULT CHARSET=euckr;

CREATE TABLE `tline_ad_price` (
  `el_cate_c1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c4` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_n` varchar(100) NOT NULL DEFAULT '',
  `el_prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `el_prod_n` tinytext NOT NULL,
  `seq_c` smallint(5) unsigned NOT NULL DEFAULT '0',
  `price_q` int(10) DEFAULT NULL,
  `bigo` tinytext,
  KEY `el_cate_c1` (`el_cate_c1`,`el_cate_c2`,`seq_c`),
  KEY `el_prod_c` (`el_cate_c1`,`el_cate_c2`,`el_cate_c3`,`el_cate_c4`,`el_prod_c`,`seq_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tlocale` (
  `locale_c` smallint(5) unsigned NOT NULL DEFAULT '0',
  `plocale_c` smallint(5) unsigned DEFAULT NULL,
  `depth` smallint(5) unsigned DEFAULT NULL,
  `locale_n` varchar(255) NOT NULL,
  PRIMARY KEY (`locale_c`),
  KEY `locale_depth_idx` (`locale_c`,`depth`),
  KEY `locale_plocale_depth_idx` (`locale_c`,`plocale_c`,`depth`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='지역코드';

CREATE TABLE `tmanage_price_sync_concern` (
  `seq` smallint(5) NOT NULL DEFAULT '0',
  `mode` varchar(20) NOT NULL,
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cate_c1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cate_c2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cate_c3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `cate_c4` smallint(5) unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tmap` (
  `map_seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `map_c` varchar(255) NOT NULL DEFAULT '',
  `cmpny_n` varchar(255) NOT NULL DEFAULT '',
  `consul_n` varchar(20) DEFAULT '',
  `phone_n` varchar(255) DEFAULT '',
  `fax_n` varchar(255) DEFAULT '',
  `hp_n` varchar(255) DEFAULT '',
  `addr_n` varchar(255) DEFAULT '',
  `email_n` varchar(255) DEFAULT '',
  `url_m` varchar(255) DEFAULT '',
  `sale_type` varchar(255) DEFAULT '',
  `deliver_q` varchar(255) DEFAULT '',
  `open_time_m` varchar(255) DEFAULT '',
  `off_day_m` varchar(255) DEFAULT '',
  `img_n` varchar(255) DEFAULT '',
  `cmpny_c` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`map_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=5872 DEFAULT CHARSET=euckr;

CREATE TABLE `tmodel_map` (
  `dica_make` varchar(50) NOT NULL DEFAULT '',
  `dica_model` varchar(50) NOT NULL DEFAULT '',
  `seq_c` char(1) NOT NULL DEFAULT '',
  `prod_cmpny_n` varchar(30) DEFAULT NULL,
  `prod_c` varchar(30) DEFAULT NULL,
  `elprod_c` int(10) unsigned NOT NULL DEFAULT '0',
  `name_n` tinytext,
  PRIMARY KEY (`dica_make`,`dica_model`,`seq_c`),
  KEY `imodel_map_prod_c` (`prod_c`),
  KEY `elprod_c` (`elprod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tpkg_price` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `link_prod_c` varchar(100) DEFAULT NULL,
  `tprice_link` varchar(255) DEFAULT NULL,
  `prod_n` varchar(100) NOT NULL DEFAULT '',
  `price_q` int(10) unsigned NOT NULL DEFAULT '0',
  `cnt_q` smallint(5) unsigned NOT NULL DEFAULT '0',
  `disp_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `input_d` datetime DEFAULT '0000-00-00 00:00:00',
  `etc_m` varchar(255) DEFAULT NULL,
  `name_search_m` varchar(255) DEFAULT NULL,
  `code_search_m` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `cmpny_c` (`cmpny_c`,`link_prod_c`)
) ENGINE=InnoDB AUTO_INCREMENT=3908 DEFAULT CHARSET=euckr COMMENT='패키지 정보';

CREATE TABLE `tpkg_prod` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `price_seq` int(10) unsigned NOT NULL DEFAULT '0',
  `prod_c` mediumint(8) unsigned DEFAULT NULL,
  `prod_n` varchar(100) DEFAULT NULL,
  `type_c` enum('D','U','C') NOT NULL DEFAULT 'D',
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `depth_n` smallint(5) unsigned NOT NULL DEFAULT '0',
  `optval_c1` smallint(5) unsigned DEFAULT NULL,
  `optval_c2` smallint(5) unsigned DEFAULT NULL,
  `optval_c3` smallint(5) unsigned DEFAULT NULL,
  `optval_c4` smallint(5) unsigned DEFAULT NULL,
  `optval_c5` smallint(5) unsigned DEFAULT NULL,
  `optval_c6` smallint(5) unsigned DEFAULT NULL,
  `optval_c7` smallint(5) unsigned DEFAULT NULL,
  `optval_c8` smallint(5) unsigned DEFAULT NULL,
  `optval_c9` smallint(5) unsigned DEFAULT NULL,
  `optval_c10` smallint(5) unsigned DEFAULT NULL,
  `optval_c11` smallint(5) unsigned DEFAULT NULL,
  `optval_c12` smallint(5) unsigned DEFAULT NULL,
  `optval_c13` smallint(5) unsigned DEFAULT NULL,
  `optval_c14` smallint(5) unsigned DEFAULT NULL,
  `optval_c15` smallint(5) unsigned DEFAULT NULL,
  `optval_c16` smallint(5) unsigned DEFAULT NULL,
  `optval_c17` smallint(5) unsigned DEFAULT NULL,
  `optval_c18` smallint(5) unsigned DEFAULT NULL,
  `optval_c19` smallint(5) unsigned DEFAULT NULL,
  `optval_c20` smallint(5) unsigned DEFAULT NULL,
  `optval_c21` smallint(5) unsigned DEFAULT NULL,
  `optval_c22` smallint(5) unsigned DEFAULT NULL,
  `optval_c23` smallint(5) unsigned DEFAULT NULL,
  `optval_c24` smallint(5) unsigned DEFAULT NULL,
  `optval_c25` smallint(5) unsigned DEFAULT NULL,
  `optval_c26` smallint(5) unsigned DEFAULT NULL,
  `optval_c27` smallint(5) unsigned DEFAULT NULL,
  `optval_c28` smallint(5) unsigned DEFAULT NULL,
  `optval_c29` smallint(5) unsigned DEFAULT NULL,
  `optval_c30` smallint(5) unsigned DEFAULT NULL,
  `optval_c31` smallint(5) unsigned DEFAULT NULL,
  `optval_c32` smallint(5) unsigned DEFAULT NULL,
  `optval_c33` smallint(5) unsigned DEFAULT NULL,
  `optval_c34` smallint(5) unsigned DEFAULT NULL,
  `optval_c35` smallint(5) unsigned DEFAULT NULL,
  `optval_c36` smallint(5) unsigned DEFAULT NULL,
  `optval_c37` smallint(5) unsigned DEFAULT NULL,
  `optval_c38` smallint(5) unsigned DEFAULT NULL,
  `optval_c39` smallint(5) unsigned DEFAULT NULL,
  `optval_c40` smallint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `price_seq` (`price_seq`),
  KEY `prod_c` (`prod_c`)
) ENGINE=InnoDB AUTO_INCREMENT=11664 DEFAULT CHARSET=euckr COMMENT='패키지 구성상품';

CREATE TABLE `tpreset` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유키',
  `name_n` varchar(30) NOT NULL DEFAULT 'PRESET 1' COMMENT '프리셋명',
  `icon_n` varchar(45) DEFAULT '' COMMENT '프리셋 아이콘',
  `desc_m` varchar(150) DEFAULT NULL COMMENT '프리셋 간단설명',
  `view_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `make_d` datetime DEFAULT NULL,
  `sort` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '정렬순서',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=euckr COMMENT='가상온라인견적서용 프리셋설정';

CREATE TABLE `tpreset_option` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `depth` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '옵션의 카테고리 뎁스',
  `option_n` varchar(30) NOT NULL COMMENT '옵션명',
  `step` varchar(30) NOT NULL COMMENT '옵션의 단계',
  `view_yn` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '보임/숨김',
  `sort` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '정렬순서',
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=euckr COMMENT='프리셋옵션들(카테고리)';

CREATE TABLE `tpreset_option_value` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `preset_seq` int(10) unsigned NOT NULL COMMENT '프리셋의 고유번호',
  `option_seq` int(10) unsigned NOT NULL COMMENT '프리셋옵션의 고유번호',
  PRIMARY KEY (`seq`),
  KEY `preset_fkey` (`preset_seq`),
  KEY `option_fkey` (`option_seq`),
  CONSTRAINT `option_fkey` FOREIGN KEY (`option_seq`) REFERENCES `tpreset_option` (`seq`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `preset_fkey` FOREIGN KEY (`preset_seq`) REFERENCES `tpreset` (`seq`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2361 DEFAULT CHARSET=euckr COMMENT='프리셋설정 옵션값들';

CREATE TABLE `tpreset_prodlist` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `preset_seq` int(10) unsigned NOT NULL,
  `cate_c1` mediumint(8) unsigned NOT NULL,
  `cate_c2` mediumint(8) unsigned NOT NULL,
  `cate_c3` mediumint(8) unsigned NOT NULL,
  `cate_c4` mediumint(8) unsigned NOT NULL,
  `prod_c` mediumint(8) unsigned NOT NULL,
  `count_q` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '구매갯수',
  PRIMARY KEY (`seq`),
  KEY `preset_seq` (`preset_seq`),
  CONSTRAINT `preset_seq` FOREIGN KEY (`preset_seq`) REFERENCES `tpreset` (`seq`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4444 DEFAULT CHARSET=euckr COMMENT='프리셋 상품리스트';

CREATE TABLE `tprice` (
  `nPriceSeq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `link_prod_c` varchar(100) NOT NULL DEFAULT '',
  `link_prod_n` varchar(255) DEFAULT NULL,
  `price_q` int(10) unsigned NOT NULL DEFAULT '0',
  `post_q` mediumint(8) unsigned NOT NULL DEFAULT '16777215',
  `cnt_q` smallint(5) unsigned NOT NULL DEFAULT '65535',
  `disp_type` char(1) DEFAULT NULL,
  `disp_seq` tinyint(3) unsigned DEFAULT NULL,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`nPriceSeq`),
  UNIQUE KEY `idxUMProdC` (`prod_c`,`link_prod_c`,`cmpny_c`),
  KEY `idxMProdC` (`prod_c`,`cmpny_c`),
  KEY `idxMLinkProdC` (`link_prod_c`,`cmpny_c`),
  KEY `idxCmpnyC` (`cmpny_c`),
  KEY `idxMInputD` (`input_d`,`cmpny_c`)
) ENGINE=InnoDB AUTO_INCREMENT=210586793 DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_affiliate` (
  `nPriceSeq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `link_prod_c` varchar(100) NOT NULL DEFAULT '',
  `link_prod_n` varchar(255) DEFAULT NULL,
  `price_q` int(10) unsigned NOT NULL DEFAULT '0',
  `post_q` mediumint(8) unsigned NOT NULL DEFAULT '16777215',
  `cnt_q` smallint(5) unsigned DEFAULT '65535',
  `disp_type` char(1) DEFAULT NULL,
  `disp_seq` tinyint(3) unsigned DEFAULT NULL,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`nPriceSeq`),
  UNIQUE KEY `idxUMProdC` (`prod_c`,`link_prod_c`,`cmpny_c`),
  KEY `idxMProdC` (`prod_c`,`cmpny_c`),
  KEY `idxMLinkProdC` (`link_prod_c`,`cmpny_c`),
  KEY `idxCmpnyC` (`cmpny_c`),
  KEY `idxMInputD` (`input_d`,`cmpny_c`)
) ENGINE=InnoDB AUTO_INCREMENT=16058962 DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_affiliate_detail` (
  `nPriceSeq` int(10) unsigned NOT NULL DEFAULT '0',
  `point_q` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `desc_m` tinytext,
  `coupon_m` varchar(100) DEFAULT NULL,
  `nointerest_m` varchar(100) DEFAULT NULL,
  `gift_m` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`nPriceSeq`),
  CONSTRAINT `fkPricecompare2` FOREIGN KEY (`nPriceSeq`) REFERENCES `tprice_affiliate` (`nPriceSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_detail` (
  `nPriceSeq` int(10) unsigned NOT NULL DEFAULT '0',
  `point_q` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `desc_m` tinytext,
  `coupon_m` varchar(100) DEFAULT NULL,
  `nointerest_m` varchar(100) DEFAULT NULL,
  `gift_m` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`nPriceSeq`),
  CONSTRAINT `fkPricecompare1` FOREIGN KEY (`nPriceSeq`) REFERENCES `tprice` (`nPriceSeq`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_hide_cmpny` (
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `dtCreateDate` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '숨김처리날짜',
  PRIMARY KEY (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`,`cmpny_c`),
  KEY `tprice_hide_cmpny_cate_c2_idx` (`cate_c2`),
  KEY `tprice_hide_cmpny_cate_c3_idx` (`cate_c3`),
  KEY `tprice_hide_cmpny_cate_c4_idx` (`cate_c4`),
  KEY `tprice_hide_cmpny_cmpny_c_idx` (`cmpny_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_high` (
  `sn` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유번호',
  `cate_c1` smallint(5) unsigned NOT NULL COMMENT '대분류 고유번호',
  `cate_c2` smallint(5) unsigned NOT NULL COMMENT '중분류 고유번호',
  `cate_c3` smallint(5) unsigned NOT NULL COMMENT '소분류 고유번호',
  `cate_c4` smallint(5) unsigned NOT NULL COMMENT '세분류 고유번호',
  `prod_c` mediumint(8) unsigned NOT NULL COMMENT '상품 고유번호',
  `link_prod_c` varchar(100) NOT NULL COMMENT '업체 상품 코드',
  `cmpny_c` varchar(6) NOT NULL COMMENT '업체코드',
  `standard_deviation` double NOT NULL COMMENT '통계용 표준편차',
  `skewness` double NOT NULL COMMENT '통계용 왜도',
  `kurtosis` double NOT NULL COMMENT '통계용 첨도',
  `input_d` date NOT NULL,
  `input_t` time NOT NULL,
  PRIMARY KEY (`sn`),
  UNIQUE KEY `idxMPriceLink` (`prod_c`,`cmpny_c`,`link_prod_c`,`input_d`),
  KEY `idxMCateC` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`),
  KEY `idxInputD` (`input_d`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='확률(P)이 약 95%이상인 경우 테이블';

CREATE TABLE `tprice_hst` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `input_d` date NOT NULL DEFAULT '0000-00-00',
  `minprice_q` int(10) unsigned DEFAULT '0',
  `maxprice_q` int(10) unsigned DEFAULT '0',
  `avgprice_q` int(10) unsigned DEFAULT '0',
  `basket_q` int(10) unsigned DEFAULT '0',
  `total_q` int(10) unsigned DEFAULT '0',
  `save_q` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`prod_c`,`input_d`),
  KEY `tprice_hst_input_d_idx` (`input_d`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_hst_daily` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `input_d` date NOT NULL DEFAULT '0000-00-00',
  `minprice_q` int(10) unsigned DEFAULT '0',
  `maxprice_q` int(10) unsigned DEFAULT '0',
  `avgprice_q` int(10) unsigned DEFAULT '0',
  `basket_q` int(10) unsigned DEFAULT '0',
  `total_q` int(10) unsigned DEFAULT '0',
  `save_q` int(10) unsigned NOT NULL DEFAULT '0',
  `shop_q` smallint(5) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`prod_c`,`input_d`),
  KEY `tprice_hst_daily_input_d_idx` (`input_d`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_jehyu_print` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `link_prod_c` varchar(100) NOT NULL DEFAULT '',
  `price_q` int(10) unsigned NOT NULL DEFAULT '0',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `link_prod_n` varchar(255) DEFAULT NULL,
  `post_q` mediumint(8) unsigned NOT NULL DEFAULT '16777215',
  PRIMARY KEY (`prod_c`,`cmpny_c`,`link_prod_c`),
  KEY `tprice_jehyu_for_print_cmpny_c_link_prod_c_idx` (`cmpny_c`,`link_prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprice_jehyu_print_high` (
  `sn` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유번호',
  `cate_c1` smallint(5) unsigned NOT NULL COMMENT '대분류 고유번호',
  `cate_c2` smallint(5) unsigned NOT NULL COMMENT '중분류 고유번호',
  `cate_c3` smallint(5) unsigned NOT NULL COMMENT '소분류 고유번호',
  `cate_c4` smallint(5) unsigned NOT NULL COMMENT '세분류 고유번호',
  `prod_c` mediumint(8) unsigned NOT NULL COMMENT '상품 고유번호',
  `link_prod_c` varchar(100) NOT NULL COMMENT '업체 상품코드',
  `cmpny_c` varchar(6) NOT NULL COMMENT '업체 고유번호',
  `shop_q` smallint(5) unsigned NOT NULL COMMENT '표본몰 수',
  `standard_deviation` double NOT NULL COMMENT '통계용 표준편차',
  `standardize` double NOT NULL COMMENT '통계용 표준화 변수 Z',
  `skewness` double NOT NULL COMMENT '통계용 왜도',
  `kurtosis` double NOT NULL COMMENT '통계용 첨도',
  `input_d` date NOT NULL COMMENT '입력날짜',
  `input_t` time NOT NULL COMMENT '입력시간',
  `check_yn` char(1) NOT NULL DEFAULT 'N' COMMENT '담당자 확인여부 (N: 미확인, Y:확인)',
  PRIMARY KEY (`sn`),
  UNIQUE KEY `idxUMPriceLink` (`prod_c`,`cmpny_c`,`link_prod_c`,`shop_q`,`input_d`),
  KEY `idxMCateC` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`),
  KEY `idxInputD` (`input_d`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='제휴용 확률(P)이 약 99.8%이상인 경우 테이블';

CREATE TABLE `tprice_jehyu_print_low` (
  `sn` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유번호',
  `cate_c1` smallint(5) unsigned NOT NULL COMMENT '대분류 고유번호',
  `cate_c2` smallint(5) unsigned NOT NULL COMMENT '중분류 고유번호',
  `cate_c3` smallint(5) unsigned NOT NULL COMMENT '소분류 고유번호',
  `cate_c4` smallint(5) unsigned NOT NULL COMMENT '세분류 고유번호',
  `prod_c` mediumint(8) unsigned NOT NULL COMMENT '상품 고유번호',
  `link_prod_c` varchar(100) NOT NULL COMMENT '업체 상품코드',
  `cmpny_c` varchar(6) NOT NULL COMMENT '업체 고유번호',
  `shop_q` smallint(5) unsigned NOT NULL COMMENT '표본몰 수',
  `standard_deviation` double NOT NULL COMMENT '통계용 표준편차',
  `standardize` double NOT NULL COMMENT '통계용 표준화 변수 Z',
  `skewness` double NOT NULL COMMENT '통계용 왜도',
  `kurtosis` double NOT NULL COMMENT '통계용 첨도',
  `input_d` date NOT NULL COMMENT '입력날짜',
  `input_t` time NOT NULL COMMENT '입력시간',
  `check_yn` char(1) NOT NULL DEFAULT 'N' COMMENT '담당자 확인여부 (N: 미확인, Y:확인)',
  PRIMARY KEY (`sn`),
  UNIQUE KEY `idxUMPriceLink` (`prod_c`,`cmpny_c`,`link_prod_c`,`shop_q`,`input_d`),
  KEY `idxMCateC` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`),
  KEY `idxInputD` (`input_d`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='제휴용 확률(P)이 약 0.2%이하인 경우 테이블';

CREATE TABLE `tprice_low` (
  `sn` mediumint(8) unsigned NOT NULL AUTO_INCREMENT COMMENT '고유번호',
  `cate_c1` smallint(5) unsigned NOT NULL COMMENT '대분류 고유번호',
  `cate_c2` smallint(5) unsigned NOT NULL COMMENT '중분류 고유번호',
  `cate_c3` smallint(5) unsigned NOT NULL COMMENT '소분류 고유번호',
  `cate_c4` smallint(5) unsigned NOT NULL COMMENT '세분류 고유번호',
  `prod_c` mediumint(8) unsigned NOT NULL COMMENT '상품 고유번호',
  `link_prod_c` varchar(100) NOT NULL COMMENT '업체 상품 코드',
  `cmpny_c` varchar(6) NOT NULL COMMENT '업체코드',
  `standard_deviation` double NOT NULL COMMENT '통계용 표준편차',
  `skewness` double NOT NULL COMMENT '통계용 왜도',
  `kurtosis` double NOT NULL COMMENT '통계용 첨도',
  `input_d` date NOT NULL,
  `input_t` time NOT NULL,
  PRIMARY KEY (`sn`),
  UNIQUE KEY `idxMPriceLink` (`prod_c`,`cmpny_c`,`link_prod_c`,`input_d`),
  KEY `idxMCateC` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`),
  KEY `idxInputD` (`input_d`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='확률(P)이 약 5%이하인 경우 테이블';

CREATE TABLE `tprice_memo` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `link_prod_c` varchar(100) NOT NULL DEFAULT '',
  `memo_m` tinytext,
  PRIMARY KEY (`prod_c`,`cmpny_c`,`link_prod_c`),
  KEY `tprice_memo_cmpny_c_link_prod_c_idx` (`cmpny_c`,`link_prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprod` (
  `prod_c` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,
  `prod_n` varchar(100) NOT NULL DEFAULT '',
  `search_prod_n` varchar(255) DEFAULT NULL,
  `search_way` tinyint(3) unsigned NOT NULL DEFAULT '2',
  `maker_c` int(10) unsigned NOT NULL DEFAULT '0',
  `brand_c` int(10) unsigned DEFAULT NULL,
  `circul_c` smallint(5) unsigned DEFAULT NULL,
  `maker_url_c` tinyint(3) unsigned DEFAULT NULL,
  `brand_url_c` tinyint(3) unsigned DEFAULT NULL,
  `circul_url_c` tinyint(3) unsigned DEFAULT NULL,
  `minprice_q` int(10) unsigned NOT NULL DEFAULT '0',
  `avgprice_q` int(10) unsigned NOT NULL DEFAULT '0',
  `nPromotionMinPriceQ` int(10) unsigned NOT NULL DEFAULT '0',
  `make_d` date DEFAULT NULL,
  `input_d` datetime DEFAULT NULL,
  `modify_d` datetime DEFAULT NULL,
  `shop_q` smallint(5) unsigned NOT NULL DEFAULT '0',
  `nCntPremiumShop` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '책임배송업체수(프리미엄업체수)',
  `package_q` smallint(5) unsigned NOT NULL DEFAULT '0',
  `save_q` int(10) unsigned NOT NULL DEFAULT '0',
  `buy_q` int(10) unsigned NOT NULL DEFAULT '0',
  `disp_yn` enum('Y','N') NOT NULL DEFAULT 'Y',
  `shot_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `box_size` enum('A','B') DEFAULT NULL,
  `box_ratio` smallint(5) unsigned DEFAULT NULL,
  `movie_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `nOutSideOfferImageType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '0:다나와제작, 1:GSEshop제공 ...',
  `sImageRegisterType` char(1) NOT NULL DEFAULT 'Y' COMMENT 'Y:등록, N:미등록',
  `sProductRegisterType` char(1) NOT NULL DEFAULT 'G' COMMENT 'G:일반등록, T:가등록',
  `sPriceLockYN` char(1) NOT NULL DEFAULT 'N' COMMENT '가격입력잠금여부 Y:잠금, N:해제',
  `dtPriceModifyDate` date NOT NULL DEFAULT '1970-01-01' COMMENT '가격 수정일',
  PRIMARY KEY (`prod_c`),
  KEY `tprod_maker_c_idx` (`maker_c`),
  KEY `tprod_brand_c_idx` (`brand_c`),
  KEY `tprod_circul_c_idx` (`circul_c`),
  KEY `tprod_prod_n_idx` (`prod_n`)
) ENGINE=InnoDB AUTO_INCREMENT=1929398 DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_ad_price` (
  `el_cate_c1` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c2` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c3` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_c4` smallint(5) unsigned NOT NULL DEFAULT '0',
  `el_cate_n` varchar(100) NOT NULL DEFAULT '',
  `el_prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `el_prod_n` tinytext NOT NULL,
  `seq_c` smallint(5) unsigned NOT NULL DEFAULT '0',
  `price_q` int(10) DEFAULT NULL,
  `bigo` tinytext,
  KEY `el_cate_c1` (`el_cate_c1`,`el_cate_c2`,`seq_c`),
  KEY `el_prod_c` (`el_cate_c1`,`el_cate_c2`,`el_cate_c3`,`el_cate_c4`,`el_prod_c`,`seq_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_brand` (
  `brand_c` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `maker_c` int(10) unsigned NOT NULL DEFAULT '0',
  `brand_n` varchar(50) NOT NULL DEFAULT '',
  `url_m` text,
  `desc_m` text,
  `astel_n` varchar(30) DEFAULT NULL,
  `asaddr_m` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_c`),
  KEY `tprod_brand_maker_c_idx` (`maker_c`),
  KEY `brand_n` (`brand_n`)
) ENGINE=InnoDB AUTO_INCREMENT=6751 DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_bundle` (
  `prod_c` mediumint(8) unsigned NOT NULL,
  `bundle_n` varchar(50) NOT NULL DEFAULT '',
  `bundle_disp_seq` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `unlimited_link_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `sSelectYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '묶음 조건 노출 선택 여부',
  `nOptionType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '추가금 발생 유무 0:일반, 1:필수패키지',
  PRIMARY KEY (`prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='묶음조건/가입조건 정보';

CREATE TABLE `tprod_circul` (
  `circul_c` smallint(5) unsigned NOT NULL AUTO_INCREMENT,
  `maker_c` int(10) unsigned NOT NULL DEFAULT '0',
  `circul_n` varchar(50) NOT NULL DEFAULT '',
  `url_m` text,
  `desc_m` text,
  `astel_n` varchar(30) DEFAULT NULL,
  `asaddr_m` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`circul_c`),
  KEY `tprod_circul_maker_c_idx` (`maker_c`),
  KEY `circul_n` (`circul_n`)
) ENGINE=InnoDB AUTO_INCREMENT=475 DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_contents` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `contents_seq` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `size_q` smallint(5) NOT NULL DEFAULT '0',
  `offer_seq` int(10) unsigned DEFAULT NULL,
  `default_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `sViewYN` char(1) NOT NULL DEFAULT 'Y' COMMENT '컨텐츠 노출 여부',
  `html_desc_m` mediumtext,
  `input_d` datetime DEFAULT NULL,
  `modify_d` datetime DEFAULT NULL,
  `dtMakeDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '제작일',
  `dtRenewalDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '갱신일',
  PRIMARY KEY (`prod_c`,`contents_seq`),
  KEY `tprod_contents_idx` (`prod_c`,`size_q`,`offer_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_contents_banner` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `contents_seq` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `banner_desc_m` text,
  PRIMARY KEY (`prod_c`,`contents_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_contents_hst` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `input_d` date NOT NULL DEFAULT '0000-00-00',
  `contents_seq` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `offer_seq` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`prod_c`,`input_d`,`contents_seq`),
  KEY `tprod_contents_hst_input_d_idx` (`input_d`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_img` (
  `prod_c` mediumint(8) unsigned NOT NULL,
  `img_seq` smallint(5) unsigned NOT NULL DEFAULT '0',
  `img_type` enum('jpg','gif','png','bmp','tiff','swf') NOT NULL DEFAULT 'jpg',
  `nImgWidthSize` smallint(5) unsigned NOT NULL DEFAULT '0',
  `nImgHeightSize` smallint(5) unsigned NOT NULL DEFAULT '0',
  `sOrigin` char(1) NOT NULL DEFAULT 'N' COMMENT 'N:출처없음 D:다나와제작 C:다나와제작CM G:제조사제공 S:협력사제공 E:기타(직접입력)',
  `sOriginDetail` varchar(10) DEFAULT NULL COMMENT '출처명(sOrigin=E의 경우 직접입력)',
  `sUrl` varchar(2048) DEFAULT NULL COMMENT '출처의url',
  PRIMARY KEY (`prod_c`,`img_seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_inout_manage` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` varchar(20) NOT NULL,
  `prod_n` varchar(150) NOT NULL,
  `status_c` enum('IN','OUT') NOT NULL DEFAULT 'IN',
  `cnt_q` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `memo_m` varchar(255) DEFAULT NULL,
  `date_in_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `date_out_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `request_ip_n` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='상품 입출력관리';

CREATE TABLE `tprod_keyword` (
  `prod_c` mediumint(8) unsigned NOT NULL,
  `link_candidate_num` smallint(5) unsigned NOT NULL,
  `nCntSearchLinkCandidate` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '검색을 통한 가링크 수',
  `keyword` text,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modify_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `auto_link_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='기준상품 검색 키워드 정보';

CREATE TABLE `tprod_maker` (
  `maker_c` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `maker_n` varchar(50) NOT NULL DEFAULT '',
  `url_m` text,
  `desc_m` text,
  `astel_n` varchar(30) DEFAULT NULL,
  `asaddr_m` varchar(255) DEFAULT NULL,
  `sOfferContentDisplayYN` char(1) DEFAULT 'Y' COMMENT '제공 컨텐츠 노출여부',
  PRIMARY KEY (`maker_c`),
  KEY `maker_n` (`maker_n`)
) ENGINE=InnoDB AUTO_INCREMENT=75498 DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_register` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `cate_c1` int(10) unsigned NOT NULL,
  `cate_c2` int(10) unsigned NOT NULL,
  `cate_c3` int(10) unsigned NOT NULL,
  `cate_c4` int(10) unsigned NOT NULL,
  `prod_c` int(10) unsigned DEFAULT NULL,
  `prod_n` varchar(150) NOT NULL,
  `maker_c` int(10) unsigned DEFAULT NULL,
  `maker_n` varchar(50) DEFAULT NULL,
  `brand_c` int(10) unsigned DEFAULT NULL,
  `brand_n` varchar(50) DEFAULT NULL,
  `circul_c` int(10) unsigned DEFAULT NULL,
  `prod_img1` varchar(50) DEFAULT NULL,
  `prod_img2` varchar(50) DEFAULT NULL,
  `prod_img3` varchar(50) DEFAULT NULL,
  `prod_img4` varchar(50) DEFAULT NULL,
  `make_d` varchar(30) DEFAULT NULL,
  `content_m` mediumtext,
  `etc_m` mediumtext,
  `register_id` varchar(20) NOT NULL,
  `register_result_id` varchar(20) DEFAULT NULL,
  `register_d` datetime NOT NULL,
  `status_c` enum('A','B','C','D','E','F','G') NOT NULL DEFAULT 'A',
  `apply_state_d` datetime DEFAULT NULL,
  `contents_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `reject_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `reject_memo` varchar(255) DEFAULT NULL,
  `inner_memo` mediumtext,
  `request_back_memo` mediumtext,
  `ip` varchar(30) NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=euckr COMMENT='상품 등록 요청 메인 테이블';

CREATE TABLE `tprod_register_comment` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `register_seq` int(11) NOT NULL,
  `type_c` enum('M','D') NOT NULL DEFAULT 'M',
  `register_id` varchar(20) NOT NULL,
  `date_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status_c` enum('N','P','Y') NOT NULL DEFAULT 'N',
  `comment_m` mediumtext,
  `ip` varchar(15) NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `register_seq` (`register_seq`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=euckr COMMENT='상품 등록 요청 댓글';

CREATE TABLE `tprod_register_convert` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `member_seq` int(10) unsigned NOT NULL,
  `id` varchar(20) NOT NULL DEFAULT '',
  `cmpny_n` varchar(30) NOT NULL DEFAULT '',
  `manager_n` varchar(30) DEFAULT NULL,
  `email` tinytext,
  `phone_no` varchar(20) DEFAULT NULL,
  `memo` mediumtext,
  `cmpny_type` enum('제조사','유통사','수입사','DB제작사') DEFAULT NULL,
  `contents_buy_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `contents_buy_status_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status_c` enum('신청','승인','보류') DEFAULT '신청',
  `status_d` datetime DEFAULT NULL,
  `settle_id` varchar(20) DEFAULT NULL,
  `settle_memo` mediumtext,
  `domain_n` tinytext,
  `ip` varchar(30) NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=euckr COMMENT='상품컨텐츠제작청전환요청';

CREATE TABLE `tprod_register_watermark` (
  `seq` int(11) NOT NULL AUTO_INCREMENT,
  `register_id` varchar(20) NOT NULL,
  `date_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `memo_m` varchar(255) DEFAULT NULL,
  `file_n` varchar(255) DEFAULT NULL,
  `position_c` enum('LT','CT','RT','LC','CC','RC','LB','CB','RB') NOT NULL DEFAULT 'RB',
  PRIMARY KEY (`register_id`,`seq`)
) ENGINE=MyISAM DEFAULT CHARSET=euckr COMMENT='상품 등록 워터마크관리';

CREATE TABLE `tprod_search` (
  `sn` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `keyword` varchar(25) NOT NULL DEFAULT '',
  PRIMARY KEY (`sn`),
  UNIQUE KEY `idxUProdCKeyword` (`prod_c`,`keyword`)
) ENGINE=InnoDB AUTO_INCREMENT=398037 DEFAULT CHARSET=euckr;

CREATE TABLE `tprod_search_for_search` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `keyword_all` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr COMMENT='상품 검색 키워드 관련 색인(1:1) 정보 테이블';

CREATE TABLE `tprod_search_manage` (
  `sn` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0' COMMENT '다나와 상품코드',
  `check_yn` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '담당자 확인여부 (0: 미확인, 1:확인)',
  `member_seq` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '최종 수정자 seq',
  `member_name` varchar(30) NOT NULL DEFAULT '' COMMENT '최종 수정자 name',
  `modify_dt` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '최종 수정일',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '비고',
  PRIMARY KEY (`sn`),
  UNIQUE KEY `idxUProdC` (`prod_c`),
  KEY `idxMProdCCheckYn` (`prod_c`,`check_yn`),
  KEY `idxMProdCModifyDt` (`prod_c`,`modify_dt`)
) ENGINE=InnoDB AUTO_INCREMENT=119343 DEFAULT CHARSET=euckr;

CREATE TABLE `tsave_plus` (
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cate_c1` int(10) unsigned DEFAULT NULL,
  `cate_c2` int(10) unsigned DEFAULT NULL,
  `cate_c3` int(10) unsigned DEFAULT NULL,
  `cate_c4` int(10) unsigned DEFAULT NULL,
  `save_plus_q` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`prod_c`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tslrclub_cate_click` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_d` date NOT NULL DEFAULT '0000-00-00',
  `site_c` smallint(2) unsigned NOT NULL,
  `cate_c1` smallint(5) unsigned NOT NULL,
  `cate_c2` smallint(5) unsigned NOT NULL,
  `cate_c3` smallint(5) unsigned DEFAULT NULL,
  `cate_c4` smallint(5) unsigned DEFAULT NULL,
  `click_q` mediumint(9) unsigned NOT NULL,
  `click_pre_q` mediumint(9) unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `date_d` (`date_d`),
  KEY `site_c` (`site_c`),
  KEY `category` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB AUTO_INCREMENT=49742 DEFAULT CHARSET=euckr;

CREATE TABLE `tslrclub_cate_rank` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_d` date NOT NULL DEFAULT '0000-00-00',
  `rank_q` smallint(3) unsigned NOT NULL DEFAULT '0',
  `rank_pre_q` smallint(3) unsigned DEFAULT '0',
  `site_c` smallint(2) unsigned NOT NULL,
  `cate_c1` smallint(5) unsigned NOT NULL,
  `cate_c2` smallint(5) unsigned NOT NULL,
  `cate_c3` smallint(5) unsigned DEFAULT NULL,
  `cate_c4` smallint(5) unsigned DEFAULT NULL,
  `click_q` mediumint(9) unsigned NOT NULL,
  `click_pre_q` mediumint(9) unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `date_d` (`date_d`),
  KEY `site_c` (`site_c`),
  KEY `category` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB AUTO_INCREMENT=49419 DEFAULT CHARSET=euckr;

CREATE TABLE `tslrclub_log` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cate_c1` smallint(5) unsigned NOT NULL,
  `cate_c2` smallint(5) unsigned NOT NULL,
  `cate_c3` smallint(5) unsigned DEFAULT NULL,
  `cate_c4` smallint(5) unsigned DEFAULT NULL,
  `prod_c` mediumint(8) unsigned NOT NULL,
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `user_ip` varchar(15) DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `tslrclub_prod_rank` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `date_d` date NOT NULL DEFAULT '0000-00-00',
  `rank_q` smallint(3) unsigned NOT NULL DEFAULT '0',
  `site_c` smallint(2) unsigned NOT NULL,
  `rank_pre_q` smallint(3) unsigned DEFAULT '0',
  `cate_c1` smallint(5) unsigned NOT NULL,
  `cate_c2` smallint(5) unsigned NOT NULL,
  `cate_c3` smallint(5) unsigned DEFAULT NULL,
  `cate_c4` smallint(5) unsigned DEFAULT NULL,
  `prod_c` mediumint(8) unsigned NOT NULL,
  `click_q` mediumint(9) unsigned NOT NULL,
  `click_pre_q` mediumint(9) unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `date_d` (`date_d`),
  KEY `site_c` (`site_c`),
  KEY `category` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`)
) ENGINE=InnoDB AUTO_INCREMENT=102548 DEFAULT CHARSET=euckr;

CREATE TABLE `tuser_report` (
  `seq_c` int(9) NOT NULL AUTO_INCREMENT,
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `cmpny_c` varchar(10) NOT NULL DEFAULT '',
  `link_prod_c` varchar(50) NOT NULL DEFAULT '',
  `link_prod_n` varchar(255) NOT NULL DEFAULT '',
  `price_q` int(10) unsigned NOT NULL DEFAULT '0',
  `report` set('S0','S1','S2','S3','S4','C0','C1','C2','C3','C4','C5','P0','P1','P2','P3','P4','P5','W0','W1','W2','W3','W4','W5','Z0','Z1','Z2','Z3','Z4','Z5') DEFAULT NULL,
  `content_m` text,
  `attach_n` tinytext,
  `id_n` varchar(20) DEFAULT NULL,
  `ip_n` varchar(30) DEFAULT NULL,
  `name_n` varchar(30) DEFAULT NULL,
  `email_n` varchar(40) DEFAULT NULL,
  `input_d` datetime DEFAULT NULL,
  `reply_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `reply_m` text,
  `reply_d` datetime DEFAULT NULL,
  `reply_id_n` varchar(30) DEFAULT NULL,
  `mail_ok` enum('Y','N') NOT NULL DEFAULT 'N',
  `sErrorPageUrl` varchar(500) NOT NULL DEFAULT '' COMMENT '오류발생 URL',
  `sCustomerCenter` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '고객센터에서의 신고 여부',
  PRIMARY KEY (`seq_c`),
  KEY `tuser_report_cmpny_cate_idx` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`),
  KEY `tuser_report_cmpny_prod_c_idx` (`prod_c`),
  KEY `tuser_report_cmpny_cmpny_c_idx` (`cmpny_c`),
  KEY `tuser_report_cmpny_link_prod_c_idx` (`link_prod_c`),
  KEY `tuser_report_cmpny_report_idx` (`report`)
) ENGINE=InnoDB AUTO_INCREMENT=212136 DEFAULT CHARSET=euckr;

CREATE TABLE `twish_folder` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `folder_n` varchar(30) NOT NULL DEFAULT '관심상품1',
  `folder_sort_q` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `id` varchar(20) NOT NULL DEFAULT '',
  `input_d` datetime DEFAULT '0000-00-00 00:00:00',
  `nServiceType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '서비스구분 0:관심상품, 1:가상온라인견적서',
  PRIMARY KEY (`seq`),
  KEY `IDX_id` (`id`),
  KEY `IDX_sort` (`folder_sort_q`)
) ENGINE=InnoDB AUTO_INCREMENT=4731672 DEFAULT CHARSET=euckr;

CREATE TABLE `twish_list` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `folder_seq` int(10) unsigned NOT NULL,
  `cate_c1` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c2` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c3` int(10) unsigned NOT NULL DEFAULT '0',
  `cate_c4` int(10) unsigned NOT NULL DEFAULT '0',
  `prod_c` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `input_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `count_q` smallint(3) unsigned NOT NULL DEFAULT '0',
  `id` char(32) DEFAULT NULL,
  `nServiceType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '서비스구분 0:관심상품, 1:가상온라인견적서',
  PRIMARY KEY (`seq`),
  KEY `IDX_FK_seq` (`folder_seq`),
  KEY `IDX_category` (`cate_c1`,`cate_c2`,`cate_c3`,`cate_c4`),
  KEY `IDX_prod_c` (`prod_c`),
  KEY `IDX_date` (`input_d`),
  KEY `IDX_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175748430 DEFAULT CHARSET=euckr;

CREATE TABLE `twork_manage` (
  `seq` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ymm` varchar(6) NOT NULL DEFAULT '',
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `cmpny_n` varchar(255) NOT NULL DEFAULT '',
  `uclick_id_n` varchar(15) NOT NULL DEFAULT '',
  `resno` varchar(20) NOT NULL DEFAULT '',
  `owner_n` varchar(255) NOT NULL DEFAULT '',
  `fee_type` char(1) NOT NULL DEFAULT '',
  `mall_type` char(1) NOT NULL DEFAULT '',
  `vir_acct` varchar(20) NOT NULL DEFAULT '',
  `danawa_amt` int(10) unsigned NOT NULL DEFAULT '0',
  `danawa_admission_amt` int(10) unsigned NOT NULL DEFAULT '0',
  `uclick_amt1` int(10) unsigned NOT NULL DEFAULT '0',
  `uclick_amt2` int(10) unsigned NOT NULL DEFAULT '0',
  `uclick_amt3` int(10) unsigned NOT NULL DEFAULT '0',
  `baropc_amt` int(10) unsigned NOT NULL DEFAULT '0',
  `nEstimateAmount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '견적시스템 판매 수수료',
  `nPremiumAmount` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '프리미엄 판매 수수료',
  `receive_amt` int(10) unsigned NOT NULL DEFAULT '0',
  `receive_d` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `input_d` datetime DEFAULT NULL,
  `usignal_m` varchar(255) DEFAULT NULL,
  `pc_cmpny_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  PRIMARY KEY (`seq`),
  UNIQUE KEY `cmpny_c` (`cmpny_c`,`ymm`),
  KEY `twork_manage_ymm_idx` (`ymm`),
  KEY `twork_manage_cmpny_c_idx` (`cmpny_c`),
  KEY `twork_manage_uclick_id_n_idx` (`uclick_id_n`),
  KEY `twork_manage_resno_idx` (`resno`),
  KEY `twork_manage_vir_acct_idx` (`vir_acct`)
) ENGINE=InnoDB AUTO_INCREMENT=96898 DEFAULT CHARSET=euckr;

CREATE TABLE `work_end` (
  `year_month` varchar(6) NOT NULL DEFAULT '0',
  PRIMARY KEY (`year_month`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `work_hist` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '0',
  `receive_date` date DEFAULT NULL,
  `receive_amt` int(11) NOT NULL DEFAULT '0',
  `tax_chk` char(1) DEFAULT NULL,
  `bigo` mediumtext,
  `input_date` date DEFAULT NULL,
  `print_d` date DEFAULT NULL,
  `tax_type_c` smallint(6) DEFAULT '1',
  `write_d` date DEFAULT NULL,
  `req_type_c` char(1) DEFAULT '1',
  `write_seq` int(3) DEFAULT '0',
  `cmpny_n` tinytext,
  `owner_n` tinytext,
  `resno` tinytext,
  `eobtae` tinytext,
  `jongmok` tinytext,
  `addr_m1` tinytext,
  `bigo2` mediumtext,
  `bank_input_d` date DEFAULT NULL,
  `tax_amt` int(10) NOT NULL DEFAULT '0',
  `tax_vat` int(10) NOT NULL DEFAULT '0',
  `receive_end_yn` enum('Y','N') NOT NULL DEFAULT 'N',
  `nTaxBillManagerSeq` int(10) unsigned NOT NULL COMMENT '세금계산서 담당자 고유키',
  `nTaxBillStatus` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '세금계산서 상태 0:발행, 1:승인, 2:미승인',
  `dtCollectionDate` date NOT NULL DEFAULT '0000-00-00' COMMENT '회수예정일',
  `sReceiveUser` varchar(30) NOT NULL DEFAULT '' COMMENT '공급받는자 담당자명',
  `sReceiveEmail` varchar(40) NOT NULL DEFAULT '' COMMENT '공급받는자 Email',
  `sReceiveTelephone` varchar(20) NOT NULL DEFAULT '' COMMENT '공급받는자 전화번호',
  `nCategoryType` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '카테고리분류 (0: 미분류, 1: PC, 2: 가전, 3: 디카, 4: 노트북, 5: 모니터, 6: 자동차, 7: 비가전, 8: 게임, 9: 휴대폰)',
  `nTaxTypeDetailSeq` smallint(5) unsigned NOT NULL DEFAULT '0' COMMENT '상세 품목 고유번호',
  PRIMARY KEY (`cmpny_c`,`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `work_hist_detail` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `seq` int(11) NOT NULL DEFAULT '0',
  `receive_num` smallint(4) NOT NULL DEFAULT '1',
  `tax_type_c` smallint(6) DEFAULT '1',
  `tax_type_n` varchar(50) DEFAULT NULL,
  `tax_standard` varchar(100) DEFAULT NULL,
  `tax_quantity` int(10) DEFAULT NULL,
  `tax_unitCost` int(10) DEFAULT NULL,
  `tax_amt` int(10) NOT NULL DEFAULT '0',
  `tax_vat` int(10) NOT NULL DEFAULT '0',
  `memo` mediumtext,
  `tax_view_yn` enum('Y','N') DEFAULT 'Y',
  `input_d` date DEFAULT NULL,
  PRIMARY KEY (`cmpny_c`,`seq`,`receive_num`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

CREATE TABLE `work_open` (
  `cmpny_c` varchar(6) NOT NULL DEFAULT '',
  `ymm` varchar(6) NOT NULL DEFAULT '',
  `amt` int(11) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  PRIMARY KEY (`cmpny_c`,`ymm`)
) ENGINE=InnoDB DEFAULT CHARSET=euckr;

SET foreign_key_checks = 1;