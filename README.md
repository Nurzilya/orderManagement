# Order Management API

## Feature
 - Manage Orders (Create, Update, Cancel, Finish)
 - Manage Products & Categories
 - Stock Validation & Price Recalculation
 - Mock Payment Gateway
 - Role-Based Security (ADMIN, USER)
 - Unit Tests with JUnit & Mockito
 - API Documentation with Swagger
 - H2 Database for Testing
 - Docker Support


docker build -t order-management .
docker run -p 8080:8080 order-management

## Setup
1. Clone the repository
2. Run `docker build -t order-management .`
2. Run `docker run -p 8080:8080 order-management`
2. Run `mvn spring-boot:run`
3. Open `http://localhost:8080/api/orders`

## Swagger
http://localhost:8080/swagger-ui/index.html#/

## Docker
docker build -t order-management .
docker run -p 8080:8080 order-management

Technologies Used
 - Spring Boot 3 (REST API, Security, JPA)
 - Spring Security (Role-Based Access Control)
 - H2  (Database)
 - Springdoc OpenAPI (Swagger UI) (API Documentation)
 - JUnit, Mockito (Unit Testing)
 - Docker (Deployment)