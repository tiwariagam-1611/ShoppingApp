E-Commerce Backend Project

Overview

This project is a Spring Boot based E-Commerce Backend Application developed to manage users, products, inventory, orders, order items, and notifications. The application follows a layered architecture using Spring Boot, Spring Data JPA, Hibernate, REST APIs, and MySQL database integration.

The system allows users to view products, place orders, manage inventory, and receive notifications. It is designed using modular architecture to ensure scalability, maintainability, and clean code structure.

Technologies Used

Backend Technologies:
Java 17
Spring Boot
Spring Data JPA
Hibernate
REST APIs
Maven

Database:
MySQL

Tools:
Postman
IntelliJ IDEA / Eclipse
Git & GitHub

Project Architecture

The project follows layered architecture:

Controller Layer → Service Layer → Repository Layer → Database

Controller Layer handles incoming API requests.
Service Layer contains business logic.
Repository Layer interacts with the database using JPA repositories.
Database stores application data.

Modules in the Project

User Module

The User module handles all user-related operations.

Features:
Create user
Update user details
Delete user
Fetch user information

Components:
User Entity
User Controller
User Service
User Repository

Product Module

The Product module manages all product-related operations.

Features:
Add product
Update product
Delete product
View product details
Get product list

Components:
Product Entity
Product Controller
Product Service
Product Repository

Inventory Module

The Inventory module manages product stock details.

Features:
Maintain stock quantity
Update stock after order placement
Validate product availability

Components:
Inventory Entity
Inventory Service
Inventory Repository

Order Module

The Order module handles customer orders.

Features:
Place orders
Fetch order details
Manage order status

Components:
Order Entity
Order Controller
Order Service
Order Repository

Order Item Module

The Order Item module stores individual products inside an order.

Features:
Maintain ordered product details
Store quantity and pricing information

Components:
OrderItem Entity
OrderItem Repository

Notification Module

The Notification module manages user notifications.

Features:
Send order notifications
Send status updates
Product-related notifications

Components:
Notification Entity
Notification Service
Notification Repository

Database Relationships

One-to-One Relationship:
Product ↔ Inventory

One-to-Many Relationship:
User → Orders
Order → OrderItems

Many-to-One Relationship:
Order → User
OrderItem → Product

Project Flow

Product Creation Flow

Admin creates a product using the Product API. The request is received by the Product Controller, processed in the Product Service, saved using Product Repository, and stored in the database.

Order Placement Flow

1. User places an order.
2. Order request reaches the Order Controller.
3. Order Service validates product availability.
4. Inventory quantity is checked and updated.
5. Order and Order Items are saved in the database.
6. Notification is generated for the user.

API Endpoints

User APIs:
POST /users → Create User
GET /users → Get All Users
GET /users/{id} → Get User By ID
PUT /users/{id} → Update User
DELETE /users/{id} → Delete User

Product APIs:
POST /products → Add Product
GET /products → Get All Products
GET /products/{id} → Get Product By ID
PUT /products/{id} → Update Product
DELETE /products/{id} → Delete Product

Order APIs:
POST /orders → Place Order
GET /orders → Get All Orders
GET /orders/{id} → Get Order By ID

Inventory APIs:
GET /inventory → Get Inventory Details
PUT /inventory/{id} → Update Inventory

Validation

The project uses validation annotations such as:
@NotNull
@Size
@Min
@Max
@Email

Validation helps ensure correct and secure data handling.

Exception Handling

Global exception handling is implemented using:
@ControllerAdvice
Custom Exceptions

Examples:
ProductNotFoundException
UserNotFoundException
OrderNotFoundException

This improves API response handling and error management.

Security Enhancements (Future Scope)

Possible future improvements:
Spring Security
JWT Authentication
Role-based Authorization
API Token Validation

Future Enhancements

Add Cart Module
Add Payment Gateway
Add Product Search
Add Pagination
Add Redis Caching
Add Swagger Documentation
Add Unit Testing and Integration Testing
Add Docker Deployment Support

How to Run the Project

Step 1:
Clone the project repository from GitHub.

Step 2:
Configure database credentials inside application.properties file.

Step 3:
Run the Spring Boot application using Maven or directly from the IDE.

Step 4:
Use Postman to test all REST API endpoints.

Conclusion

This project demonstrates the implementation of a complete E-Commerce Backend System using Spring Boot and MySQL. It follows layered architecture, RESTful API principles, proper database relationships, validation, and exception handling practices. The project is scalable and can be extended with advanced features such as authentication, payment integration, caching, and microservices architecture in the future.
