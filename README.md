# SYSTEM-REPORT-SIMO-GATEWATE 🚀

A comprehensive full-stack application for Shinhan Bank's reporting integration with the SIMO gateway. This system provides secure user management, template management, file uploads, and automated reporting capabilities through a modern web interface.

## 📋 Table of Contents

- [Overview](#overview)
- [Architecture](#architecture)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [Development Setup](#development-setup)
- [Docker Deployment](#docker-deployment)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

SYSTEM-REPORT-SIMO-GATEWATE is a complete solution for managing financial reports and integrating with the SIMO (State Bank of Vietnam Information Management Office) gateway. The system consists of:

- **Backend**: Spring Boot REST API with MongoDB database
- **Frontend**: React-based web application with modern UI
- **Database**: MongoDB with authentication and initialization scripts
- **Containerization**: Docker support for easy deployment

## 🏗️ Architecture

```
SYSTEM-REPORT-SIMO-GATEWATE/
├── backend/                    # Spring Boot application
│   └── REPORT_SIMO_SYSTEM/
│       └── REPORT_SIMO/
│           ├── src/           # Java source code
│           ├── mongo-init/    # Database initialization
│           ├── docker-compose.yml
│           └── Dockerfile
├── frontend/                   # React application
│   ├── src/                   # React source code
│   ├── docker-compose.yml
│   └── Dockerfile
└── README.md
```

## ✨ Features

### 🔐 Authentication & Authorization
- JWT-based authentication system
- Role-based access control (USER, ADMIN, CHECKER)
- Token refresh mechanism
- Secure password handling

### 👥 User Management
- User registration and authentication
- Role assignment and management
- User profile management
- Admin user management interface

### 📊 Template Management
- CRUD operations for report templates
- Template schema definitions
- Template versioning
- Template validation

### 📁 File Upload System
- Secure file upload endpoints
- Multiple file format support
- Upload history tracking
- File validation and processing

### 🔄 SIMO Integration
- Automated reporting to SIMO gateway
- Multiple report type support
- Data transformation and validation
- Error handling and retry mechanisms

### 📈 History & Analytics
- Query history tracking
- Report generation history
- User activity monitoring
- Audit trail

## 🛠️ Technology Stack

### Backend
- **Java 17** - Programming language
- **Spring Boot 3.4.3** - Application framework
- **Spring Data MongoDB** - Database access
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based authentication
- **Log4j2** - Logging framework
- **Lombok** - Code generation
- **Maven** - Build tool

### Frontend
- **React 19** - UI framework
- **Vite 6** - Build tool
- **Bootstrap 5.3** - CSS framework
- **Tailwind CSS 4.0** - Utility-first CSS
- **React Router 7** - Client-side routing
- **Axios** - HTTP client
- **React DatePicker** - Date handling
- **SweetAlert2** - Notifications

### Database
- **MongoDB** - NoSQL database
- **MongoDB Docker Image** - Containerized database

### DevOps
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Nginx** - Web server (frontend)

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 17+** installed
- **Node.js 18.18.0+** installed
- **Docker & Docker Compose** installed
- **Maven 3.8+** installed
- **Git** for version control

## 🚀 Quick Start

### Option 1: Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd SYSTEM-REPORT-SIMO-GATEWATE
   ```

2. **Start the entire stack**
   ```bash
   # From the frontend directory
   cd frontend
   docker-compose up -d
   ```

3. **Access the application**
   - Frontend: http://localhost:5173
   - Backend API: http://localhost:8081
   - MongoDB: localhost:27017

### Option 2: Manual Setup

#### Backend Setup
```bash
cd backend/REPORT_SIMO_SYSTEM/REPORT_SIMO

# Build the application
./mvnw clean package

# Run the application
java -jar target/REPORT_SIMO-0.0.1-SNAPSHOT.jar
```

#### Frontend Setup
```bash
cd frontend

# Install dependencies
npm install

# Create environment file
echo "VITE_SIMO_APP_API_URL=http://localhost:8081" > .env.development

# Start development server
npm run dev
```

## 🔧 Development Setup

### Backend Development

1. **Database Setup**
   ```bash
   # Start MongoDB with Docker
   docker run -d \
     --name mongodb \
     -p 27017:27017 \
     -e MONGO_INITDB_ROOT_USERNAME=admin \
     -e MONGO_INITDB_ROOT_PASSWORD=admin123 \
     mongo
   ```

2. **Configuration**
   - Edit `backend/REPORT_SIMO_SYSTEM/REPORT_SIMO/src/main/resources/application.properties`
   - Set MongoDB connection string
   - Configure SIMO gateway URL
   - Set file upload directory

3. **Run with Maven**
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend Development

1. **Environment Configuration**
   ```bash
   # Create .env.development file
   VITE_SIMO_APP_API_URL=http://localhost:8081
   ```

2. **Development Commands**
   ```bash
   npm run dev      # Start development server
   npm run build    # Build for production
   npm run lint     # Run ESLint
   npm run preview  # Preview production build
   ```

## 🐳 Docker Deployment

### Backend Docker Setup
```bash
cd backend/REPORT_SIMO_SYSTEM/REPORT_SIMO

# Build and run with Docker Compose
docker-compose up -d
```

### Frontend Docker Setup
```bash
cd frontend

# Build and run with Docker Compose
docker-compose up -d
```

### Environment Variables

#### Backend
- `SPRING_DATA_MONGODB_URI` - MongoDB connection string
- `UPLOAD_DIR` - File upload directory
- `SIMO_URL` - SIMO gateway URL

#### Frontend
- `VITE_SIMO_APP_API_URL` - Backend API URL

## 📚 API Documentation

### Authentication Endpoints
- `POST /login` - User authentication
- `POST /refresh-token` - Token refresh

### User Management
- `GET /api/users` - Get all users
- `POST /api/users` - Create user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Template Management
- `GET /api/templates` - Get all templates
- `POST /api/templates` - Create template
- `PUT /api/templates/{id}` - Update template
- `DELETE /api/templates/{id}` - Delete template

### File Upload
- `POST /api/upload` - Upload single file
- `GET /api/upload_history` - Get upload history

### SIMO Integration
- `POST /api/simo/tktt/upload` - Upload TKTT report
- `POST /api/simo/token` - Get SIMO token

## 🗄️ Database Schema

### Collections
- **users** - User accounts and authentication
- **templates** - Report template definitions
- **api_1_6_tktt_dinh_ky** - Periodic TKTT reports
- **api_1_7_tktt_nnngl** - Non-periodic TKTT reports
- **list_file_upload** - File upload history

### Initialization
The database is automatically initialized with:
- Default admin user
- Sample templates
- Required indexes

## ⚙️ Configuration

### Backend Configuration
```properties
# Application
spring.application.name=REPORT_SIMO
server.port=8081

# Database
spring.data.mongodb.database=SIMO
spring.data.mongodb.uri=mongodb://admin:admin123@localhost:27017/?authSource=admin

# File Upload
file.upload-dir=/path/to/upload

# HTTP Client
http.client.max-total=50
http.client.max-per-route=20
http.client.connect-timeout=30000
```

### Frontend Configuration
```javascript
// vite.config.js
export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173
  }
})
```

## 🤝 Contributing

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**
4. **Test your changes**
   ```bash
   # Backend tests
   ./mvnw test
   
   # Frontend tests
   npm run lint
   ```
5. **Commit your changes**
   ```bash
   git commit -m 'Add your feature description'
   ```
6. **Push to the branch**
   ```bash
   git push origin feature/your-feature-name
   ```
7. **Open a Pull Request**

## 📄 License

This project is proprietary software developed for Shinhan Bank. All rights reserved.

## 🆘 Support

For support and questions:
- Check the existing documentation
- Review the source code
- Contact the development team

---

**Note**: This application is designed for internal use at Shinhan Bank and integrates with the SIMO gateway system operated by the State Bank of Vietnam.
