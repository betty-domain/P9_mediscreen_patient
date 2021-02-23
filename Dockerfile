FROM openjdk:8-jdk-alpine
COPY target/*.jar mediscreen-patient.jar
ENTRYPOINT ["java","-jar","/mediscreen-patient.jar"]