FROM maven:3.8.5-openjdk-11-slim AS build
COPY src /build/src
COPY pom.xml /build/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:11-jdk-slim
COPY --from=build /build/target/person-sectors*.jar /app/person-sectors.jar
EXPOSE 8080
CMD ["java","-jar","/app/person-sectors.jar"]