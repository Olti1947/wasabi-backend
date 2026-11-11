# 🍣 Wasabi Sushi – Backend API

Backend service for the **Wasabi Sushi App**: authentication, menu, ordering with discounts, loyalty QR codes, admin management and push notifications.

The mobile app lives in **[wasabi-client](https://github.com/Olti1947/wasabi-client)**, which also has screenshots of the app.

---

## 🚀 Getting started

### Prerequisites

- **Java 17 or newer**
- **Docker** (runs the PostgreSQL database)

Maven comes with the repo (`./mvnw`), so you don't need to install it.

### Run it

```bash
git clone https://github.com/Olti1947/wasabi-backend.git
cd wasabi-backend

# 1. Configure environment variables
cp .env.example .env
# then set JWT_SECRET in .env, e.g. to the output of: openssl rand -base64 32

# 2. Start PostgreSQL (localhost:5431, db "wasabi", user postgres / root)
docker compose up -d --wait

# 3. Run the app (Flyway applies the database migrations on startup)
./mvnw spring-boot:run
```

The API runs on http://localhost:8080. Swagger UI: http://localhost:8080/swagger-ui/index.html

### Demo accounts and data

On first start, with the values from `.env.example`, the backend creates:

| Role | Email | Password |
| --- | --- | --- |
| Admin | `admin@wasabi.local` | `admin123` |
| Customer | `customer@wasabi.local` | `customer123` |

- **The admin** comes from `ADMIN_EMAIL` / `ADMIN_PASSWORD`. It's only created while the database has no admin, so changing those values later doesn't affect an existing admin.
- **Demo content** (`SEED_DEMO_DATA=true`) fills an empty database with a 22-item menu, home banners, three discounts and a demo customer with a saved address and orders in different states. The data is in [`src/main/resources/db/demo/demo-data.sql`](src/main/resources/db/demo/demo-data.sql).
- **To start over** with a fresh database: `docker compose down -v`, then start again.

Use different credentials and turn `SEED_DEMO_DATA` off anywhere other than your own machine.

### Optional integrations

| Feature | What you need in `.env` |
| --- | --- |
| Image uploads | `CLOUDINARY_CLOUD_NAME`, `CLOUDINARY_API_KEY`, `CLOUDINARY_API_SECRET` |
| Push notifications | A Firebase service-account JSON at `src/main/resources/firebase-service-account/sushi-wasabi-service-account.json`, or its path in `FIREBASE_CREDENTIALS_PATH` |

The app starts without either one. Uploads fail, and push notifications are skipped with a warning in the log.

### Useful commands

```bash
./mvnw test                 # needs the database running
docker compose down         # stop the database (data is kept)
docker compose down -v      # stop it and delete all data
```

---

## 🧰 Tech Stack

- **Java 17**, **Spring Boot 3**
- **Spring Security + JWT** (stateless authentication)
- **Spring Data JPA / Hibernate**, **PostgreSQL**, **Flyway** migrations
- **Firebase Admin SDK** (push notifications)
- **Cloudinary** (image hosting)
- **springdoc-openapi** (Swagger UI)
- **Maven**

---

## 📦 Features

### 👤 Authentication & users
- Registration and login with JWT access and refresh tokens
- Role-based access (`USER`, `ADMIN`) and BCrypt password hashing
- Profile, saved addresses and loyalty spending per user

### 🍱 Menu & orders
- Menu with categories (nigiri, maki, uramaki, starters, combos, other), popular items and filters
- Checkout with a price preview and discounts
- Order statuses: created, in progress, delivery, ready, completed, cancelled, refunded

### 🎟️ Discounts & loyalty
- Percentage and fixed discounts, limited to specific products, with minimum order values and date ranges
- Users activate discounts, and admins mark them as used
- Personal QR code per user. Admins scan it to add spending.

### 🧑‍💼 Admin
- Manage food items, banners and discounts (with image uploads)
- View all orders and update their status
- Push notifications for new orders and status changes

---

## 🔌 API overview

Full, interactive documentation is in Swagger UI at `/swagger-ui/index.html` while the app is running.

| Base path | Purpose |
| --- | --- |
| `/api/v1/auth-service` | Register, login, refresh and validate tokens |
| `/api/foods` | Menu, filters, popular items; admin CRUD under `/api/foods/admin` |
| `/api/banner-images` | Home screen banners |
| `/api/discounts` | Available and active discounts, activation; admin management |
| `/api/checkout` | Price preview, applicable discounts, placing an order |
| `/api/me` | Current user's profile, addresses and spending |
| `/api/admin/orders` | Admin order list and status updates |
| `/api/admin/qr` | Look up a customer by QR code and add spending |
| `/api/notification` | Register device tokens for push notifications |

---

## 🗂 Project structure

```
src/main/java/com/sushi/wasabi
├── common          # Exception handling and shared responses
├── config          # Firebase, Cloudinary, admin and demo-data seeding
├── controller      # REST controllers
├── dto             # Data transfer objects
├── entity          # JPA entities
├── enums           # Categories, statuses, discount types
├── repository      # Spring Data repositories
├── security        # JWT filter and Spring Security configuration
└── services        # Business logic
src/main/resources
├── db/migration    # Flyway migrations
└── db/demo         # Demo data for local development
```

---

## 👨‍💻 Author

**Olt Bajrami**
Computer Science Engineer

---

## 📄 License

This project is private and intended for educational and commercial use.
