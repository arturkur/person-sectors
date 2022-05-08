# person-sectors backend

## Local development


**Requirements**
- [OpenJDK 11](https://openjdk.java.net/projects/jdk/11/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/)

**Running the application**
- PostgreSQL must be available and connection info is specified in `application.properties`
- Build `mvn clean install`
- Run `mvn spring-boot:run`
- Application starts at `http://localhost:8080`