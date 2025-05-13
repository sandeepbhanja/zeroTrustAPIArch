FROM maven:latest as builder

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/target/*.jar root.jar

CMD ["java", "-jar", "root.jar"]
