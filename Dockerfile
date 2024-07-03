FROM openjdk:21-jdk-slim
COPY target/hacken-test-app-0.0.1-SNAPSHOT.jar .
EXPOSE 8081
CMD ["java","-jar","/hacken-test-app-0.0.1-SNAPSHOT.jar"]
