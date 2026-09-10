@echo off
chcp 65001 >nul
setlocal EnableExtensions EnableDelayedExpansion
title Pharmacy Management System - Startup

REM ============================================================
REM  药店管理系统 一键启动脚本
REM  启动顺序：Redis (WSL Ubuntu) -> MySQL -> 后端(8080) -> 前端(5173)
REM  依赖：JDK 21+ / Maven / Node.js(npm) / WSL Ubuntu(含 redis) / MySQL 服务
REM ============================================================

set "ROOT=%~dp0"
set "REDIS_DISTRO=Ubuntu"
set "REDIS_PASSWORD=123456"
set "MYSQL_SERVICE=MySQL"
set "BACKEND_PORT=8080"
set "FRONTEND_PORT=5173"
REM ---------- 环境检查 ----------
if not exist "%ROOT%pom.xml" (
    echo [ERROR] 未找到 pom.xml，请把本脚本放到项目根目录。
    pause
    exit /b 1
)
if not exist "%ROOT%vue-medicine\package.json" (
    echo [ERROR] 未找到 vue-medicine\package.json。
    pause
    exit /b 1
)
where java >nul 2>&1
if errorlevel 1 ( echo [ERROR] 未找到 Java（需 JDK 21+）。 & pause & exit /b 1 )
where mvn >nul 2>&1
if errorlevel 1 ( echo [ERROR] 未找到 Maven。 & pause & exit /b 1 )
where npm >nul 2>&1
if errorlevel 1 ( echo [ERROR] 未找到 npm（需 Node.js）。 & pause & exit /b 1 )
where wsl.exe >nul 2>&1
if errorlevel 1 ( echo [ERROR] 未找到 WSL。 & pause & exit /b 1 )

echo.
echo 正在启动药店管理系统，共 4 个组件：
echo   1/4 Redis   (WSL %REDIS_DISTRO%)     localhost:6379
echo   2/4 MySQL   (%MYSQL_SERVICE% 服务)   localhost:3306
echo   3/4 后端    (Spring Boot)            localhost:%BACKEND_PORT%
echo   4/4 前端    (Vue 3)                  localhost:%FRONTEND_PORT%
echo.

REM ---------- [1/4] 启动 Redis (WSL) ----------
echo [1/4] 正在启动 Redis ...
wsl.exe -d %REDIS_DISTRO% -- bash -lc "echo '%REDIS_PASSWORD%' | sudo -S systemctl start redis-server 2>/dev/null"
del "%TEMP%\redis_ping.txt" >nul 2>&1
wsl.exe -d %REDIS_DISTRO% -- bash -lc "redis-cli -a %REDIS_PASSWORD% ping" > "%TEMP%\redis_ping.txt" 2>&1
findstr /i "PONG" "%TEMP%\redis_ping.txt" >nul 2>&1
if errorlevel 1 (
    echo [ERROR] Redis 未就绪。请检查 WSL 发行版 %REDIS_DISTRO% 是否安装了 redis。
    del "%TEMP%\redis_ping.txt" >nul 2>&1
    pause
    exit /b 1
)
del "%TEMP%\redis_ping.txt" >nul 2>&1
echo       Redis 已就绪：localhost:6379

REM ---------- [2/4] 启动 MySQL 服务 ----------
echo [2/4] 正在启动 MySQL 服务 (%MYSQL_SERVICE%) ...
powershell.exe -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $s=Get-Service -Name '%MYSQL_SERVICE%' -ErrorAction SilentlyContinue; if (-not $s) { Write-Host 'NOT_FOUND'; exit 2 }; if ($s.Status -ne 'Running') { Start-Service -Name '%MYSQL_SERVICE%' }; $s.WaitForStatus('Running','00:20:00'); exit 0"
if errorlevel 1 (
    echo [ERROR] MySQL 服务不可用或未能启动。
    echo         请确认存在名为 %MYSQL_SERVICE% 的 Windows 服务。
    pause
    exit /b 1
)
echo       MySQL 已就绪：localhost:3306

REM ---------- [3/4] 启动后端 ----------
echo [3/4] 正在启动后端 (端口 %BACKEND_PORT%) ...
start "Medicine Backend" /D "%ROOT%" cmd.exe /k mvn spring-boot:run

REM ---------- [4/4] 启动前端 ----------
echo [4/4] 正在启动前端 (端口 %FRONTEND_PORT%) ...
if not exist "%ROOT%vue-medicine\node_modules" (
    echo       首次运行，正在安装前端依赖...
    pushd "%ROOT%vue-medicine"
    call npm install
    if errorlevel 1 (
        echo [ERROR] 前端依赖安装失败。
        popd
        pause
        exit /b 1
    )
    popd
)
start "Medicine Frontend" /D "%ROOT%vue-medicine" cmd.exe /k npm run dev

REM ---------- 打开浏览器 ----------
timeout /t 10 /nobreak >nul
start "" "http://localhost:%FRONTEND_PORT%"

echo.
echo [OK] 启动命令已全部发出：
echo      前端：  http://localhost:%FRONTEND_PORT%
echo      后端：  http://localhost:%BACKEND_PORT%
echo      Redis： localhost:6379
echo      数据库： localhost:3306
echo.
echo 关闭 "Medicine Backend" 与 "Medicine Frontend" 窗口即可停止服务。
echo 如需一键停止，可运行 stop.bat。
timeout /t 6 /nobreak >nul

endlocal
exit /b 0
