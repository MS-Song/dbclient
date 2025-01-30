FROM openjdk:11-slim-buster
ARG JAR_FILE=build/libs/song7749-module-dbclient.jar
ADD ${JAR_FILE} boot.jar
ENTRYPOINT ["java","-Dspring.profiles.active=local","-jar","/boot.jar"]

