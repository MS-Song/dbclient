DBClient ? 
----
* DBClient 는 DB 검색 및 DB 기반의 Model,SQL 자동완성 소프트웨어 입니다.
* 현재는 Mysql 및 Oracle 을 지원하고 추후 여러 DBMS 를 지원할 예정입니다.

# 구동 환경 
* 소스를 다운로드 받은 후에 1회 아래 파일을 수정한 후 WAS 를 기동 합니다.
* /src/main/resources/hibernate.cfg.xml
```xml
			<property name="hibernate.hbm2ddl.auto">create</property>
			<property name="hibernate.hbm2ddl.import_files">./dbClient.sql</property>
```
* 그 뒤에는 위 코드를 제거하고 재시작 하시면 사용 가능 합니다. 
* 초기 비밀번호는 root / 12345678 입니다 .

# 개발 진행 상황
* 아직 admin 기능이 완료되지 않아 database 를 직접 입력 할 수 없습니다 .
* 아래와 같이 database 에 직접 입력 해야 합니다. 
* /src/main/resources/dbClient.sql
 
```sql
insert into ServerInfo (account, charset, driver, host, hostAlias, password, port, schemaName) values ('dbclient','UTF-8','mysql','127.0.0.1','local_Mysql','1234','3306','dbclient');
```

* 위와 같이 SQL 문을 입력하면, 초기 기동시에 data 가 입력 됩니다 .
* 구동 환경의 코드를 수정후에 기동해야 합니다. 