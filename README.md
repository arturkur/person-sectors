# person-sectors

## Running the application

**Requirements**
- [Docker desktop](https://www.docker.com/products/docker-desktop)

Running the application: `docker-compose up --build -d`
It builds images from backend and frontend code, then starts them as containers with postgres.

Application is accessible from: [**localhost:34200**](http://localhost:34200)

Stop the application: `docker-compose down` <br> With removing database volume: `docker-compose down --volumes`

## Technologies

**Frontend**
- Angular 13
- HTML 5
- CSS
- Angular Material

**Backend**
- Java 11
- Spring Boot
- Hibernate
- JPA
- Liquibase
- Testcontainers

**Database**
- PostgreSQL 14.2



## Database structure

Database changes and structure is handled by Liquibase on application startup. Template sectors are inserted to database by [insert_sectors.sql](./backend/src/main/resources/db/insert_sectors.sql) on first startup.

![Database structure](./docs/db_structure.PNG?raw=true)
