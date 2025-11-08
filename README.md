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
```