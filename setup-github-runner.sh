#!/bin/bash

# GitHub Self-Hosted Runner Setup Script for WSL2/VM
# This script helps set up a GitHub Actions self-hosted runner

set -e

echo "=========================================="
echo "GitHub Self-Hosted Runner Setup"
echo "=========================================="
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if running as root
if [ "$EUID" -eq 0 ]; then 
   echo -e "${YELLOW}Warning: Running as root user${NC}"
   echo -e "${YELLOW}GitHub recommends using a non-root user, but we'll continue...${NC}"
   echo ""
   export RUNNER_ALLOW_RUNASROOT="1"
fi

echo -e "${YELLOW}Step 1: Install dependencies${NC}"
echo "Installing required packages..."

# Update package list
sudo apt-get update

# Install dependencies
sudo apt-get install -y curl jq libicu-dev

echo -e "${GREEN}✓ Dependencies installed${NC}"
echo ""

echo -e "${YELLOW}Step 2: Create runner directory${NC}"
RUNNER_DIR="$HOME/actions-runner"
mkdir -p "$RUNNER_DIR"
cd "$RUNNER_DIR"

echo -e "${GREEN}✓ Runner directory created: $RUNNER_DIR${NC}"
echo ""

echo -e "${YELLOW}Step 3: Download GitHub Actions Runner${NC}"
echo "Fetching latest runner version..."

# Get latest runner version
RUNNER_VERSION=$(curl -s https://api.github.com/repos/actions/runner/releases/latest | jq -r '.tag_name' | sed 's/v//')

echo "Latest version: $RUNNER_VERSION"

# Download runner
RUNNER_FILE="actions-runner-linux-x64-${RUNNER_VERSION}.tar.gz"
curl -o "$RUNNER_FILE" -L "https://github.com/actions/runner/releases/download/v${RUNNER_VERSION}/${RUNNER_FILE}"

# Extract
tar xzf "$RUNNER_FILE"
rm "$RUNNER_FILE"

echo -e "${GREEN}✓ Runner downloaded and extracted${NC}"
echo ""

echo "=========================================="
echo -e "${GREEN}Installation Complete!${NC}"
echo "=========================================="
echo ""
echo -e "${YELLOW}Next Steps:${NC}"
echo ""
echo "1. Go to your GitHub repository:"
echo "   https://github.com/thaboxan/spring_api/settings/actions/runners/new"
echo ""
echo "2. Select 'Linux' as the operating system"
echo ""
echo "3. You'll see a token. Copy it and run:"
echo "   cd $RUNNER_DIR"
echo "   ./config.sh --url https://github.com/thaboxan/spring_api --token YOUR_TOKEN_HERE"
echo ""
echo "4. When prompted:"
echo "   - Runner name: Press Enter (use default) or type 'wsl2-runner'"
echo "   - Runner group: Press Enter (use default)"
echo "   - Labels: Press Enter (use default)"
echo "   - Work folder: Press Enter (use default)"
echo ""
echo "5. Start the runner:"
echo "   ./run.sh"
echo ""
echo "6. (Optional) To run as a service:"
echo "   sudo ./svc.sh install"
echo "   sudo ./svc.sh start"
echo ""
echo "=========================================="
