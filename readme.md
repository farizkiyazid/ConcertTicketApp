# Database Setup

Before running the Spring Boot application, you'll need to set up your MySQL database. Follow the steps below:

1. **Create Database Schema:**
    - Connect to your MySQL server.
    - Run the following SQL query to create the database schema:

   ```sql
   CREATE SCHEMA IF NOT EXISTS concert_ticketing_db;
   ```
2. **Create Tables:**
   - Once the schema is created, run the following SQL queries to create the necessary tables within the "concert_ticketing_db" schema:
    ```sql
    CREATE TABLE IF NOT EXISTS concert_ticketing_db.user (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        email VARCHAR(255) UNIQUE
    );

    CREATE TABLE IF NOT EXISTS concert_ticketing_db.concert (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        concert_datetime DATETIME,
        venue VARCHAR(255),
        tickets_available INT,
        booking_start_datetime DATETIME,
        booking_end_datetime DATETIME
    );
    
    CREATE TABLE IF NOT EXISTS concert_ticketing_db.ticket (
        id INT AUTO_INCREMENT PRIMARY KEY,
        concert_id INT,
        user_id INT,
        purchaseTime DATETIME,
        quantity INT,
        FOREIGN KEY (concert_id) REFERENCES concert(id),
        FOREIGN KEY (user_id) REFERENCES user(id)
    );
   ```
