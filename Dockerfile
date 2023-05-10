FROM openjdk:17-jdk-alpine
COPY build/libs/Cinema-Center-0.0.1-SNAPSHOT.jar /Cinema-Center-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "/Cinema-Center-0.0.1-SNAPSHOT.jar"]