# Student Registration System 🎓

A full-stack, responsive web application designed to manage student enrollments. This project was built explicitly using a manual data persistence pipeline to demonstrate mastery over core Java database connectivity without relying on high-level ORM automation frameworks.

---

## 🚀 Key Features

- **Full CRUD Support:** Seamlessly Create, Read, Update, and Delete student profiles from a unified dashboard.
- **Pure JDBC Database Connectivity:** Built entirely using raw SQL queries via Java `Connection`, `PreparedStatement`, and `ResultSet` architectures.
- **Robust Exception Handling:** Implements a global exception controller intercepting application runtime crashes to protect system infrastructure details.
- **Automated Test Coverage:** Includes unit testing suites leveraging JUnit 5 and Mockito to validate data operations in isolation.
- **Single-Server Static Routing:** Serves a clean, dual-panel asynchronous web interface directly from Spring Boot's internal static resource handlers.

---

## 🛠️ Tech Stack & Architecture

- **Backend:** Java, Spring Boot (REST API Engine)
- **Data Access:** Pure JDBC, HikariCP Connection Pooling
- **Database:** MySQL
- **Testing:** JUnit 5, Mockito
- **Frontend:** HTML5, CSS3 (Flexbox/Grid), JavaScript (Native Async Fetch API)

---

## 💻 Local Setup Instructions

### 1. Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven
- MySQL Server

### 2. Database Configuration
Log into your local MySQL instance and initialize the target schema:

```sql
CREATE DATABASE student_db;

USE student_db;

CREATE TABLE students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
