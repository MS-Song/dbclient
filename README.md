# DBClient ?  
[![Build Status](https://travis-ci.org/MS-Song/dbclient.svg?branch=master)](https://travis-ci.org/MS-Song/dbclient)

----

* DBClient 는 SQL IDE Tool 입니다.
* 현재는 Mysql/MariaDB/H2/Oracle/MSSQL 을 지원하고 추후 여러 DBMS 를 지원할 예정입니다. 
* Tode/Orange 와 유사한 인터페이스를 지원하며, WEB 베이스로 제작되어 있습니다.
* 버그가 있으면 issue 또는 request 에 올려주세요
* docker base 로 변경 되었습니다. 아래 명령어로 실행 가능 합니다.  


docker run -it -p 9009:9009 --name dbclient  song7749/db-client:latest

 
# 기능 요약
* 데이터베이스 등록 기능 
* 사용자 관리 및 로그인, 로그인 로그 기록
* 사용자별 데이터베이스 접근 권한 부여
* Database 의 Schema 정보 확인
* table, field, index, view, function 등의 정보 확인
* databas Query 실행
* PL/SQL 지원
* Bind Type 쿼리 자동 완성 ( :id, #{id}, ${id}, ? )
* 필드/테이블의 한글 코멘트로 검색 지원
* Query 의 필드 자동완성 지원
* 쿼리 저장 기능 및 재사용 기능 지원
* Table 기반의 Java Model, Hibernate Model 자동 완성 기능
* Table 기반의 Mybatis Insert/Update/Select/Result 자동 매팽 기능
* Query 결과 엑셀 다운로드 지원
* Auto Commit on/off 기능
* Auto Limit on/off 기능
* CSV 파일 업로드 및 TEMP 테이블 생성기능 추가 - 데이터 마이그레이션 기능

# 구동 환경 
* SpringBoot 2.0 으로 기동되며 java8 이상에서 작동 합니다.  
* gradle 을 통한 빌드를 해야 하며, gradle Profile 을 통한 환경 변수 관리가 필요 합니다.
* profile 환경 변수로는 local, dev, production 등이 있으며 로그 레벨 및 DB 파일 위치등을 정의 합니다. 
* 관리자 계정 및 비밀번호는 root@test.com / 12345678 입니다 
* 기본 서비스 포트는 9009 포트이며, 아래 방법으로 실행하시면 됩니다.
	* 크롬 브라우저를 여시고  127.0.0.1:9009 입력 후 실행 (IP 는 설치되어 있는 서버마다 다름
	
----

# 실행 방법
### 일반적 실행 방법
* java -jar dbclient-1.0-SNAPSHOT.jar

### 파일로그와 함께 실행 방법
* java -Dlogging.file=/home/song7749/log/dbclient.log -jar dbclient-1.0-SNAPSHOT.jar

### 로그 레벨 변경 방법
* java -Dlogging.level.org.springframework=TRAC -jar dbclient-1.0-SNAPSHOT.jar
* java -jar dbclient-1.0-SNAPSHOT.jar --debug

### 서비스 포트 변경 방법
* java -jar -Dserver.port=8080 dbclient-1.0-SNAPSHOT.jar


# 향후 계획
* 모든 데이터베이스 및 NoSQL 지원
* websock 을 이용한 transaction 기능 지원 

[![DB Client Live Demo](https://img.youtube.com/vi/2-QUOFtrfpA/0.jpg)](https://youtu.be/2-QUOFtrfpA)

# screenshot
![DBClient Execute Image](https://raw.githubusercontent.com/MS-Song/dbclient/master/dbclient_example_image.png "dbClient Screenshot")

## 월급쟁이 생활 중이라 업데이트가 늦을 수 있으니 양해 부탁 드립니다.
## 개인 및 회사에서 무료입니다.
## 제품화 하여 판매는 불허 합니다.

