# flyingfy - Sample Spring Boot project

Minimal Spring Boot project implementing a Booking entity and simple CRUD endpoints.

## Requirements
- Java 17
- Maven
- (Optional) IntelliJ IDEA

## Run locally
1. Clone or copy files into a Maven project (groupId=com.flyingfy, artifactId=flyingfy).
2. Build: `mvn clean package`
3. Run: `mvn spring-boot:run` or `java -jar target/flyingfy-0.0.1-SNAPSHOT.jar`
4. API endpoints:
    - POST /api/bookings
    - GET /api/bookings
    - GET /api/bookings/{id}

Example POST body:
```
{
  "passengerName": "Asha",
  "origin": "DEL",
  "destination": "BLR",
  "travelDate": "2025-12-25"
}
```

## H2 console
Open http://localhost:8080/h2-console and use JDBC URL `jdbc:h2:mem:flyingfydb` and user `sa`.