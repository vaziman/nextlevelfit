# NextLevelFit

NextLevelFit is a Java-based fitness analytics platform that integrates with Strava to collect and analyze workout data. The application allows users to register, authenticate via Strava OAuth2, and view personalized training statistics.

## Features

-  Workout data collection from Strava API (running, cycling, etc.)
-  User registration and login (with role-based access)
-  OAuth2 authentication with Strava
-  Basic workout statistics (distance, duration, pace)
-  RESTful API built with Spring Boot
-  PostgreSQL database integration with Hibernate
-  API documentation via Swagger UI

## Technologies

- Java 17
- Spring Boot
- Spring Security (OAuth2)
- Hibernate / JPA
- PostgreSQL
- Maven
- Docker (optional)
- Swagger / OpenAPI

## Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL (running locally or in Docker)

### Configuration

Create a file `src/main/resources/application.properties` or `application-secret.properties` and provide the following settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nextlevelfit
spring.datasource.username=postgres
spring.datasource.password=yourpassword

strava.client.id=your_strava_client_id
strava.client.secret=your_strava_client_secret
strava.redirect.uri=http://localhost:8080/api/auth/strava/callback

Run the Application

```bash
./mvnw spring-boot:run

Swagger UI available at:
`http://localhost:8080/swagger-ui/index.html`
