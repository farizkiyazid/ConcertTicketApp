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
        purchase_time DATETIME,
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


-----------------------------------------------------------------

# API Documentation
## User
Base URL: `/users`

### Get All Users
- URL: `GET /users`
- Description: Retrieves all users from the system.
- Response Body: List of User objects.
- Response Example:
```json
[
   {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com"
   },
   {
      "id": 2,
      "name": "Jane Smith",
      "email": "jane@example.com"
   }
]
```
- Response Codes:
  - 200 OK: Successful retrieval of users.
  - 404 Not Found: No users found.

### Get User by ID
- URL: `GET /users/{id}`

- Description: Retrieves a user by their ID.

- Path Parameters:
{id}: The ID of the user to retrieve.

- Response Body:
APIResponse containing User object.

- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": {
      "id": 1,
      "name": "John Doe",
      "email": "john@example.com"
   }
}
```
- Response Codes:

  - 200 OK: User found and retrieved successfully.

  - 404 Not Found: User with the specified ID not found.
  
### Add User
- URL: POST `/users/add`
- Description: Adds a new user to the system.

- Request Body Example:
```json
{
   "name": "John Doe",
   "email": "john@example.com"
}
```

- Response Body:
APIResponse containing added User object.

- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": {
      "id": 3,
      "name": "John Doe",
      "email": "john@example.com"
   }
}
```
- Response Codes:
  - 201 Created: User added successfully.

  - 400 Bad Request: Invalid request body.

  - 500 Internal Server Error: Failed to add user.

### Add Multiple Users
- URL: POST `/users/addAll`

- Description: Adds multiple users to the system.

- Request Body Example:
```json
[
   {
      "name": "John Doe",
      "email": "john@example.com"
   },
   {
      "name": "Jane Smith",
      "email": "jane@example.com"
   }
]
```
- Response Body: APIResponse containing list of added User objects.
- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": [
      {
         "id": 4,
         "name": "John Doe",
         "email": "john@example.com"
      },
      {
         "id": 5,
         "name": "Jane Smith",
         "email": "jane@example.com"
      }
   ]
}
```
- Response Codes:
  - 201 Created: Users added successfully.
  - 400 Bad Request: Invalid request body.
  - 500 Internal Server Error: Failed to add users.

### Update User
- URL: PUT `/users/update`
- Description: Updates an existing user in the system.
- Request Body Example:
```json
{
   "id": 1,
   "name": "John Doe",
   "email": "johnDoe@example.com"
}
```
- Response Body:
APIResponse containing updated User object.

- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": {
      "id": 1,
      "name": "John Doe",
      "email": "johnDoe@example.com"
   }
}
```
- Response Codes:
  - 200 OK: User updated successfully.
  - 400 Bad Request: Invalid request body.
  - 404 Not Found: User to update not found.

### Remove User
URL- : DELETE `/users/remove/{id}`
- Description: Removes a user from the system by their ID.
- Path Parameters:
`{id}`: The ID of the user to remove.
- Response Body:
APIResponse indicating success or failure.
- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": null
}
```
- Response Codes:
  - 200 OK: User removed successfully.
  - 404 Not Found: User with the specified ID not found.
  - 500 Internal Server Error: Failed to remove user.


## Concert
Base URL: `/concerts`

### Get All Concerts
- URL: GET `/concerts`
- Description: Retrieves all concerts from the system.
- Response Body:
List of Concert objects.
- Response Example:
```json
[
   {
      "id": 1,
      "name": "Concert A",
      "concertDateTime": "2024-04-10T19:30:00",
      "venue": "Venue A",
      "ticketsAvailable": 100,
      "bookingStartDateTime": "2024-04-01T09:00:00",
      "bookingEndDateTime": "2024-04-10T18:00:00"
   },
   {
      "id": 2,
      "name": "Concert B",
      "concertDateTime": "2024-04-15T20:00:00",
      "venue": "Venue B",
      "ticketsAvailable": 150,
      "bookingStartDateTime": "2024-04-05T09:00:00",
      "bookingEndDateTime": "2024-04-15T19:00:00"
   }
]
```
- Response Codes:
  - 200 OK: Successful retrieval of concerts.
  - 404 Not Found: No concerts found.

### Get Concert by ID
- URL: GET `/concerts/{id}`
- Description: Retrieves a concert by its ID.
- Path Parameters:
`{id}`: The ID of the concert to retrieve.
- Response Body:
APIResponse containing Concert object.
- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": {
      "id": 1,
      "name": "Concert A",
      "concertDateTime": "2024-04-10T19:30:00",
      "venue": "Venue A",
      "ticketsAvailable": 100,
      "bookingStartDateTime": "2024-04-01T09:00:00",
      "bookingEndDateTime": "2024-04-10T18:00:00"
   }
}
```
- Response Codes:
  - 200 OK: Concert found and retrieved successfully.
  - 404 Not Found: Concert with the specified ID not found.

### Add Concert
- URL: POST `/concerts/add`

- Description: Adds a new concert to the system.

- Request Body Example:
```json
{
   "name": "Concert C",
   "concertDateTime": "2024-04-20T21:00:00",
   "venue": "Venue C",
   "ticketsAvailable": 200,
   "bookingStartDateTime": "2024-04-10T09:00:00",
   "bookingEndDateTime": "2024-04-20T20:00:00"
}
```
- Response Body:
APIResponse containing added Concert object.

- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": {
      "id": 3,
      "name": "Concert C",
      "concertDateTime": "2024-04-20T21:00:00",
      "venue": "Venue C",
      "ticketsAvailable": 200,
      "bookingStartDateTime": "2024-04-10T09:00:00",
      "bookingEndDateTime": "2024-04-20T20:00:00"
   }
}
```
- Response Codes:
  - 201 Created: Concert added successfully.
  - 400 Bad Request: Invalid request body.
  - 500 Internal Server Error: Failed to add concert.

### Update Concert
- URL: PUT `/concerts/update`
- Description: Updates an existing concert in the system.
- Request Body Example:
```json
{
   "id": 3,
   "name": "Concert C",
   "concertDateTime": "2024-04-20T21:00:00",
   "venue": "New Venue C",
   "ticketsAvailable": 200,
   "bookingStartDateTime": "2024-04-10T09:00:00",
   "bookingEndDateTime": "2024-04-20T20:00:00"
}
```
- Response Body:
APIResponse containing updated Concert object.
- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": {
      "id": 3,
      "name": "Concert C",
      "concertDateTime": "2024-04-20T21:00:00",
      "venue": "New Venue C",
      "ticketsAvailable": 200,
      "bookingStartDateTime": "2024-04-10T09:00:00",
      "bookingEndDateTime": "2024-04-20T20:00:00"
   }
}
```
- Response Codes:
  - 200 OK: Concert updated successfully.
  - 400 Bad Request: Invalid request body.
  - 404 Not Found: Concert to update not found.

### Remove Concert
- URL: DELETE `/concerts/remove/{id}`
- Description: Removes a concert from the system by its ID.
- Path Parameters:
`{id}`: The ID of the concert to remove.
- Response Body:
APIResponse indicating success or failure.
- Response Example:
```json
{
   "status": "SUCCESS",
   "errorMessage": null,
   "data": null
}
```
- Response Codes:
  - 200 OK: Concert removed successfully.
  - 404 Not Found: Concert with the specified ID not found.
  - 500 Internal Server Error: Failed to remove concert.

### Get All Available Concerts
- URL: GET `/concerts/available`
- Description: Retrieves all **available** concerts from the system. Available concerts are concerts that : 
  - still has ticketsAvailable
  - concertDateTime > current date time
  - bookingStartDateTime < current date time
  - bookingEndDateTime > current date time
- Response Body:
  List of available Concert objects.
- Response Example:
```json
[
   {
      "id": 1,
      "name": "Concert A",
      "concertDateTime": "2024-04-10T19:30:00",
      "venue": "Venue A",
      "ticketsAvailable": 100,
      "bookingStartDateTime": "2024-04-01T09:00:00",
      "bookingEndDateTime": "2024-04-10T18:00:00"
   }
]
```
- Response Codes:
    - 200 OK: Successful retrieval of concerts.
    - 404 Not Found: No concerts found.

## Ticket
Base URL: `/tickets`
### Book Ticket
- URL: POST /tickets/book
- Description: Books a ticket for a user for a specific concert.
- Request Body Example:
```json
{
    "userId": 1,
    "concertId": 1
}
```
- Response Body:
APIResponse containing booked Ticket object. 
- Response Example (Success):
```json
{
    "status": "SUCCESS",
    "errorMessage": null,
    "data": {
        "id": 1,
        "concertId": 1,
        "userId": 1,
        "purchaseTime": "2024-04-10T12:30:00",
        "quantity": 1
    }
}
```
- Response Example (Error):
```json
{
    "status": "ERROR",
    "errorMessage": "Insufficient tickets available.",
    "data": null
}
```
- Response Codes:
  - 200 OK: Ticket booked successfully.
  - 400 Bad Request: Invalid request body or invalid user/concert ID.
  - 404 Not Found: User or concert not found.
  - 409 Conflict: Insufficient tickets available.

### Get User Tickets
- URL: GET `/tickets/user/{userId}`
- Description: Retrieves all tickets booked by a user.
- Path Parameters:
`{userId}`: ID of the user.
- Response Body:
APIResponse containing list of Ticket objects.
- Response Example (Success):
```json
{
    "status": "SUCCESS",
    "errorMessage": null,
    "data": [
        {
            "id": 1,
            "concertId": 1,
            "userId": 1,
            "purchaseTime": "2024-04-10T12:30:00",
            "quantity": 1
        },
        {
            "id": 2,
            "concertId": 2,
            "userId": 1,
            "purchaseTime": "2024-04-15T14:00:00",
            "quantity": 2
        }
    ]
}
```
- Response Example (Error):
```json
{
    "status": "ERROR",
    "errorMessage": "User with ID 1 not found.",
    "data": null
}
```
- Response Codes:
  - 200 OK: Tickets retrieved successfully.
  - 400 Bad Request: Invalid user ID.
  - 404 Not Found: User not found.
