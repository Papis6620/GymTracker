# GymTracker API

Spring Boot REST API for tracking gym workouts and exercise progress. Built as a personal project by a gym enthusiast who wanted a simple way to log sessions, track weights over time, and browse exercises — while learning Spring Security and JWT authentication.

## Stack

- **Java 21** + **Spring Boot 3.5**
- **Spring Security** with stateless JWT authentication (JJWT 0.12.6)
- **Spring Data JPA** + **PostgreSQL**
- **Lombok**, **Maven**

## Prerequisites

- Java 21
- PostgreSQL running on `localhost:5432`

## Setup

1. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE gym_tracker;
   ```

2. Configure `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password
   jwt.secret-key=your_base64_encoded_secret
   jwt.expiration-time=86400000
   ```

3. Run:
   ```bash
   ./mvnw spring-boot:run
   ```

On first run, Hibernate auto-creates the schema and the app seeds 800+ exercises from `exercises.json`.

## Authentication

Register or login to receive a JWT token, then pass it in the `Authorization` header for protected routes:
```
Authorization: Bearer <token>
```

## Endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/api/v1/auth/register` | No | Register |
| POST | `/api/v1/auth/login` | No | Login, returns JWT |
| GET | `/api/v1/exercises` | No | List exercises (filterable by `muscle`, `level`, `category`, `equipment`) |
| GET | `/api/v1/exercises/{id}` | No | Get exercise by ID |
| GET | `/api/v1/workouts` | Yes | Get user's workout sessions |
| POST | `/api/v1/workouts` | Yes | Create workout session |
| POST | `/api/v1/workouts/{id}/entries` | Yes | Add exercise entry to session |
| GET | `/api/v1/progress/{exerciseId}` | Yes | Get progress history for an exercise |

Protected endpoints require `Authorization: Bearer <token>`.

## Roadmap

- [ ] Pagination for exercises and workout history
- [ ] Workout templates (save and reuse a session structure)
- [ ] Personal records — automatically track best set per exercise
- [ ] Exercise instructions and images stored and exposed via the API
- [ ] Frontend client (React or mobile app)

## Credits

Exercise data sourced from [free-exercise-db](https://github.com/yuhonas/free-exercise-db) by [@yuhonas](https://github.com/yuhonas).
