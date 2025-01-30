FROM openjdk:11-slim-buster
ARG JAR_FILE=build/libs/song7749-module-srcenter.jar
ADD ${JAR_FILE} boot.jar
ENV JAVA_OPTS=$JAVA_OPTS
ENTRYPOINT ["java","$JAVA_OPTS -Dspring.profiles.active=local","-jar","/boot.jar"]
