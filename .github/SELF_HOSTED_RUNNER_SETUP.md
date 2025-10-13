# Setting Up Self-Hosted GitHub Actions Runner

To enable **automated Docker deployment** on your Windows machine, you need to set up a self-hosted GitHub Actions runner.

## Why Self-Hosted Runner?

- ✅ Automatically deploys Docker container on your local machine when code changes
- ✅ No manual intervention needed
- ✅ Direct access to your local Docker daemon
- ✅ Same machine where Docker is already running

## Setup Instructions

### Step 1: Go to Your Repository Settings

1. Open your GitHub repository: https://github.com/thaboxan/spring_api
2. Click **Settings** → **Actions** → **Runners**
3. Click **New self-hosted runner**
4. Select **Windows** as the operating system

### Step 2: Download and Configure Runner on Windows

GitHub will show you commands. Here's what to do:

**Open PowerShell as Administrator** and run:

```powershell
# Create a folder for the runner
mkdir actions-runner; cd actions-runner

# Download the latest runner package
Invoke-WebRequest -Uri https://github.com/actions/runner/releases/download/v2.311.0/actions-runner-win-x64-2.311.0.zip -OutFile actions-runner-win-x64-2.311.0.zip

# Extract the installer
Add-Type -AssemblyName System.IO.Compression.FileSystem
[System.IO.Compression.ZipFile]::ExtractToDirectory("$PWD/actions-runner-win-x64-2.311.0.zip", "$PWD")
```

### Step 3: Configure the Runner

```powershell
# Configure the runner (GitHub will provide the exact token and URL)
./config.cmd --url https://github.com/thaboxan/spring_api --token YOUR_TOKEN_FROM_GITHUB

# When prompted:
# - Enter the name of runner: windows-docker (or any name you prefer)
# - Enter the runner group: Press Enter (default)
# - Enter the labels: Press Enter (default)
# - Enter the work folder: Press Enter (default: _work)
```

### Step 4: Install as Windows Service (Recommended)

To keep the runner running even after you close PowerShell:

```powershell
# Install as service
./svc.sh install

# Start the service
./svc.sh start
```

**Alternative**: Run interactively (closes when PowerShell closes):
```powershell
./run.cmd
```

### Step 5: Verify Runner is Connected

1. Go back to your GitHub repository → Settings → Actions → Runners
2. You should see your runner with a green dot ✅ labeled "Idle"

## Testing the Setup

Once the runner is set up, test it:

1. Make a small change to your code
2. Commit and push to main branch:
   ```bash
   git add .
   git commit -m "test: trigger automated deployment"
   git push origin main
   ```
3. Go to Actions tab in GitHub
4. Watch the workflow run on your self-hosted runner
5. Docker container should automatically deploy on localhost:8080

## Troubleshooting

### Runner shows as "Offline"
- Check if the runner service is running:
  ```powershell
  ./svc.sh status
  ```
- Restart if needed:
  ```powershell
  ./svc.sh restart
  ```

### Docker commands fail in workflow
- Make sure Docker Desktop is running on Windows
- Verify the runner has access to Docker:
  ```powershell
  docker ps
  ```
- You might need to add the runner service account to the docker-users group

### Permission issues
- Run PowerShell as Administrator when setting up
- Ensure Docker Desktop allows connections from localhost

## Alternative: Docker Host Secret (If Runner Setup Fails)

If you can't set up a self-hosted runner, you can still deploy to a remote machine by setting:

```yaml
DOCKER_HOST=172.20.61.178  # Your WSL2 IP
DOCKER_USER=root           # Your WSL2 username
DOCKER_SSH_KEY=<your-ssh-key>
```

Then change `runs-on: self-hosted` to `runs-on: ubuntu-latest` in the workflow and use SSH deployment (like the VM deployment).

## Security Notes

- ⚠️ Self-hosted runners execute code from your repository
- ✅ Only use self-hosted runners in private repositories or trusted public repos
- ✅ Keep your runner software updated
- ✅ Run the runner as a limited user account (not Administrator) in production

## Next Steps

After setup:
1. ✅ Runner is installed and running
2. ✅ Push code changes to trigger automated deployment
3. ✅ Both Docker (localhost:8080) and VM (172.20.61.178:8081) will deploy automatically
4. ✅ Check the Actions tab to monitor deployments
