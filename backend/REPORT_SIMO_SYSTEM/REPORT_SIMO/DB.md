# 🐳 Local MongoDB Setup Using Docker
ecam
This guide helps you run MongoDB locally using Docker with authentication and optional Mongo Express web UI.

---

## 📋 Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop) installed on your system

---

## 🚀 Getting Started

### 🏗️ Step 1: Pull MongoDB Image

```bash
docker pull mongo
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=admin123 \
  mongo
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -v mongodb_data:/data/db \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=admin123 \
  mongo
spring.data.mongodb.uri=mongodb://admin:admin123@localhost:27017/mydb?authSource=admin
docker run -d \
  --name mongodb \
  -p 27017:27017 \
  -e MONGO_INITDB_ROOT_USERNAME=admin \
  -e MONGO_INITDB_ROOT_PASSWORD=admin123 \
  mongo
docker run -d --name mongo-express -p 8082:8081 -e ME_CONFIG_MONGODB_ADMINUSERNAME=admin -e ME_CONFIG_MONGODB_ADMINPASSWORD=admin123 -e ME_CONFIG_MONGODB_SERVER=mongodb --link mongodb:mongo mongo-express

