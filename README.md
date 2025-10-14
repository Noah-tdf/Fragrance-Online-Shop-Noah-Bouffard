# Fragrance Online Shop – Spring Boot Backend

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
- CRUD operations for `Product`, `Order`, and `Customer`
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
| GET    | `/api/products`                 | Retrieve all products                      |
| GET    | `/api/products/{id}`            | Retrieve a single product by ID            |
| POST   | `/api/products`                 | Add a new product                          |
| DELETE | `/api/products/{id}`            | Delete a product                           |
| GET    | `/api/products/order/{orderId}` | Retrieve all products for a specific order |


## ORDERS

| Method | URL                                 | Description                                 |
| ------ | ----------------------------------- | ------------------------------------------- |
| GET    | `/api/orders`                       | Retrieve all orders                         |
| GET    | `/api/orders/{id}`                  | Retrieve a single order by ID               |
| POST   | `/api/orders`                       | Create a new order                          |
| DELETE | `/api/orders/{id}`                  | Delete an order                             |
| GET    | `/api/orders/customer/{customerId}` | Retrieve all orders for a specific customer |


## CUSTOMERS

| Method | URL                   | Description                      |
| ------ | --------------------- | -------------------------------- |
| GET    | `/api/customers`      | Retrieve all customers           |
| GET    | `/api/customers/{id}` | Retrieve a single customer by ID |
| POST   | `/api/customers`      | Add a new customer               |
| DELETE | `/api/customers/{id}` | Delete a customer                |


---

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

###  Sample Data

When you start the application, a few **demo records** are automatically inserted by the `CommandLineRunner` inside  
`TermProjectBouffardApplication.java`.

**Customers**
- Luca Raro — Montreal  
- Emma Smith — Toronto  

**Orders**
- Two demo orders linked to the customers  

**Products**
- *YSL Babycat* – Warm amber scent  
- *Xerjoff Erba Pura* – Fruity luxury fragrance  
- *Valentino Born in Roma* – Modern woody floral  

**Try testing in Postman:**
```http
GET /api/products      → Lists all perfumes  
GET /api/orders        → Shows demo orders  
GET /api/customers     → Lists demo customers  

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

## 🧱 Project Structure

Below is the structure of the backend application.  
It follows a **3-layer architecture**:  
- **DataAccessLayer** → Entities and Repositories  
- **BusinessLogicLayer** → Services and logic  
- **PresentationLayer** → REST Controllers (endpoints)

```bash
src/main/java/org/example/termproject_bouffard/
 ├── DataAccessLayer/
 │    ├── Customer.java
 │    ├── Order.java
 │    ├── Product.java
 │    ├── CustomerRepository.java
 │    ├── OrderRepository.java
 │    └── ProductRepository.java
 │
 ├── BusinessLogicLayer/
 │    ├── CustomerService.java
 │    ├── OrderService.java
 │    └── ProductService.java
 │
 ├── PresentationLayer/
 │    ├── CustomerController.java
 │    ├── OrderController.java
 │    └── ProductController.java
 │
 ├── Exception/
 │    └── GlobalExceptionHandler.java
 │
 └── TermProjectBouffardApplication.java
```

🧰 Technologies Used

Spring Boot 3.5.6

Spring Data JPA

H2 Database

Lombok

Gradle Build Tool

Java 22

Postman for REST API testing

🧾 Deployment (Next Milestone)

The project is ready to deploy to:

Render

Railway

or any cloud Spring Boot host.

✅ Rubric Alignment
Section	Task	Points
Design Report	UML, Endpoints, Validation	5
Backend Architecture	3-layer, DTO naming	6
Data & Persistence	H2 config + seeded data	4
API Functionality	CRUD + relationship endpoints	6
Error Handling	Exception handling	2
Documentation	README + clarity	2
Total	25 / 25	
Bonus	Exception handler + basic tests	+2
📎 License

This project is part of Champlain College Saint-Lambert coursework.
All rights reserved © Noah Bouffard 2025.
