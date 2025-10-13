# GitHub Self-Hosted Runner Setup Guide

This guide will help you set up a self-hosted GitHub Actions runner on your WSL2 VM.

## Why Self-Hosted Runner?

- ✅ No need to expose your VM to the internet
- ✅ Direct access to your local environment
- ✅ Faster deployments (no SSH overhead)
- ✅ Better for WSL2 environments

## Setup Instructions

### Step 1: Run the Setup Script

```bash
cd /root/spring_api
./setup-github-runner.sh
```

This script will:
- Install required dependencies (curl, jq, libicu-dev)
- Create a runner directory at `~/actions-runner`
- Download the latest GitHub Actions runner

### Step 2: Get Your Runner Token

1. Go to your GitHub repository: https://github.com/thaboxan/spring_api

2. Navigate to: **Settings** → **Actions** → **Runners** → **New self-hosted runner**

3. Select **Linux** as the operating system

4. You'll see a configuration command with a **token** - copy this token

### Step 3: Configure the Runner

```bash
cd ~/actions-runner

# Replace YOUR_TOKEN_HERE with the token from GitHub
./config.sh --url https://github.com/thaboxan/spring_api --token YOUR_TOKEN_HERE
```

**Configuration prompts:**
- Runner name: Press Enter (default) or type `wsl2-runner`
- Runner group: Press Enter (default)
- Labels: Press Enter (default)
- Work folder: Press Enter (default)

### Step 4: Start the Runner

**Option A: Run interactively (for testing)**
```bash
cd ~/actions-runner
./run.sh
```

**Option B: Run as a service (recommended)**
```bash
cd ~/actions-runner
sudo ./svc.sh install
sudo ./svc.sh start
```

### Step 5: Verify Runner is Connected

1. Go to: https://github.com/thaboxan/spring_api/settings/actions/runners
2. You should see your runner listed as "Idle" (green dot)

### Step 6: Update Workflow Configuration

The new workflow `vm-deploy-selfhosted.yml` is already created and uses `runs-on: self-hosted`.

**To switch from SSH to self-hosted deployment:**

1. Rename or disable the old workflow:
   ```bash
   cd /root/spring_api/.github/workflows
   mv vm-deploy.yml vm-deploy-ssh.yml.disabled
   ```

2. The new `vm-deploy-selfhosted.yml` will automatically be used on next push

## Testing the Setup

1. Make a small change to your code
2. Commit and push to the `main` branch:
   ```bash
   git add .
   git commit -m "test: self-hosted runner deployment"
   git push origin main
   ```

3. Watch the Actions tab: https://github.com/thaboxan/spring_api/actions

## Managing the Runner

### Check status (if running as service)
```bash
sudo ~/actions-runner/svc.sh status
```

### Stop the runner
```bash
sudo ~/actions-runner/svc.sh stop
```

### Start the runner
```bash
sudo ~/actions-runner/svc.sh start
```

### Uninstall the service
```bash
sudo ~/actions-runner/svc.sh uninstall
```

### Remove the runner from GitHub
```bash
cd ~/actions-runner
./config.sh remove --token YOUR_TOKEN_HERE
```

## Troubleshooting

### Runner shows offline
- Check if the runner process is running: `ps aux | grep Runner.Listener`
- Check runner logs: `cat ~/actions-runner/_diag/*.log`
- Restart the service: `sudo ~/actions-runner/svc.sh restart`

### Deployment fails
- Check application logs: `cat ~/spring-api/app.log`
- Verify Java 21 is installed: `java -version`
- Check if port 8081 is available: `sudo lsof -i :8081`

### Maven build fails
- Ensure Maven wrapper is executable: `chmod +x ./mvnw`
- Check disk space: `df -h`
- Clear Maven cache: `rm -rf ~/.m2/repository`

## Security Notes

- The runner runs under your user account
- It has access to your home directory
- Only install runners on machines you trust
- Regularly update the runner: `cd ~/actions-runner && ./config.sh remove --token TOKEN && ./config.sh --url URL --token NEW_TOKEN`

## Comparison: Self-Hosted vs SSH

| Feature | Self-Hosted Runner | SSH Deployment |
|---------|-------------------|----------------|
| Setup Complexity | Medium | Low |
| Network Requirements | None | Public IP/Port Forward |
| Security | Runs locally | SSH exposed |
| Speed | Fast | Slower (network) |
| Best for | WSL2/Local VMs | Cloud VMs |

## Next Steps

Once your runner is set up and working:
1. Keep `vm-deploy-selfhosted.yml` active
2. Delete or disable `vm-deploy.yml` (SSH version)
3. Remove the SSH secrets from GitHub (no longer needed)

---

**Need Help?** Check the GitHub Actions logs or runner diagnostic logs for detailed error messages.
