FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} alumno-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/alumno-service-0.0.1-SNAPSHOT.jar"]