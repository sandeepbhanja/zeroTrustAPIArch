FROM maven:latest as build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:25-jdk-slim

COPY --from=build /app/target/*.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]