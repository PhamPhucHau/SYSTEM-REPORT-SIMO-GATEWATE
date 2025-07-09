# SVB SIMO API Web Testing Interface

This document describes the web-based interface for testing and managing SVB SIMO APIs.

## Overview

The web interface provides a user-friendly way to interact with the SVB SIMO APIs without needing to use external tools like Postman or curl. It includes:

- **Authentication APIs**: Generate tokens and hashing values
- **Batch Job APIs**: Execute file processing, file scanning, and SVB upload operations
- **File Management APIs**: Query and filter uploaded files

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Running SVB SIMO application

### Running the Application

1. Navigate to the project directory:
   ```bash
   cd svbom
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the web interface:
   ```
   http://localhost:8080/simo/api/web/
   ```

## Web Interface Features

### 1. Home Page (`/web/`)
- Overview of all available API categories
- Quick navigation to different API sections
- System status indicators

### 2. Authentication APIs (`/web/auth`)

#### Generate Token
- **Endpoint**: `POST /v1/auth/generateToken`
- **Description**: Generates authentication token for user
- **Input**: JSON parameters (username, password, etc.)
- **Output**: User token information

#### Generate Hashing Value
- **Endpoint**: `GET /v1/auth/generateHashingValue`
- **Description**: Generates hash value from input string
- **Input**: Source string parameter
- **Output**: Hashed value

### 3. Batch Job APIs (`/web/batch`)

#### Process Files
- **Endpoint**: `POST /batch-job/process`
- **Description**: Executes SIMO file processing batch job
- **Action**: Processes uploaded files

#### Get Files
- **Endpoint**: `POST /batch-job/get-file`
- **Description**: Scans for new files in configured directories
- **Action**: Retrieves files from source locations

#### Upload to SVB
- **Endpoint**: `POST /batch-job/upload-svb`
- **Description**: Processes and uploads files to SVB system
- **Action**: Uploads processed files to SVB

### 4. File Management APIs (`/web/file`)

#### List Files
- **Endpoint**: `GET /file/list`
- **Description**: Query uploaded files with filtering
- **Parameters**:
  - `uploadDateStart` (required): Start date for file uploads
  - `uploadDateEnd` (required): End date for file uploads
  - `templateCode` (required): Template code to filter by
  - `fileName` (optional): Filter by specific file name
  - `fileStatus` (optional): Filter by file status

## Features

### Interactive Forms
- Pre-filled example data for easy testing
- Real-time validation
- Clear error messages

### Response Display
- Formatted JSON responses
- Syntax highlighting
- Copy-to-clipboard functionality

### Quick Search Templates
- Today, Yesterday, This Week, This Month
- One-click date range selection

### Job Status Monitoring
- Real-time job execution status
- Response logging
- Success/error indicators

### Responsive Design
- Mobile-friendly interface
- Bootstrap 5 styling
- Modern UI components

## API Documentation

Each page includes built-in API documentation showing:
- Endpoint URLs
- Required and optional parameters
- Expected response formats
- Usage examples

## Configuration

### Thymeleaf Settings
The following Thymeleaf configuration is applied in `application.properties`:

```properties
spring.thymeleaf.cache=false
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.mode=HTML
```

### Static Resources
- CSS: `/static/css/style.css`
- JavaScript: `/static/js/api-client.js`
- Templates: `/templates/`

## Security Considerations

- The web interface is designed for testing and development
- No authentication is required for the web interface itself
- API endpoints maintain their existing security configurations
- Consider restricting access in production environments

## Troubleshooting

### Common Issues

1. **Page not loading**
   - Check if the application is running on the correct port
   - Verify Thymeleaf dependency is included in pom.xml

2. **API calls failing**
   - Check browser console for JavaScript errors
   - Verify API endpoints are accessible
   - Check application logs for backend errors

3. **Styling issues**
   - Clear browser cache
   - Verify static resources are being served correctly

### Logs
Check application logs for detailed error information:
```bash
tail -f logs/simo.log
```

## Development

### Adding New APIs
1. Create new controller method
2. Add corresponding web page template
3. Update navigation in layout.html
4. Add JavaScript handlers for API calls

### Customizing Styles
- Modify `/static/css/style.css`
- Use Bootstrap 5 classes for layout
- Add custom animations and effects

### Extending Functionality
- Add new JavaScript functions to `/static/js/api-client.js`
- Create additional utility functions as needed
- Implement new UI components

## Support

For issues or questions regarding the web interface:
1. Check the application logs
2. Review the API documentation
3. Test API endpoints directly
4. Contact the development team

---

**Note**: This web interface is intended for development and testing purposes. For production use, consider implementing proper authentication and authorization mechanisms. 