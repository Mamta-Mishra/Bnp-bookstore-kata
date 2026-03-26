# Online Bookstore Backend (Spring Boot & MySQL)

This is a Spring Boot REST API for a Simple Online Bookstore. It includes features for user authentication (JWT), book catalog management, a shopping cart, and order history.

## 🚀 Quick Start Guide for Interviewers

Follow these steps to get the application running and test the full workflow.

### 1. Prerequisites
- **Java 21** (or 17+)
- **Maven 3.8+**
- **Docker & Docker Compose**

### 2. Step-by-Step Execution

#### **Step A: Start the Database**
Launch the MySQL instance using Docker Compose:
```bash
docker-compose up -d
```
*The database `bookstore` will be created automatically with the user `user` and password `password`.*

#### **Step B: Build and Test**
Run the Maven build to install dependencies and verify the logic with the test suite:
```bash
mvn clean install
```
*Tests use an in-memory H2 database and mocking to ensure a fast, reliable build.*

#### **Step C: Initialize Data**
Start the application to create the schema and populate the initial books:
```bash
mvn spring-boot:run
```
1. On the first run, `src/main/resources/application.properties` is set to `spring.sql.init.mode=always`. 
2. This executes `schema.sql` and `data.sql`, creating the tables and inserting a curated list of books on growth and success.
3. **Important**: Once the app has started successfully once, you can stop it and change `spring.sql.init.mode=never` in `application.properties` to prevent duplicate data on future restarts.

---

## 🛠 Tech Stack
- **Framework**: Spring Boot 3.4.3
- **Security**: Spring Security with **JWT (JSON Web Token)**
- **Database**: MySQL 8.0 (Development) / H2 (Testing)
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Maven
- **Lombok**: For cleaner boilerplate-free code

---

## 📂 API Documentation

### 1. Authentication (Public)
1. **Register**: `POST /api/auth/register`
   - Body: `{"username": "testuser", "password": "password123"}`
2. **Login**: `POST /api/auth/login`
   - Body: Same as above.
   - **Returns**: A JSON object with a `token`. Copy this token for all subsequent calls.

### 2. Protected Endpoints (Requires Bearer Token)
*Add the header `Authorization: Bearer <your_token>` to all calls below.*

- **Books**:
  - `GET /api/books`: List all books.
  - `GET /api/books/{id}`: Detailed view of a book.
- **Shopping Cart**:
  - `GET /api/cart`: View your current items.
  - `POST /api/cart`: Add a book (`{"bookId": 1, "quantity": 1}`).
  - `PUT /api/cart/{itemId}`: Update quantity (`{"quantity": 5}`).
  - `DELETE /api/cart/{itemId}`: Remove an item.
  - `POST /api/cart/checkout`: Convert cart to a permanent Order record.
- **Orders**:
  - `GET /api/orders`: View your full purchase history.

---

## 🔍 Database & Logs
- **MySQL**: Accessible via port `3306`.
- **H2 Console**: Available at `http://localhost:8080/h2-console` (during tests).
- **Logs**: The application uses `logback.xml` for detailed console logging, including Hibernate SQL queries.
