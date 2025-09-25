#!/bin/bash
# Database connection test script

echo "Testing database connection..."

# Your actual database URL from Render
DATABASE_URL="postgresql://todolist_rddd_user:LwPa8KxAq4cjekhLRvxwATRI0Zom0sfO@dpg-d3ahrj95pdvs73csvie0-a.oregon-postgres.render.com/todolist_rddd"

# Test connection using curl (if your app has a health endpoint)
echo "When your app is deployed, test with:"
echo "curl https://your-app-name.onrender.com/api/todos"

echo ""
echo "To connect directly to your database from your local machine:"
echo "psql postgresql://todolist_rddd_user:LwPa8KxAq4cjekhLRvxwATRI0Zom0sfO@dpg-d3ahrj95pdvs73csvie0-a.oregon-postgres.render.com/todolist_rddd"
