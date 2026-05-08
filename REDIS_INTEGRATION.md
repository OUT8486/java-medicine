# Redis 集成说明

## 概述
本项目已成功集成 Redis 缓存，用于提升系统性能，减少数据库查询压力。

## 已完成的工作

### 1. 依赖配置
- 在 `pom.xml` 中添加了 `spring-boot-starter-data-redis` 依赖
- Maven 会自动下载相关依赖包

### 2. Redis 配置
在 `application.yml` 中配置了 Redis 连接参数：
```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: 
      database: 0
      timeout: 3000ms
      lettuce:
        pool:
          max-active: 8
          max-wait: -1ms
          max-idle: 8
          min-idle: 0
```

### 3. 核心组件

#### RedisConfig.java
- 位置：`src/main/java/com/example/spring_boot/config/RedisConfig.java`
- 功能：配置 RedisTemplate，设置序列化方式（String + JSON）

#### RedisUtils.java
- 位置：`src/main/java/com/example/spring_boot/utils/RedisUtils.java`
- 功能：提供常用的 Redis 操作工具方法
  - `set(key, value)` - 设置缓存
  - `set(key, value, timeout, unit)` - 设置带过期时间的缓存
  - `get(key)` - 获取缓存
  - `delete(key)` - 删除缓存
  - `hasKey(key)` - 判断键是否存在
  - `expire(key, timeout, unit)` - 设置过期时间

### 4. 已集成 Redis 的 Service

以下 Service 已经实现了 Redis 缓存功能：

#### DrugService（药品管理）
- 缓存策略：
  - 单个药品：`drug:{drug_id}`，过期时间 30 分钟
  - 药品列表：`drug:list`，过期时间 30 分钟
- 缓存逻辑：
  - 查询时先查 Redis，未命中再查数据库并写入缓存
  - 新增/修改/删除时清除相关缓存

#### CustomerService（客户管理）
- 缓存策略：
  - 单个客户：`customer:{customer_id}`，过期时间 30 分钟
  - 客户列表：`customer:list`，过期时间 30 分钟

#### InventoryService（库存管理）
- 缓存策略：
  - 单个库存：`inventory:{inventory_id}`，过期时间 30 分钟
  - 库存列表：`inventory:list`，过期时间 30 分钟

### 5. 测试接口

提供了 Redis 测试 Controller：
- 位置：`src/main/java/com/example/spring_boot/controller/RedisTestController.java`
- 接口：
  - `POST /api/redis/set?key=xxx&value=xxx` - 设置缓存
  - `GET /api/redis/get?key=xxx` - 获取缓存
  - `DELETE /api/redis/delete?key=xxx` - 删除缓存

## 使用前准备

### 安装 Redis 服务器

#### Windows 系统
1. 下载 Redis for Windows：https://github.com/microsoftarchive/redis/releases
2. 解压后运行 `redis-server.exe` 启动服务
3. 默认端口：6379

#### 使用 Docker（推荐）
```bash
docker run -d --name redis -p 6379:6379 redis:latest
```

#### Linux 系统
```bash
# Ubuntu/Debian
sudo apt-get install redis-server
sudo systemctl start redis-server

# CentOS/RHEL
sudo yum install redis
sudo systemctl start redis
```

### 验证 Redis 是否启动
```bash
# 使用 redis-cli 连接测试
redis-cli ping
# 返回 PONG 表示成功
```

## 启动项目

1. 确保 Redis 服务正在运行
2. 启动后端应用：
   ```bash
   mvn spring-boot:run
   ```
3. 前端应用正常启动即可使用

## 测试 Redis 功能

### 方法一：使用测试接口
```bash
# 设置缓存
curl -X POST "http://localhost:8080/api/redis/set?key=test&value=hello"

# 获取缓存
curl http://localhost:8080/api/redis/get?key=test

# 删除缓存
curl -X DELETE "http://localhost:8080/api/redis/delete?key=test"
```

### 方法二：访问业务接口
访问任何已集成缓存的业务接口（如药品列表、客户列表等），第一次会查询数据库并缓存，第二次会直接从 Redis 读取。

## 如何为其他 Service 添加缓存

参考已有的 Service 实现，步骤如下：

1. 注入 RedisUtils：
```java
@Autowired
private RedisUtils redisUtils;
```

2. 定义缓存键常量：
```java
private static final String CACHE_KEY = "your_prefix:";
private static final String CACHE_LIST_KEY = "your_prefix:list";
private static final long CACHE_EXPIRE_TIME = 30; // 分钟
```

3. 查询方法添加缓存逻辑：
```java
public YourEntity getById(String id) {
    String cacheKey = CACHE_KEY + id;
    YourEntity entity = (YourEntity) redisUtils.get(cacheKey);
    
    if (entity != null) {
        return entity;
    }
    
    entity = mapper.selectById(id);
    if (entity != null) {
        redisUtils.set(cacheKey, entity, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
    }
    return entity;
}
```

4. 修改/删除方法清除缓存：
```java
public void update(YourEntity entity) {
    mapper.update(entity);
    redisUtils.delete(CACHE_KEY + entity.getId());
    redisUtils.delete(CACHE_LIST_KEY);
}
```

## 注意事项

1. **数据一致性**：缓存有过期时间（默认30分钟），在此期间如果数据被直接修改数据库，可能出现短暂不一致
2. **缓存穿透**：当前实现未处理缓存穿透问题，生产环境建议添加空值缓存或布隆过滤器
3. **缓存雪崩**：可以为不同 key 设置不同的过期时间，避免大量缓存同时失效
4. **序列化**：使用 GenericJackson2JsonRedisSerializer，实体类需要有无参构造函数
5. **监控**：建议在生产环境添加 Redis 监控，观察命中率、内存使用等情况

## 常见问题

### Q1: 连接 Redis 失败？
- 检查 Redis 服务是否启动
- 检查 application.yml 中的 host 和 port 配置是否正确
- 检查防火墙是否阻止了连接

### Q2: 缓存不生效？
- 检查 Redis 是否正常连接（查看启动日志）
- 使用测试接口验证 Redis 是否正常工作
- 检查 Service 中是否正确注入了 RedisUtils

### Q3: 数据序列化失败？
- 确保实体类有 `@Data` 或 getter/setter 方法
- 确保实体类有无参构造函数（`@NoArgsConstructor`）

## 后续优化建议

1. 使用 Spring Cache 注解简化缓存代码（`@Cacheable`, `@CacheEvict`）
2. 添加缓存预热机制，系统启动时加载热点数据
3. 实现分布式锁，防止缓存击穿
4. 添加缓存统计和监控功能
5. 考虑使用 Redis Cluster 实现高可用
