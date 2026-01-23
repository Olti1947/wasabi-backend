# 🍣 Sushi Wasabi – Backend API

Backend service for the **Sushi Wasabi App**, powering authentication, ordering, payments, admin management, and real‑time chat between users and admins.

---

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security + JWT** (authentication & authorization)
* **Spring Data JPA / Hibernate**
* **PostgreSQL** (production) / **H2** (development & testing)
* **WebSockets (STOMP)** – live chat & real‑time updates
* **Firebase Admin SDK** – push notifications
* **Maven** – dependency management

---

## 📦 Features

### 👤 Authentication & Users

* User registration & login
* JWT‑based authentication
* Role‑based access (`USER`, `ADMIN`)
* Secure password hashing

### 🍱 Orders & Payments

* Create and manage sushi orders
* Order status tracking (pending, preparing, delivered, cancelled)
* Payment intent handling
* Admin order management

### 💬 Live Chat

* Real‑time user ↔ admin messaging
* WebSocket connection with STOMP
* Unread message counters
* Chat history persistence

### 🔔 Notifications

* Firebase push notifications
* Order status updates
* New admin/user messages

### 🧑‍💼 Admin Panel Support

* Manage users
* Manage menu items
* View & update orders
* Respond to live chat

---

## 🗂 Project Structure

```
src/main/java/com/sushiwasabi
│
├── config          # Security, WebSocket, Firebase config
├── controller      # REST & WebSocket controllers
├── dto             # Data Transfer Objects
├── entity          # JPA entities
├── repository      # Database repositories
├── service         # Business logic
├── security        # JWT filters & auth logic
└── util            # Helpers & utilities
```

---

## 🔐 Security

* JWT tokens for stateless authentication
* Access control via Spring Security
* Protected admin‑only endpoints
* WebSocket authentication using JWT

---

## ⚙️ Environment Variables

Create an `application.yml` or `.env` file with the following:

```yaml
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/sushiwasabi
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=yourpassword

JWT_SECRET=your_jwt_secret
JWT_EXPIRATION=86400000

FIREBASE_CONFIG=resources/your_service_account.json
```

---

## ▶️ Running the Project

### Development

```bash
mvn spring-boot:run
```

Backend runs at:

```
http://localhost:8080
```

### Build

```bash
mvn clean package
java -jar target/sushi-wasabi-backend.jar
```

---

## 🔌 API Overview

| Method | Endpoint           | Description          |
| ------ | ------------------ | -------------------- |
| POST   | /auth/login        | User login           |
| POST   | /auth/register     | User registration    |
| GET    | /orders            | Get user orders      |
| POST   | /orders            | Create order         |
| PUT    | /admin/orders/{id} | Update order status  |
| WS     | /ws/chat           | Live chat connection |

---

## 🧪 Testing

* Manual API testing via Postman

---

## 📱 Frontend Integration

Designed to work with:

* **React Native (Expo)** mobile app
* Redux for state management
* STOMP WebSocket client for chat

---

## 🛠 Future Improvements

* Order analytics dashboard
* Message read receipts
* Rate limiting
* Dockerized deployment
* CI/CD pipeline

---

## 👨‍💻 Author

**Olt Bajrami**
Computer Science Engineer

---

## 📄 License

This project is private and intended for educational and commercial use.
