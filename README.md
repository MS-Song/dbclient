# DBClient ? 
----
* DBClient 는 SQL IDE Tool 입니다.
* 현재는 Mysql 및 Oracle 을 지원하고 추후 여러 DBMS 를 지원할 예정입니다. 
* Tode/Orange 와 유사한 인터페이스를 지원하며, WEB 베이스로 제작되어 있습니다.
* 사용자 Login 을 지원하며, 별도의 회원관리메뉴, Database 권한 부여 기능이 있습니다.
* Query 결과에 대한 엑셀 다운로드를 지원 합니다.

# 구동 환경 
* SpringBoot 2.0 으로 기동되며 java8 이상에서 작동 합니다.  
* Maven을 통한 빌드를 해야 하며, Maven Profile 을 통한 환경 변수 관리가 필요 합니다.
* profile 환경 변수로는 local, development, production 등이 있으며 로그 레벨 및 DB 파일 위치등을 정의 합니다. 
* 관리자 계정 및 비밀번호는 root / 12345678 입니다 

----

# 실행 방법
### 일반적 실행 방법
* java -jar dbclient-1.0-SNAPSHOT.jar

### 파일로그와 함께 실행 방법
* java -Dlogging.file=/home/mkyong/app/logs/app.log -jar dbclient-1.0-SNAPSHOT.jar

### 파일로그와 함께 실행 방법
* java -Dlogging.file=/home/song7749/log/dbclient.log -jar dbclient-1.0-SNAPSHOT.jar

### 로그 레벨 변경 방법
* java -Dlogging.level.org.springframework=TRAC -jar dbclient-1.0-SNAPSHOT.jar
* java -jar dbclient-1.0-SNAPSHOT.jar --debug

# 향후 계획
* SpringBoot 2.0 으로 재 개발 중이며, 완료 후에는 jar 파일 형태로 제공할 계획 입니다. 

# screenshot
![DBClient Execute Image](https://raw.githubusercontent.com/MS-Song/dbclient/master/dbclient_example_image.png "dbClient Screenshot")