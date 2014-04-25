SET foreign_key_checks = 0;

CREATE TABLE `tAccountingCycle` (
  `nAccountingCycleSeq` int(10) unsigned NOT NULL auto_increment COMMENT '정산주기정보 고유키',
  `nAccountingCycle` tinyint(3) unsigned NOT NULL default '0' COMMENT '정산주기',
  PRIMARY KEY  (`nAccountingCycleSeq`),
  UNIQUE KEY `idxUnAccountingCycle` (`nAccountingCycle`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='정산주기정보';

CREATE TABLE `tAdvertisement` (
  `nAdvertisementSeq` int(10) unsigned NOT NULL auto_increment COMMENT '광고 고유키',
  `sAdvertisementName` varchar(20) default '' COMMENT '광고명',
  PRIMARY KEY  (`nAdvertisementSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='광고정보';

CREATE TABLE `tAdvertisementInflowStatistics` (
  `nAdvertisementInflowStatisticsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '광고 유입 통계 고유키',
  `nAdvertisementSeq` int(10) unsigned NOT NULL default '0' COMMENT '광고 고유키',
  `nCount` int(10) unsigned NOT NULL default '0' COMMENT '유입수',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  PRIMARY KEY  (`nAdvertisementInflowStatisticsSeq`),
  UNIQUE KEY `idxUMCount` (`nAdvertisementSeq`,`dtCreateDate`),
  CONSTRAINT `fkdbBilling147` FOREIGN KEY (`nAdvertisementSeq`) REFERENCES `tAdvertisement` (`nAdvertisementSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='광고를 통한 유입 통계';

CREATE TABLE `tAfterServiceCompany` (
  `nAfterServiceCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT 'AS업체 고유번호',
  `sAfterServiceCompanyName` varchar(50) default NULL COMMENT 'AS업체명',
  PRIMARY KEY  (`nAfterServiceCompanySeq`),
  UNIQUE KEY `idxUsAfterServiceCompanyName` (`sAfterServiceCompanyName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='AS업체 정보';

CREATE TABLE `tBackoodsDeliveryPrice` (
  `sPostCode1` char(3) NOT NULL default '000' COMMENT '우편번호',
  `sPostCode2` char(3) NOT NULL default '000' COMMENT '우편번호',
  `nPrice` smallint(5) unsigned NOT NULL default '0' COMMENT '배송비',
  PRIMARY KEY  (`sPostCode1`,`sPostCode2`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `tBank` (
  `nBankSeq` int(10) unsigned NOT NULL auto_increment COMMENT '은행 고유키',
  `sPublicBankCode` char(3) default NULL COMMENT '공용은행코드',
  `sBankName` varchar(20) default NULL COMMENT '은행명',
  PRIMARY KEY  (`nBankSeq`),
  UNIQUE KEY `idxUsPublicBankCode` (`sPublicBankCode`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='은행정보';

CREATE TABLE `tBenefitExceptProduct` (
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP고유번호',
  `nProductSeq` int(10) unsigned NOT NULL default '0' COMMENT '다나와대표상품코드',
  PRIMARY KEY  (`nMarketPlaceSeq`,`nProductSeq`),
  CONSTRAINT `fkBenefitExceptProduct1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='혜택제외상품';

CREATE TABLE `tBookmarkDelivery` (
  `nBookmarkDeliverySeq` int(10) unsigned NOT NULL auto_increment COMMENT '즐겨?는 배송지 고유키',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '회원 고유키',
  `sTitle` varchar(50) default NULL COMMENT '제목',
  `sName` varchar(10) default NULL COMMENT '수취자 명',
  `sZipCode1` varchar(3) default NULL COMMENT '수취자 우편번호',
  `sZipcode2` varchar(3) default NULL COMMENT '수취자 우편번호',
  `sAddress1` varchar(308) default NULL COMMENT '수취자 주소',
  `sAddress2` varchar(308) default NULL COMMENT '수취자 상세주소',
  `sPhoneNumber` varchar(50) default NULL COMMENT '수취자 전화번호',
  `sCellularPhoneNumber` varchar(47) default NULL COMMENT '수취자 핸드폰',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nBookmarkDeliverySeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='즐겨?는 배송지';

CREATE TABLE `tCardCompany` (
  `nCardCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT '카드사 정보 고유키',
  `sPublicCardCompanyCode` char(2) default NULL COMMENT '카드사 공용코드',
  `sCardCompanyName` varchar(20) default NULL COMMENT '카드사명',
  PRIMARY KEY  (`nCardCompanySeq`),
  UNIQUE KEY `idxUsPublicCardCompanyCode` (`sPublicCardCompanyCode`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='카드사 정보';

CREATE TABLE `tCart` (
  `nCartSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 고유키',
  `sMemberId` varchar(20) default NULL COMMENT '주문회원 아이디',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '주문회원 고유키',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록 일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록 시간',
  `sMemberIP` varchar(39) default NULL COMMENT '주문회원 IP',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nCartSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니';

CREATE TABLE `tCartGoods` (
  `nCartGoodsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품 고유키',
  `nCartSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 고유키',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품기본정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송방법 고유키',
  `sGoodsName` varchar(200) default NULL COMMENT '상품명',
  `nGoodsPrice` int(10) unsigned NOT NULL default '0' COMMENT '상품가격',
  `nGoodsQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '갯수',
  `nDeliveryPrice` int(10) unsigned NOT NULL default '0' COMMENT '배송비',
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송비 지급구분 고유키',
  `nDiscountPrice` int(10) NOT NULL default '0' COMMENT '할인금액',
  PRIMARY KEY  (`nCartGoodsSeq`),
  KEY `fkdbBilling70` (`nCartSeq`),
  KEY `fkdbBilling71` (`nGoodsSeq`),
  KEY `fkdbBilling72` (`nMarketPlaceSeq`),
  KEY `fkdbBilling73` (`nSellerSeq`),
  KEY `fkdbBilling74` (`nPaymentMethodSeq`),
  KEY `fkdbBilling75` (`nDeliverySeq`),
  KEY `fkdbBilling165` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling165` FOREIGN KEY (`nDeliveryPaymentDivisionSeq`) REFERENCES `tDeliveryPaymentDivision` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling70` FOREIGN KEY (`nCartSeq`) REFERENCES `tCart` (`nCartSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling71` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling72` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling73` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling74` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling75` FOREIGN KEY (`nDeliverySeq`) REFERENCES `tDelivery` (`nDeliverySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니 상품';

CREATE TABLE `tCartGoodsDetail` (
  `nCartGoodsDetailSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품별 상세가격 고유번호',
  `nCartGoodsSeq` int(10) unsigned NOT NULL COMMENT '장바구니 상품 고유번호',
  `nGoodsDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 기본정보 고유번호',
  `sGoodsDetailName` varchar(200) default NULL COMMENT '상세상품명',
  `nGoodsDetailPrice` int(10) NOT NULL default '0' COMMENT '판매등록가격 +,- 가능',
  `nProductType` tinyint(3) NOT NULL default '1' COMMENT '상품구분 1:기본상품, 2:선택상품, 3:옵션상품',
  `nGoodsDetailQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '갯수',
  PRIMARY KEY  (`nCartGoodsDetailSeq`),
  KEY `fkdbBilling76` (`nCartGoodsSeq`),
  CONSTRAINT `fkdbBilling76` FOREIGN KEY (`nCartGoodsSeq`) REFERENCES `tCartGoods` (`nCartGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니 상품별 상세가격 정보';

CREATE TABLE `tCartGoodsDiscount` (
  `nCartGoodsDiscountSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니상품 할인정보 고유키',
  `nCartGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품 고유키',
  `emDiscountType` enum('MEMER_GRADE_UPDOWN_PERCENT','SELLER_ADD_DC') NOT NULL default 'MEMER_GRADE_UPDOWN_PERCENT' COMMENT '할인 유형 MEMER_GRADE_UPDOWN_PERCENT:회원 등급 증감률, SELLER_ADD_DC : 판매자 추가 할인',
  `nDiscountPrice` int(10) unsigned NOT NULL default '0' COMMENT '할인가격',
  PRIMARY KEY  (`nCartGoodsDiscountSeq`),
  KEY `fkCartGoodsDiscount1` (`nCartGoodsSeq`),
  CONSTRAINT `fkCartGoodsDiscount1` FOREIGN KEY (`nCartGoodsSeq`) REFERENCES `tCartGoods` (`nCartGoodsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='장바구니상품 할인정보';

CREATE TABLE `tCartGoodsOptionItem` (
  `nCartGoodsOptionItemSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품 옵션 고유키',
  `nCartGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품 고유키',
  `nOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목 이름 정보 고유키',
  PRIMARY KEY  (`nCartGoodsOptionItemSeq`),
  KEY `fkdbBilling77` (`nCartGoodsSeq`),
  KEY `fkdbBilling78` (`nOptionItemSeq`),
  CONSTRAINT `fkdbBilling77` FOREIGN KEY (`nCartGoodsSeq`) REFERENCES `tCartGoods` (`nCartGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling78` FOREIGN KEY (`nOptionItemSeq`) REFERENCES `tOptionItem` (`nOptionItemSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니 상품옵션 항목';

CREATE TABLE `tCartGoodsOptionItemAttribute` (
  `nCartGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품옵션 항목별 속성 고유키',
  `nCartGoodsOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품 옵션 고유키',
  `nOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목값 이름 정보 고유키',
  `nOptionQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '옵션 주문 갯수',
  PRIMARY KEY  (`nCartGoodsOptionItemAttributeSeq`),
  KEY `fkdbBilling79` (`nCartGoodsOptionItemSeq`),
  KEY `fkdbBilling80` (`nOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling79` FOREIGN KEY (`nCartGoodsOptionItemSeq`) REFERENCES `tCartGoodsOptionItem` (`nCartGoodsOptionItemSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling80` FOREIGN KEY (`nOptionItemAttributeSeq`) REFERENCES `tOptionItemAttribute` (`nOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니 상품옵션 항목별 속성';

CREATE TABLE `tCartGoodsOptionItemAttributePrice` (
  `nCartGoodsOptionItemAttributePriceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품옵션 항목별 속성 가격 고유키',
  `nCartGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품옵션 항목별 속성',
  `nOptionPrice` int(10) unsigned NOT NULL default '0' COMMENT '옵션 주문 가격',
  PRIMARY KEY  (`nCartGoodsOptionItemAttributePriceSeq`),
  KEY `fkdbBilling81` (`nCartGoodsOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling81` FOREIGN KEY (`nCartGoodsOptionItemAttributeSeq`) REFERENCES `tCartGoodsOptionItemAttribute` (`nCartGoodsOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니 상품옵션 항목별 속성 가격';

CREATE TABLE `tCartGoodsOptionItemAttributeValue` (
  `nCartGoodsOptionItemAttributeValueSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품옵션 항목별 속성값 고유키',
  `nCartGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품옵션 항목별 속성 고유키',
  `sAttributeValue` varchar(100) default NULL COMMENT '옵션 속성값',
  PRIMARY KEY  (`nCartGoodsOptionItemAttributeValueSeq`),
  KEY `fkCartGoodsOptionItemAttributeValue1` (`nCartGoodsOptionItemAttributeSeq`),
  CONSTRAINT `fkCartGoodsOptionItemAttributeValue1` FOREIGN KEY (`nCartGoodsOptionItemAttributeSeq`) REFERENCES `tCartGoodsOptionItemAttribute` (`nCartGoodsOptionItemAttributeSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='장바구니 상품옵션 항목별 속성값';

CREATE TABLE `tCombinationDelivery` (
  `nCombinationDeliverySeq` int(10) unsigned NOT NULL auto_increment COMMENT '묶음 배송 고유키',
  PRIMARY KEY  (`nCombinationDeliverySeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='묶음 배송';

CREATE TABLE `tCombinationDeliveryDescription` (
  `nCombinationDeliveryDescriptionSeq` int(10) unsigned NOT NULL auto_increment COMMENT '묶음 배송 상세정보 고유키',
  `nCombinationDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '묶음 배송 고유키',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  PRIMARY KEY  (`nCombinationDeliveryDescriptionSeq`),
  UNIQUE KEY `idxUOrderNumberSeq` (`nOrderNumberSeq`),
  KEY `fkdbBilling99` (`nCombinationDeliverySeq`),
  CONSTRAINT `fkdbBilling100` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling99` FOREIGN KEY (`nCombinationDeliverySeq`) REFERENCES `tCombinationDelivery` (`nCombinationDeliverySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='묶음 배송 상세정보';

CREATE TABLE `tCostCommissionLimit` (
  `nCostCommissionLimitSeq` int(10) unsigned NOT NULL auto_increment COMMENT '결제 제한 수수료 정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nPayGateCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'PG사 고유키',
  `nMinLimitPirce` int(10) unsigned NOT NULL default '0' COMMENT '최저기준 결제 총금액',
  `nValueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용값 타입 1:요율, 2:금액',
  `fValue` float(11,2) unsigned NOT NULL default '0.00' COMMENT '적용값 % 또는 금액',
  PRIMARY KEY  (`nCostCommissionLimitSeq`),
  KEY `fkdbBilling11` (`nMarketPlaceSeq`),
  KEY `fkdbBilling12` (`nPaymentMethodSeq`),
  KEY `fkdbBilling13` (`nPayGateCompanySeq`),
  CONSTRAINT `fkdbBilling11` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling12` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling13` FOREIGN KEY (`nPayGateCompanySeq`) REFERENCES `tPayGateCompany` (`nPayGateCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='원가수수료 결제 제한정보';

CREATE TABLE `tDelivery` (
  `nDeliverySeq` int(10) unsigned NOT NULL auto_increment COMMENT '배송방법 고유키',
  `sDeliveryName` varchar(10) default NULL COMMENT '배송방법',
  PRIMARY KEY  (`nDeliverySeq`),
  UNIQUE KEY `idxUsDeliveryName` (`sDeliveryName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='배송방법';

CREATE TABLE `tDeliveryCompany` (
  `nDeliveryCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT '배송업체고유키',
  `sDeliveryCompanyName` varchar(20) default NULL COMMENT '배송업체명',
  PRIMARY KEY  (`nDeliveryCompanySeq`),
  UNIQUE KEY `idxUsDeliveryCompanyName` (`sDeliveryCompanyName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='배송업체';

CREATE TABLE `tDeliveryMethod` (
  `nDeliveryMethodSeq` int(10) unsigned NOT NULL auto_increment COMMENT '배송수단 고유키',
  `sDeliveryMethodName` varchar(20) default NULL COMMENT '배송수단',
  PRIMARY KEY  (`nDeliveryMethodSeq`),
  UNIQUE KEY `idxUsDeliveryMethodName` (`sDeliveryMethodName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='배송수단';

CREATE TABLE `tDeliveryPaymentDivision` (
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL auto_increment COMMENT '배송비 지급구분 고유키',
  `sDeliveryPaymentDivisionName` varchar(20) default NULL COMMENT '배송비 지급구분명',
  PRIMARY KEY  (`nDeliveryPaymentDivisionSeq`),
  UNIQUE KEY `idxUsDeliveryPaymentDivisionName` (`sDeliveryPaymentDivisionName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='배송비 지급구분';

CREATE TABLE `tDenyBusinessDay` (
  `nDenyBusinessDaySeq` int(10) unsigned NOT NULL auto_increment COMMENT '영업일 제외 고유키',
  `dtDenyDate` date NOT NULL default '0000-00-00' COMMENT '영업제외일',
  `sComment` varchar(50) NOT NULL default '' COMMENT '설명',
  PRIMARY KEY  (`nDenyBusinessDaySeq`),
  UNIQUE KEY `idxUdtDenyDate` (`dtDenyDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='영업일 제외 정보';

CREATE TABLE `tDictionaryData` (
  `nDictionaryDataSeq` int(10) unsigned NOT NULL auto_increment COMMENT '테이블 매핑 사전정보 고 유키',
  `sTableName` varchar(50) default NULL COMMENT '테이블명',
  `sFieldName` varchar(100) default NULL COMMENT '필드명',
  PRIMARY KEY  (`nDictionaryDataSeq`),
  UNIQUE KEY `idxUMsTableName` (`sTableName`,`sFieldName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='테이블 매핑 사전정보';

CREATE TABLE `tEvent` (
  `nEventSeq` int(10) unsigned NOT NULL auto_increment COMMENT '일반 이벤트정보 고유키',
  `nTradingCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT '제공처 고유키',
  `sTitle` varchar(50) default NULL COMMENT '이벤트 제목',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '금액',
  `sComment` varchar(200) default NULL COMMENT '이벤트 내용',
  `dtStartDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 시작일',
  `dtEndDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 종료일',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 등록일',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '이벤트 상태',
  PRIMARY KEY  (`nEventSeq`),
  KEY `fkdbBilling44` (`nTradingCompanySeq`),
  CONSTRAINT `fkdbBilling44` FOREIGN KEY (`nTradingCompanySeq`) REFERENCES `tTradingCompany` (`nTradingCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='일반 이벤트정보';

CREATE TABLE `tEventCard` (
  `nEventCardSeq` int(10) unsigned NOT NULL auto_increment COMMENT '카드 이벤트 정보 고유키',
  `nCardCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT '카드사 정보 고유키',
  `sTerm` varchar(20) default NULL COMMENT '기간',
  `fSellerCommission` float(4,2) unsigned NOT NULL default '0.00' COMMENT '판매자 수수료',
  `dtStartDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 시작일',
  `dtEndDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 종료일',
  `dtInterruptDate` date NOT NULL default '0000-00-00' COMMENT '중지처리 일',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 등록일',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '이벤트 상태',
  PRIMARY KEY  (`nEventCardSeq`),
  KEY `fkdbBilling43` (`nCardCompanySeq`),
  CONSTRAINT `fkdbBilling43` FOREIGN KEY (`nCardCompanySeq`) REFERENCES `tCardCompany` (`nCardCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카드 이벤트 정보';

CREATE TABLE `tEventCardDetail` (
  `nEventCardSeq` int(10) unsigned NOT NULL COMMENT '카드 이벤트 정보 고유키',
  `nCardCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT '카드사 정보 고유키',
  `sTerm` varchar(30) NOT NULL COMMENT '카드사 무이자 기간',
  `sCriteria` varchar(60) default NULL COMMENT '카드사 무이자 조건',
  PRIMARY KEY  (`nEventCardSeq`,`nCardCompanySeq`),
  KEY `fkEventCardDetail2` (`nCardCompanySeq`),
  CONSTRAINT `fkEventCardDetail1` FOREIGN KEY (`nEventCardSeq`) REFERENCES `tEventCardInfo` (`nEventCardSeq`),
  CONSTRAINT `fkEventCardDetail2` FOREIGN KEY (`nCardCompanySeq`) REFERENCES `tCardCompany` (`nCardCompanySeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카드 무이자 이벤트 상세 정보';

CREATE TABLE `tEventCardInfo` (
  `nEventCardSeq` int(10) unsigned NOT NULL auto_increment COMMENT '카드 이벤트 정보 고유키',
  `sEventTitle` varchar(5) NOT NULL COMMENT '카드 이벤트 타이틀',
  `dtStartDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 시작일',
  `dtEndDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 종료일',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '이벤트 등록일',
  `emState` enum('0','1') NOT NULL default '0' COMMENT '0: 비노출 , 1: 노출',
  PRIMARY KEY  (`nEventCardSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='카드 무이자 이벤트 정보';

CREATE TABLE `tGoods` (
  `nGoodsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격 기본정보 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nMakerSeq` int(10) unsigned NOT NULL default '0' COMMENT '제조사 고유키',
  `nProductSeq` int(10) unsigned NOT NULL default '0' COMMENT '다나와상품고유코드',
  `sGoodsName` varchar(200) default NULL COMMENT '상품명',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '가격',
  `nDeliveryPaymentType` tinyint(3) unsigned NOT NULL default '0' COMMENT '배송비 결제 시기',
  `nOptionUseType` tinyint(3) unsigned NOT NULL default '0' COMMENT '옵션사용 여부',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함,N:노출안함',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '상품가격상태 1:판매중, 2:판매완료,3:노출거부..',
  `nDeliveryPrice` int(10) unsigned NOT NULL default '0' COMMENT '배송비',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일자',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `dtUpdateDate` date NOT NULL default '0000-00-00' COMMENT '수정일자',
  `dtUpdateTime` time NOT NULL default '00:00:00' COMMENT '수정시간',
  PRIMARY KEY  (`nGoodsSeq`),
  KEY `fkdbBilling45` (`nSellerSeq`),
  KEY `idxMnMarketPlace` (`nMarketPlaceSeq`,`nProductSeq`,`nSellerSeq`),
  CONSTRAINT `fkdbBilling45` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling46` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='상품가격 기본정보';

CREATE TABLE `tGoodsAddition` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `sOriginName` varchar(20) NOT NULL default '' COMMENT '원산지 이름',
  `nAfterServiceType` tinyint(3) unsigned NOT NULL default '0' COMMENT 'a/s 여부',
  `dtSaleEnd` date NOT NULL default '0000-00-00' COMMENT '판매마감일',
  `sMemo` varchar(50) default '' COMMENT '판매점메모',
  `nBoxRatio` float(4,3) unsigned NOT NULL default '0.000' COMMENT '박스점유량',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling55` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 부가정보';

CREATE TABLE `tGoodsAdditionMintMall` (
  `nGoodsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격 기본정보 고유키',
  `sShortGoodsName` varchar(20) NOT NULL default '' COMMENT '짧은 상품명',
  `nDiscountRatio` tinyint(3) unsigned NOT NULL default '0' COMMENT '할인율',
  `sDayDiscountYN` char(1) NOT NULL default 'N' COMMENT '일일 할인율 사용여부',
  `nLimiteQuantity` tinyint(3) unsigned NOT NULL default '0' COMMENT '1인 구매수량',
  `dtStartDay` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '진행시작 일시',
  `dtEndDay` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '진행종료 일시',
  `dtDisplayDay` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '노출시작 일시',
  `dtUpdateDay` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '업데이트 일시',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling173` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='민트몰 상품 부가 정보';

CREATE TABLE `tGoodsAfterServiceCompany` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nAfterServiceCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'AS업체 고유번호',
  PRIMARY KEY  (`nGoodsSeq`),
  KEY `fkdbBilling63` (`nAfterServiceCompanySeq`),
  CONSTRAINT `fkdbBilling62` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling63` FOREIGN KEY (`nAfterServiceCompanySeq`) REFERENCES `tAfterServiceCompany` (`nAfterServiceCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 AS업체 정보';

CREATE TABLE `tGoodsApprovalLog` (
  `nGoodsApprovalLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 승인로그 고유번호',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 고유번호',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '상태값 1:승인, 2:승인거부..',
  `nAdminMemberSeq` int(10) unsigned default '0' COMMENT '관리자ID 고유번호',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nGoodsApprovalLogSeq`),
  KEY `fkdbBilling64` (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling64` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 승인로그 정보';

CREATE TABLE `tGoodsApprovalLogComment` (
  `nGoodsApprovalLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 승인로그 고유번호',
  `sComment` text NOT NULL COMMENT '상세내용',
  PRIMARY KEY  (`nGoodsApprovalLogSeq`),
  CONSTRAINT `fkdbBilling65` FOREIGN KEY (`nGoodsApprovalLogSeq`) REFERENCES `tGoodsApprovalLog` (`nGoodsApprovalLogSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 승인로그 상세 정보';

CREATE TABLE `tGoodsCardQuotaCommission` (
  `nGoodsCardQuotaCommissionSeq` int(10) unsigned NOT NULL auto_increment COMMENT '카드할부 수수료정보 고유키',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  PRIMARY KEY  (`nGoodsCardQuotaCommissionSeq`),
  KEY `fkdbBilling60` (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling60` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='카드할부 수수료정보';

CREATE TABLE `tGoodsDelivery` (
  `nGoodsDeliverySeq` int(10) unsigned NOT NULL,
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송방법 고유키',
  PRIMARY KEY  (`nGoodsSeq`),
  KEY `fkdbBilling59` (`nDeliverySeq`),
  CONSTRAINT `fkdbBilling59` FOREIGN KEY (`nDeliverySeq`) REFERENCES `tDelivery` (`nDeliverySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 배송정보';

CREATE TABLE `tGoodsDeliveryMethod` (
  `nGoodsDeliveryMethodSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 배송수단 고유키',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품기본정보 고유키',
  `nDeliveryMethodSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송수단 고유키',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  PRIMARY KEY  (`nGoodsDeliveryMethodSeq`),
  KEY `fkdbBilling159` (`nGoodsSeq`),
  KEY `fkdbBilling160` (`nDeliveryMethodSeq`),
  CONSTRAINT `fkdbBilling159` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling160` FOREIGN KEY (`nDeliveryMethodSeq`) REFERENCES `tDeliveryMethod` (`nDeliveryMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 배송수단 정보';

CREATE TABLE `tGoodsDeliveryPaymentDivision` (
  `nGoodsDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 배송비 지급수단 고유키',
  `nGoodsDeliveryMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 배송수단 고유키',
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송비 지급구분 고유키',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  PRIMARY KEY  (`nGoodsDeliveryPaymentDivisionSeq`),
  KEY `fkdbBilling161` (`nGoodsDeliveryMethodSeq`),
  KEY `fkdbBilling162` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling161` FOREIGN KEY (`nGoodsDeliveryMethodSeq`) REFERENCES `tGoodsDeliveryMethod` (`nGoodsDeliveryMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling162` FOREIGN KEY (`nDeliveryPaymentDivisionSeq`) REFERENCES `tDeliveryPaymentDivision` (`nDeliveryPaymentDivisionSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 배송비 지급수단 정보';

CREATE TABLE `tGoodsDescription` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `sGoodsDescription` text COMMENT '상품설명',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling56` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 상세설명';

CREATE TABLE `tGoodsDetail` (
  `nGoodsDetailSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 상세가격정보 고유번호',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유번호',
  `nProductDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 대표상품별 상세상품정보 고유번호',
  `sGoodsDetailName` varchar(200) default NULL COMMENT '상세상품명',
  `nGoodsDetailUserInputPrice` int(10) unsigned NOT NULL default '0' COMMENT '사용자입력가격',
  `nGoodsDetailPrice` int(10) NOT NULL default '0' COMMENT '판매등록가격 +,- 가능',
  `nProductType` tinyint(3) NOT NULL default '1' COMMENT '상품구분 1:기본상품, 2:선택상품, 3:옵션상품',
  PRIMARY KEY  (`nGoodsDetailSeq`),
  KEY `fkdbBilling66` (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling66` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='상품가격별 상세가격 정보';

CREATE TABLE `tGoodsDetailStock` (
  `nGoodsDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 상세가격정보 고유번호',
  `nDetailQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '재고수량',
  PRIMARY KEY  (`nGoodsDetailSeq`),
  CONSTRAINT `fkdbBilling67` FOREIGN KEY (`nGoodsDetailSeq`) REFERENCES `tGoodsDetail` (`nGoodsDetailSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 상세가격 재고정보';

CREATE TABLE `tGoodsDetailUserAuctionLog` (
  `nGoodsDetailUserAuctionLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 상세가격 사용자 액션로그 고유번호',
  `nGoodsDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 상세가격정보 고유번호',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '상태값 1:등록, 2:수정, 3:삭제',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '사용자 고유번호',
  `sMemberIP` varchar(39) NOT NULL default '' COMMENT '사용자 IP',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nGoodsDetailUserAuctionLogSeq`),
  KEY `fkdbBilling68` (`nGoodsDetailSeq`),
  CONSTRAINT `fkdbBilling68` FOREIGN KEY (`nGoodsDetailSeq`) REFERENCES `tGoodsDetail` (`nGoodsDetailSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 상세가격 사용자 액션로그 정보';

CREATE TABLE `tGoodsDetailUserAuctionLogComment` (
  `nGoodsDetailUserAuctionLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 승인로그 고유번호',
  `sComment` text NOT NULL COMMENT '상세내용',
  PRIMARY KEY  (`nGoodsDetailUserAuctionLogSeq`),
  CONSTRAINT `fkdbBilling69` FOREIGN KEY (`nGoodsDetailUserAuctionLogSeq`) REFERENCES `tGoodsDetailUserAuctionLog` (`nGoodsDetailUserAuctionLogSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 상세가격 사용자 액션로그 상세정보';

CREATE TABLE `tGoodsDiscount` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nDiscountPrice` int(10) unsigned NOT NULL default '0' COMMENT '할인가격',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkGoodsDiscount1` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품할인가격';

CREATE TABLE `tGoodsInfo` (
  `nProductSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 상품고유번호 예)tprod, tGoods...',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nCashLowPrice` int(10) unsigned NOT NULL default '0' COMMENT '현금최저가',
  `nCardLowPrice` int(10) unsigned NOT NULL default '0' COMMENT '카드최저가',
  `nSellerCount` int(10) unsigned NOT NULL default '0' COMMENT '업체수',
  PRIMARY KEY  (`nProductSeq`,`nMarketPlaceSeq`),
  KEY `fkGoodsInfo1` (`nMarketPlaceSeq`),
  CONSTRAINT `fkGoodsInfo1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대표상품정보';

CREATE TABLE `tGoodsInfoSpool` (
  `nProductSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 상품고유번호',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '16' COMMENT 'MP별 기본정보 고유키',
  `sType` enum('0','1','2') NOT NULL default '0' COMMENT '입력구분 0:오류값, 1:insert, 2:update',
  `dtCreateTime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일시',
  PRIMARY KEY  (`nProductSeq`,`nMarketPlaceSeq`),
  KEY `idxdtCreateTime` (`dtCreateTime`),
  KEY `fkGoodsInfoSpool1` (`nMarketPlaceSeq`),
  CONSTRAINT `fkGoodsInfoSpool1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='대표상품 갱신정보';

CREATE TABLE `tGoodsOptionItem` (
  `nGoodsOptionItemSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격별 옵션정보 고유키',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목 이름 정보 고유키',
  `nOptionNecessityType` tinyint(3) unsigned NOT NULL default '0' COMMENT '옵션 필수 선택여부',
  PRIMARY KEY  (`nGoodsOptionItemSeq`),
  KEY `fkdbBilling50` (`nGoodsSeq`),
  KEY `fkdbBilling51` (`nOptionItemSeq`),
  CONSTRAINT `fkdbBilling50` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling51` FOREIGN KEY (`nOptionItemSeq`) REFERENCES `tOptionItem` (`nOptionItemSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 옵션 항목 정보';

CREATE TABLE `tGoodsOptionItemAttribute` (
  `nGoodsOptionAttributeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격 옵션 항목별 속성 고유키',
  `nGoodsOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 옵션정보 고유키',
  `nOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목값 이름 정보 고유키',
  PRIMARY KEY  (`nGoodsOptionAttributeSeq`),
  KEY `fkdbBilling52` (`nGoodsOptionItemSeq`),
  KEY `fkdbBilling53` (`nOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling52` FOREIGN KEY (`nGoodsOptionItemSeq`) REFERENCES `tGoodsOptionItem` (`nGoodsOptionItemSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling53` FOREIGN KEY (`nOptionItemAttributeSeq`) REFERENCES `tOptionItemAttribute` (`nOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격 옵션 항목별 속성 정보';

CREATE TABLE `tGoodsOptionItemAttributePrice` (
  `nGoodsOptionAttributePriceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품가격 옵션 항목별 속성 가격정보 고유키',
  `nGoodsOptionAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 옵션 항목별 속성 고유키',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '옵션가격',
  PRIMARY KEY  (`nGoodsOptionAttributePriceSeq`),
  KEY `fkdbBilling54` (`nGoodsOptionAttributeSeq`),
  CONSTRAINT `fkdbBilling54` FOREIGN KEY (`nGoodsOptionAttributeSeq`) REFERENCES `tGoodsOptionItemAttribute` (`nGoodsOptionAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격 옵션 항목별 속성 가격정보';

CREATE TABLE `tGoodsOptionItemAttributeValue` (
  `nGoodsOptionAttribureValueSeq` int(10) unsigned NOT NULL auto_increment COMMENT '상품옵션 항목별 속성값 고유키',
  `nGoodsOptionAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품옵션값 정보 고유키',
  `nOptionItemAttributeValueSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목 속성값 고유키',
  `sAttributeValue` varchar(20) default NULL COMMENT '옵션 속성값',
  PRIMARY KEY  (`nGoodsOptionAttribureValueSeq`),
  KEY `fkGoodsOptionItemAttributeValue1` (`nGoodsOptionAttributeSeq`),
  KEY `fkGoodsOptionItemAttributeValue2` (`nOptionItemAttributeValueSeq`),
  CONSTRAINT `fkGoodsOptionItemAttributeValue1` FOREIGN KEY (`nGoodsOptionAttributeSeq`) REFERENCES `tGoodsOptionItemAttribute` (`nGoodsOptionAttributeSeq`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkGoodsOptionItemAttributeValue2` FOREIGN KEY (`nOptionItemAttributeValueSeq`) REFERENCES `tOptionItemAttributeValue` (`nOptionItemAttributeValueSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품옵션 항목별 속성 값';

CREATE TABLE `tGoodsSaleStatistics` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nSaleEndCount` smallint(5) unsigned NOT NULL default '0' COMMENT '총 누적 판매수',
  `nServiceScore` smallint(5) unsigned NOT NULL default '0' COMMENT '총 만족도 점수',
  `nPostScriptCount` smallint(5) unsigned NOT NULL default '0' COMMENT '총 구매후기수',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling61` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 판매통계 정보';

CREATE TABLE `tGoodsStock` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '판매수량',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling57` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별 재고정보';

CREATE TABLE `tGoodsWholeInfo` (
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nWholeSeq` int(10) unsigned NOT NULL default '0' COMMENT '총판고유키',
  `fCommissionRatio` float(3,1) unsigned NOT NULL default '0.0' COMMENT '마진율',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '가격',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  PRIMARY KEY  (`nGoodsSeq`),
  CONSTRAINT `fkGoodsWholeInfo` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='상품가격별총판정보';

CREATE TABLE `tGroupMarketPlace` (
  `nGroupMarketPlaceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '마텟플레이스 그룹 고유키',
  `sGroupName` varchar(30) NOT NULL default '' COMMENT '그룹명',
  PRIMARY KEY  (`nGroupMarketPlaceSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='마켓플레이스 그룹 정보';

CREATE TABLE `tLinkGroupMarketPlace` (
  `nLinkGroupMarketPlaceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '그룹별 마켓플레이스 연결 고유키',
  `nGroupMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT '마텟플레이스 그룹 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  PRIMARY KEY  (`nLinkGroupMarketPlaceSeq`),
  KEY `fkdbBilling140` (`nGroupMarketPlaceSeq`),
  KEY `fkdbBilling141` (`nMarketPlaceSeq`),
  CONSTRAINT `fkdbBilling140` FOREIGN KEY (`nGroupMarketPlaceSeq`) REFERENCES `tGroupMarketPlace` (`nGroupMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling141` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='그룹별 마켓플레이스 연결정보';

CREATE TABLE `tLinkMaketPlaceOptionItem` (
  `nLinkMarketPlaceOptinItemSeq` int(10) unsigned NOT NULL auto_increment COMMENT '마켓플레이스별 옵션항목 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL COMMENT 'MP별 기본정보 고유키',
  `nOptionItemSeq` int(10) unsigned NOT NULL COMMENT '옵션항목 이름 정보 고유키',
  PRIMARY KEY  (`nLinkMarketPlaceOptinItemSeq`),
  KEY `fkLinkMaketPlaceOptionItem1` (`nMarketPlaceSeq`),
  KEY `fkLinkMaketPlaceOptionItem2` (`nOptionItemSeq`),
  CONSTRAINT `fkLinkMaketPlaceOptionItem1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkLinkMaketPlaceOptionItem2` FOREIGN KEY (`nOptionItemSeq`) REFERENCES `tOptionItem` (`nOptionItemSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='마켓플레이스별 옵션항목';

CREATE TABLE `tLinkOrderGoodsTaxInvoiceReceipt` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nOrderTaxInvoiceReceiptSeq` int(10) unsigned NOT NULL default '0' COMMENT '세금계산서 발급 정보 고유키',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  KEY `fkdbBilling130` (`nOrderTaxInvoiceReceiptSeq`),
  CONSTRAINT `fkdbBilling129` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling130` FOREIGN KEY (`nOrderTaxInvoiceReceiptSeq`) REFERENCES `tOrderTaxInvoiceReceipt` (`nOrderTaxInvoiceReceiptSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 세금계산서 발급 정보';

CREATE TABLE `tLogAction` (
  `nLogActionSeq` int(10) unsigned NOT NULL auto_increment COMMENT '로그 액션 정보 고유키',
  `sLogActionName` varchar(20) default NULL COMMENT '로그 액션명',
  PRIMARY KEY  (`nLogActionSeq`),
  UNIQUE KEY `idxUsLogActionName` (`sLogActionName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='로그 액션 정보';

CREATE TABLE `tService` (
  `nServiceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '서비스 고유키',
  `sServiceName` varchar(50) NOT NULL COMMENT '서비스 명칭',
  PRIMARY KEY  (`nServiceSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='서비스구별';

CREATE TABLE `tMarketPlace` (
  `nMarketPlaceSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 기본정보 고유키',
  `sMarketPlaceName` varchar(20) default NULL COMMENT 'MP이름',
  `sMarketPlaceComment` varchar(100) default NULL COMMENT 'MP설명',
  `nAdminMemberGroupSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 관리자 그룹 고유키',
  `nServiceSeq` int(10) unsigned NOT NULL default '1' COMMENT '서비스 고유키',
  PRIMARY KEY  (`nMarketPlaceSeq`),
  UNIQUE KEY `idxUsMarketPlaceName` (`sMarketPlaceName`),
  KEY `FKtMarketPlace1` (`nServiceSeq`),
  CONSTRAINT `FKtMarketPlace1` FOREIGN KEY (`nServiceSeq`) REFERENCES `tService` (`nServiceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 기본정보';

CREATE TABLE `tMarketPlaceAccounting` (
  `nMarketPlaceAccountingSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 정산정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nAccountingCycleSeq` int(10) unsigned NOT NULL default '0' COMMENT '정산주기정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '정산수수료율',
  `nOrderStateSeq` int(10) unsigned NOT NULL COMMENT '주문상태정보 고유키',
  PRIMARY KEY  (`nMarketPlaceAccountingSeq`),
  KEY `fkdbBilling1` (`nMarketPlaceSeq`),
  KEY `fkdbBilling2` (`nAccountingCycleSeq`),
  KEY `fkdbBilling3` (`nPaymentMethodSeq`),
  KEY `fktMarketPlaceAccounting1` (`nOrderStateSeq`),
  CONSTRAINT `fkdbBilling1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling2` FOREIGN KEY (`nAccountingCycleSeq`) REFERENCES `tAccountingCycle` (`nAccountingCycleSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling3` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fktMarketPlaceAccounting1` FOREIGN KEY (`nOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 정산정보';

CREATE TABLE `tMarketPlaceAccountingScheduleLog` (
  `nMarketPlaceAccountingScheduleLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 정산 적용예정 로그 고유번호',
  `nMarketPlaceAccountingSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 정산정보 고유키',
  `dtScheduleDate` date NOT NULL default '0000-00-00' COMMENT '적용예정일',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '적용 정산수수료율',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '관리자 고유번호',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nMarketPlaceAccountingScheduleLogSeq`),
  KEY `fkdbBilling4` (`nMarketPlaceAccountingSeq`),
  CONSTRAINT `fkdbBilling4` FOREIGN KEY (`nMarketPlaceAccountingSeq`) REFERENCES `tMarketPlaceAccounting` (`nMarketPlaceAccountingSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 정산 적용 예정 로그 정보';

CREATE TABLE `tMarketPlaceAccountingScheduleLogComment` (
  `nMarketPlaceAccountingScheduleLogSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 정산 적용예정 로그 고유번호',
  `sComment` varchar(255) default NULL COMMENT '상세내용',
  PRIMARY KEY  (`nMarketPlaceAccountingScheduleLogSeq`),
  CONSTRAINT `fkdbBilling5` FOREIGN KEY (`nMarketPlaceAccountingScheduleLogSeq`) REFERENCES `tMarketPlaceAccountingScheduleLog` (`nMarketPlaceAccountingScheduleLogSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 정산 적용 예정 로그 상세정보';

CREATE TABLE `tMarketPlaceDeferredAccounting` (
  `nMarketPlaceDeferredAccounting` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 후불정산정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nAccountingCycleSeq` int(10) unsigned NOT NULL default '0' COMMENT '정산주기정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '정산수수료율',
  PRIMARY KEY  (`nMarketPlaceDeferredAccounting`),
  KEY `fkMarketPlaceDeferredAccounting1` (`nMarketPlaceSeq`),
  KEY `fkMarketPlaceDeferredAccounting2` (`nAccountingCycleSeq`),
  KEY `fkMarketPlaceDeferredAccounting3` (`nPaymentMethodSeq`),
  CONSTRAINT `fkMarketPlaceDeferredAccounting1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkMarketPlaceDeferredAccounting2` FOREIGN KEY (`nAccountingCycleSeq`) REFERENCES `tAccountingCycle` (`nAccountingCycleSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkMarketPlaceDeferredAccounting3` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 후불 정산정보';

CREATE TABLE `tMarketPlaceDelivery` (
  `nMarketPlaceDeliverySeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 배송방법 정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송방법 고유키',
  PRIMARY KEY  (`nMarketPlaceDeliverySeq`),
  KEY `fkdbBilling9` (`nMarketPlaceSeq`),
  KEY `fkdbBilling10` (`nDeliverySeq`),
  CONSTRAINT `fkdbBilling10` FOREIGN KEY (`nDeliverySeq`) REFERENCES `tDelivery` (`nDeliverySeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling9` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 배송방법 정보';

CREATE TABLE `tMarketPlaceDeliveryMethod` (
  `nMarketPlaceDeliveryMethodSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 배송수단 고유키',
  `nMarketPlaceDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 배송방법 고유키',
  `nDeliveryMethodSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송수단 고유키',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  PRIMARY KEY  (`nMarketPlaceDeliveryMethodSeq`),
  KEY `fkdbBilling150` (`nMarketPlaceDeliverySeq`),
  KEY `fkdbBilling151` (`nDeliveryMethodSeq`),
  CONSTRAINT `fkdbBilling150` FOREIGN KEY (`nMarketPlaceDeliverySeq`) REFERENCES `tMarketPlaceDelivery` (`nMarketPlaceDeliverySeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling151` FOREIGN KEY (`nDeliveryMethodSeq`) REFERENCES `tDeliveryMethod` (`nDeliveryMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 배송수단 정보';

CREATE TABLE `tMarketPlaceDeliveryPaymentDivision` (
  `nMarketPlaceDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 배송비 지급수단 고유키',
  `nMarketPlaceDeliveryMethodSeq` int(10) unsigned NOT NULL default '1' COMMENT 'MP별 배송수단 고유키',
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송비 지급구분 고유키',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  `nDeliveryPrice` int(10) unsigned NOT NULL default '0' COMMENT '배송비',
  PRIMARY KEY  (`nMarketPlaceDeliveryPaymentDivisionSeq`),
  KEY `fkdbBilling152` (`nMarketPlaceDeliveryMethodSeq`),
  KEY `fkdbBilling153` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling152` FOREIGN KEY (`nMarketPlaceDeliveryMethodSeq`) REFERENCES `tMarketPlaceDeliveryMethod` (`nMarketPlaceDeliveryMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling153` FOREIGN KEY (`nDeliveryPaymentDivisionSeq`) REFERENCES `tDeliveryPaymentDivision` (`nDeliveryPaymentDivisionSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 배송비 지급수단 정보';

CREATE TABLE `tMarketPlaceMember` (
  `nMarketPlaceMemberSeq` int(10) unsigned NOT NULL auto_increment COMMENT '마켓별 회원 고유번호',
  `sMemberKey` varchar(50) NOT NULL default '0' COMMENT '회원고유번호',
  `emStatus` enum('ACTIVE','INACTIVE') NOT NULL default 'ACTIVE' COMMENT '회원상태',
  `nServiceSeq` int(10) unsigned NOT NULL default '0' COMMENT '서비스 고유키',
  PRIMARY KEY  (`nMarketPlaceMemberSeq`),
  UNIQUE KEY `UIX_tMarketPlaceMember` (`nServiceSeq`,`sMemberKey`),
  CONSTRAINT `FKtMarketPlaceMember1` FOREIGN KEY (`nServiceSeq`) REFERENCES `tService` (`nServiceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='마켓별회원';

CREATE TABLE `tMarketPlaceMemberAuth` (
  `nMarketPlaceMemberAuthSeq` int(10) unsigned NOT NULL auto_increment COMMENT '마켓별회원마켓권한고유번호',
  `nMarketPlaceMemberSeq` int(10) unsigned NOT NULL COMMENT '마켓별 회원 고유번호',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP고유번호',
  `emStatus` enum('ACTIVE','INACTIVE') NOT NULL default 'ACTIVE' COMMENT '회원상태',
  PRIMARY KEY  (`nMarketPlaceMemberAuthSeq`),
  UNIQUE KEY `UIX_tMarketPlaceMemberAuth` (`nMarketPlaceSeq`,`nMarketPlaceMemberSeq`),
  KEY `FKtMarketPlaceMemberAuth1` (`nMarketPlaceMemberSeq`),
  CONSTRAINT `FKtMarketPlaceMemberAuth1` FOREIGN KEY (`nMarketPlaceMemberSeq`) REFERENCES `tMarketPlaceMember` (`nMarketPlaceMemberSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='마켓별회원마켓권한';

CREATE TABLE `tMarketPlaceMemberGrade` (
  `nMarketPlaceMemberGradeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '마켓별 회원등급 고유번호',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP고유번호',
  `sGradeName` varchar(50) NOT NULL default '' COMMENT '등급명',
  PRIMARY KEY  (`nMarketPlaceMemberGradeSeq`),
  KEY `fkMarketPlaceMemberGrade1` (`nMarketPlaceSeq`),
  CONSTRAINT `fkMarketPlaceMemberGrade1` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='마켓별회원등급';

CREATE TABLE `tMarketPlaceMemberGradeActionLog` (
  `nMarketPlaceMemberGradeActionLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '액션로그고유번호',
  `emType` enum('INSERT','UPDATE','DELETE') NOT NULL default 'INSERT' COMMENT '유형 INSERT, UPDATE, DELETE',
  `sTableName` varchar(50) NOT NULL default '' COMMENT '테이블명',
  `sTableField` varchar(50) NOT NULL default '' COMMENT '테이블필드명',
  `nTableSeq` int(10) unsigned NOT NULL default '0' COMMENT '테이블의 각고유번호',
  `sValueBefore` text COMMENT '변경전값',
  `sValueAfter` text COMMENT '변경후적용값',
  `dtDateTime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '변경일자',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '변경회원고유번호',
  `emApplyYN` enum('N','Y') NOT NULL default 'N' COMMENT '적용여부 N:적용안됨 Y:적용됨',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nMarketPlaceMemberGradeActionLogSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='마켓별회원등급액션로그';

CREATE TABLE `tMarketPlaceMemberGradeDetail` (
  `nMarketPlaceMemberGradeDetailSeq` int(10) unsigned NOT NULL auto_increment COMMENT '등급상세정보고유번호',
  `nMarketPlaceMemberGradeSeq` int(10) unsigned NOT NULL default '0' COMMENT '마켓별 회원등급 고유번호',
  `sGradeDetailName` varchar(50) NOT NULL default '' COMMENT '등급명',
  `nGradeDetailPeriod` tinyint(3) unsigned NOT NULL default '0' COMMENT '등급산출기간(월)',
  `sMemo` varchar(200) NOT NULL default '' COMMENT '메모',
  PRIMARY KEY  (`nMarketPlaceMemberGradeDetailSeq`),
  KEY `fkMarketPlaceMemberGradeDetail` (`nMarketPlaceMemberGradeSeq`),
  CONSTRAINT `fkMarketPlaceMemberGradeDetail` FOREIGN KEY (`nMarketPlaceMemberGradeSeq`) REFERENCES `tMarketPlaceMemberGrade` (`nMarketPlaceMemberGradeSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='등급상세정보';

CREATE TABLE `tMarketPlaceMemberGradeDetailAttribute` (
  `nMarketPlaceMemberGradeDetailAttributeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '등급상세정보 속성고유번호',
  `nMarketPlaceMemberGradeDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '등급상세정보고유번호',
  `emAttributeType` enum('DEFAULT','ADVERTISEMENT','ADD_DISCOUNT') NOT NULL default 'DEFAULT' COMMENT 'DEFAULT:기본등급 ADVERTISEMENT:광고등급 ADD_DISCOUNT: 추가 할인',
  PRIMARY KEY  (`nMarketPlaceMemberGradeDetailAttributeSeq`),
  KEY `fkMarketPlaceMemberGradeDetailAttribute` (`nMarketPlaceMemberGradeDetailSeq`),
  CONSTRAINT `fkMarketPlaceMemberGradeDetailAttribute` FOREIGN KEY (`nMarketPlaceMemberGradeDetailSeq`) REFERENCES `tMarketPlaceMemberGradeDetail` (`nMarketPlaceMemberGradeDetailSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='등급상세정보 속성';

CREATE TABLE `tMarketPlaceMemberGradeDetailBenefit` (
  `nMarketPlaceMemberGradeDetailBenefitSeq` int(10) unsigned NOT NULL auto_increment COMMENT '등급상세정보별혜택정보고유번호',
  `nMarketPlaceMemberGradeDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '등급상세정보고유번호',
  `emType` enum('TOTAL_GOODS_PRICE') NOT NULL default 'TOTAL_GOODS_PRICE' COMMENT '기준유형 TOTAL_GOODS_PRICE: 총 상품금액',
  `nMoreThanStandardValue` int(10) unsigned NOT NULL default '0' COMMENT 'more than 기준값',
  `emBenefitType` enum('UPDOWN_PERCENT_BENEFIT','DELIVERY_CHARGE_BENEFIT') NOT NULL default 'UPDOWN_PERCENT_BENEFIT' COMMENT '혜택유형 배송비를 제외한 결제금액에 대한 혜택  UPDOWN_PERCENT_BENEFIT: 증감률 혜택, DELIVERY_CHARGE_BENEFIT: 배송비 혜택',
  `emValueType` enum('PERCENT','PRICE') NOT NULL default 'PERCENT' COMMENT '적용값타입 PERCENT:%, PRICE: 금액',
  `fValue` float NOT NULL default '0' COMMENT '적용값 %(소수점 한자리) 또는 금액',
  PRIMARY KEY  (`nMarketPlaceMemberGradeDetailBenefitSeq`),
  KEY `fkMarketPlaceMemberGradeDetailBenefit` (`nMarketPlaceMemberGradeDetailSeq`),
  CONSTRAINT `fkMarketPlaceMemberGradeDetailBenefit` FOREIGN KEY (`nMarketPlaceMemberGradeDetailSeq`) REFERENCES `tMarketPlaceMemberGradeDetail` (`nMarketPlaceMemberGradeDetailSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='등급상세정보별혜택정보';

CREATE TABLE `tMarketPlaceMemberGradeDetailLink` (
  `nMarketPlaceMemberSeq` int(10) unsigned NOT NULL default '0' COMMENT '마켓별 회원 고유번호',
  `nMarketPlaceMemberGradeDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '등급상세정보고유번호',
  PRIMARY KEY  (`nMarketPlaceMemberSeq`,`nMarketPlaceMemberGradeDetailSeq`),
  KEY `fkMarketPlaceMemberGradeDetailLink2` (`nMarketPlaceMemberGradeDetailSeq`),
  CONSTRAINT `fkMarketPlaceMemberGradeDetailLink1` FOREIGN KEY (`nMarketPlaceMemberSeq`) REFERENCES `tMarketPlaceMember` (`nMarketPlaceMemberSeq`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkMarketPlaceMemberGradeDetailLink2` FOREIGN KEY (`nMarketPlaceMemberGradeDetailSeq`) REFERENCES `tMarketPlaceMemberGradeDetail` (`nMarketPlaceMemberGradeDetailSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='마켓별회원등급연결정보';

CREATE TABLE `tMarketPlaceMemberGradeDetailNextMonth` (
  `nMarketPlaceMemberGradeDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '등급상세정보고유번호',
  `sMarketPlaceMemberGradeDetailNextMonthInfo` text NOT NULL COMMENT '등급설정정보 XML',
  PRIMARY KEY  (`nMarketPlaceMemberGradeDetailSeq`),
  CONSTRAINT `fkMarketPlaceMemberGradeDetailNextMonth1` FOREIGN KEY (`nMarketPlaceMemberGradeDetailSeq`) REFERENCES `tMarketPlaceMemberGradeDetail` (`nMarketPlaceMemberGradeDetailSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='다음달반영등급상세정보';

CREATE TABLE `tMarketPlaceMemberGradeDetailStandard` (
  `nMarketPlaceMemberGradeDetailStandardSeq` int(10) unsigned NOT NULL auto_increment COMMENT '등급산출기준고유번호',
  `nMarketPlaceMemberGradeDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '등급상세정보고유번호',
  `emType` enum('TOTAL_PURCHASE_PRICE') NOT NULL default 'TOTAL_PURCHASE_PRICE' COMMENT '유형 TOTAL_PURCHASE_PRICE:구매금액',
  `nMoreThanStandardValue` int(10) unsigned NOT NULL default '0' COMMENT 'more than 기준값',
  `nUnderStandardValue` int(10) unsigned NOT NULL default '0' COMMENT 'under 기준값',
  PRIMARY KEY  (`nMarketPlaceMemberGradeDetailStandardSeq`),
  KEY `fkMarketPlaceMemberGradeDetailStandard1` (`nMarketPlaceMemberGradeDetailSeq`),
  CONSTRAINT `fkMarketPlaceMemberGradeDetailStandard1` FOREIGN KEY (`nMarketPlaceMemberGradeDetailSeq`) REFERENCES `tMarketPlaceMemberGradeDetail` (`nMarketPlaceMemberGradeDetailSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='등급산출기준';

CREATE TABLE `tMarketPlaceMemberMonthBuyPriceStatistics` (
  `nMarketPlaceMemberMonthBuyPriceStatisticsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '월별회원구매금액통계고유번호',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL COMMENT 'MP별 기본정보 고유키',
  `nMarketPlaceMemberSeq` int(10) unsigned NOT NULL default '0' COMMENT '마켓별 회원 고유번호',
  `dtDate` date NOT NULL default '0000-00-00' COMMENT '연월, 일은 1일로 무조건 설정',
  `nTotalBuyPrice` int(10) unsigned NOT NULL default '0' COMMENT '총구매금액',
  PRIMARY KEY  (`nMarketPlaceMemberMonthBuyPriceStatisticsSeq`),
  UNIQUE KEY `UIX_tMarketPlaceMemberStatistics` (`nMarketPlaceSeq`,`nMarketPlaceMemberSeq`,`dtDate`),
  KEY `fkMarketPlaceMemberMonthBuyPriceStatistics1` (`nMarketPlaceMemberSeq`),
  CONSTRAINT `fkMarketPlaceMemberMonthBuyPriceStatistics1` FOREIGN KEY (`nMarketPlaceMemberSeq`) REFERENCES `tMarketPlaceMember` (`nMarketPlaceMemberSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='월별회원구매금액통계 회원 등급을 산정하기 위한 구매금액 통계 테이블';

CREATE TABLE `tMarketPlacePayment` (
  `nMarketPlacePaymentSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 지원 결제정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nPayGateCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'PG사 고유키',
  `nPaymentMethodDefault` tinyint(3) unsigned NOT NULL default '1' COMMENT '기본선택 결제수단 1:기본선택 않함 ,2:기본선택',
  PRIMARY KEY  (`nMarketPlacePaymentSeq`),
  KEY `fkdbBilling6` (`nMarketPlaceSeq`),
  KEY `fkdbBilling7` (`nPaymentMethodSeq`),
  KEY `fkdbBilling8` (`nPayGateCompanySeq`),
  CONSTRAINT `fkdbBilling6` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling7` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling8` FOREIGN KEY (`nPayGateCompanySeq`) REFERENCES `tPayGateCompany` (`nPayGateCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 지원되는 결제업체 정보';

CREATE TABLE `tMarketPlacePaymentSettingInfo` (
  `nMarketPlacePaymentSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 지원 결제정보 고유키',
  `sPGCompanyMID` varchar(30) NOT NULL default '' COMMENT 'pg사 MID',
  `nTracePossibleBuyDecisionPeriod` tinyint(3) unsigned NOT NULL default '0' COMMENT '배송추적가능택배사 구매결정확인기간',
  `nTraceImpossibleBuyDecisionPeriod` tinyint(3) unsigned NOT NULL default '0' COMMENT '배송추적불가능택배사 구매결정확인기간',
  `nCardPaymentAccountingDate` tinyint(3) unsigned NOT NULL default '0' COMMENT '카드결제고정정산일',
  `nCashPaymentAccountingDate` tinyint(3) unsigned NOT NULL default '0' COMMENT '현금결제고정정산일',
  `emMonetaryUnit` enum('WON','USD') NOT NULL default 'WON' COMMENT '화폐단위 WON 또는 USD',
  `sKeyFilePassward` varchar(30) NOT NULL default '' COMMENT '키파일패스워드',
  PRIMARY KEY  (`nMarketPlacePaymentSeq`),
  CONSTRAINT `fkMarketPlacePaymentSettingInfo1` FOREIGN KEY (`nMarketPlacePaymentSeq`) REFERENCES `tMarketPlacePayment` (`nMarketPlacePaymentSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='마켓결제수단별설정정보';

CREATE TABLE `tMarketPlaceSectionRefundLimit` (
  `nMarketPlaceSectionRefundLimitSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 부분환불 제한정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `fSectionRefundLimitRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '부분환불 제한율',
  `nState` tinyint(3) unsigned NOT NULL default '1' COMMENT '상태값 1:사용, 2:중지..',
  PRIMARY KEY  (`nMarketPlaceSectionRefundLimitSeq`),
  KEY `fkdbBilling148` (`nMarketPlaceSeq`),
  CONSTRAINT `fkdbBilling148` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 부분환불 제한 정보';

CREATE TABLE `tMarketPlaceSectionRefundLimitLog` (
  `nMarketPlaceSectionRefundLimitLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 부분환불 제한 로그정보 고유키',
  `nMarketPlaceSectionRefundLimitSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 부분환불 제한정보 고유키',
  `fBeforeSectionRefundLimitRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '이전 부분환불 제한율',
  `nBeforeState` tinyint(3) unsigned NOT NULL default '1' COMMENT '이전 상태값 1:사용, 2:중지..',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '등록자 고유키',
  `sMemberIP` varchar(39) NOT NULL default '' COMMENT '등록자 IP',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nMarketPlaceSectionRefundLimitLogSeq`),
  KEY `fkdbBilling149` (`nMarketPlaceSectionRefundLimitSeq`),
  CONSTRAINT `fkdbBilling149` FOREIGN KEY (`nMarketPlaceSectionRefundLimitSeq`) REFERENCES `tMarketPlaceSectionRefundLimit` (`nMarketPlaceSectionRefundLimitSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 부분환불 제한 로그 정보';

CREATE TABLE `tMarketPlaceSeller` (
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 정보 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 상품가격상태 0:운영중,1:일시정지,2:영구정지...',
  PRIMARY KEY  (`nMarketPlaceSellerSeq`),
  KEY `fkdbBilling29` (`nSellerSeq`),
  KEY `fkdbBilling30` (`nMarketPlaceSeq`),
  CONSTRAINT `fkdbBilling29` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling30` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 정보';

CREATE TABLE `tMarketPlaceSellerAddress` (
  `nMarketPlaceSellerAddressSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 주소정보 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `sAddressTitle` varchar(30) default NULL COMMENT '주소 구분제목',
  `sAddress1` varchar(100) default NULL COMMENT '주소',
  `sAddress2` varchar(100) default NULL COMMENT '상세주소',
  `sZipCode1` varchar(3) default NULL COMMENT '우편번호1',
  `sZipCode2` varchar(3) default NULL COMMENT '우편번호2',
  `sPhoneNumber` varchar(20) default NULL COMMENT '전화',
  `sCellularPhoneNumber` varchar(13) default NULL COMMENT '핸드폰',
  PRIMARY KEY  (`nMarketPlaceSellerAddressSeq`),
  KEY `fkdbBilling35` (`nMarketPlaceSellerSeq`),
  CONSTRAINT `fkdbBilling35` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 주소정보';

CREATE TABLE `tMarketPlaceSellerAfterServiceCompany` (
  `nMarketPlaceSellerAfterServiceCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 AS업체정보 고유번호',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nAfterServiceCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'AS업체 고유번호',
  `sAgencyCode` varchar(8) NOT NULL default '' COMMENT '가맹점고유키',
  `sAgencyUserID` varchar(20) NOT NULL default '' COMMENT '대표기사ID',
  `sAgencyUserPW` varchar(10) NOT NULL default '' COMMENT '대표기사PW',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY  (`nMarketPlaceSellerAfterServiceCompanySeq`),
  KEY `fkdbBilling40` (`nMarketPlaceSellerSeq`),
  KEY `fkdbBilling41` (`nAfterServiceCompanySeq`),
  CONSTRAINT `fkdbBilling40` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling41` FOREIGN KEY (`nAfterServiceCompanySeq`) REFERENCES `tAfterServiceCompany` (`nAfterServiceCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 AS업체정보';

CREATE TABLE `tMarketPlaceSellerDeferredCommission` (
  `nMarketPlaceSellerDeferredCommissionSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 상품별 후불 수수료 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '수수료율',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품기본정보 고유키',
  `dtConfirmStartDate` date NOT NULL default '0000-00-00' COMMENT '적용시작일',
  `dtConfirmEndDate` date NOT NULL default '0000-00-00' COMMENT '적용종료일',
  PRIMARY KEY  (`nMarketPlaceSellerDeferredCommissionSeq`),
  KEY `fkMarketPlaceSellerDeferredCommission1` (`nMarketPlaceSellerSeq`),
  KEY `fkMarketPlaceSellerDeferredCommission2` (`nPaymentMethodSeq`),
  KEY `fkMarketPlaceSellerDeferredCommission3` (`nGoodsSeq`),
  CONSTRAINT `fkMarketPlaceSellerDeferredCommission1` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkMarketPlaceSellerDeferredCommission2` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkMarketPlaceSellerDeferredCommission3` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 상품별 후불 수수료 정보';

CREATE TABLE `tMarketPlaceSellerDelivery` (
  `nMarketPlaceSellerDeliverySeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 배송정보 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송방법 고유키',
  PRIMARY KEY  (`nMarketPlaceSellerDeliverySeq`),
  KEY `fkdbBilling31` (`nMarketPlaceSellerSeq`),
  KEY `fkdbBilling32` (`nDeliverySeq`),
  CONSTRAINT `fkdbBilling31` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling32` FOREIGN KEY (`nDeliverySeq`) REFERENCES `tDelivery` (`nDeliverySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 배송정보';

CREATE TABLE `tMarketPlaceSellerDeliveryCompany` (
  `nMarketPlaceSellerDeliveryCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 배송방법별 업체 고유번호',
  `nMarketPlaceSellerDeliveryMethodSeq` int(10) unsigned NOT NULL default '1' COMMENT 'MP별 판매자 배송비 지급구분 정보',
  `nMarketPlaceSellerDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 배송정보 고유키',
  `nDeliveryCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송업체 고유번호',
  PRIMARY KEY  (`nMarketPlaceSellerDeliveryCompanySeq`),
  KEY `fkdbBilling33` (`nMarketPlaceSellerDeliverySeq`),
  KEY `fkdbBilling34` (`nDeliveryCompanySeq`),
  KEY `fkdbBilling158` (`nMarketPlaceSellerDeliveryMethodSeq`),
  CONSTRAINT `fkdbBilling158` FOREIGN KEY (`nMarketPlaceSellerDeliveryMethodSeq`) REFERENCES `tMarketPlaceSellerDeliveryMethod` (`nMarketPlaceSellerDeliveryMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling34` FOREIGN KEY (`nDeliveryCompanySeq`) REFERENCES `tDeliveryCompany` (`nDeliveryCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 배송방법별 업체정보';

CREATE TABLE `tMarketPlaceSellerDeliveryMethod` (
  `nMarketPlaceSellerDeliveryMethodSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 배송수단 고유키',
  `nMarketPlaceSellerDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 배송방법 고유키',
  `nDeliveryMethodSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송수단 고유키',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  PRIMARY KEY  (`nMarketPlaceSellerDeliveryMethodSeq`),
  KEY `fkdbBilling154` (`nMarketPlaceSellerDeliverySeq`),
  KEY `fkdbBilling155` (`nDeliveryMethodSeq`),
  CONSTRAINT `fkdbBilling154` FOREIGN KEY (`nMarketPlaceSellerDeliverySeq`) REFERENCES `tMarketPlaceSellerDelivery` (`nMarketPlaceSellerDeliverySeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling155` FOREIGN KEY (`nDeliveryMethodSeq`) REFERENCES `tDeliveryMethod` (`nDeliveryMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 배송수단 정보';

CREATE TABLE `tMarketPlaceSellerDeliveryPaymentDivision` (
  `nMarketPlaceSellerDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 배송비 지급수단 고유키',
  `nMarketPlaceSellerDeliveryMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 배송수단 고유키',
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송비 지급구분 고유키',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함, N:노출안함',
  PRIMARY KEY  (`nMarketPlaceSellerDeliveryPaymentDivisionSeq`),
  KEY `fkdbBilling156` (`nMarketPlaceSellerDeliveryMethodSeq`),
  KEY `fkdbBilling157` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling156` FOREIGN KEY (`nMarketPlaceSellerDeliveryMethodSeq`) REFERENCES `tMarketPlaceSellerDeliveryMethod` (`nMarketPlaceSellerDeliveryMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling157` FOREIGN KEY (`nDeliveryPaymentDivisionSeq`) REFERENCES `tDeliveryPaymentDivision` (`nDeliveryPaymentDivisionSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 배송비 지급수단 정보';

CREATE TABLE `tMarketPlaceSellerDescription` (
  `nMarketPlaceSellerDescriptionSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 부가정보 고유번호',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nDescriptionType` tinyint(3) unsigned NOT NULL default '1' COMMENT '부가정보 구분 1:반품일, 2:AS정보, 3:교환/반품정보, 4:공지정보, 5:행사정보, 6:판매점 소개, 7:배송정책',
  `sDescription` varchar(150) default NULL COMMENT '부가정보',
  PRIMARY KEY  (`nMarketPlaceSellerDescriptionSeq`),
  KEY `fkdbBilling42` (`nMarketPlaceSellerSeq`),
  CONSTRAINT `fkdbBilling42` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 부가정보';

CREATE TABLE `tMarketPlaceSellerGoodsCommission` (
  `nMarketPlaceSellerGoodsCommissionSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 상품별 수수료 정보 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '수수료율',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품기본정보 고유키',
  `dtConfirmStartDate` date NOT NULL default '0000-00-00' COMMENT '적용시작일',
  `dtConfirmEndDate` date NOT NULL default '0000-00-00' COMMENT '적용종료일',
  PRIMARY KEY  (`nMarketPlaceSellerGoodsCommissionSeq`),
  KEY `fkdbBilling47` (`nMarketPlaceSellerSeq`),
  KEY `fkdbBilling48` (`nPaymentMethodSeq`),
  KEY `fkdbBilling49` (`nGoodsSeq`),
  CONSTRAINT `fkdbBilling47` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling48` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling49` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 상품별 수수료 정보';

CREATE TABLE `tMarketPlaceSellerMaster` (
  `nMarketPlaceSellerMasterSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 담당 정보 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `sMasterTitle` varchar(30) default NULL COMMENT '담당자 구분제목',
  `sMasterName` varchar(10) default NULL COMMENT '담당자',
  `sPhoneNumber` varchar(14) default NULL COMMENT '담당자 전화번호',
  `sExtensionNumber` varchar(10) default NULL COMMENT '내선번호',
  `sCellularPhoneNumber` varchar(13) default NULL COMMENT '담당자 핸드폰 번호',
  `sEmail` varchar(50) default NULL COMMENT '이메일',
  PRIMARY KEY  (`nMarketPlaceSellerMasterSeq`),
  KEY `fkdbBilling36` (`nMarketPlaceSellerSeq`),
  CONSTRAINT `fkdbBilling36` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 담당 정보';

CREATE TABLE `tMarketPlaceSellerPayment` (
  `nMarketPlaceSellerPaymentSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 결제수단별 정보 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nAccountingCycleSeq` int(10) unsigned NOT NULL default '0' COMMENT '정산주기정보 고유키',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '수수료율',
  PRIMARY KEY  (`nMarketPlaceSellerPaymentSeq`),
  KEY `fkdbBilling37` (`nMarketPlaceSellerSeq`),
  KEY `fkdbBilling38` (`nPaymentMethodSeq`),
  KEY `fkdbBilling39` (`nAccountingCycleSeq`),
  CONSTRAINT `fkdbBilling37` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling38` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling39` FOREIGN KEY (`nAccountingCycleSeq`) REFERENCES `tAccountingCycle` (`nAccountingCycleSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 결제수단별 정보';

CREATE TABLE `tMarketPlaceSellerPaymentDeferred` (
  `nMarketPlaceSellerPaymentDeferredSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP별 판매자 결제수단멸 후불 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nAccountingCycleSeq` int(10) unsigned NOT NULL default '0' COMMENT '정산주기정보 고유키',
  `fCommissionRatio` float(4,2) unsigned NOT NULL default '0.00' COMMENT '수수료율',
  PRIMARY KEY  (`nMarketPlaceSellerPaymentDeferredSeq`),
  KEY `fkdMarketPlaceSellerPaymentDeferred1` (`nMarketPlaceSellerSeq`),
  KEY `fkdMarketPlaceSellerPaymentDeferred2` (`nPaymentMethodSeq`),
  KEY `fkdMarketPlaceSellerPaymentDeferred3` (`nAccountingCycleSeq`),
  CONSTRAINT `fkdMarketPlaceSellerPaymentDeferred1` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdMarketPlaceSellerPaymentDeferred2` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdMarketPlaceSellerPaymentDeferred3` FOREIGN KEY (`nAccountingCycleSeq`) REFERENCES `tAccountingCycle` (`nAccountingCycleSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP별 판매자 결제수단별 후불정보';

CREATE TABLE `tMarketPlaceSellerStateLog` (
  `nMarketPlaceSellerStateLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'MP 판매자별 상품가격상태정보 고유키',
  `nMarketPlaceSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 정보 고유키',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 상품가격상태 0:운영중,1:일시정지,2:영구정지',
  `dtStartDate` date NOT NULL default '0000-00-00' COMMENT '상태변경 시작일자',
  `dtEndDate` date NOT NULL default '0000-00-00' COMMENT '상태변경 종료일자',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일자',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '처리자 회원 SEQ',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nMarketPlaceSellerStateLogSeq`),
  KEY `fktMarketPlaceSellerStateLog1` (`nMarketPlaceSellerSeq`),
  CONSTRAINT `fktMarketPlaceSellerStateLog1` FOREIGN KEY (`nMarketPlaceSellerSeq`) REFERENCES `tMarketPlaceSeller` (`nMarketPlaceSellerSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='MP 판매자별 상품가격상태 로그정보';

CREATE TABLE `tMarketPlaceSetting` (
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP고유번호',
  `emMarketPlaceMemberUsedYN` enum('Y','N') NOT NULL default 'N' COMMENT '마켓별회원사용여부 Y, N',
  `emMarketPlaceBoxRatioUsedYN` enum('Y','N') NOT NULL default 'N' COMMENT '마켓별 박스점유량 사용여부',
  `emMarketPlaceMiniShopUsedYN` enum('Y','N') NOT NULL default 'N' COMMENT '미니샵 이용여부',
  `emMarketPlaceGoodsInfoUsedYN` enum('Y','N') NOT NULL default 'N' COMMENT '대표상품정보 사용여부',
  `emMarketPlaceProductBlogDisplayYN` enum('Y','N') NOT NULL default 'N' COMMENT '다나와 상품블로그 노출여부',
  `emMarketPlaceAssembleYN` enum('Y','N') NOT NULL default 'N' COMMENT '견적상품 여부',
  `emMarketPlaceCartUsedYN` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '마켓별 장바구니 사용여부',
  `emMarketPlaceCartQuantityChangesUsedYN` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '마켓별 장바구니 수량변경 사용여부',
  `emMarketPlaceGoodsDiscountUsedYN` enum('Y','N') NOT NULL DEFAULT 'N' COMMENT '마켓별 추가할인 사용 여부',
  `emMarketPlacePaymentUsedYN` enum('Y','N') NOT NULL DEFAULT 'Y' COMMENT '마켓별 결제 사용여부',
  PRIMARY KEY  (`nMarketPlaceSeq`),
  CONSTRAINT `fkMarketPlaceSetting` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='마켓별설정';

CREATE TABLE `tOptionItem` (
  `nOptionItemSeq` int(10) unsigned NOT NULL auto_increment COMMENT '옵션항목 이름 정보 고유키',
  `sOptionName` varchar(20) default NULL COMMENT '옵션명',
  PRIMARY KEY  (`nOptionItemSeq`),
  UNIQUE KEY `idxUsOptionName` (`sOptionName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='옵션항목 이름 정보';

CREATE TABLE `tOptionItemAttribute` (
  `nOptionItemAttributeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '옵션항목값 이름 정보 고유키',
  `sOptionAttributeName` varchar(20) default NULL COMMENT '옵션 항목별 속성명 ',
  `nOptionItemSeq` int(10) unsigned NOT NULL COMMENT '옵션항목 이름 정보 고유키',
  PRIMARY KEY  (`nOptionItemAttributeSeq`),
  KEY `fkOptionItemAttribute1` (`nOptionItemSeq`),
  CONSTRAINT `fkOptionItemAttribute1` FOREIGN KEY (`nOptionItemSeq`) REFERENCES `tOptionItem` (`nOptionItemSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='옵션항목 속성 이름 정보';

CREATE TABLE `tOptionItemAttributeValue` (
  `nOptionItemAttributeValueSeq` int(10) unsigned NOT NULL auto_increment COMMENT '옵션항목 속성값 고유키',
  `nOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목값 이름 정보 고유키',
  `sOptionAttributeValue` varchar(20) default NULL COMMENT '옵션 항목 속성 값',
  PRIMARY KEY  (`nOptionItemAttributeValueSeq`),
  KEY `fkOptionItemAttributeValue1` (`nOptionItemAttributeSeq`),
  CONSTRAINT `fkOptionItemAttributeValue1` FOREIGN KEY (`nOptionItemAttributeSeq`) REFERENCES `tOptionItemAttribute` (`nOptionItemAttributeSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='옵션항목 속성 값 정보';

CREATE TABLE `tOrder` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL COMMENT '주문번호',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송방법 고유키',
  `nOrderStateSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상태정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `sMemberId` varchar(20) default NULL COMMENT '주문회원 아이디',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '주문회원 고유키',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `sMemberIP` varchar(39) default NULL COMMENT '주문회원 아이피',
  `nPrice` int(10) default NULL COMMENT '결제금액',
  `nVoucherSeq` int(10) unsigned NOT NULL default '0' COMMENT '증빙서류 고유키',
  `nTotalDeliveryPrice` int(10) unsigned NOT NULL default '0' COMMENT '총 배송비금액',
  `sIDNumber` varchar(64) default '' COMMENT '주문자 주민번호',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderNumberSeq`),
  KEY `fkdbBilling94` (`nPaymentMethodSeq`),
  KEY `fkdbBilling95` (`nDeliverySeq`),
  KEY `fkdbBilling96` (`nVoucherSeq`),
  KEY `fkdbBilling97` (`nOrderStateSeq`),
  KEY `fkdbBilling98` (`nMarketPlaceSeq`),
  CONSTRAINT `fkdbBilling94` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling95` FOREIGN KEY (`nDeliverySeq`) REFERENCES `tDelivery` (`nDeliverySeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling96` FOREIGN KEY (`nVoucherSeq`) REFERENCES `tVoucher` (`nVoucherSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling97` FOREIGN KEY (`nOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling98` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문정보';

CREATE TABLE `tOrderAdvertisementInfo` (
  `nOrderAdvertisementInfoSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문별 광고정보 고유키',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호고유키',
  `nAdvertisementSeq` int(10) unsigned NOT NULL default '0' COMMENT '광고 고유키',
  PRIMARY KEY  (`nOrderAdvertisementInfoSeq`),
  KEY `fkdbBilling145` (`nOrderNumberSeq`),
  KEY `fkdbBilling146` (`nAdvertisementSeq`),
  CONSTRAINT `fkdbBilling145` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling146` FOREIGN KEY (`nAdvertisementSeq`) REFERENCES `tAdvertisement` (`nAdvertisementSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문별 광고 정보';

CREATE TABLE `tOrderCashReceipt` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nRequestType` tinyint(3) unsigned NOT NULL default '0' COMMENT '요청유형(1.소득공제용, 2.지출증빙용)',
  `nRequestNumberType` tinyint(3) unsigned NOT NULL default '0' COMMENT '신청자 고유번호 구분(1.주민번호 2.사업자번호 3.휴대폰번호 4.카드번호)',
  `sRequestNumber` varchar(64) default NULL COMMENT '신청자 고유번호',
  `sName` varchar(10) default NULL COMMENT '신청자 명',
  `sEmail` varchar(158) default NULL COMMENT '신청자 이메일',
  `sCellularPhoneNumber` varchar(68) default NULL COMMENT '신청자 핸드폰',
  PRIMARY KEY  (`nOrderNumberSeq`),
  CONSTRAINT `fkdbBilling105` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문별 현금영수증 신청 정보';

CREATE TABLE `tOrderCashReceiptRequest` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문 번호',
  `nCashReceiptType` tinyint(3) unsigned NOT NULL default '0' COMMENT '현금 영수증 구분(1.선불배송비)',
  `nRequestType` tinyint(3) unsigned NOT NULL default '0' COMMENT '요청 구분(1.발급 요청, 2.취소 요청)',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '신청 상태(1:신청, 2:대기, 3:완료, 4:실패)',
  `dtDate` date NOT NULL default '0000-00-00' COMMENT '업데이트 일',
  `dtTime` time NOT NULL default '00:00:00' COMMENT '업데이트 시간',
  PRIMARY KEY  (`nOrderNumberSeq`,`nCashReceiptType`),
  CONSTRAINT `fkOrderCashReceiptRequest1` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 현금 영수증 신청';

CREATE TABLE `tOrderCashReceiptRequestLog` (
  `nOrderCashReceiptRequestLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 현금 영수증 신청 기록 번호',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문 번호',
  `nCashReceiptType` tinyint(3) unsigned NOT NULL default '0' COMMENT '현금 영수증 구분(1.선불배송비)',
  `nRequestType` tinyint(3) unsigned NOT NULL default '0' COMMENT '요청 구분(1.발급 요청, 2.취소 요청)',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '신청 상태(1:신청, 2:대기, 3:완료, 4:실패)',
  `nIssueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '발급 시점(1:결제 후, 2:구매 결정 후, 3:관리자확인후)',
  `sComment` varchar(100) default NULL COMMENT '실패 사유',
  `dtDate` date NOT NULL default '0000-00-00' COMMENT '등록 일',
  `dtTime` time NOT NULL default '00:00:00' COMMENT '등록 시간',
  PRIMARY KEY  (`nOrderCashReceiptRequestLogSeq`),
  KEY `fkOrderCashReceiptRequestLog1` (`nOrderNumberSeq`),
  CONSTRAINT `fkOrderCashReceiptRequestLog1` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrderCashReceiptRequest` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문 현금 영수증 신청 기록';

CREATE TABLE `tOrderCostCommissionLog` (
  `nOrderCostCommissionLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '정산및 원가 수수료 관련 로그 정보 고유키',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nSaleCommissionType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용된 판매수수료 구분 (1:원가 수수료 결제 재한, 2:MP별 원가 수수료)',
  `nValueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용값 타입',
  `fValue` float(4,2) unsigned NOT NULL default '0.00' COMMENT '적용값',
  `nCommissionPrice` int(10) unsigned NOT NULL default '0' COMMENT '수수료율 적용금액',
  PRIMARY KEY  (`nOrderCostCommissionLogSeq`),
  KEY `fkdbBilling123` (`nOrderNumberSeq`),
  KEY `fkdbBilling124` (`nPaymentMethodSeq`),
  CONSTRAINT `fkdbBilling123` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling124` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문상품별 원가 수수료 관련 로그 정보';

CREATE TABLE `tOrderGoods` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 고유키',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품기본정보 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nOrderStateSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상태정보 고유키',
  `sGoodsName` varchar(200) default NULL COMMENT '상품명',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '상품가격',
  `nCommissionPrice` int(10) unsigned NOT NULL default '0' COMMENT '수수료 적용금액',
  `nQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '갯수',
  `nDeliveryPrice` int(10) unsigned NOT NULL default '0' COMMENT '배송비',
  `nDeliveryMethodSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송수단 고유키',
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송비 지급구분 고유키',
  `nDiscountPrice` int(10) NOT NULL default '0' COMMENT '할인금액',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  KEY `fkdbBilling107` (`nOrderNumberSeq`),
  KEY `fkdbBilling108` (`nGoodsSeq`),
  KEY `fkdbBilling109` (`nSellerSeq`),
  KEY `fkdbBilling110` (`nOrderStateSeq`),
  KEY `fkdbBilling163` (`nDeliveryMethodSeq`),
  KEY `fkdbBilling164` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling107` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tTemporaryOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling108` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling109` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling110` FOREIGN KEY (`nOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling163` FOREIGN KEY (`nDeliveryMethodSeq`) REFERENCES `tDeliveryMethod` (`nDeliveryMethodSeq`),
  CONSTRAINT `fkdbBilling164` FOREIGN KEY (`nDeliveryPaymentDivisionSeq`) REFERENCES `tDeliveryPaymentDivision` (`nDeliveryPaymentDivisionSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문상품';

CREATE TABLE `tOrderGoodsAfterService` (
  `nOrderGoodsAfterServiceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품별 AS정보 고유번호',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nAfterServiceCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'AS업체 고유번호',
  `sSerialNumber` varchar(20) NOT NULL default '' COMMENT 'AS시리얼번호',
  `nState` tinyint(3) NOT NULL default '1' COMMENT '목록상태 1:등록, 2:성공, 3:취소',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  PRIMARY KEY  (`nOrderGoodsAfterServiceSeq`),
  KEY `fkdbBilling131` (`nOrderGoodsSeq`),
  KEY `fkdbBilling132` (`nAfterServiceCompanySeq`),
  CONSTRAINT `fkdbBilling131` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling132` FOREIGN KEY (`nAfterServiceCompanySeq`) REFERENCES `tAfterServiceCompany` (`nAfterServiceCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 AS정보';

CREATE TABLE `tOrderGoodsBuyerComment` (
  `nBuyerCommentSeq` int(10) unsigned NOT NULL auto_increment COMMENT '구매자 상품별 커멘트 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nOrderStateSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상태정보 고유키',
  `sComment` varchar(255) default NULL COMMENT '사유',
  PRIMARY KEY  (`nBuyerCommentSeq`),
  KEY `fkOrderGoodsBuyerComment1` (`nOrderGoodsSeq`),
  KEY `fkOrderGoodsBuyerComment2` (`nOrderStateSeq`),
  CONSTRAINT `fkOrderGoodsBuyerComment1` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkOrderGoodsBuyerComment2` FOREIGN KEY (`nOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='구매자 주문 상품별 사유';

CREATE TABLE `tOrderGoodsBuyingPostScript` (
  `nOrderGoodsBuyingPostScriptSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품별 구매후기 고유번호',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유번호',
  `sTitle` varchar(100) NOT NULL default '' COMMENT '제목',
  `nTotalScore` smallint(5) unsigned NOT NULL default '0' COMMENT '총점수',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '구매자 고유번호',
  `sMemberID` varchar(20) NOT NULL default '' COMMENT '구매자 ID',
  `sMemberNick` varchar(20) NOT NULL default '' COMMENT '구매자 닉네임',
  `sMemberIP` varchar(39) NOT NULL default '' COMMENT '구매자 IP',
  `nState` tinyint(3) NOT NULL default '1' COMMENT '상태값 1:노출, 2:숨김, 3:삭제',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일자',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderGoodsBuyingPostScriptSeq`),
  KEY `fkdbBilling133` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling133` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문상품별 구매후기 정보';

CREATE TABLE `tOrderGoodsBuyingPostScriptDescription` (
  `nOrderGoodsBuyingPostScriptSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 구매후기별 고유번호',
  `sDescription` varchar(255) default NULL COMMENT '상세내용',
  PRIMARY KEY  (`nOrderGoodsBuyingPostScriptSeq`),
  CONSTRAINT `fkdbBilling134` FOREIGN KEY (`nOrderGoodsBuyingPostScriptSeq`) REFERENCES `tOrderGoodsBuyingPostScript` (`nOrderGoodsBuyingPostScriptSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 구매후기별 상세정보';

CREATE TABLE `tOrderGoodsBuyingPostScriptFile` (
  `nOrderGoodsBuyingPostScriptFileSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 구매후기별 파일정보 고유번호',
  `nOrderGoodsBuyingPostScriptSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품별 구매후기 고유번호',
  `sSaveName` varchar(50) NOT NULL default '' COMMENT '저장파일명',
  `nSort` tinyint(3) unsigned NOT NULL default '1' COMMENT '노출 순서',
  PRIMARY KEY  (`nOrderGoodsBuyingPostScriptFileSeq`),
  KEY `fkdbBilling135` (`nOrderGoodsBuyingPostScriptSeq`),
  CONSTRAINT `fkdbBilling135` FOREIGN KEY (`nOrderGoodsBuyingPostScriptSeq`) REFERENCES `tOrderGoodsBuyingPostScript` (`nOrderGoodsBuyingPostScriptSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 구매후기별 파일정보';

CREATE TABLE `tOrderGoodsBuyingPostScriptScore` (
  `nOrderGoodsBuyingPostScriptScoreSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 구매후기별 만족도 고유번호',
  `nOrderGoodsBuyingPostScriptSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품별 구매후기 고유번호',
  `nScoreType` tinyint(3) unsigned NOT NULL default '1' COMMENT '평가구분 1:품질, 2:배송, 3:가격..',
  `nScore` tinyint(3) unsigned NOT NULL default '1' COMMENT '평가점수',
  PRIMARY KEY  (`nOrderGoodsBuyingPostScriptScoreSeq`),
  KEY `fkdbBilling136` (`nOrderGoodsBuyingPostScriptSeq`),
  CONSTRAINT `fkdbBilling136` FOREIGN KEY (`nOrderGoodsBuyingPostScriptSeq`) REFERENCES `tOrderGoodsBuyingPostScript` (`nOrderGoodsBuyingPostScriptSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문상품 구매후기별 만족도 정보';

CREATE TABLE `tOrderGoodsCashReceipt` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `sRequestNumber` varchar(40) default NULL COMMENT '신청 완료 번호 ',
  `nRequestType` tinyint(3) unsigned NOT NULL default '0' COMMENT '요청 구분(1.신청요청, 2.취소요청)',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '신청 상태(1:신청, 2:대기, 3:완료, 4:실패)',
  `dtDate` date NOT NULL default '0000-00-00' COMMENT '업데이트 일',
  `dtTime` time NOT NULL default '00:00:00' COMMENT '업데이트 시간',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling128` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 상품별 현금영수증 정보';

CREATE TABLE `tOrderGoodsCashReceiptLog` (
  `nOrderGoodsCashReceiptLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT 'tOrderGoodsCashReceiptLog',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `sRequestNumber` varchar(40) default NULL COMMENT '신청 완료 번호 ',
  `nRequestType` tinyint(3) unsigned NOT NULL default '0' COMMENT '요청 구분(1.신청요청, 2.취소요청)',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '신청 상태(1:신청, 2:대기, 3:완료, 4:실패)',
  `nIssueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '발급 시점(1:결제후, 2:구매결정후, 3:관리자확인후)',
  `sComment` varchar(100) default NULL COMMENT '실패 사유',
  `dtDate` date NOT NULL default '0000-00-00' COMMENT '등록 일',
  `dtTime` time NOT NULL default '00:00:00' COMMENT '등록 시간',
  PRIMARY KEY  (`nOrderGoodsCashReceiptLogSeq`),
  KEY `fkdbBilling106` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling106` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoodsCashReceipt` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 상품별 현금 영수증 정보 로그';

CREATE TABLE `tOrderGoodsComment` (
  `nOrderGoodsCommentSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품별 비고 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `sDescription` varchar(200) default NULL COMMENT '내용',
  PRIMARY KEY  (`nOrderGoodsCommentSeq`),
  KEY `fkdbBilling122` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling122` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 상품별 비고';

CREATE TABLE `tOrderGoodsDelivery` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL COMMENT '주문상품 고유키',
  `nDeliveryCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송업체고유키',
  `sInvoiceNumber` varchar(20) default NULL COMMENT '송장번호',
  `nWMSOrderStateSeq` int(10) unsigned NOT NULL default '0' COMMENT 'WMS 주문상태정보 고유키',
  `sWMSBarcode` varchar(30) default NULL COMMENT 'WMS 주문상품 바코트 정보 (Interleaved2of5 타입)',
  `nOrderStateSeq` int(10) unsigned NOT NULL default '1' COMMENT '주문상태정보 고유키',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  KEY `fkdbBilling113` (`nDeliveryCompanySeq`),
  KEY `fkdbBilling142` (`nWMSOrderStateSeq`),
  KEY `fkOrderGoodsDelivery1` (`nOrderStateSeq`),
  CONSTRAINT `fkdbBilling112` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling113` FOREIGN KEY (`nDeliveryCompanySeq`) REFERENCES `tDeliveryCompany` (`nDeliveryCompanySeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling142` FOREIGN KEY (`nWMSOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkOrderGoodsDelivery1` FOREIGN KEY (`nOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 배송정보';

CREATE TABLE `tOrderGoodsDeliveryMethodChangeRequest` (
  `nOrderGoodsDeliveryMethodChangeRequestSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품별 배송수단 변경요청 고유번호',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL COMMENT '주문상품 고유키',
  `nChangeRequestType` tinyint(3) unsigned NOT NULL default '1' COMMENT '변경요청상태 1:신청, 2:승인, 3:거부..',
  `nCurrentDeliveryMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '현재 배송수단 고유키',
  `nChangeDeliveryMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '변경 배송수단 고유키',
  `nCurrentDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '0' COMMENT '현재 배송비 지급 구분 고유키',
  `nChangeDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '0' COMMENT '변경 배송비 지급 구분 고유키',
  `dtRequestDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '요청일자',
  PRIMARY KEY  (`nOrderGoodsDeliveryMethodChangeRequestSeq`),
  KEY `fkOrderGoodsMethodChangeRequest1` (`nOrderGoodsSeq`),
  CONSTRAINT `fkOrderGoodsMethodChangeRequest1` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문상품별 배송수단 변경요청 정보';

CREATE TABLE `tOrderGoodsDeliveryMethodChangeRequestLog` (
  `nOrderGoodsDeliveryMethodChangeRequestSeq` int(10) unsigned NOT NULL COMMENT '주문상품별 배송수단 변경요청 고유번호',
  `nChangeRequestType` tinyint(3) unsigned NOT NULL default '1' COMMENT '변경요청상태 1:신청, 2:승인, 3:거부..',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '처리 회원 고유키',
  `sComment` varchar(100) default NULL COMMENT '메모',
  `dtInputDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '처리일자',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderGoodsDeliveryMethodChangeRequestSeq`),
  CONSTRAINT `fkOrderGoodsMethodChangeRequest2` FOREIGN KEY (`nOrderGoodsDeliveryMethodChangeRequestSeq`) REFERENCES `tOrderGoodsDeliveryMethodChangeRequest` (`nOrderGoodsDeliveryMethodChangeRequestSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 배송수단 변경요청 로그정보';

CREATE TABLE `tOrderGoodsDetail` (
  `nOrderGoodsDetailSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품별 상세가격 고유번호',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL COMMENT '주문상품 고유번호',
  `nGoodsDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 기본정보 고유번호',
  `sGoodsDetailName` varchar(200) default NULL COMMENT '상세상품명',
  `nGoodsDetailPrice` int(10) NOT NULL default '0' COMMENT '판매등록가격 +,- 가능',
  `nProductType` tinyint(3) NOT NULL default '1' COMMENT '상품구분 1:기본상품, 2:선택상품, 3:옵션상품',
  `nProductSeq` int(10) unsigned NOT NULL default '0' COMMENT '다나와 상품고유번호',
  `nGoodsDetailQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '갯수',
  PRIMARY KEY  (`nOrderGoodsDetailSeq`),
  KEY `fkdbBilling111` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling111` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 상세가격 정보';

CREATE TABLE `tOrderGoodsDiscount` (
  `nOrderGoodsDiscountSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 할인정보 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문 상품 고유키',
  `emDiscountType` enum('MEMER_GRADE_UPDOWN_PERCENT','SELLER_ADD_DC') NOT NULL default 'MEMER_GRADE_UPDOWN_PERCENT' COMMENT '할인 유형 MEMER_GRADE_UPDOWN_PERCENT:회원 등급 증감률, SELLER_ADD_DC : 판매자 추가 할인',
  `nDiscountPrice` int(10) unsigned NOT NULL default '0' COMMENT '할인가격',
  PRIMARY KEY  (`nOrderGoodsDiscountSeq`),
  KEY `fkOrderGoodsDiscount1` (`nOrderGoodsSeq`),
  CONSTRAINT `fkOrderGoodsDiscount1` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 할인 정보';

CREATE TABLE `tOrderGoodsMarketPlaceAdminComment` (
  `nOrderGoodsMarketPlaceAdminCommentSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품별 MP별 다나와 관리자 커멘트 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `sComment` varchar(200) default NULL COMMENT '커멘트 내용',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '관리자 고유키',
  `sMemberId` varchar(20) default NULL COMMENT '관리자 아이디',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderGoodsMarketPlaceAdminCommentSeq`),
  KEY `fkdbBilling121` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling121` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 상품별 MP별 다나와 관리자 커멘트';

CREATE TABLE `tOrderGoodsOptionItem` (
  `nOrderGoodsOptionItemSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 옵션 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목 이름 정보 고유키',
  PRIMARY KEY  (`nOrderGoodsOptionItemSeq`),
  KEY `fkdbBilling114` (`nOrderGoodsSeq`),
  KEY `fkdbBilling115` (`nOptionItemSeq`),
  CONSTRAINT `fkdbBilling114` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling115` FOREIGN KEY (`nOptionItemSeq`) REFERENCES `tOptionItem` (`nOptionItemSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 옵션';

CREATE TABLE `tOrderGoodsOptionItemAttribute` (
  `nOrderGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 옵션 항목별 속성 고유키',
  `nOrderGoodsOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 옵션 고유키',
  `nOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목값 이름 정보 고유키',
  `nOptonQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '옵션 주문가격 ',
  PRIMARY KEY  (`nOrderGoodsOptionItemAttributeSeq`),
  KEY `fkdbBilling116` (`nOrderGoodsOptionItemSeq`),
  KEY `fkdbBilling117` (`nOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling116` FOREIGN KEY (`nOrderGoodsOptionItemSeq`) REFERENCES `tOrderGoodsOptionItem` (`nOrderGoodsOptionItemSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling117` FOREIGN KEY (`nOptionItemAttributeSeq`) REFERENCES `tOptionItemAttribute` (`nOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 옵션 항목별 속성';

CREATE TABLE `tOrderGoodsOptionItemAttributePrice` (
  `nOrderGoodsOptionItemAttributePriceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품 옵션 항목별 속성가격 고유키',
  `nOrderGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 옵션 항목별 속성 고유키',
  `nOptionPrice` int(10) unsigned NOT NULL default '0' COMMENT '옵션 주문가격',
  PRIMARY KEY  (`nOrderGoodsOptionItemAttributePriceSeq`),
  KEY `fkdbBilling118` (`nOrderGoodsOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling118` FOREIGN KEY (`nOrderGoodsOptionItemAttributeSeq`) REFERENCES `tOrderGoodsOptionItemAttribute` (`nOrderGoodsOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 옵션 항목별 속성가격';

CREATE TABLE `tOrderGoodsOptionItemAttributeValue` (
  `nOrderGoodsOptionItemAttributeValueSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품옵션 항목별 속성값 고유키',
  `nOrderGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품옵션 항목별 속성 고유키',
  `sAttributeValue` varchar(100) default NULL COMMENT '옵션 속성값',
  PRIMARY KEY  (`nOrderGoodsOptionItemAttributeValueSeq`),
  KEY `fkOrderGoodsOptionItemAttributeValue1` (`nOrderGoodsOptionItemAttributeSeq`),
  CONSTRAINT `fkOrderGoodsOptionItemAttributeValue1` FOREIGN KEY (`nOrderGoodsOptionItemAttributeSeq`) REFERENCES `tOrderGoodsOptionItemAttribute` (`nOrderGoodsOptionItemAttributeSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품 옵션 항목별 속성값';

CREATE TABLE `tOrderGoodsRefundAccount` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nBankSeq` int(10) unsigned NOT NULL default '0' COMMENT '은행고유키',
  `sAccountNumber` varchar(64) NOT NULL default '' COMMENT '계좌번호(암호화)',
  `sDepositor` varchar(30) NOT NULL default '' COMMENT '예금주',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '환불계좌 신청일',
  `dtUpdateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '환불계좌 수정일',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  KEY `fkdbBilling168` (`nBankSeq`),
  CONSTRAINT `fkdbBilling167` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling168` FOREIGN KEY (`nBankSeq`) REFERENCES `tBank` (`nBankSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 환불계좌 정보';

CREATE TABLE `tOrderGoodsSaleCommissionLog` (
  `nOrderGoodsSaleCommissionLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '정산및 판매 수수료관련 로그 정보 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nSaleCommissionType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용된 판매수수료 구분 (1:판매수수료 결제 재한, 2:MP별 판매자 상품별, 3:MP별 판매자 결제수단, 4:MP별 판매수수료)',
  `nValueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용값 타입',
  `fValue` float(4,2) unsigned NOT NULL default '0.00' COMMENT '적용값',
  `nCommissionPrice` int(10) unsigned NOT NULL default '0' COMMENT '수수료율 적용금액',
  PRIMARY KEY  (`nOrderGoodsSaleCommissionLogSeq`),
  KEY `fkdbBilling125` (`nOrderGoodsSeq`),
  KEY `fkdbBilling126` (`nPaymentMethodSeq`),
  CONSTRAINT `fkdbBilling125` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling126` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 판매 수수료관련 로그 정보';

CREATE TABLE `tOrderGoodsSaleDeferredCommissionLog` (
  `nOrderGoodsSaleDeferredCommissionLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상품별 후불 판매 수수료 로그 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nSaleCommissionType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용된 판매수수료 구분 (1:판매수수료 결제 재한, 2:MP별 판매자 상품별, 3:MP별 판매자 결제수단, 4:MP별 판매수수료)',
  `nValueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용값 타입',
  `fValue` float(4,2) unsigned NOT NULL default '0.00' COMMENT '적용값',
  `nCommissionPrice` int(10) unsigned NOT NULL default '0' COMMENT '수수료율 적용금액',
  PRIMARY KEY  (`nOrderGoodsSaleDeferredCommissionLogSeq`),
  KEY `fkOrderGoodsSaleDeferredCommissionLog1` (`nOrderGoodsSeq`),
  KEY `fkOrderGoodsSaleDeferredCommissionLog2` (`nPaymentMethodSeq`),
  CONSTRAINT `fkOrderGoodsSaleDeferredCommissionLog1` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkOrderGoodsSaleDeferredCommissionLog2` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 후불 판매 수수료 로그 정보';

CREATE TABLE `tOrderGoodsSellerComment` (
  `nOrderGoodsSellerCommentSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품별 판매자 커멘트 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nMarketPlaceSellerMasterSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 판매자 담당 정보 고유키',
  `sComment` varchar(200) default NULL COMMENT '커멘트 내용',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  PRIMARY KEY  (`nOrderGoodsSellerCommentSeq`),
  KEY `fkdbBilling119` (`nOrderGoodsSeq`),
  KEY `fkdbBilling120` (`nMarketPlaceSellerMasterSeq`),
  CONSTRAINT `fkdbBilling119` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling120` FOREIGN KEY (`nMarketPlaceSellerMasterSeq`) REFERENCES `tMarketPlaceSellerMaster` (`nMarketPlaceSellerMasterSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 상품별 판매자 커멘트';

CREATE TABLE `tOrderGoodsStateLog` (
  `nOrderGoodsStateLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품별 주문상태 로그 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '상태값',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '상태 변경 회원 고유키',
  `sMemberIp` varchar(39) default NULL COMMENT '등록자 아이피',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderGoodsStateLogSeq`),
  KEY `fkdbBilling127` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling127` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 상품별 주문상태 로그';

CREATE TABLE `tOrderGoodsTakebackDelivery` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nDeliveryCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송업체고유키',
  `sInvoiceNumber` varchar(20) default NULL COMMENT '송장번호',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  KEY `fkOrderGoodsTakebackDelivery2` (`nDeliveryCompanySeq`),
  CONSTRAINT `fkOrderGoodsTakebackDelivery1` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkOrderGoodsTakebackDelivery2` FOREIGN KEY (`nDeliveryCompanySeq`) REFERENCES `tDeliveryCompany` (`nDeliveryCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='반품교환환불 배송 정보';

CREATE TABLE `tOrderGoodsTakebackReason` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상품 고유키',
  `nTakebackType` tinyint(3) unsigned NOT NULL default '0' COMMENT '취소/반품  유형 1:구매의사취소, 2:상품불량, 3:배송오류..',
  `sReason` varchar(255) NOT NULL default '' COMMENT '사유',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling169` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상품별 취소/반품상유 정보';

CREATE TABLE `tOrderInfraRequestServiceInfo` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nMemberRelationSeq` int(10) unsigned NOT NULL default '0' COMMENT '인프라회원관계고유번호',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '등록회원고유번호',
  `dtCreateDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일자',
  `dtApplyDate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '적용일자',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderNumberSeq`),
  CONSTRAINT `fkOrderInfraRequestServiceInfo1` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문별 인프라서비스 거점연결 정보';

CREATE TABLE `tOrderPaymentPriceConfirm` (
  `nOrderPaymentPriceConfirmSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문별결제금액반영적용정보',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `emApplyType` enum('DELIVERY_CHARGE','TOTAL_PURCHASE_PRICE') NOT NULL default 'DELIVERY_CHARGE' COMMENT '반영유형 DELIVERY_CHARGE:배송비, TOTAL_PURCHASE_PRICE:총결제금액',
  `emDivisionType` enum('COUPON_DC','EVENT_DC','PERIOD_DC','MEMBER_GRADE_DELIVERY_CHARGE_DC','MEMBER_GRADE_UPDOWN_PERCENT','BACKWOODS_DELIVERY_CHARGE','EVENT_UNAPPLIED_ACCOUNTING','REMOVE_GRADE_DC_DELIVERY_CHANGE','SELLER_ADD_DC') NOT NULL default 'COUPON_DC' COMMENT '분류유형 COUPON_DC:쿠폰,EVENT_DC:이벤트,PERIOD_DC:구간,MEMBER_GRADE_DELIVERY_CHARGE_DC:등급배송비,MEMBER_GRADE_UPDOWN_PERCENT:등급증감률,BACKWOODS_DELIVERY_CHARGE:도서산간배송비,EVENT_UNAPPLIED_ACCOUNTING:정산미적용이벤트,REMOVE_GRADE_DC_DELIVERY_CHANGE:배송변경에의한등급배송,SELLER_ADD_DC:판매자추가',
  `emValueType` enum('PERCENT','PRICE') NOT NULL default 'PERCENT' COMMENT '적용값유형 PERCENT: %, PRICE: 금액',
  `fValue` float NOT NULL default '0' COMMENT '적용값 %(소수점 2자리) 또는 금액',
  PRIMARY KEY  (`nOrderPaymentPriceConfirmSeq`),
  KEY `fkOrderPaymentPriceConfirm` (`nOrderNumberSeq`),
  CONSTRAINT `fkOrderPaymentPriceConfirm` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문별결제금액반영적용정보';

CREATE TABLE `tOrderPaymentPriceInfo` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nPaymentPrice` int(10) NOT NULL default '0' COMMENT '반영금액',
  PRIMARY KEY  (`nOrderNumberSeq`),
  CONSTRAINT `fkOrderPaymentPriceInfo1` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문별결제금액반영정보';

CREATE TABLE `tOrderReceiving` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `sName` varchar(10) default NULL COMMENT '수취자명',
  `sZipCode1` varchar(3) default NULL COMMENT '수취자 우편번호',
  `sZipCode2` varchar(3) default NULL COMMENT '수취자 우편번호',
  `sAddress1` varchar(308) default NULL COMMENT '수취자 주소',
  `sAddress2` varchar(308) default NULL COMMENT '수취자 상세주소',
  `sPhoneNumber` varchar(50) default NULL COMMENT '수취자 전화번호',
  `sCellularPhoneNumber` varchar(47) default NULL COMMENT '수취자 핸드폰',
  `sMessage` varchar(100) default NULL COMMENT '수취자 전달 메시지',
  PRIMARY KEY  (`nOrderNumberSeq`),
  CONSTRAINT `fkdbBilling103` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 수취자 정보';

CREATE TABLE `tOrderSending` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `sName` varchar(10) default NULL COMMENT '주문자 명',
  `sZipCode1` varchar(3) default NULL COMMENT '주문자 우편번호',
  `sZipcode2` varchar(3) default NULL COMMENT '주문자 우편번호',
  `sAddress1` varchar(308) default NULL COMMENT '주문자 주소',
  `sAddress2` varchar(308) default NULL COMMENT '주문자 상세주소',
  `sPhoneNumber` varchar(50) default NULL COMMENT '주문자 전화번호',
  `sCellularPhoneNumber` varchar(47) default NULL COMMENT '주문자 핸드폰 번호',
  `sMessage` varchar(100) default NULL COMMENT '주문자 메시지',
  `sEmail` varchar(158) default NULL COMMENT '주문자 이메일',
  `emOrderProductPriceShowYN` enum('Y','N') NOT NULL default 'Y' COMMENT '주문서 상품 금액 표기 여부 Y:표기, N:표기안함 ',
  PRIMARY KEY  (`nOrderNumberSeq`),
  CONSTRAINT `fkdbBilling102` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문 발송자 정보';

CREATE TABLE `tOrderState` (
  `nOrderStateSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상태정보 고유키',
  `nOrderStateGroup` tinyint(3) unsigned NOT NULL default '0' COMMENT '주문상태 그룹 1:주문상태, 2:정산상태, 3:환불상태, 4:취소상태, 5: 교환상태, 6: WMS상태, 7:배송상태(굿스플로)...',
  `sStateName` varchar(20) default NULL COMMENT '주문상태명',
  PRIMARY KEY  (`nOrderStateSeq`),
  UNIQUE KEY `idxUnStateName` (`sStateName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문상태정보';

CREATE TABLE `tOrderStateDailyAdvertisementStatistics` (
  `nOrderStateDailyAdvertisementStatisticsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상태별광고통계고유번호',
  `nOrderStateDailyPriceStatisticsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상태별금액통계고유번호',
  `nAdvertisementSeq` int(10) unsigned NOT NULL default '0' COMMENT '광고고유번호',
  `nCount` int(10) unsigned NOT NULL default '0' COMMENT '건수',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '금액',
  PRIMARY KEY  (`nOrderStateDailyAdvertisementStatisticsSeq`),
  KEY `fkdbBilling177` (`nAdvertisementSeq`),
  KEY `fkdbBilling178` (`nOrderStateDailyPriceStatisticsSeq`),
  CONSTRAINT `fkdbBilling177` FOREIGN KEY (`nAdvertisementSeq`) REFERENCES `tAdvertisement` (`nAdvertisementSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling178` FOREIGN KEY (`nOrderStateDailyPriceStatisticsSeq`) REFERENCES `tOrderStateDailyPriceStatistics` (`nOrderStateDailyPriceStatisticsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상태별광고통계정보';

CREATE TABLE `tOrderStateDailyPriceStatistics` (
  `nOrderStateDailyPriceStatisticsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상태별금액통계고유번호',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nOrderStateSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문상태고유번호',
  `nCount` int(10) unsigned NOT NULL default '0' COMMENT '건수',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '금액',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '입력일자',
  PRIMARY KEY  (`nOrderStateDailyPriceStatisticsSeq`),
  UNIQUE KEY `idxUMnOrderState` (`dtCreateDate`,`nMarketPlaceSeq`,`nOrderStateSeq`),
  KEY `fkdbBilling175` (`nMarketPlaceSeq`),
  KEY `fkdbBilling176` (`nOrderStateSeq`),
  CONSTRAINT `fkdbBilling175` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling176` FOREIGN KEY (`nOrderStateSeq`) REFERENCES `tOrderState` (`nOrderStateSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상태별금액통계정보';

CREATE TABLE `tOrderStateInfo` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nOrderState` int(10) unsigned NOT NULL default '0' COMMENT '주문 상태값',
  `dtDate` date NOT NULL default '0000-00-00' COMMENT '상태 등록일',
  `dtTime` time NOT NULL default '00:00:00' COMMENT '상태 등록시간',
  PRIMARY KEY  (`nOrderNumberSeq`,`nOrderState`),
  KEY `idxMdtDateOrderState` (`dtDate`,`nOrderState`),
  CONSTRAINT `fkdbBilling174` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문별 상태 관련 정보';

CREATE TABLE `tOrderStateLog` (
  `nOrderStateLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문상태별 로그 고유키',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '상태값',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '상태 변경 회원 고유키',
  `sMemberIp` varchar(39) default NULL COMMENT '상태 변경 회원 아이피',
  `nOrderStateHandlerType` tinyint(3) unsigned default '0' COMMENT '주문상태 변경 주체 (1:구매자, 2:판매자, 3:mp관리자, 4:빌링관리자, 5:ESCROW, 6:WMS, 7:이니시스PG결제모듈, 8:GoodsFlow, 9:cron 주문상태변경, 10:인프라 거점센터)',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderStateLogSeq`),
  KEY `fkdbBilling101` (`nOrderNumberSeq`),
  CONSTRAINT `fkdbBilling101` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='주문상태별 로그';

CREATE TABLE `tOrderTaxInvoice` (
  `nOrderTaxInvoiceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문별 세금계산서 신청 고유번호',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `sCompanyNumber` char(12) default NULL COMMENT '사업자 등록번호',
  `sCEOName` varchar(20) default NULL COMMENT '대표자',
  `sCompanyName` varchar(20) default NULL COMMENT '판매자 상호',
  `sAddress` varchar(200) default NULL COMMENT '주소',
  `sBusinessType` varchar(50) default NULL COMMENT '업태',
  `sBusinessItem` varchar(50) default NULL COMMENT '종목',
  `sEmail` varchar(50) default NULL COMMENT '이메일',
  PRIMARY KEY  (`nOrderTaxInvoiceSeq`),
  KEY `fkdbBilling104` (`nOrderNumberSeq`),
  CONSTRAINT `fkdbBilling104` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문별 세금계산서 신청 정보';

CREATE TABLE `tOrderTaxInvoiceReceipt` (
  `nOrderTaxInvoiceReceiptSeq` int(10) unsigned NOT NULL auto_increment COMMENT '세금계산서 발급 정보 고유키',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '발급상태(1:신청,2:발급대기,3:발급완료,4:발급실패,5:발급취소)',
  PRIMARY KEY  (`nOrderTaxInvoiceReceiptSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='세금계산서 발급 정보';

CREATE TABLE `tPayGateCompany` (
  `nPayGateCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT 'PG사 고유키',
  `sPayGateCompanyName` varchar(20) default NULL COMMENT 'PG사 명',
  `sPayGateModuleName` varchar(30) default NULL COMMENT 'PG사별 모듈클레스 네임(개발자가 사용할 결제 모듈 클레스 이름을 저장한다)',
  `nPayGateJoinType` tinyint(3) NOT NULL default '1' COMMENT 'PG사 가맹형태구분 (1:대표가맹, 2:개별가맹)',
  PRIMARY KEY  (`nPayGateCompanySeq`),
  UNIQUE KEY `idxUMsPayGateCompany` (`nPayGateJoinType`,`sPayGateCompanyName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='PG사 정보';

CREATE TABLE `tPayment` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nPayGateCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'PG사 고유키',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '결제 진행상태',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록 시간',
  PRIMARY KEY  (`nOrderNumberSeq`),
  KEY `fkdbBilling138` (`nPayGateCompanySeq`),
  CONSTRAINT `fkdbBilling137` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling138` FOREIGN KEY (`nPayGateCompanySeq`) REFERENCES `tPayGateCompany` (`nPayGateCompanySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='결제정보';

CREATE TABLE `tPaymentInicis` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `sTid` varchar(40) default NULL COMMENT '거래번호',
  `sOid` varchar(40) default NULL COMMENT '상점주문번호',
  `sAuthCode` varchar(10) default NULL COMMENT '신용카드 승인번호',
  `nCardQuota` smallint(3) unsigned NOT NULL default '0' COMMENT '할부기간',
  `nQuotaInterest` smallint(3) unsigned NOT NULL default '0' COMMENT '무이자할부 여부 1:무이자할부',
  `nEventFlag` smallint(3) unsigned NOT NULL default '0' COMMENT '각종 이벤트 적용 여부',
  `sVacct` varchar(20) default NULL COMMENT '가상계좌 번호',
  `nVcdbank` smallint(3) unsigned NOT NULL default '0' COMMENT '입금할 은행 코드',
  `dtInput` date NOT NULL default '0000-00-00' COMMENT '입금예정일',
  `sNminput` varchar(20) default NULL COMMENT '송금자 명',
  `sNmvacct` varchar(20) default NULL COMMENT '예금주 명',
  `sCardIssuerCode` char(2) NOT NULL default '00' COMMENT '카드사 코드',
  PRIMARY KEY  (`nOrderNumberSeq`),
  CONSTRAINT `fkdbBilling139` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tPayment` (`nOrderNumberSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='결제정보 이니시스';

CREATE TABLE `tPaymentMethod` (
  `nPaymentMethodSeq` int(10) unsigned NOT NULL auto_increment COMMENT '결제수단 고유키',
  `sPaymentName` varchar(20) default NULL COMMENT '결제수단명',
  PRIMARY KEY  (`nPaymentMethodSeq`),
  UNIQUE KEY `idxUsPaymentName` (`sPaymentName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='결제수단';

CREATE TABLE `tSaleCommissionAccountingTemplate` (
  `nSaleCommissionAccountingTemplateSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매수수료 정산주기 템플릿 고유키',
  `nAccountingCycleSeq` int(10) unsigned NOT NULL default '0' COMMENT '정산주기정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  PRIMARY KEY  (`nSaleCommissionAccountingTemplateSeq`),
  KEY `fkdbBilling17` (`nAccountingCycleSeq`),
  KEY `fkdbBilling143` (`nPaymentMethodSeq`),
  CONSTRAINT `fkdbBilling143` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling17` FOREIGN KEY (`nAccountingCycleSeq`) REFERENCES `tAccountingCycle` (`nAccountingCycleSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매수수료 정산주기 템플릿';

CREATE TABLE `tSaleCommissionAccountingTemplateLog` (
  `nSaleCommissionAccountingTemplateLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매수수료 정산주기 템플릿 로그 고유키',
  `nSaleCommissionAccountingTemplateSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매수수료 정산주기 템플릿 고유키',
  `sComment` varchar(200) default NULL COMMENT '내용',
  PRIMARY KEY  (`nSaleCommissionAccountingTemplateLogSeq`),
  KEY `fkdbBilling18` (`nSaleCommissionAccountingTemplateSeq`),
  CONSTRAINT `fkdbBilling18` FOREIGN KEY (`nSaleCommissionAccountingTemplateSeq`) REFERENCES `tSaleCommissionAccountingTemplate` (`nSaleCommissionAccountingTemplateSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매수수료 정산주기 템플릿 로그';

CREATE TABLE `tSaleCommissionLimit` (
  `nSaleCommissionLimitSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매수수료 결제 제한 정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nMinLimitPirce` int(10) unsigned NOT NULL default '0' COMMENT '최저 기준 결제 총금액',
  `nValueType` tinyint(3) unsigned NOT NULL default '0' COMMENT '적용값 타입 1:요율, 2:금액',
  `fValue` float(11,2) unsigned NOT NULL default '0.00' COMMENT '적용값 % 또는 금액',
  PRIMARY KEY  (`nSaleCommissionLimitSeq`),
  KEY `fkdbBilling14` (`nMarketPlaceSeq`),
  KEY `fkdbBilling15` (`nPaymentMethodSeq`),
  CONSTRAINT `fkdbBilling14` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling15` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='판매수수료 결제 제한정보';

CREATE TABLE `tSaleCommissionTemplate` (
  `nSaleCommissionTemplateSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매 수수료 템플릿 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nCommission` float(4,2) unsigned NOT NULL default '0.00' COMMENT '일반수수료',
  `nMinLimitPirce` int(10) unsigned NOT NULL default '0' COMMENT '최저 기준 결제 총금액',
  `nLimitCommission` float(4,2) unsigned NOT NULL default '0.00' COMMENT '최저수수료',
  PRIMARY KEY  (`nSaleCommissionTemplateSeq`),
  KEY `fkdbBilling144` (`nPaymentMethodSeq`),
  CONSTRAINT `fkdbBilling144` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매 수수료 템플릿';

CREATE TABLE `tSaleCommissionTemplateLog` (
  `nSaleCommissionTemplateLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매수수료 템플릿 로그 고유키',
  `nSaleCommissionTemplateSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매 수수료 템플릿 고유키',
  `sComment` varchar(200) default NULL COMMENT '내용',
  PRIMARY KEY  (`nSaleCommissionTemplateLogSeq`),
  KEY `fkdbBilling16` (`nSaleCommissionTemplateSeq`),
  CONSTRAINT `fkdbBilling16` FOREIGN KEY (`nSaleCommissionTemplateSeq`) REFERENCES `tSaleCommissionTemplate` (`nSaleCommissionTemplateSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매수수료 템플릿 로그';

CREATE TABLE `tSeller` (
  `nSellerSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 기본정보 고유키',
  `sCompanyName` varchar(20) default NULL COMMENT '판매자 상호',
  `sShopName` varchar(20) default NULL COMMENT '쇼핑몰명',
  `sCEOName` varchar(20) default NULL COMMENT '대표자',
  `sCEOIDNumber` varchar(64) default NULL COMMENT '대표자 주민등록번호',
  `sCompanyNumber` char(12) default NULL COMMENT '사업자 등록번호',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '판매자 회원 고유키',
  `sMemberId` varchar(20) NOT NULL default '' COMMENT '판매자 회원 아이디',
  `nMemberState` tinyint(3) unsigned NOT NULL default '0' COMMENT '회원상태(1:정상, 2:탈퇴, 3:정지)',
  `sUrl` varchar(30) default NULL COMMENT '사이트 주소',
  `nSellerType` tinyint(3) unsigned NOT NULL default '0' COMMENT '판매자 유형 구분',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nSellerSeq`),
  KEY `idxnMemberSeq` (`nMemberSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='판매자 기본정보';

CREATE TABLE `tSellerAddonInformation` (
  `nSellerSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 기본정보 고유키',
  `sSaleOpen` varchar(100) NOT NULL default '-' COMMENT '영업시간',
  `sCloseOpen` varchar(100) NOT NULL default '-' COMMENT '휴무일',
  `sDisplay` enum('Y','N') NOT NULL default 'N' COMMENT '판매점소개 노출여부 (Y:노출, N:비노출)',
  PRIMARY KEY  (`nSellerSeq`),
  CONSTRAINT `fkSellerAddonInfomation1` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 부가 정보';

CREATE TABLE `tSellerAddonInformationContent` (
  `nSellerSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 기본정보 고유키',
  `sContent` text NOT NULL COMMENT '판매자 상세설명',
  PRIMARY KEY  (`nSellerSeq`),
  CONSTRAINT `fkSellerAddonInformationContent1` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 부가 상세 정보';

CREATE TABLE `tSellerApplication` (
  `nSellerApplicationSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 입점신청목록 정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `sCompanyName` varchar(20) default NULL COMMENT '판매자상호',
  `sShopName` varchar(20) default NULL COMMENT '쇼핑몰명',
  `sCEOName` varchar(20) default NULL COMMENT '대표자',
  `sCEOIDNumber` varchar(64) default NULL COMMENT '대표자 주민등록번호',
  `sCompanyNumber` varchar(12) default NULL COMMENT '사업자 등록번호',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '신청자 회원고유키',
  `sMemberId` varchar(20) NOT NULL default '' COMMENT '판매자 회원 아이디',
  `nConfirmType` tinyint(3) unsigned NOT NULL default '0' COMMENT '승인상태',
  `dtConfirmDate` date NOT NULL default '0000-00-00' COMMENT '승인일',
  `sUrl` varchar(30) default NULL COMMENT '사이트 주소',
  `nSellerType` tinyint(3) unsigned NOT NULL default '0' COMMENT '판매자 유형 구분',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nSellerApplicationSeq`),
  KEY `fkdbBilling21` (`nMarketPlaceSeq`),
  CONSTRAINT `fkdbBilling21` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 입점신청목록 정보';

CREATE TABLE `tSellerApplicationLog` (
  `nSellerApplicationLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 입점신청 관리자 로그 고유키',
  `nSellerApplicationSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 입점신청목록 정보 고유키',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '관리자 ',
  `dtCreateDateTime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일시',
  `sComment` varchar(200) default NULL COMMENT '내용',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nSellerApplicationLogSeq`),
  KEY `fkdbBilling22` (`nSellerApplicationSeq`),
  CONSTRAINT `fkdbBilling22` FOREIGN KEY (`nSellerApplicationSeq`) REFERENCES `tSellerApplication` (`nSellerApplicationSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 입점신청 관리자 로그';

CREATE TABLE `tSellerBanking` (
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nBankSeq` int(10) unsigned NOT NULL default '0' COMMENT '은행고유키',
  `sBankingOwnerName` varchar(20) default NULL,
  `sBankingNumber` varchar(64) default NULL COMMENT '계좌번호',
  PRIMARY KEY  (`nSellerSeq`),
  KEY `fkdbBilling20` (`nBankSeq`),
  CONSTRAINT `fkdbBilling19` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling20` FOREIGN KEY (`nBankSeq`) REFERENCES `tBank` (`nBankSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 계좌 정보';

CREATE TABLE `tSellerBankingApplication` (
  `nSellerBankingApplicationSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 계좌 번호 변경 신청 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nBankSeq` int(10) unsigned NOT NULL default '0' COMMENT '은행고유키',
  `sBankingNumber` varchar(64) default NULL COMMENT '계좌번호',
  `sBankingOwnerName` varchar(20) default NULL COMMENT '예금주',
  `dtCreateDateTime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  `dtConfirmDateTime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '승인일',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '처리상태',
  PRIMARY KEY  (`nSellerBankingApplicationSeq`),
  KEY `fkdbBilling23` (`nSellerSeq`),
  KEY `fkdbBilling24` (`nBankSeq`),
  CONSTRAINT `fkdbBilling23` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling24` FOREIGN KEY (`nBankSeq`) REFERENCES `tBank` (`nBankSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 계좌 번호 변경 신청';

CREATE TABLE `tSellerBankingLog` (
  `nSellerBankingLogSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 계좌번호 정보 로그1',
  `nSellerBankingApplicationSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 계좌 번호 변경 신청 고유키',
  `dtCreateDateTime` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록일',
  `sComment` varchar(20) default NULL COMMENT '내용',
  PRIMARY KEY  (`nSellerBankingLogSeq`),
  KEY `fkdbBilling25` (`nSellerBankingApplicationSeq`),
  CONSTRAINT `fkdbBilling25` FOREIGN KEY (`nSellerBankingApplicationSeq`) REFERENCES `tSellerBankingApplication` (`nSellerBankingApplicationSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 계좌번호 정보 로그';

CREATE TABLE `tSellerPayment` (
  `nSellerPaymentSeq` int(10) unsigned NOT NULL auto_increment COMMENT '판매자 결제수단 정보',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nPayGateCompanySeq` int(10) unsigned NOT NULL default '0' COMMENT 'PG사 고유키',
  `sPayGateShopID` varchar(20) default NULL COMMENT 'PG사 상점 아이디',
  `sPayGateShopNumber` varchar(8) default NULL COMMENT 'PG사 상점번호 - 개별가맹용',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nState` tinyint(3) unsigned NOT NULL default '0' COMMENT '판매자 현재상태',
  PRIMARY KEY  (`nSellerPaymentSeq`),
  KEY `fkdbBilling26` (`nSellerSeq`),
  KEY `fkdbBilling27` (`nPayGateCompanySeq`),
  KEY `fkdbBilling28` (`nPaymentMethodSeq`),
  CONSTRAINT `fkdbBilling26` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling27` FOREIGN KEY (`nPayGateCompanySeq`) REFERENCES `tPayGateCompany` (`nPayGateCompanySeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling28` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='판매자 결제수단 정보';

CREATE TABLE `tStockVerification` (
  `nStockVerificationSeq` int(10) unsigned NOT NULL auto_increment COMMENT '재고검증 고유키',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격 기본정보 고유키',
  `nStockQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '주문시점 재고수량',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '주문회원 고유키',
  `dtCreate` datetime NOT NULL default '0000-00-00 00:00:00' COMMENT '등록날짜시분초',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nStockVerificationSeq`),
  KEY `idxMGoodsSeq` (`nGoodsSeq`,`nMemberSeq`)
) ENGINE=MEMORY DEFAULT CHARSET=utf8 COMMENT='재고검증';

CREATE TABLE `tTemporaryExcelGoods` (
  `nTemporaryExcelGoodsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '엑셀상품가격 기본정보 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nMakerSeq` int(10) unsigned NOT NULL default '0' COMMENT '제조사 고유키',
  `nProductSeq` int(10) unsigned NOT NULL default '0' COMMENT '다나와상품고유코드',
  `sGoodsName` varchar(200) default NULL COMMENT '상품명',
  `nPrice` int(10) unsigned NOT NULL default '0' COMMENT '가격',
  `sDisplayYN` char(1) NOT NULL default 'Y' COMMENT '노출여부 Y:노출함,N:노출안함',
  `nQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '판매수량',
  `nValidateState` tinyint(3) unsigned NOT NULL default '0' COMMENT '상품유효성상태 1:정상, 2:비정상',
  `sValidateStateDescription` varchar(100) default NULL COMMENT '유효성체크 상태 설명',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록일자',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록시간',
  `nDiscountPrice` int(10) unsigned NOT NULL default '0' COMMENT '추가할인금액',
  PRIMARY KEY  (`nTemporaryExcelGoodsSeq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='임시 엑셀상품입력 정보';

CREATE TABLE `tTemporaryOrder` (
  `nOrderNumberSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문번호',
  `sMemberId` varchar(20) default NULL COMMENT '주문회원 아이디',
  `nMemberSeq` int(10) unsigned default '0' COMMENT '주문회원 고유키',
  `dtCreateDate` date NOT NULL default '0000-00-00' COMMENT '등록 일',
  `dtCreateTime` time NOT NULL default '00:00:00' COMMENT '등록 시간',
  `sMemberIP` varchar(39) default NULL COMMENT '주문회원 IP',
  `nMarketPlaceMemberSeq` int(10) unsigned default '0',
  PRIMARY KEY  (`nOrderNumberSeq`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='임시주문';

CREATE TABLE `tTemporaryOrderGoods` (
  `nOrderGoodsSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품 고유키',
  `nOrderNumberSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문번호',
  `nGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품기본정보 고유키',
  `nMarketPlaceSeq` int(10) unsigned NOT NULL default '0' COMMENT 'MP별 기본정보 고유키',
  `nSellerSeq` int(10) unsigned NOT NULL default '0' COMMENT '판매자 기본정보 고유키',
  `nPaymentMethodSeq` int(10) unsigned NOT NULL default '0' COMMENT '결제수단 고유키',
  `nDeliverySeq` int(10) unsigned NOT NULL default '0' COMMENT '배송방법 고유키',
  `sGoodsName` varchar(200) default NULL COMMENT '상품명',
  `nGoodsPrice` int(10) unsigned NOT NULL default '0' COMMENT '상품가격',
  `nGoodsQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '갯수',
  `nDeliveryPrice` int(10) unsigned NOT NULL default '0' COMMENT '배송비',
  `nDeliveryPaymentDivisionSeq` int(10) unsigned NOT NULL default '1' COMMENT '배송비 지급구분 고유키',
  `nDiscountPrice` int(10) NOT NULL default '0' COMMENT '할인금액',
  PRIMARY KEY  (`nOrderGoodsSeq`),
  KEY `fkdbBilling82` (`nOrderNumberSeq`),
  KEY `fkdbBilling83` (`nGoodsSeq`),
  KEY `fkdbBilling84` (`nMarketPlaceSeq`),
  KEY `fkdbBilling85` (`nSellerSeq`),
  KEY `fkdbBilling86` (`nPaymentMethodSeq`),
  KEY `fkdbBilling87` (`nDeliverySeq`),
  KEY `fkdbBilling166` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling166` FOREIGN KEY (`nDeliveryPaymentDivisionSeq`) REFERENCES `tDeliveryPaymentDivision` (`nDeliveryPaymentDivisionSeq`),
  CONSTRAINT `fkdbBilling82` FOREIGN KEY (`nOrderNumberSeq`) REFERENCES `tTemporaryOrder` (`nOrderNumberSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling83` FOREIGN KEY (`nGoodsSeq`) REFERENCES `tGoods` (`nGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling84` FOREIGN KEY (`nMarketPlaceSeq`) REFERENCES `tMarketPlace` (`nMarketPlaceSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling85` FOREIGN KEY (`nSellerSeq`) REFERENCES `tSeller` (`nSellerSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling86` FOREIGN KEY (`nPaymentMethodSeq`) REFERENCES `tPaymentMethod` (`nPaymentMethodSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling87` FOREIGN KEY (`nDeliverySeq`) REFERENCES `tDelivery` (`nDeliverySeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='임시주문 상품';

CREATE TABLE `tTemporaryOrderGoodsDetail` (
  `nOrderGoodsDetailSeq` int(10) unsigned NOT NULL auto_increment COMMENT '임시주문 상품별 상세가격 고유번호',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL COMMENT '주문상품 고유번호',
  `nGoodsDetailSeq` int(10) unsigned NOT NULL default '0' COMMENT '상품가격별 기본정보 고유번호',
  `sGoodsDetailName` varchar(200) default NULL COMMENT '상세상품명',
  `nGoodsDetailPrice` int(10) NOT NULL default '0' COMMENT '판매등록가격 +,- 가능',
  `nProductType` tinyint(3) NOT NULL default '1' COMMENT '상품구분 1:기본상품, 2:선택상품, 3:옵션상품',
  `nGoodsDetailQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '갯수',
  PRIMARY KEY  (`nOrderGoodsDetailSeq`),
  KEY `fkdbBilling88` (`nOrderGoodsSeq`),
  CONSTRAINT `fkdbBilling88` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tTemporaryOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='임시주문 상품별 상세가격 정보';

CREATE TABLE `tTemporaryOrderGoodsDiscount` (
  `nTemporaryGoodsDiscountSeq` int(10) unsigned NOT NULL auto_increment COMMENT '임시주문 상품 할인 정보 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '임시 주문 상품 고유키',
  `emDiscountType` enum('MEMER_GRADE_UPDOWN_PERCENT','SELLER_ADD_DC') NOT NULL default 'MEMER_GRADE_UPDOWN_PERCENT' COMMENT '할인 유형 MEMER_GRADE_UPDOWN_PERCENT:회원 등급 증감률, SELLER_ADD_DC : 판매자 추가 할인',
  `nDiscountPrice` int(10) unsigned NOT NULL default '0' COMMENT '할인가격',
  PRIMARY KEY  (`nTemporaryGoodsDiscountSeq`),
  KEY `fkTemporaryOrderGoodsDiscount1` (`nOrderGoodsSeq`),
  CONSTRAINT `fkTemporaryOrderGoodsDiscount1` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tTemporaryOrderGoods` (`nOrderGoodsSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='임시주문 상품 할인 정보';

CREATE TABLE `tTemporaryOrderGoodsOptionItem` (
  `nOrderGoodsOptionItemSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품 옵션 고유키',
  `nOrderGoodsSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문 상품 고유키',
  `nOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목 이름 정보 고유키',
  PRIMARY KEY  (`nOrderGoodsOptionItemSeq`),
  KEY `fkdbBilling89` (`nOrderGoodsSeq`),
  KEY `fkdbBilling90` (`nOptionItemSeq`),
  CONSTRAINT `fkdbBilling89` FOREIGN KEY (`nOrderGoodsSeq`) REFERENCES `tTemporaryOrderGoods` (`nOrderGoodsSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling90` FOREIGN KEY (`nOptionItemSeq`) REFERENCES `tOptionItem` (`nOptionItemSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='임시 주문 상품옵션 항목';

CREATE TABLE `tTemporaryOrderGoodsOptionItemAttribute` (
  `nOrderGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품옵션 항목별 속성 고유키',
  `nOrderGoodsOptionItemSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문 상품 옵션 고유키',
  `nOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '옵션항목값 이름 정보 고유키',
  `nOptonQuantity` smallint(5) unsigned NOT NULL default '0' COMMENT '옵션 주문 갯수',
  PRIMARY KEY  (`nOrderGoodsOptionItemAttributeSeq`),
  KEY `fkdbBilling91` (`nOrderGoodsOptionItemSeq`),
  KEY `fkdbBilling92` (`nOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling91` FOREIGN KEY (`nOrderGoodsOptionItemSeq`) REFERENCES `tTemporaryOrderGoodsOptionItem` (`nOrderGoodsOptionItemSeq`) ON DELETE NO ACTION,
  CONSTRAINT `fkdbBilling92` FOREIGN KEY (`nOptionItemAttributeSeq`) REFERENCES `tOptionItemAttribute` (`nOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='임시주문 상품옵션 항목별 속성';

CREATE TABLE `tTemporaryOrderGoodsOptionItemAttributePrice` (
  `nOrderGoodsOptionItemAttributePriceSeq` int(10) unsigned NOT NULL auto_increment COMMENT '주문 상품옵션 항목별 속성 가격 고유키',
  `nOrderGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '주문 상품옵션 항목별 속성 고유키',
  `nOptionPrice` int(10) unsigned NOT NULL default '0' COMMENT '옵션 주문 가격',
  PRIMARY KEY  (`nOrderGoodsOptionItemAttributePriceSeq`),
  KEY `fkdbBilling93` (`nOrderGoodsOptionItemAttributeSeq`),
  CONSTRAINT `fkdbBilling93` FOREIGN KEY (`nOrderGoodsOptionItemAttributeSeq`) REFERENCES `tTemporaryOrderGoodsOptionItemAttribute` (`nOrderGoodsOptionItemAttributeSeq`) ON DELETE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='주문 상품옵션 항목별 속성 가격';

CREATE TABLE `tTemporaryOrderGoodsOptionItemAttributeValue` (
  `nOrderGoodsOptionItemAttributeValueSeq` int(10) unsigned NOT NULL auto_increment COMMENT '장바구니 상품옵션 항목별 속성값 고유키',
  `nOrderGoodsOptionItemAttributeSeq` int(10) unsigned NOT NULL default '0' COMMENT '장바구니 상품옵션 항목별 속성 고유키',
  `sAttributeValue` varchar(100) default NULL COMMENT '옵션 속성값',
  PRIMARY KEY  (`nOrderGoodsOptionItemAttributeValueSeq`),
  KEY `fkTemporaryOrderGoodsOptionItemAttributeValue1` (`nOrderGoodsOptionItemAttributeSeq`),
  CONSTRAINT `fkTemporaryOrderGoodsOptionItemAttributeValue1` FOREIGN KEY (`nOrderGoodsOptionItemAttributeSeq`) REFERENCES `tTemporaryOrderGoodsOptionItemAttribute` (`nOrderGoodsOptionItemAttributeSeq`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='임시주문 상품옵션 항목별 속성값';

CREATE TABLE `tTradingCompany` (
  `nTradingCompanySeq` int(10) unsigned NOT NULL auto_increment COMMENT '제공처 고유키',
  `sTradingCompanyName` varchar(20) default NULL COMMENT '제공처이름',
  PRIMARY KEY  (`nTradingCompanySeq`),
  UNIQUE KEY `idxUsTradingCompanyName` (`sTradingCompanyName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='제공처';

CREATE TABLE `tVoucher` (
  `nVoucherSeq` int(10) unsigned NOT NULL auto_increment COMMENT '증빙서류 고유키',
  `sVoucherName` varchar(20) default NULL COMMENT '증빙서류명',
  PRIMARY KEY  (`nVoucherSeq`),
  UNIQUE KEY `idxUsVoucherName` (`sVoucherName`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COMMENT='증빙서류';


SET foreign_key_checks = 1;