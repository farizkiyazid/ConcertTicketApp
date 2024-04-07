# Initial Setup

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
    CREATE TABLE IF NOT EXISTS concert_ticketing_db.users (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        email VARCHAR(255) UNIQUE
    );

    CREATE TABLE IF NOT EXISTS concert_ticketing_db.concerts (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        concert_datetime DATETIME,
        venue VARCHAR(255),
        tickets_available INT,
        booking_start_datetime DATETIME,
        booking_end_datetime DATETIME
    );
    
    CREATE TABLE IF NOT EXISTS concert_ticketing_db.tickets (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        concert_id BIGINT,
        user_id BIGINT,
        purchaseTime DATETIME,
        quantity INT,
        FOREIGN KEY (concert_id) REFERENCES concerts(id),
        FOREIGN KEY (user_id) REFERENCES users(id)
    );
   ```
3. **Configure application.properties:**
   - Before running the application, make sure to configure the application.properties file according to your MySQL setup. 
   - Open the src/main/resources/application.properties file. 
   - Modify the following properties to match your MySQL configuration:
   ```sql
   spring.datasource.url=jdbc:mysql://localhost:3306/concert_ticketing_db
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   ```
   Replace `your_mysql_username` and `your_mysql_password` with your MySQL username and password.
   
   Once you've completed these steps, you should be ready to run the Spring Boot application.
