FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# cache deps
COPY pom.xml .
RUN mvn dependency:go-offline -B

# copy source
COPY src ./src

# build without clean
RUN mvn package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 4060
ENTRYPOINT ["java","-jar","app.jar"]
