FROM maven:3.8.5-jdk-11 AS compile
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml -DskipTests=true clean package

FROM eclipse-temurin:11-jre
COPY --from=compile /usr/src/app/target/backbase-test-task-1.0-SNAPSHOT.jar /usr/app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app.jar"]