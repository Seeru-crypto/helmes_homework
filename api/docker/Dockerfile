FROM openjdk:17-jdk-alpine

ARG SPRING_DB_PATH
ENV SPRING_DB_PATH = ${SPRING_DB_PATH}

ARG JAR_FILE=build/libs/\*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar","/app.jar"]
