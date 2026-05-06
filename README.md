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

## API Endpoints

> Protected endpoints require `Authorization: Bearer <token>` header.

### Auth
| Method | Path | Description |
|--------|------|-------------|
| POST | `/api/v1/auth/register` | Register new account |
| POST | `/api/v1/auth/login` | Login, returns JWT |

### Exercises
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/exercises` | List all exercises (filter by `muscle`, `level`, `category`, `equipment`) |
| GET | `/api/v1/exercises/{id}` | Get exercise by ID |

### Workouts
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/workouts` | Get all workout sessions |
| POST | `/api/v1/workouts` | Create workout session |
| GET | `/api/v1/workouts/{id}` | Get session by ID |
| DELETE | `/api/v1/workouts/{id}` | Delete session |
| POST | `/api/v1/workouts/{id}/entries` | Add exercise entry to session |
| PUT | `/api/v1/workouts/{sessionId}/entries/{id}` | Update entry |
| DELETE | `/api/v1/workouts/{sessionId}/entries/{id}` | Delete entry |

### Progress & Records
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/progress/{exerciseId}` | Get progress history for an exercise |
| GET | `/api/v1/records` | Get all personal records |
| GET | `/api/v1/records/{exerciseId}` | Get personal record for specific exercise |

### User
| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/v1/users/me` | Get profile |
| PUT | `/api/v1/users/me` | Update profile |
| PUT | `/api/v1/users/me/password` | Change password |
| DELETE | `/api/v1/users/me` | Delete account |

## Roadmap
- [x] Personal records — automatically track best set per exercise
- [x] User profile - update user details, delete account
- [x] Add missing Workout operations: update, delete
- [x] Error handling - global exception handling
- [ ] Pagination for exercises and workout history
- [ ] Workout templates (save and reuse a session structure)
- [ ] Exercise instructions and images stored and exposed via the API
- [ ] Frontend client (React or mobile app)

## Credits

Exercise data sourced from [free-exercise-db](https://github.com/yuhonas/free-exercise-db) by [@yuhonas](https://github.com/yuhonas).
