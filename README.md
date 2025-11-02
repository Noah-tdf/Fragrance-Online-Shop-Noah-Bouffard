# Fragrance Online Shop – Noah Bouffard

**Author:** Noah Bouffard  
**Student ID:** 2431848  
**Course:** 420-N34-LA Java Web Programming  
**Professor:** Haikel Hichri  
**Term:** Fall 2025  

---

##  Project Overview

The **Fragrance Online Shop** is a RESTful Spring Boot backend designed to manage customers, perfume products, and their purchase orders.  
It follows a **clean 3-layer architecture** separating data access, business logic, and presentation.

**Primary Users:** shop administrators who manage products, track orders, and maintain customer data.  
**Core Features:**
- CRUD operations for `Customer`, `Product`, and `Order`
- Supports multi-item orders through `OrderItem` entity
- Relationship handling between customers, orders, and products
- H2 in-memory database with sample data
- REST-compliant endpoints ready for frontend integration

---

##  How to Run Locally

### 1️ Requirements
- **Java 22**
- **Gradle** (included with wrapper)
- **IntelliJ IDEA** or any Java IDE

### 2️ Running the Project
**Option 1 – IntelliJ**
1. Open the project folder in IntelliJ.  
2. Locate `TermProjectBouffardApplication.java`.  
3. Click the green ▶️ *Run* button.

**Option 2 – Terminal**
bash
./gradlew bootRun



###  API Endpoints

## PROUCTS

| Method | URL                             | Description                                |
| ------ | ------------------------------- | ------------------------------------------ |
| GET    | `/products`                 | Retrieve all products                      |
| GET    | `/products/{id}`            | Retrieve a single product by ID            |
| POST   | `/products`                 | Add a new product                          |
| DELETE | `/products/{id}`            | Delete a product                           |
| GET    | `/products/order/{orderId}` | Retrieve all products for a specific order |


## ORDERS

| Method | URL                                 | Description                                 |
| ------ | ----------------------------------- | ------------------------------------------- |
| GET    | `/orders`                       | Retrieve all orders                         |
| GET    | `/orders/{id}`                  | Retrieve a single order by ID               |
| POST   | `/orders`                       | Create a new order                          |
| DELETE | `/orders/{id}`                  | Delete an order                             |
| GET    | `/orders/customer/{customerId}` | Retrieve all orders for a specific customer |

### Multi-Item Orders

Orders now support multiple products at once using an `items` array.

**Example JSON Request:**
```json
{
  "orderDate": "2025-11-02",
  "customerId": 1,
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}

```

## CUSTOMERS

| Method | URL                   | Description                      |
| ------ | --------------------- | -------------------------------- |
| GET    | `/customers`      | Retrieve all customers           |
| GET    | `/customers/{id}` | Retrieve a single customer by ID |
| POST   | `/customers`      | Add a new customer               |
| DELETE | `/customers/{id}` | Delete a customer                |


---



### UML & ERD Summary

**UML (Class Relationships)**
- `Customer` → has many `Order` objects (`@OneToMany`)
- `Order` → belongs to one `Customer` and has many `OrderItem` objects
- `OrderItem` → belongs to one `Order` and one `Product`

**ERD (Entity Relationships)**
- **Customer (1)** ───< **Order (Many)**
- **Order (1)** ───< **OrderItem (Many)**
- **OrderItem (Many)** ───> **Product (1)**
- Foreign keys:
  - `customer_id` in **Order**
  - `order_id` in **Product**
 


---

### Validation Plan

| Field | Entity | Validation Rule |
|--------|---------|----------------|
| firstName, lastName | Customer | `@NotBlank`, `@Size(min = 2)` |
| email | Customer | `@Email`, `@NotBlank` |
| name, brand | Product | `@NotBlank` |
| price | Product | `@Positive` |
| totalAmount | Order | `@Positive` |

All invalid inputs return **HTTP 400 – Bad Request** with error details.



###  Database Configuration

The project uses an **H2 in-memory database**, automatically created and filled with sample data on startup.

**Access the H2 Console:**
- URL → [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL → `jdbc:h2:mem:fragranceDB`
- Username → `sa`
- Password → *(leave blank)*

**application.properties**
```properties
spring.datasource.url=jdbc:h2:mem:fragranceDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

```

---


### DTO Implementation
The application uses DTOs (`CustomerRequestDTO`, `CustomerResponseDTO`, etc.) with mappers to isolate entity models from the API layer, 

---

###  Sample Data

When you start the application, a few **demo records** are automatically inserted by the `CommandLineRunner` inside  
`TermProjectBouffardApplication.java`.

**Customers**
- Noah Bouffard — Montreal  
- Emma Smith — Toronto  

**Orders**
- Two demo orders linked to the customers  

**Products**
- *YSL Babycat* – Warm amber scent  
- *Xerjoff Erba Pura* – Fruity luxury fragrance  
- *Valentino Born in Roma* – Modern woody floral  

**Try testing in Postman:**
```http
GET /products      → Lists all perfumes  
GET /orders        → Shows demo orders  
GET /customers     → Lists demo customers  

```

---

### Error Handling

The backend uses `.orElseThrow()` in the service layer to handle cases where a resource (like a Product, Order, or Customer) cannot be found.  
A **Global Exception Handler** (inside the `Exception` package) catches these runtime errors and returns a clean JSON error response instead of a raw stack trace.

**Example of a JSON error response:**
```json
{
  "timestamp": "2025-10-14T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found with ID: 99"
}
```

---

### Project Structure

Below is the structure of the backend application.  
It follows a **3-layer architecture**:  
- **DataAccessLayer** → Entities and Repositories  
- **BusinessLogicLayer** → Services and logic  
- **PresentationLayer** → REST Controllers (endpoints)

```bash
src/main/java/org/example/termproject_bouffard/
 ├── DataAccessLayer/
 │    ├── Customer.java
 │    ├── Product.java
 │    ├── Order.java
 │    ├── OrderItem.java
 │    ├── CustomerRepository.java
 │    ├── ProductRepository.java
 │    ├── OrderRepository.java
 │    └── OrderItemRepository.java
 │
 ├── BusinessLogicLayer/
 │    ├── CustomerService.java
 │    ├── ProductService.java
 │    └── OrderService.java
 │
 ├── PresentationLayer/
 │    ├── CustomerController.java
 │    ├── ProductController.java
 │    └── OrderController.java
 │
 ├── DTO/
 │    ├── CustomerRequestDTO.java
 │    ├── CustomerResponseDTO.java
 │    ├── ProductRequestDTO.java
 │    ├── ProductResponseDTO.java
 │    ├── OrderRequestDTO.java
 │    ├── OrderResponseDTO.java
 │    ├── OrderItemRequestDTO.java
 │    ├── OrderItemResponseDTO.java
 │    ├── CustomerSummary.java
 │    └── ProductSummary.java
 │
 ├── Mapper/
 │    ├── CustomerMapper.java
 │    ├── ProductMapper.java
 │    └── OrderMapper.java
 │
 ├── Exception/
 │    └── GlobalExceptionHandler.java
 │
 └── TermProjectBouffardApplication.java

```

---

##  Technologies Used

This project is built with modern Java backend technologies to ensure scalability, maintainability, and ease of development.

| Technology | Purpose |
|-------------|----------|
| **Spring Boot 3.5.6** | Main framework for building and running the application |
| **Spring Data JPA** | Simplifies database access and object-relational mapping (ORM) |
| **H2 Database** | Lightweight in-memory database for development and testing |
| **Lombok** | Reduces boilerplate code by auto-generating constructors and getters/setters |
| **Gradle Build Tool** | Manages dependencies and automates the build process |
| **Java 22** | Programming language used to develop the backend |
| **Postman** | Used to test API endpoints (GET, POST, DELETE requests) |

---

**Additional Notes:**
- All dependencies are managed automatically through Gradle’s `build.gradle` file.  
- H2 provides fast, temporary storage that resets with every app restart (ideal for testing).  
- The project is lightweight, modular, and easily deployable to Render or Railway.


## License

This project is part of Champlain College Saint-Lambert coursework.
All rights reserved © Noah Bouffard 2025.
