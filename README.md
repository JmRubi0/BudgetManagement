# BudgetManagement API

## Project Overview
The Budget Management API is a Spring Boot RESTful API that allows users to:
- Manage budget categories  
- Track income & expenses  
- Generate financial reports  
- Support multi-language responses (English & French)
---
## Core Features
- User Authentication & Role Management** (`USER` & `ADMIN`)  
- Budget CRUD (Create, Update, Delete Categories)**  
- Transaction Recording (Income & Expenses)**  
- Financial Reports & Summary Calculations**  
- Internationalization (English & French)**  
- Spring Security with Basic Authentication**  
---
## API Endpoints
USER MANAGEMENT
- POST	/users/register	Register a new user (for simplicity use username user as USER role and username admin as ADMIN role)
- GET	/users/{username}	Get user details
- GET /admin/users Get all users ONLY role ADMIN can access this endpoint
BUDGET MANAGEMENT
- POST	"/budget/category"	Create budget category
- PUT	"/budget/category/{id}"	Update budget category by id
- DELETE	"/budget/category/{id}"	Delete budget category by id
- GET	"/budget/summary"	Get total income, expenses, and balance
- GET "budget/category" filter search category key: name | value:(name of the category you want to filter eg: Food)
TRANSACTION MANAGEMENT
- POST	"/budget/transaction"	Add income/expense transaction
- GET "/budget/transactions/report/{year}" Get report income vs expense from year entered
---
## Postman
To test
Content-Type applicaton/json
They are all done on raw body JSON format
for "budget/category" key: Accept-Language value:(en as default and you can set it to fr for french)

-To register
{
    "username": "user",
    "password": "password",
    "role": "USER"
}

-To create a budgetCategory
{
    "name": "Food",
    "description": "all expenses for food"
}

-Add a transaction
{
  "amount": 850.0,
  "date": "2024-12-31T14:30:00",
  "type": "expense",
  "owner": {
    "id": 1
  },
  "category": {
    "id": 1
  }
}

