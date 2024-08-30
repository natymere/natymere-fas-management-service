# Financial Assistance Scheme Management System

This is a Spring Boot-based application designed to manage financial assistance schemes, applicants, and their respective household members. The system enables administrators to create, view, and manage schemes, as well as check applicant eligibility and process applications.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Database Design](#database-design)
- [API Endpoints](#api-endpoints)
- [Code Quality](#code-quality)
- [Setup and Installation](#setup-and-installation)
- [Running the Application](#running-the-application)

# Project Overview

This project is aimed at providing a streamlined process for managing various financial assistance schemes and the applicants for these schemes. It involves a well-designed database schema, RESTful API implementation, and secure backend logic, all while ensuring high code quality and comprehensive documentation.

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
