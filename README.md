# Financial Assistance Scheme Management System

This is a Spring Boot-based application designed to manage financial assistance schemes, applicants, and their respective household members. The system enables administrators to create, view, and manage schemes, as well as check applicant eligibility and process applications.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Database Design](#database-design)
- [API Endpoints](#api-endpoints)
- [Code Quality](#code-quality)
- [Setup and Installation](#setup-and-installation)

# Project Overview

This project is aimed at providing a streamlined process for managing various financial assistance schemes and the applicants for these schemes. It involves a well-designed database schema, RESTful API implementation, and secure backend logic, all while ensuring high code quality.

## Features

- **Scheme Management**: Administrators can create, update, and delete financial assistance schemes.
- **Applicant Management**: Applicants can be registered, and their eligibility for schemes is automatically checked.
- **Household Management**: Applicants can have multiple household members associated with them.
- **Error Handling**: Proper error messages and status codes are returned for various edge cases and exceptions.

# Database Design

The database schema is designed to ensure data integrity and support for all required operations. It includes tables for `administrators`, `applicants`, `household_members`, `schemes`, `benefits` and `applications`. The relationships are established with proper foreign keys and constraints to maintain data consistency. Refer to the Entity Relationship Diagram to understand how the entities relate to one another.

## Code Quality

The project follows best practices in terms of readability and maintainability:

- **Java Naming Conventions**: Proper naming conventions are followed for classes, methods, and variables.
- **Modular Code Structure**: The code is structured in a modular way, with clear separation of concerns (Controllers, Services, Repositories).
- **Comments and Documentation**: The code is well-documented with inline comments explaining the purpose of key methods and logic.

## Setup and Installation

Follow these steps to set up and run

### Prerequisites
- **Java 17**: The project uses Java 17
- **Maven**: Maven is used to manage dependencies and build the project.
- **Docker**: Docker and Docker Compose are required to run the application and the PostgreSQL database.

If docker is not installed, you can use the h2 database. Refer to option 2.

Option 1:

1. **Clone the Repository**  
   First, clone the repository to your local machine:
   ```bash
   git clone https://github.com/natymere/natymere-fas-management-service.git
   cd natymere-fas-management-service
   ```

2. **Build the project**  
   Use Maven to clean, compile, and package the project into a JAR file. Skipping tests.
   ```bash
   mvn clean install -DskipTests
   ```

3. **Run the app with Docker Compose**  
   Use Docker Compose to build the Docker images and start the application and PostgreSQL database:
   ```bash
   docker-compose up --build
   ```

4. **Stopping the Application**  
   To stop the containers. Run the following:
   ```bash
   docker-compose down -v
   ```

Option 2 Running the application locally without Docker:
1. If you have intellij installed, the CLI argument is set to:
   ```bash
   --server.port=8080
   ```
   You can run the application with the following command too:
   ```bash
   mvn spring-boot:run
   ```
   
2. Ensure that docker postgres db configuration is commented off in application.properties file:
   ```properties
   # H2 Database configuration
   spring.h2.console.enabled=true
   spring.h2.console.path=/h2-console
   spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL;
   spring.datasource.driverClassName=org.h2.Driver
   spring.datasource.username=sa
   spring.datasource.password=password
   spring.datasource.platform=h2
   
   # docker postgres db
   #spring.datasource.url=jdbc:postgresql://localhost:5432/mydb
   #spring.datasource.username=myuser
   #spring.datasource.password=mypassword
   #spring.datasource.driver-class-name=org.postgresql.Driver
   #spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ...
   ```
