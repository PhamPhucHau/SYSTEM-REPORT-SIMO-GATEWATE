# syntax=docker/dockerfile:1

# Build stage
FROM node:18-alpine AS build

WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm ci

# Copy source code
COPY . .
# Thiết lập biến môi trường cho quá trình build
ARG VITE_SIMO_APP_API_URL=http://localhost:8081
ENV VITE_SIMO_APP_API_URL=${VITE_SIMO_APP_API_URL}
# Build the application
RUN npm run build

# Production stage
FROM nginx:alpine AS final

# Copy built files from build stage to nginx serve directory
COPY --from=build /app/dist /usr/share/nginx/html

# Copy nginx configuration if needed
# COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]