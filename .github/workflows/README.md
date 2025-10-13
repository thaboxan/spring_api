# GitHub Actions Workflows

This directory contains CI/CD workflows for the Spring API project.

## Workflows

### 1. Build and Test (`build-and-test.yml`)
- **Triggers**: Push/PR to `main` or `develop` branches
- **Purpose**: Run tests and build the application with Maven
- **Jobs**:
  - `test`: Runs unit tests with PostgreSQL service
  - `build-maven`: Builds the JAR file and uploads as artifact

### 2. Docker Build (`docker-build.yml`)
- **Triggers**: Push to `main` branch
- **Purpose**: Build and push Docker image to GitHub Container Registry
- **Jobs**:
  - `build-docker`: Builds Docker image and pushes to GHCR
  - `test-docker`: Tests the Docker image using Docker Compose
- **Image Location**: `ghcr.io/thaboxan/spring_api`

### 3. VM Deploy (`vm-deploy.yml`)
- **Triggers**: Push to `main` branch
- **Purpose**: Deploy application to VM/WSL2 environment
- **Jobs**:
  - `deploy-to-vm`: Builds JAR, copies to VM, and restarts application
  - `verify-vm-deployment`: Verifies the deployment was successful

## Setup Instructions

### For Docker Deployment
Docker deployment works out of the box and will push images to GitHub Container Registry (GHCR).

**To pull the image:**
```bash
docker pull ghcr.io/thaboxan/spring_api:latest
```

### For VM Deployment
To enable VM deployment, add the following secrets to your GitHub repository:

1. Go to: Settings → Secrets and variables → Actions → New repository secret

2. Add these secrets:
   - `VM_SSH_KEY`: Your private SSH key for VM access
   - `VM_HOST`: VM hostname or IP address (e.g., `192.168.1.100`)
   - `VM_USER`: SSH username (e.g., `root` or your username)
   - `VM_DEPLOY_PATH`: Deployment directory on VM (e.g., `/opt/spring-api`)

**Example setup on your VM:**
```bash
# Create deployment directory
mkdir -p /opt/spring-api
cd /opt/spring-api

# Ensure PostgreSQL is running
sudo systemctl status postgresql

# The workflow will:
# 1. Copy the JAR file to this directory
# 2. Copy application properties
# 3. Stop any existing instance
# 4. Start the new instance on port 8081
```

### SSH Key Setup
Generate an SSH key pair for GitHub Actions:
```bash
# On your VM
ssh-keygen -t rsa -b 4096 -f ~/.ssh/github_actions -N ""

# Add the public key to authorized_keys
cat ~/.ssh/github_actions.pub >> ~/.ssh/authorized_keys

# Copy the private key content (add this as VM_SSH_KEY secret)
cat ~/.ssh/github_actions
```

## Manual Workflow Trigger
All workflows can be triggered manually:
1. Go to: Actions → Select workflow → Run workflow

## Monitoring Deployments
- **Docker**: Check GitHub Packages for built images
- **VM**: SSH into your VM and check:
  ```bash
  cd /opt/spring-api
  cat app.log
  ps aux | grep java
  ```

## Application Ports
- **Docker version**: Port 8080
- **VM version**: Port 8081

## Troubleshooting

### Docker build fails
- Check Dockerfile syntax
- Verify Maven build succeeds locally

### VM deployment fails
- Verify SSH credentials are correct
- Ensure PostgreSQL is running on VM
- Check VM has enough disk space and memory
- Review app.log on the VM

### Tests fail
- Ensure PostgreSQL service starts correctly
- Check database connection settings
- Review test logs in GitHub Actions
