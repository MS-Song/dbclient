FROM openjdk:8-jdk-alpine
ARG JAR_FILE=build/libs/song7749-module-srcenter.jar
ADD ${JAR_FILE} boot.jar
ENTRYPOINT ["java","-Dspring.profiles.active=local","-jar","/boot.jar"]
