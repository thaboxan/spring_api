# CI/CD Setup Checklist

Follow this checklist to get your full CI/CD pipeline working:

## ✅ Prerequisites
- [x] Docker Desktop installed and running on Windows
- [x] WSL2 Ubuntu VM set up (172.20.61.178)
- [x] PostgreSQL running in Docker with persistent volume
- [x] GitHub repository created (thaboxan/spring_api)

## 📋 Setup Steps

### 1. Generate SSH Keys (for VM deployment)
```bash
# In WSL2/Ubuntu
ssh-keygen -t ed25519 -C "github-actions-deploy"
cat ~/.ssh/id_ed25519.pub >> ~/.ssh/authorized_keys
chmod 600 ~/.ssh/authorized_keys
cat ~/.ssh/id_ed25519  # Copy this for GitHub secrets
```

- [ ] SSH key generated
- [ ] Public key added to authorized_keys
- [ ] Private key copied for GitHub secrets

### 2. Add GitHub Secrets
Go to: https://github.com/thaboxan/spring_api/settings/secrets/actions

Add these **3 secrets only**:
- [ ] `VM_HOST` = `172.20.61.178`
- [ ] `VM_USER` = `root` (or your WSL username)
- [ ] `VM_SSH_KEY` = (paste entire private key)

### 3. ~~Set Up Self-Hosted Runner~~ **NOT NEEDED!**
✅ **Skip this step!** The workflow now deploys both Docker and VM via SSH.
No self-hosted runner required.

### 4. Commit and Push Workflow Files
```bash
git add .github/
git commit -m "ci: add automated CI/CD pipeline"
git push origin main
```

- [ ] Workflow files committed
- [ ] Pushed to main branch

### 5. Test the CI/CD Pipeline

#### Make a test change:
```bash
# Edit a file
echo "# Test CI/CD" >> README.md

# Commit and push
git add README.md
git commit -m "test: trigger CI/CD pipeline"
git push origin main
```

- [ ] Change pushed to GitHub
- [ ] GitHub Actions workflow started
- [ ] All jobs completed successfully
- [ ] Docker container deployed to 172.20.61.178:8080
- [ ] VM application deployed to 172.20.61.178:8081

### 6. Verify Deployments

Check Docker deployment:
```bash
curl http://172.20.61.178:8080/actuator/health
```
- [ ] Docker returns healthy status

Check VM deployment:
```bash
curl http://172.20.61.178:8081/actuator/health
```
- [ ] VM returns healthy status

### 7. Test Database Persistence

Add a todo:
```bash
curl -X POST http://172.20.61.178:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Test Todo", "description": "Testing CI/CD", "completed": false}'
```
- [ ] Todo created successfully

Make a code change and deploy:
```bash
# Make any small change
echo "// CI/CD test" >> src/main/java/com/springapi/spring_api/SpringApiApplication.java
git add .
git commit -m "test: verify database persistence"
git push origin main
```
- [ ] Pipeline runs and deploys
- [ ] Wait for deployment to complete

Check if todo still exists:
```bash
curl http://172.20.61.178:8080/api/todos
```
- [ ] Previous todo is still there (database persisted!)

## 🎉 Success Criteria

Your CI/CD is fully working when:
- ✅ Every push to `main` triggers automated build
- ✅ Tests run automatically
- ✅ Docker image is built and pushed
- ✅ Docker container deploys to 172.20.61.178:8080
- ✅ Application deploys to VM at 172.20.61.178:8081
- ✅ Health checks pass
- ✅ Database data persists across deployments
- ✅ No manual intervention needed
- ✅ No self-hosted runner required!

## 🔧 Troubleshooting

### Workflow fails at build step:
- Check GitHub Actions logs
- Verify Java 21 is available
- Check Maven dependencies

### Docker deployment fails:
- Check SSH connection to VM
- Verify Docker is installed and running on WSL2
- Check that VM_HOST is accessible

### VM deployment fails:
- Verify SSH keys are correct
- Check VM is accessible from internet
- Check VM_HOST IP is correct

### Database doesn't persist:
- Verify volume is created: `docker volume ls`
- Check volume mount in docker-compose.yml
- Verify Hibernate ddl-auto is set to "update"

## 📚 Documentation

- [WORKFLOW_SETUP.md](WORKFLOW_SETUP.md) - Detailed workflow configuration
- [SELF_HOSTED_RUNNER_SETUP.md](SELF_HOSTED_RUNNER_SETUP.md) - Runner setup guide
- [CI_CD_OVERVIEW.md](CI_CD_OVERVIEW.md) - How the CI/CD pipeline works

## 🆘 Need Help?

Check the GitHub Actions tab for detailed logs:
https://github.com/thaboxan/spring_api/actions
