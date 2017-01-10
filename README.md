# DBClient ? 
----
* DBClient 는 SQL IDE Tool 입니다. 
* 현재는 Mysql 및 Oracle 을 지원하고 추후 여러 DBMS 를 지원할 예정입니다.
* Tode/Orange 와 유사한 인터페이스를 지원하며, WEB 베이스로 제작되어 있습니다.
* 사용자 Login 을 지원하며, 별도의 회원관리메뉴, Database 권한 부여 기능이 있습니다.
* Query 결과에 대한 엑셀 다운로드를 지원 합니다.

# 구동 환경 
* 소스를 다운로드 받은 후에 1회 아래 파일을 수정한 후 WAS 를 기동 합니다.
* /src/main/resources/hibernate.cfg.xml
```xml
			<property name="hibernate.hbm2ddl.auto">create</property>
			<property name="hibernate.hbm2ddl.import_files">./dbClient.sql</property>
```
* 그 뒤에는 위 코드를 제거하고 재시작 하시면 사용 가능 합니다. 
* 초기 비밀번호는 root / 12345678 입니다 

# screenshot
![DBClient Execute Image](https://raw.githubusercontent.com/MS-Song/dbclient/master/dbclient_example_image.png "dbClient Screenshot")