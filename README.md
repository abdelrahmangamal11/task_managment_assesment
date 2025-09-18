Task Management API
Description

A RESTful API for managing tasks per user, with JWT authentication and logout via token blacklisting.

Features

User registration (/auth/register)

User login (/auth/login) → JWT token

Create, read, update, delete tasks

JWT-protected endpoints

Logout → invalidate token using a blacklist

Clear exception handling:

400 Bad Request

401 Unauthorized

403 Forbidden

Endpoints
Authentication
Method	Endpoint	Description	Auth Required
POST	/auth/register	Register a new user	❌
POST	/auth/login	Login → returns JWT	❌
POST	/auth/logout	Logout → blacklist token	✅
Tasks
Method	Endpoint	Description	Auth Required
GET	/tasks	Get all tasks of current user	✅
GET	/tasks/{id}	Get task by ID (current user)	✅
POST	/tasks	Create a new task	✅
PUT	/tasks/{id}	Update a task	✅
DELETE	/tasks/{id}	Delete a task	✅
Authentication

After login, the user receives a JWT token.
Include the token in the header for protected endpoints:

Authorization: Bearer <JWT_TOKEN>

Logout

Endpoint: /auth/logout
Adds the token to an in-memory blacklist.
Any subsequent request with the same token will return 401 Unauthorized.

Error Handling

400 → Invalid input

401 → Invalid or missing token

403 → Forbidden access

500 → Internal server error

Entities

User: id, email, password, name, tasks

Task: id, title, description, status, user

Running the Project

Clone the repository:

git clone <repo-url>
cd <repo-folder>


Add jwt.secret in src/main/resources/application.properties:

jwt.secret=your_secret_key_here


Run with Maven:

mvn spring-boot:run


API will run at: http://localhost:8080

Example Requests
Register User
curl -X POST http://localhost:8080/auth/register \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com","password":"password123","name":"John Doe"}'

Login User
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com","password":"password123"}'

Get Tasks
curl -X GET http://localhost:8080/tasks \
-H "Authorization: Bearer <JWT_TOKEN>"

Create Task
curl -X POST http://localhost:8080/tasks \
-H "Authorization: Bearer <JWT_TOKEN>" \
-H "Content-Type: application/json" \
-d '{"title":"Finish README","description":"Write GitHub README","status":"Open"}'

Logout
curl -X POST http://localhost:8080/auth/logout \
-H "Authorization: Bearer <JWT_TOKEN>"
