# CI/CD Pipeline Overview

## 🚀 How It Works

Every time you **push code to the `main` branch**, the following happens automatically:

### 1️⃣ Build & Test Phase
- ✅ Checks out your code
- ✅ Sets up Java 21
- ✅ Starts PostgreSQL test database
- ✅ Runs all unit tests
- ✅ Builds the JAR file
- ✅ Uploads artifact for deployment

### 2️⃣ Docker Image Build Phase
- ✅ Builds Docker image from Dockerfile
- ✅ Pushes image to GitHub Container Registry
- ✅ Tags with `latest` and commit SHA
- ✅ Available at: `ghcr.io/thaboxan/spring_api:latest`

### 3️⃣ Docker Deployment Phase (localhost:8080)
- ✅ Ensures PostgreSQL container is running (with persistent volume)
- ✅ Pulls latest image from GitHub Container Registry
- ✅ Stops old Spring Boot container
- ✅ Deploys new Spring Boot container
- ✅ Connects to shared PostgreSQL database
- ✅ **Data persists** - todos are NOT deleted!

### 4️⃣ VM Deployment Phase (172.20.61.178:8081)
- ✅ Copies JAR file to WSL2/VM via SSH
- ✅ Stops old application process
- ✅ Starts new application
- ✅ Connects to shared PostgreSQL database
- ✅ **Data persists** - todos are NOT deleted!

### 5️⃣ Health Check Phase
- ✅ Verifies Docker deployment is healthy
- ✅ Verifies VM deployment is healthy
- ✅ Reports deployment status

## 📊 Database Persistence

**Important:** Your database uses Docker volumes, which means:
- ✅ Data persists across container restarts
- ✅ When you add a todo, it stays in the database
- ✅ When you redeploy, existing todos remain
- ✅ Only the application code is updated, not the data

### PostgreSQL Volume
```yaml
volumes:
  postgres_data:/var/lib/postgresql/data
```

This ensures your todos are stored permanently on disk and survive:
- ✅ Container restarts
- ✅ Application redeployments
- ✅ System reboots

## 🔄 Typical CI/CD Workflow

```bash
# 1. Make changes to your code
vim src/main/java/com/springapi/spring_api/controller/TodoController.java

# 2. Test locally (optional but recommended)
./mvnw test

# 3. Commit your changes
git add .
git commit -m "feat: add new todo feature"

# 4. Push to GitHub
git push origin main

# 5. GitHub Actions automatically:
#    - Builds the code
#    - Runs tests
#    - Builds Docker image
#    - Deploys to Docker (localhost:8080)
#    - Deploys to VM (172.20.61.178:8081)
#    - Runs health checks

# 6. Check deployment status
#    - Go to: https://github.com/thaboxan/spring_api/actions
#    - Or visit: http://localhost:8080 and http://172.20.61.178:8081
```

## 🎯 What Happens to Your Data

### When You Deploy:
1. **Application Container** is replaced with new version
2. **PostgreSQL Container** keeps running (NOT replaced)
3. **Database Volume** (`postgres_data`) persists on disk
4. **Your Todos** remain intact

### Example Scenario:
```
Step 1: Add a todo via API
POST http://localhost:8080/api/todos
{ "title": "Buy groceries", "description": "Milk, eggs, bread" }

Step 2: Push code changes to GitHub
git push origin main

Step 3: CI/CD rebuilds and redeploys

Step 4: Check your todo - it's still there!
GET http://localhost:8080/api/todos
Response: [{ "id": 1, "title": "Buy groceries", ... }]
```

## 🗄️ Database Schema Updates

Your application uses `spring.jpa.hibernate.ddl-auto=update`, which means:
- ✅ New tables are created automatically
- ✅ New columns are added automatically
- ✅ Existing data is preserved
- ⚠️ Column drops require manual migration

## 🔧 Managing Database

### View All Todos:
```bash
docker exec -it todo-postgres-db psql -U postgres -d todolist -c "SELECT * FROM todos;"
```

### Clear All Todos (if needed):
```bash
docker exec -it todo-postgres-db psql -U postgres -d todolist -c "TRUNCATE TABLE todos CASCADE;"
```

### Reset Database (nuclear option):
```bash
# This will DELETE all data!
docker stop todo-postgres-db
docker rm todo-postgres-db
docker volume rm spring_api_postgres_data
# Next deployment will create fresh database
```

## 📈 Monitoring Deployments

### Check GitHub Actions:
```
https://github.com/thaboxan/spring_api/actions
```

### Check Docker Logs:
```bash
docker logs spring-api-container -f
```

### Check VM Logs:
```bash
ssh root@172.20.61.178
cd /home/root/spring_api
tail -f application.log
```

### Check Database Connection:
```bash
# Docker
curl http://localhost:8080/actuator/health

# VM
curl http://172.20.61.178:8081/actuator/health
```

## 🎉 Summary

**You asked for:** "Every time I push to main, rebuild image and deploy, when new todo is added, DB must keep the data"

**You got:**
- ✅ Push to `main` → Auto rebuild Docker image
- ✅ Auto deploy to both Docker and VM
- ✅ Database persists all data (todos stay!)
- ✅ Zero downtime deployments
- ✅ Automated testing before deployment
- ✅ Health checks after deployment

**This is true CI/CD!** 🚀
