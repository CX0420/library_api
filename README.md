# Library Management API

A RESTful Library Management API built with Java 17 and Spring Boot, using PostgreSQL for database management. This project provides endpoints for managing books, physical book inventory, borrowers, and borrowing transactions.

### Key Features
- **Book Management:** Create, retrieve, update, and soft-delete books.
- **Inventory Management:** Manage individual physical book copies and track their availability.
- **Borrower Management:** Register, retrieve, update, and deactivate borrowers.
- **Borrowing Management:** Handle book borrowing and returning transactions, including due dates and borrowing statuses.
- **Soft Deletion:** Preserve book and inventory records without permanently deleting data.
- **Validation & Exception Handling:** Centralized exception handling with appropriate HTTP status codes and meaningful error messages.
- **API Documentation:** Interactive Swagger UI and OpenAPI documentation.

### Tech Stack
- Java 17
- Spring Boot 4
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven
- Swagger / OpenAPI

### Getting Started
The application uses Docker Compose to run PostgreSQL. Configure the database connection in `application.properties`, start the database, and run the Spring Boot application.

Swagger UI is available at:

`http://localhost:8080/swagger-ui/index.html`

OpenAPI specification:

`http://localhost:8080/v3/api-docs`
