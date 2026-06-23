# Docker 快速启动说明

1. 构建并启动所有服务（后端、前端、MySQL、Redis）：

```bash
docker compose up --build -d
```

2. 导入表结构（如果需要）：

```bash
docker exec -i medicine-mysql mysql -uroot -p123456 medicine < creat_table.sql
```

3. 查看日志：

```bash
docker compose logs -f backend
docker compose logs -f frontend
```

4. 访问：

- 前端（带 nginx）：http://localhost:5173
- 后端 API：通过前端代理为 `/api/*`，也可直接访问 http://localhost:8080

## 开发模式（热重载）

项目包含 `docker-compose.dev.yml`，用于在容器中运行开发服务器并挂载源码，实现前后端热重载。使用方法：

```bash
# 使用主 compose + 开发覆盖文件（会挂载源码并运行 dev 服务器）
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build

# 或后台运行
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build -d
```

说明与注意事项：
- 后端容器使用 `mvn spring-boot:run`，建议在 `pom.xml` 添加 `spring-boot-devtools` 依赖以获得更快的重启体验。
- 前端容器运行 Vite（`npm run dev`），访问地址改为 `http://localhost:5173`（端口映射仍为 5173）。
- Windows 用户在文件变更监听上可能需要 `CHOKIDAR_USEPOLLING=true`（已在 compose.dev 中设置）。
- 若本机已有 MySQL/Redis 在运行，注意端口冲突；你可以停止本机服务或修改 `docker-compose.yml` 端口映射。

