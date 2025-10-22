# WatchWise - Push to GitHub Script
# Replace YOUR_USERNAME with your actual GitHub username

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  WatchWise - Push to GitHub" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Get GitHub username
$username = Read-Host "Enter your GitHub username"

if ([string]::IsNullOrWhiteSpace($username)) {
    Write-Host "Error: Username cannot be empty!" -ForegroundColor Red
    exit 1
}

$repoUrl = "https://github.com/$username/WatchWise.git"

Write-Host ""
Write-Host "Repository URL: $repoUrl" -ForegroundColor Yellow
Write-Host ""

# Check if remote already exists
$remoteExists = git remote get-url origin 2>$null

if ($remoteExists) {
    Write-Host "Remote 'origin' already exists. Updating..." -ForegroundColor Yellow
    git remote set-url origin $repoUrl
} else {
    Write-Host "Adding remote 'origin'..." -ForegroundColor Green
    git remote add origin $repoUrl
}

# Ensure we're on main branch
Write-Host "Ensuring branch is named 'main'..." -ForegroundColor Green
git branch -M main

# Push to GitHub
Write-Host ""
Write-Host "Pushing to GitHub..." -ForegroundColor Green
Write-Host ""

git push -u origin main

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "  SUCCESS! Project pushed to GitHub!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "View your repository at:" -ForegroundColor Cyan
    Write-Host "https://github.com/$username/WatchWise" -ForegroundColor Yellow
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "  Push failed!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Common issues:" -ForegroundColor Yellow
    Write-Host "1. Make sure you created the repository on GitHub first" -ForegroundColor White
    Write-Host "2. Check if you're logged in to GitHub" -ForegroundColor White
    Write-Host "3. Verify the repository name is correct" -ForegroundColor White
    Write-Host ""
}
