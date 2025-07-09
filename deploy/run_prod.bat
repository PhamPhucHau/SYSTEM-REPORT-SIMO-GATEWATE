@echo off
cd /d %~dp0
echo ======================================
echo Cleaning up TEST environment...
docker-compose -f docker-compose.yml -f docker-compose.test.yml --env-file .prod_env down -v

echo ======================================
echo Starting PRODUCTION environment...
docker-compose -f docker-compose.yml -f docker-compose.prod.yml --env-file .test_env up -d

echo ✅ SIMO production environment is now running.
pause
