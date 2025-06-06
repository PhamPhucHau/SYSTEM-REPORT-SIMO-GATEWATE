# REPORT_SIMO

## Overview
REPORT_SIMO is a Spring Boot-based backend system designed for Shinhan Bank's reporting integration with the SIMO gateway. It provides secure RESTful APIs for user management, template management, file uploads, and automated reporting to the SIMO system. The application leverages MongoDB for data storage and supports robust authentication and authorization mechanisms.

## Features
- **User Management**: Register, authenticate, and manage users with roles (USER, ADMIN, CHECKER).
- **Template Management**: CRUD operations for report templates, including schema definitions.
- **File Upload**: Secure file upload endpoints for report data, with upload history tracking.
- **Automated SIMO Reporting**: Endpoints for uploading and updating various report types to the SIMO gateway, supporting multiple templates and data formats.
- **Authentication**: JWT-based login, token refresh, and role-based access control.
- **Logging**: Log4j2 and logback support for application and debug logs.
- **Docker Support**: Containerized deployment with a provided Dockerfile.

## Technology Stack
- Java 17
- Spring Boot 3.4.x
- Spring Data MongoDB
- Spring Security (JWT)
- Log4j2
- Lombok
- Maven
- Docker

## Getting Started
### Prerequisites
- Java 17+
- Maven 3.8+
- MongoDB instance (cloud or local)

### Build & Run
```bash
# Build the project
./mvnw clean package

# Run the application
java -jar target/REPORT_SIMO-0.0.1-SNAPSHOT.jar
```

### Docker
```bash
# Build Docker image
docker build -t report_simo .

# Run Docker container
# (Ensure MongoDB is accessible from the container)
docker run -p 8081:8081 --env-file .env report_simo
```

## Configuration
Edit `src/main/resources/application.properties` for database, SIMO gateway, and other settings. Example:
```
spring.data.mongodb.uri=mongodb+srv://<user>:<pass>@host/db
server.port=8081
app.simo_URL=https://mgsimotest.sbv.gov.vn/
file.upload-dir=/path/to/upload
```

## API Endpoints
- **Auth**: `/login`, `/refresh-token`
- **User**: `/api/users` (CRUD)
- **Template**: `/api/templates` (CRUD)
- **File Upload**: `/api/upload` (single file), `/api/upload_history` (history)
- **SIMO Reporting**: `/api/simo/tktt/upload`, `/api/simo/token`, etc.

See controller classes for detailed request/response formats.

## Testing
Run unit and integration tests with:
```bash
./mvnw test
```

## Logging
- Log4j2 configuration: `src/main/resources/log4j2.properties`
- Logback (optional): `src/main/resources/logback-spring.xml`
- Logs output to `logs/` directory

## Contributing
1. Fork the repository
2. Create a feature branch
3. Commit and push your changes
4. Open a pull request

## License
Proprietary - Shinhan Bank

---
For more details, see `HELP.md` and the source code documentation.
