# Java REST API with Spring Boot

![Java](https://img.shields.io/badge/Java-8+-orange.svg)
![Spring](https://img.shields.io/badge/Spring-5.0-green.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)
![Nginx](https://img.shields.io/badge/Nginx-latest-brightgreen.svg)
![JWT](https://img.shields.io/badge/JWT-authentication-yellow.svg)
![Flyway](https://img.shields.io/badge/Flyway-migrations-red.svg)

This is a RESTful API built using Java with Spring Boot framework. It includes various design patterns, middleware for JWT authentication, and uses PostgreSQL as the database. Nginx is used as a reverse proxy server.

## Features

- **Java**: Core language used for development.
- **Spring Boot**: Framework for building Java applications.
- **PostgreSQL**: Database management system.
- **Nginx**: Reverse proxy server for routing requests.
- **JWT**: JSON Web Tokens for authentication.

## Prerequisites

- Java 8 or higher
- Maven
- PostgreSQL 15
- Nginx (latest version)
- Docker (optional, for deployment)

## Installation and Setup

### Local Development

1. Clone the repository:

   ```bash
   git clone <repository_url>
   cd javaApi
   ```
   Set up PostgreSQL database:

Install PostgreSQL locally.
Create a database named compose-postgres.
Configure the database connection parameters in src/main/resources/application.properties.
Run the application:

```bash
mvn clean install
cd target
java -jar javaApi-0.0.1-SNAPSHOT.jar
```
The application should now be running locally.

Docker Deployment (Optional)
Uncomment the relevant services in docker-compose.yml:

```yaml
version: '3.1'
services:
db:
image: "postgres:15"
ports: - "5432:5432"
environment: - POSTGRES_DB=compose-postgres - POSTGRES_USER=compose-postgres - POSTGRES_PASSWORD=compose-postgres
```
You can also uncomment the Spring Boot app and Nginx services if needed.

Run Docker Compose:

```bash
docker-compose up
```

Configure the database connection parameters in src/main/resources/application.properties.

Access the API through Nginx reverse proxy server.

Database Management
For database management, i recommend to use tools like DBeaver for easy visualization and manipulation of data.
