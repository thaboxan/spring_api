# ✅ NO SELF-HOSTED RUNNER NEEDED!

## The Simple Setup

You asked: "Do I need the runner?"

**Answer: NO!** 🎉

## Why You Don't Need It

Since both your Docker and VM are running on the same WSL2 Ubuntu machine (172.20.61.178), we can deploy both via SSH. No need for a self-hosted runner!

## What You Actually Need

### Just 3 GitHub Secrets:
```yaml
VM_HOST=172.20.61.178
VM_USER=root
VM_SSH_KEY=<your-ssh-private-key>
```

That's it! The workflow will:
1. SSH into your WSL2 machine
2. Deploy Docker container (port 8080)
3. Deploy Spring Boot app (port 8081)
4. Both use the same PostgreSQL database
5. Database persists all data

## Quick Setup (3 Steps)

### 1. Add SSH Key to GitHub
```bash
# In WSL2
cat ~/.ssh/id_ed25519
# Copy the entire output and add as VM_SSH_KEY secret
```

### 2. Add Secrets to GitHub
Go to: https://github.com/thaboxan/spring_api/settings/secrets/actions

Add:
- `VM_HOST` = `172.20.61.178`
- `VM_USER` = `root`
- `VM_SSH_KEY` = (your private key)

### 3. Push to GitHub
```bash
git add .github/
git commit -m "ci: add automated CI/CD pipeline (no runner needed)"
git push origin main
```

## Deployment Endpoints

After pushing to `main`, your apps will be available at:
- 🐳 **Docker**: http://172.20.61.178:8080
- 🖥️ **VM**: http://172.20.61.178:8081
- 🗄️ **PostgreSQL**: localhost:5432 (on WSL2)

## Why This Is Better

✅ **Simpler** - No runner to install/maintain
✅ **Faster** - Direct SSH deployment
✅ **Cleaner** - One machine, one SSH key
✅ **Reliable** - GitHub-hosted runners are maintained by GitHub
✅ **Scalable** - Works the same for production deployments

## Files You Can Ignore

You can safely ignore or delete:
- ❌ `.github/SELF_HOSTED_RUNNER_SETUP.md` (not needed anymore)

Keep these files:
- ✅ `.github/workflows/ci-cd.yml` (main workflow)
- ✅ `.github/WORKFLOW_SETUP.md` (setup guide)
- ✅ `.github/CI_CD_OVERVIEW.md` (how it works)
- ✅ `.github/SETUP_CHECKLIST.md` (step-by-step guide)

## Ready to Deploy!

Your CI/CD is now simplified and ready to use. Just add the 3 secrets and push to `main`! 🚀
