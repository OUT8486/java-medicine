@echo off
setlocal EnableExtensions
title Pharmacy Management System - Stop

REM ============================================================
REM  药店管理系统 一键停止脚本
REM  停止：后端(8080) / 前端(5173) / WSL Redis
REM  用法：直接双击运行即可停止后端、前端与 WSL Redis
REM ============================================================

set "BACKEND_PORT=8080"
set "FRONTEND_PORT=5173"
set "REDIS_DISTRO=Ubuntu"
set "REDIS_PASSWORD=123456"

echo [1/4] 正在停止后端进程 (端口 %BACKEND_PORT%) ...
for /f "tokens=5" %%p in ('netstat -ano ^| findstr ":%BACKEND_PORT%" ^| findstr "LISTENING"') do (
    taskkill /PID %%p /F >nul 2>&1
)

echo [2/4] 正在停止前端进程 (端口 %FRONTEND_PORT%) ...
for /f "tokens=5" %%p in ('netstat -ano ^| findstr ":%FRONTEND_PORT%" ^| findstr "LISTENING"') do (
    taskkill /PID %%p /F >nul 2>&1
)

echo [3/4] 正在停止 WSL Redis ...
wsl.exe -d %REDIS_DISTRO% -- bash -lc "echo '%REDIS_PASSWORD%' | sudo -S systemctl stop redis-server 2>/dev/null"

echo [4/4] 清理完成。
echo.
echo [OK] 已尝试停止后端与前端服务。
echo      注意：若后端/前端是通过独立窗口启动的，直接关闭对应窗口即可。
echo.

endlocal
exit /b 0
