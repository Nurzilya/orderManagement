# Order Management API
The Order Management API is built using Spring Boot to handle products, categories, orders, and payments 
while enforcing business rules.

## Technologies Used
- Spring Boot 3 (REST API, Security, JPA)
- Spring Security (Role-Based Access Control)
- H2  (Database)
- Springdoc OpenAPI (Swagger UI) (API Documentation)
- JUnit, Mockito (Unit Testing)
- Docker (Deployment)

## Features
 - Manage Orders (Create, Update, Cancel, Finish)
 - Manage Products & Categories
 - Stock Validation & Price Recalculation
 - Mock Payment Gateway
 - Role-Based Security (ADMIN, USER)
 - Unit Tests with JUnit & Mockito
 - API Documentation with Swagger
 - H2 Database for Testing
 - Docker Support

## Setup
1. Clone the repository
2. Run `docker build -t order-management .`
   Run `docker run -p 8080:8080 order-management`
3. Find all API documentation in Swagger:
   http://localhost:8080/swagger-ui/index.html#/
4. API credentials:
```
Role user: 
   username: user
   password: fun123
Role admin: 
   username: admin
   password: fun123
```
API endpoint example: `http://localhost:8080/api/orders`

5. Access H2 Console: http://localhost:8080/h2-console
```
   API credentials:
   username: admin
   password: fun123

   Database credentials:
   JDBC URL: jdbc:h2:mem:testdb
   Username: sa
   Password: (leave empty)

```