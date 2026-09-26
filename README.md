# 药店管理系统

一个药店进销存管理系统，采用前后端分离架构：Spring Boot 提供 REST API，Vue 3 + Element Plus 实现管理界面。

## 目录

- [技术栈](#技术栈)
- [目录结构](#目录结构)
- [环境要求](#环境要求)
- [快速开始（Windows 一键）](#快速开始windows-一键)
- [手动启动](#手动启动)
- [Docker 部署](#docker-部署)
- [配置项（环境变量）](#配置项环境变量)
- [默认账号](#默认账号)
- [认证与权限](#认证与权限)
- [前端说明](#前端说明)
- [测试与构建](#测试与构建)
- [常见问题](#常见问题)

## 技术栈

- 后端：Spring Boot 3.5、MyBatis、MySQL、Redis、Flyway、JWT（jjwt）、BCrypt
- 前端：Vue 3、Vite、Element Plus、Vue Router、Pinia、Axios

## 目录结构

```
药店管理系统/
├── src/                     # Spring Boot 后端源码与资源
├── vue-medicine/            # Vue 3 前端（结构见「前端说明」）
├── insert_date.sql          # 演示数据（纯 INSERT，可选导入）
├── start.bat / stop.bat     # Windows 一键启动 / 停止
├── docker-compose.yml       # Docker 编排（后端 + 前端 + MySQL + Redis）
├── docker-compose.dev.yml   # Docker 开发编排（源码挂载 + 热重载）
├── Dockerfile               # 后端镜像
├── .env.example             # Docker 环境变量示例
└── pom.xml
```

## 环境要求

- Java JDK 21+（`java -version`）
- Maven 3.6+（`mvn -version`）
- Node.js 18+ 与 npm（`node -v`）
- MySQL 8.0+（`localhost:3306`）
- Redis 7+（`localhost:6379`，可选：Redis 不可用时后端会自动降级为直连数据库）

## 快速开始（Windows 一键）

1. 确认已启动 MySQL 服务。
2. 双击 `start.bat`：脚本会按顺序启动 Redis（WSL）、MySQL、后端（8080）、前端（5173），并自动打开浏览器。
3. 双击 `stop.bat` 停止后端与前端，并停止 WSL 中的 Redis。

脚本中的 Redis 发行版、MySQL 服务名、各端口可在 `start.bat` 顶部变量处修改。

## 手动启动

1. 创建数据库并导入演示数据（表结构由 Flyway 在后端启动时自动迁移）：

```bash
mysql -u root -p -e "CREATE DATABASE medicine CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p medicine < insert_date.sql   # 可选：导入演示数据
```

2. 启动后端：

```bash
mvn spring-boot:run
# 启动成功后监听 http://localhost:8080
```

3. 启动前端：

```bash
cd vue-medicine
npm install
npm run dev
# http://localhost:5173
```

## Docker 部署

```bash
# 构建并启动全部服务
docker compose up --build -d

# 可选：导入演示数据
docker exec -i medicine-mysql mysql -uroot -p123456 medicine < insert_date.sql

# 查看日志
docker compose logs -f backend
docker compose logs -f frontend
```

- 前端（nginx）：http://localhost:5173
- 后端 API：http://localhost:8080（前端通过 `/api` 代理访问）
- 若本机已有 MySQL/Redis 在运行，注意端口冲突，可停止本机服务或修改 `docker-compose.yml` 的端口映射。

开发模式（源码挂载 + 热重载）：

```bash
docker compose -f docker-compose.yml -f docker-compose.dev.yml up --build
```

## 配置项（环境变量）

生产环境请通过环境变量覆盖默认值，避免硬编码。示例见 `.env.example`。

| 变量 | 说明 | 默认值 |
| --- | --- | --- |
| `SERVER_PORT` | 后端端口 | `8080` |
| `SPRING_DATASOURCE_URL` | MySQL 连接串 | `jdbc:mysql://localhost:3306/medicine...` |
| `SPRING_DATASOURCE_USERNAME` | MySQL 用户名 | `root` |
| `SPRING_DATASOURCE_PASSWORD` | MySQL 密码 | `123456` |
| `SPRING_DATA_REDIS_HOST` | Redis 地址 | `localhost` |
| `SPRING_DATA_REDIS_PORT` | Redis 端口 | `6379` |
| `SPRING_DATA_REDIS_PASSWORD` | Redis 密码 | `123456` |
| `JWT_SECRET` | JWT 密钥（至少 32 字节） | 开发用默认值 |
| `JWT_EXPIRATION_MS` | JWT 有效期（毫秒） | `86400000` |
| `MYSQL_ROOT_PASSWORD` | 仅 docker-compose 使用 | `123456` |
| `REDIS_PASSWORD` | 仅 docker-compose 使用 | `123456` |

## 默认账号

`insert_date.sql` 中的演示账号（需先导入该文件）：

- 管理员：`admin` / `123456`
- 普通用户：`out12` / `123456`

## 认证与权限

- 密码以 BCrypt 哈希存储，登录时比对哈希。
- 登录成功返回 JWT（默认 24 小时），前端请求携带 `Authorization: Bearer <token>`。
- 除 `/api/auth/login`、`/api/users/register`、`/health` 外，所有 `/api/**` 接口都需要有效令牌。
- 系统区分“管理员”和“用户”：所有写操作（新增/修改/删除）仅管理员可执行，普通用户为只读；前端按角色隐藏写入口。
- 公开注册接口创建的角色固定为“用户”；管理员账号由管理员通过 `POST /api/users` 创建。
- 数据库结构由 Flyway 管理（`src/main/resources/db/migration`），后端启动时自动迁移，无需手动建表。

## 前端说明

前端位于 `vue-medicine/`，基于 Vue 3 + Element Plus。

### 前端目录结构

```
vue-medicine/
├── src/
│   ├── api/index.js        # 各资源 CRUD 接口（工厂函数生成）
│   ├── composables/        # useCrudList / useCrudForm 通用列表与表单逻辑
│   ├── router/index.js     # 路由与登录守卫
│   ├── stores/user.js      # 用户 token / 信息（Pinia）
│   ├── utils/request.js    # Axios 实例、拦截器、401 跳转
│   ├── directives/admin.js # v-admin：仅管理员可见
│   └── views/              # 页面组件（登录、注册、首页、各业务列表/表单）
├── public/
├── nginx.conf              # 生产镜像用 nginx 配置
└── vite.config.js          # 开发服务器与 /api 代理
```

### 前端常用命令

```bash
cd vue-medicine
npm install        # 安装依赖
npm run dev        # 开发服务器，http://localhost:5173
npm run build      # 生产构建到 dist/
npm run preview    # 预览生产构建
npm run lint       # ESLint
```

开发服务器将 `/api` 代理到 `http://localhost:8080`，因此本地开发需先启动后端。

### 前端页面

- 认证：登录 `/login`、注册 `/register`
- 首页 `/`
- 业务模块：药品、客户、供应商、员工、库存、仓库、采购订单、销售订单（各有列表页与 `/xxx/form` 表单页）

### 前端权限与约定

- 登录 token 存储于 `localStorage`，请求拦截器自动附加 `Authorization: Bearer <token>`；收到 `401` 时清除本地登录信息并跳转到 `/login`。
- `v-admin` 指令用于隐藏非管理员的写操作入口，实际权限由后端校验。
- 后端接口统一返回 `{ code, message, data }`，`code = 200` 为成功，否则由拦截器统一提示。
- 表单字段名与后端实体保持一致（后端 Jackson 使用 SNAKE_CASE 命名策略）。

## 测试与构建

```bash
mvn test                                          # 后端单元测试
cd vue-medicine && npm run build                  # 前端生产构建
```

## 常见问题

- 登录提示“网络错误”：确认后端已启动、`/api` 代理或 CORS 配置正确（前端开发由 Vite 代理到 8080）。
- Redis 连接失败：确认 `redis-cli ping` 返回 `PONG`；未启动时后端会自动降级为直连数据库，仅性能下降。
- 旧数据库登录失败：密码已升级为 BCrypt，需重新导入 `insert_date.sql`，或手动把 `users` 表密码更新为 BCrypt 哈希。
- 端口被占用：修改 `application.yml`（后端）、`vite.config.js`（前端）或 `docker-compose.yml` 的端口映射。
