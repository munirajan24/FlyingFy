# flyingfy - Sample Spring Boot project with JWT auth


Minimal Spring Boot project implementing a Booking entity and simple CRUD endpoints, plus JWT-based authentication.


## Requirements
- Java 17
- Maven
- (Optional) IntelliJ IDEA


## Run locally
1. Clone or copy files into a Maven project (groupId=com.flyingfy, artifactId=flyingfy).
2. Build: `mvn clean package`
3. Run: `mvn spring-boot:run` or `java -jar target/flyingfy-0.0.1-SNAPSHOT.jar`
4. API endpoints:
- POST /auth/register { email, password, fullName }
- POST /auth/login { email, password } -> returns { token }
- Use `Authorization: Bearer <token>` for the `/api/bookings` endpoints
- POST /api/bookings
- GET /api/bookings
- GET /api/bookings/{id}


Example register request (curl):
```
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"email":"asha@example.com","password":"secret123","fullName":"Asha"}'
```


Example login request (curl):
```
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"email":"asha@example.com","password":"secret123"}'
```


Use the returned token in the Authorization header for protected endpoints:
```
curl -H "Authorization: Bearer <token>" http://localhost:8080/api/bookings
```


H2 console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:flyingfydb user: sa)


**Deploying (short guide)**


1. **Local (Docker Compose)**
- Copy `.env.example` to `.env` and change `JWT_SECRET`.
- Build & run:
```bash
docker compose up --build
```
- App will be available at `http://localhost:8080` and Postgres at `db:5432` inside the compose network.


2. **Railway / Render / Railway**
- You can connect GitHub repo directly in Render or Railway and set the build command to `mvn -DskipTests clean package` and the start command to `java -jar target/*.jar`.
- Add environment variables in the provider's dashboard (JWT_SECRET, DB URL/credentials if using managed Postgres).


3. **Persisting secrets**
- Never store `JWT_SECRET` in source. Use GitHub Secrets and platform environment variables.


4. **Database migrations**
- Consider adding Flyway or Liquibase before running on production so schema changes are versioned.

# Running & troubleshooting notes (quick reference)

This file contains practical notes you asked for: how to run Docker Compose, build with Maven, and ensure `JAVA_HOME` matches the project's Java version.

## Build with Maven (local)

Use the Maven wrapper (no global Maven required):

**Windows PowerShell:**

```powershell
# From project root
.\mvnw.cmd -DskipTests clean package
```

**macOS / Linux:**

```bash
./mvnw -DskipTests clean package
```

After a successful build, the jar will be in `target/` (e.g. `target/flyingfy-0.0.1-SNAPSHOT.jar`).

## JAVA_HOME must match project JDK

The compiler uses `JAVA_HOME` to find `javac`. If `JAVA_HOME` points to a different/older JDK (e.g., Java 8), Maven will fail with errors such as `invalid flag: --release`.

### Quick check (PowerShell):

```powershell
java -version
javac -version
where.exe java
where.exe javac
echo $env:JAVA_HOME
```

### Temporary (current session) set JAVA_HOME to JDK17

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
# verify
java -version
javac -version
```

This change affects only the current PowerShell session. Use `setx` to persist.

### Persist JAVA_HOME (permanent)

```powershell
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
# Re-open PowerShell for changes to take effect
```

## Docker Compose (start: app + Postgres + Redis)

From the project root (where `docker-compose.yml` is located):

```powershell
# build images and start (foreground)
docker compose up --build
# or run in background
docker compose up -d
```

To stop and remove containers (keep volumes):

```powershell
docker compose down
```

To stop and remove containers **and volumes** (destroys DB data):

```powershell
docker compose down -v
```

## Common Docker issue: "Unable to access jarfile /app.jar"

Cause: Docker image's ENTRYPOINT expects a jar at `/app/app.jar` but the runtime pointed at `/app.jar` (root). The provided multi-stage Dockerfile copies the jar into `/app/app.jar` and sets the entrypoint to `java -jar /app/app.jar`.

If you see `Unable to access jarfile /app.jar`, replace your `Dockerfile` with the multi-stage version provided in the repo and rebuild:

```powershell
docker compose up --build
```

## Ensure JWT_SECRET is set (important for Docker)

Copy and edit the `.env` file before running docker compose:

```powershell
copy .env.example .env
notepad .env
# edit JWT_SECRET to be at least 32 characters long
```

Docker Compose reads `.env` automatically in the project root.

## Quick API smoke-test (after app is Up)

Register a user, login, then create a booking (PowerShell examples):

```powershell
# Register
curl -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" -d '{"email":"test@example.com","password":"secret123","fullName":"Test User"}'

# Login
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"email":"test@example.com","password":"secret123"}'
# Response will be {"token":"..."}

# Create booking (replace <TOKEN>)
curl -X POST http://localhost:8080/api/bookings -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" -d '{"passengerName":"Test","origin":"DEL","destination":"BLR","travelDate":"2025-12-25"}'

# List bookings
curl -X GET http://localhost:8080/api/bookings -H "Authorization: Bearer <TOKEN>"
```

## Stopping / Restarting

* Stop foreground compose: press `Ctrl + C` in the terminal running `docker compose up`.
* Stop background compose: `docker compose down`.
* Restart: `docker compose down && docker compose up -d`.

## Final notes

* Always ensure `JAVA_HOME` and the Java version used by Maven match the project's Java target (Java 17). The Maven wrapper will use the `JAVA_HOME` visible to the process.
* Keep secrets (JWT_SECRET, DB credentials) in environment variables or your host platform secrets store — do not commit them to Git.
