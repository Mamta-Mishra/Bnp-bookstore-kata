# Bookstore API Documentation

This document provides details for all the REST API endpoints available in the Simple Online Bookstore backend.

## Base URL
`http://localhost:8811` (or the port configured in your environment)

## Authentication
This API uses **JWT (JSON Web Token)** for authentication.
1.  **Register** a new user.
2.  **Login** to receive a JWT.
3.  Include the JWT in the `Authorization` header as a **Bearer Token** for all subsequent requests:
    `Authorization: Bearer <your_token>`

---

## 1. Authentication API (Public)

### Register a User
Create a new user account.
*   **URL**: `/api/auth/register`
*   **Method**: `POST`
*   **Body**: `{"username": "...", "password": "..."}`
*   **Success**: `201 Created` - "User registered successfully!"

### Login
Validate credentials and receive a JWT.
*   **URL**: `/api/auth/login`
*   **Method**: `POST`
*   **Body**: `{"username": "...", "password": "..."}`
*   **Success Response**: `200 OK`
    ```json
    {
      "token": "eyJhbGciOiJIUzI1NiJ9...",
      "type": "Bearer"
    }
    ```

---

## 2. Books API (Auth Required)
*All Book endpoints require a **Bearer Token**.*

### Get All Books
*   **URL**: `/api/books`
*   **Method**: `GET`
*   **Success**: `200 OK`

### Get Book Details
*   **URL**: `/api/books/{id}`
*   **Method**: `GET`
*   **Success**: `200 OK`
*   **Error**: `404 Not Found`

---

## 3. Shopping Cart API (Auth Required)
*All Cart endpoints require a **Bearer Token**.*

### Get Cart Items
*   **URL**: `/api/cart`
*   **Method**: `GET`

### Add Item to Cart
*   **URL**: `/api/cart`
*   **Method**: `POST`
*   **Body**: `{"bookId": 1, "quantity": 1}`
*   **Success**: `201 Created`

### Update Item Quantity
*   **URL**: `/api/cart/{itemId}`
*   **Method**: `PUT`
*   **Body**: `{"quantity": 5}`
*   **Success**: `200 OK`

### Remove Item from Cart
*   **URL**: `/api/cart/{itemId}`
*   **Method**: `DELETE`
*   **Success**: `200 OK` - "Item successfully removed from cart"

### Clear Cart
*   **URL**: `/api/cart`
*   **Method**: `DELETE`
*   **Success**: `200 OK` - "Cart successfully cleared"

### Checkout
Process the current cart and generate a permanent Order.
*   **URL**: `/api/cart/checkout`
*   **Method**: `POST`
*   **Success**: `200 OK` - "Order placed successfully!"

---

## 4. Orders API (Auth Required)
*All Order endpoints require a **Bearer Token**.*

### Get Order History
Retrieve all past orders for the logged-in user.
*   **URL**: `/api/orders`
*   **Method**: `GET`
*   **Success Response**: `200 OK`
    ```json
    [
      {
        "id": 1,
        "orderDate": "2026-03-26T15:00:00",
        "totalAmount": 45.98,
        "items": [
          {
            "id": 1,
            "book": { "title": "The Psychology of Money" },
            "quantity": 2,
            "priceAtTimeOfPurchase": 18.50
          }
        ]
      }
    ]
    ```

---

## Error Codes
- `401 Unauthorized`: Token is missing, invalid, or expired.
- `400 Bad Request`: Validation failed.
- `404 Not Found`: Resource not found.
- `500 Internal Error`: Server-side error.
