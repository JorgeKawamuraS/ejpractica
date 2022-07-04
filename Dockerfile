FROM openjdk:11
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ejpractica.jar

ENTRYPOINT ["java","-jar","ejpractica.jar"]