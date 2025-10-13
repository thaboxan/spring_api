# GitHub Actions Setup Guide

## Required GitHub Secrets

Go to your GitHub repository: **Settings → Secrets and variables → Actions → New repository secret**

Add the following secrets:

### VM/WSL2 Configuration
- **VM_HOST**: Your WSL2/VM IP address
  - Use: `172.20.61.178`
  
- **VM_USER**: Your WSL2/VM username
  - Example: `root` or your WSL username

## How to Generate SSH Keys for WSL2/VM

If you don't have SSH keys yet, here's how to generate them:

### 1. On your WSL2/VM, generate an SSH key pair:
```bash
ssh-keygen -t ed25519 -C "github-actions-deploy"
```

Press Enter to accept the default location (`~/.ssh/id_ed25519`), and optionally set a passphrase (or leave empty for no passphrase).

### 2. Add the public key to authorized_keys:
```bash
cat ~/.ssh/id_ed25519.pub >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
chmod 700 ~/.ssh
```

### 3. Copy the private key content:
```bash
cat ~/.ssh/id_ed25519
```

Copy the **entire output** (including `-----BEGIN OPENSSH PRIVATE KEY-----` and `-----END OPENSSH PRIVATE KEY-----`).

### 4. Add to GitHub Secrets:
- Go to your repository → Settings → Secrets and variables → Actions
- Click "New repository secret"
- Name: `VM_SSH_KEY`
- Value: Paste the entire private key content
- Click "Add secret"

### 5. Test SSH connection:
```bash
# From another terminal or your Windows machine
ssh -i ~/.ssh/id_ed25519 <VM_USER>@172.20.61.178
```

If it connects without asking for a password, you're all set!

### Docker Host Configuration
**Note**: Since Docker is running locally on your Windows machine, you don't need SSH keys for Docker deployment. The workflow will build and push the image to GitHub Container Registry, and you can pull and run it manually on your local machine.

If you were deploying to a **remote** Docker host, you would need:
- **DOCKER_HOST**: Remote Docker host IP
- **DOCKER_USER**: Remote Docker host username  
- **DOCKER_SSH_KEY**: SSH private key for remote Docker host

### Database Configuration
- **DB_PASSWORD**: Your PostgreSQL database password
  - Example: `admin`

## Workflow Features

### On Every Push/PR:
- ✅ Builds the project
- ✅ Runs tests against PostgreSQL
- ✅ Creates JAR artifact

### On Push to main/dev:
- ✅ Builds Docker image
- ✅ Pushes to GitHub Container Registry

### On Push to main:
- ✅ **Auto-rebuilds Docker image** when code changes
- ✅ **Auto-deploys Docker container** (172.20.61.178:8080) via SSH
- ✅ **Auto-deploys to WSL2/VM** (172.20.61.178:8081) via SSH
- ✅ **Database persists data** - todos are NOT deleted on redeploy
- ✅ Runs health checks on both deployments

**Note**: Both deployments use SSH, so you only need to set up SSH keys (no self-hosted runner needed!).

## Database Persistence

✅ Your PostgreSQL database uses Docker volumes (`postgres_data`) for persistence
✅ When you add a todo, it stays in the database
✅ When you redeploy, existing todos remain
✅ Only the application code is updated, not the data

See [CI_CD_OVERVIEW.md](CI_CD_OVERVIEW.md) for detailed explanation.

## Port Allocation
- **Docker Container**: Port 8080 (http://localhost:8080)
- **WSL2/VM**: Port 8081 (http://172.20.61.178:8081)
- **PostgreSQL**: Port 5432 (shared)

## Testing Locally

Before pushing, test your setup:

### 1. Start PostgreSQL
```bash
docker run -d --name postgres-db -p 5432:5432 \
  -e POSTGRES_DB=todolist \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=admin \
  postgres:15
```

### 2. Run tests
```bash
./mvnw test
```

### 3. Build application
```bash
./mvnw clean package
```

## Enabling Health Checks

Make sure Spring Boot Actuator is enabled. Add to `pom.xml` if not present:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

Then add to `application.properties`:

```properties
management.endpoints.web.exposure.include=health,info
management.endpoint.health.show-details=always
```

## Manual Docker Deployment

After the workflow runs and builds the Docker image, deploy it manually on your Windows machine:

### 1. Login to GitHub Container Registry:
```powershell
# On Windows PowerShell or WSL
echo YOUR_GITHUB_TOKEN | docker login ghcr.io -u YOUR_GITHUB_USERNAME --password-stdin
```

### 2. Pull the latest image:
```powershell
docker pull ghcr.io/thaboxan/spring_api:latest
```

### 3. Stop and remove old container (if exists):
```powershell
docker stop spring-api-container
docker rm spring-api-container
```

### 4. Create Docker network (if not exists):
```powershell
docker network create spring-network
```

### 5. Run the new container:
```powershell
docker run -d `
  --name spring-api-container `
  -p 8080:8080 `
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres-db:5432/todolist `
  -e SPRING_DATASOURCE_USERNAME=postgres `
  -e SPRING_DATASOURCE_PASSWORD=admin `
  -e SERVER_PORT=8080 `
  -e SPRING_PROFILES_ACTIVE=docker `
  --network spring-network `
  --restart unless-stopped `
  ghcr.io/thaboxan/spring_api:latest
```

### 6. Verify deployment:
```powershell
# Check if container is running
docker ps

# Check logs
docker logs spring-api-container

# Test the endpoint
curl http://localhost:8080/actuator/health
```

## Simplified GitHub Secrets Setup

You only need **3 secrets** for the entire CI/CD pipeline:

```yaml
VM_HOST=172.20.61.178
VM_USER=<your-wsl-username>
VM_SSH_KEY=<your-ssh-private-key>
```

That's it! Both Docker and VM deployments use the same SSH connection.

You do **NOT** need:
- ❌ Self-hosted runner
- ❌ Separate DOCKER_HOST
- ❌ Separate DOCKER_USER
- ❌ Separate DOCKER_SSH_KEY

### Workflow fails at SSH step:
- Check that SSH keys are correctly added to secrets
- Verify VM/Docker host is accessible
- Ensure SSH service is running on target machine

### Health check fails:
- Check that applications are actually running
- Verify firewall rules allow access to ports 8080 (Docker) and 8081 (VM)
- Check application logs for startup errors

### Docker image push fails:
- Ensure GITHUB_TOKEN has package write permissions
- Check that Container Registry is enabled in GitHub settings

## Next Steps

1. Add the required secrets to GitHub
2. Commit and push this workflow file
3. Monitor the Actions tab in GitHub
4. Check both deployments are running successfully
