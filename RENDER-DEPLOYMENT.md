# Spring API - Render Deployment Guide

This guide will help you deploy your Spring Boot Todo API to Render.

## Prerequisites

1. **GitHub Repository**: Ensure your code is pushed to GitHub
2. **Render Account**: Sign up at [render.com](https://render.com)

## Deployment Options

### Option 1: Using Render Dashboard (Recommended)

#### Step 1: Create PostgreSQL Database

1. Go to your Render Dashboard
2. Click "New" → "PostgreSQL"
3. Configure:
   - **Name**: `spring-api-db`
   - **Database Name**: `todolist`
   - **User**: `postgres`
   - **Region**: Choose closest to your users
   - **Plan**: Free (for development)
4. Click "Create Database"
5. **Important**: Save the connection details (Internal Database URL)

#### Step 2: Deploy Web Service

1. Go to your Render Dashboard
2. Click "New" → "Web Service"
3. Connect your GitHub repository
4. Configure:
   - **Name**: `spring-api-backend`
   - **Environment**: `Docker` or `Java`
   - **Build Command**: `chmod +x build.sh && ./build.sh`
   - **Start Command**: `chmod +x start.sh && ./start.sh`
   - **Plan**: Free (for development)

#### Step 3: Set Environment Variables

In your web service settings, add these environment variables:

| Key | Value |
|-----|-------|
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `DATABASE_URL` | (Copy from your PostgreSQL service - Internal Database URL) |
| `PORT` | `8080` |

#### Step 4: Deploy

1. Click "Create Web Service"
2. Render will automatically build and deploy your application
3. Monitor the build logs for any issues

### Option 2: Using render.yaml (Infrastructure as Code)

1. The `render.yaml` file in your project root defines both services
2. In Render Dashboard:
   - Go to "Blueprints"
   - Click "New Blueprint Instance"
   - Connect your GitHub repository
   - Render will create both database and web service automatically

## Environment Configuration

Your application supports multiple profiles:

- **Local Development**: `application.properties`
- **Docker**: `application-docker.properties` 
- **Production (Render)**: `application-prod.properties`

The production profile uses environment variables:
- `DATABASE_URL`: PostgreSQL connection string from Render
- `PORT`: Port assigned by Render (usually 8080)

## API Endpoints

Once deployed, your API will be available at `https://your-service-name.onrender.com`

### Available Endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/todos` | Get all todos |
| POST | `/api/todos` | Create a new todo |
| GET | `/api/todos/{id}` | Get todo by ID |
| PUT | `/api/todos/{id}` | Update todo |
| PATCH | `/api/todos/{id}/toggle` | Toggle completion |
| DELETE | `/api/todos/{id}` | Delete todo |
| GET | `/api/todos/completed` | Get completed todos |
| GET | `/api/todos/incomplete` | Get incomplete todos |

### Example Usage:

```bash
# Create a todo
curl -X POST https://your-service-name.onrender.com/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Deploy to Render", "description": "Successfully deploy the Spring Boot app"}'

# Get all todos
curl https://your-service-name.onrender.com/api/todos
```

## Troubleshooting

### Common Issues:

1. **Build Failures**
   - Check Java version compatibility (using Java 21)
   - Verify Maven wrapper permissions
   - Review build logs in Render dashboard

2. **Database Connection Issues**
   - Verify `DATABASE_URL` environment variable
   - Check PostgreSQL service status
   - Ensure database and web service are in same region

3. **Application Won't Start**
   - Check start command syntax
   - Verify WAR file is generated correctly
   - Review application logs

4. **Port Issues**
   - Ensure app uses `${PORT}` environment variable
   - Default fallback is port 8080

### Viewing Logs:

- **Build Logs**: Available during deployment in Render dashboard
- **Runtime Logs**: View in service "Logs" tab
- **Database Logs**: Available in PostgreSQL service logs

### Health Check:

Test your deployment:
```bash
curl https://your-service-name.onrender.com/api/todos
```

## Production Considerations

### Security:
- Use environment variables for sensitive data
- Enable HTTPS (automatic with Render)
- Consider rate limiting for production

### Performance:
- Monitor response times
- Consider upgrading to paid plans for better performance
- Implement caching if needed

### Monitoring:
- Set up uptime monitoring
- Monitor database performance
- Review application metrics

## Support

If you encounter issues:

1. Check Render service logs
2. Verify environment variables
3. Test database connectivity
4. Review GitHub repository setup

## Files Created for Deployment:

- `application-prod.properties` - Production configuration
- `build.sh` - Build script for Render
- `start.sh` - Application start script
- `render.yaml` - Infrastructure as Code configuration
- `system.properties` - Java version specification
- `Procfile` - Alternative deployment configuration
- `RENDER-DEPLOYMENT.md` - This deployment guide

## Next Steps:

1. Push all files to your GitHub repository
2. Follow the deployment steps above
3. Test your deployed API
4. Set up monitoring and alerts

Good luck with your deployment! 🚀
