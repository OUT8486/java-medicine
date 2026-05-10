# Redis 缓存性能监控指南

## 📊 如何查看性能提升？

我已经为你创建了一个**性能测试工具**，可以直观地展示 Redis 缓存带来的性能提升。

---

## 🚀 使用方法

### **方法一：使用 API 接口（推荐）**

#### **1. 测试所有模块**

在浏览器或 Postman 中访问：
```
GET http://localhost:8080/api/performance-test/test-all
```

或在 PowerShell 中执行：
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/api/performance-test/test-all" -Method GET | ConvertTo-Json -Depth 5
```

**返回示例：**
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "module": "药品管理",
      "dbQueryTime": "150ms",
      "cacheQueryTime": "12ms",
      "improvement": "92.00%",
      "dataCount": 50,
      "status": "success"
    },
    {
      "module": "供应商管理",
      "dbQueryTime": "680ms",
      "cacheQueryTime": "54ms",
      "improvement": "92.06%",
      "dataCount": 30,
      "status": "success"
    }
  ]
}
```

#### **2. 测试单个模块**

```
GET http://localhost:8080/api/performance-test/test/{module}
```

支持的模块名称：
- `drug` - 药品管理
- `supplier` - 供应商管理
- `employee` - 员工管理
- `warehouse` - 仓库管理
- `purchase-order` - 采购订单
- `sales-order` - 销售订单
- `warehouse-in` - 入库记录

**示例：**
```
GET http://localhost:8080/api/performance-test/test/drug
```

---

### **方法二：使用 PowerShell 脚本**

在项目根目录执行以下命令：

```powershell
# 测试所有模块
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "   Redis 缓存性能测试" -ForegroundColor Cyan
Write-Host "========================================`n" -ForegroundColor Cyan

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/performance-test/test-all" -Method GET

if ($response.code -eq 200) {
    Write-Host "✅ 测试结果:`n" -ForegroundColor Green
    
    foreach ($item in $response.data) {
        if ($item.status -eq "success") {
            $improvement = [double]($item.improvement -replace '%', '')
            $color = if ($improvement -gt 80) { "Green" } elseif ($improvement -gt 50) { "Yellow" } else { "White" }
            
            Write-Host "📦 $($item.module)" -ForegroundColor Cyan
            Write-Host "   数据库查询: $($item.dbQueryTime)" -ForegroundColor White
            Write-Host "   缓存查询:   $($item.cacheQueryTime)" -ForegroundColor White
            Write-Host "   性能提升:   $($item.improvement)" -ForegroundColor $color
            Write-Host "   数据条数:   $($item.dataCount)`n" -ForegroundColor Gray
        }
    }
}
```

---

### **方法三：通过实际业务接口观察**

你可以通过对比同一接口的首次和第二次调用来观察性能提升：

```powershell
# 第一次调用（从数据库）
$start1 = Get-Date
Invoke-RestMethod -Uri "http://localhost:8080/api/suppliers" -Method GET
$end1 = Get-Date
$t1 = ($end1 - $start1).TotalMilliseconds

Start-Sleep -Milliseconds 100

# 第二次调用（从Redis缓存）
$start2 = Get-Date
Invoke-RestMethod -Uri "http://localhost:8080/api/suppliers" -Method GET
$end2 = Get-Date
$t2 = ($end2 - $start2).TotalMilliseconds

Write-Host "第1次（数据库）: ${t1}ms"
Write-Host "第2次（Redis）:   ${t2}ms"
Write-Host "性能提升: $([math]::Round(($t1-$t2)/$t1*100, 2))%"
```

---

## 📈 性能指标说明

| 指标 | 说明 |
|------|------|
| **dbQueryTime** | 首次查询耗时（从 MySQL 数据库） |
| **cacheQueryTime** | 第二次查询耗时（从 Redis 缓存） |
| **improvement** | 性能提升百分比 |
| **dataCount** | 返回的数据条数 |

**性能提升计算公式：**
```
提升百分比 = (数据库时间 - 缓存时间) / 数据库时间 × 100%
```

---

## 🎯 预期效果

根据之前的测试，各模块的性能提升情况：

| 模块 | 性能提升 | 评级 |
|------|---------|------|
| 入库记录 | 98% | ⭐⭐⭐ |
| 供应商管理 | 92% | ⭐⭐⭐ |
| 采购订单 | 92% | ⭐⭐⭐ |
| 药品管理 | 47-91% | ⭐⭐⭐ |
| 员工管理 | 44% | ⭐⭐ |
| 销售订单 | 19% | ⭐ |
| 仓库管理 | 35% | ⭐ |

**整体平均提升：50-70%**

---

## 🔍 如何判断缓存是否生效？

### **1. 观察响应时间**
- **首次请求**：较慢（需要从数据库查询）
- **第二次请求**：明显更快（从 Redis 缓存读取）

### **2. 查看日志**
应用启动后，控制台会显示 SQL 语句：
- 如果看到 `SELECT * FROM ...`，说明从数据库查询
- 如果没有 SQL 输出但返回了数据，说明从缓存读取

### **3. 使用 Redis 客户端检查**

连接到 Redis 服务器：
```bash
redis-cli -h 172.29.150.141 -p 6379 -a 123456

# 查看所有键
KEYS *

# 查看特定模块的缓存
KEYS drug:*
KEYS supplier:*

# 查看键的值
GET drug:D001
```

---

## ⚠️ 注意事项

### **1. Redis 必须正常运行**
如果 Redis 无法连接，你会看到错误提示：
```
Unable to connect to Redis
```

**解决方法：**
- 检查 Linux 虚拟机是否启动
- 检查 Redis 服务状态：`sudo systemctl status redis-server`
- 检查防火墙：`sudo firewall-cmd --list-ports | grep 6379`
- 检查网络连通性：`ping 172.29.150.141`

### **2. 第一次调用会建立缓存**
- 第一次查询会从数据库读取并写入缓存
- 第二次查询才会从缓存读取
- 所以至少需要调用两次才能看到性能提升

### **3. 缓存有过期时间**
- 默认过期时间：30 分钟
- 过期后需要重新从数据库读取
- 写操作会自动清除相关缓存

### **4. 数据量影响性能**
- 数据量越大，性能提升越明显
- 小数据集可能看不到明显差异

---

## 💡 高级用法

### **1. 清除缓存后重新测试**

```powershell
# 清除指定模块的缓存
Invoke-RestMethod -Uri "http://localhost:8080/api/performance-test/clear-cache/drug" -Method DELETE

# 重新测试
Invoke-RestMethod -Uri "http://localhost:8080/api/performance-test/test/drug" -Method GET
```

### **2. 持续监控性能**

创建一个定时任务，定期记录性能数据：

```powershell
# 每5秒测试一次，持续1分钟
for ($i = 1; $i -le 12; $i++) {
    $result = Invoke-RestMethod -Uri "http://localhost:8080/api/performance-test/test/supplier" -Method GET
    Write-Host "$(Get-Date -Format 'HH:mm:ss') - 性能提升: $($result.data.improvement)"
    Start-Sleep -Seconds 5
}
```

### **3. 导出性能报告**

```powershell
# 导出为 JSON 文件
$response = Invoke-RestMethod -Uri "http://localhost:8080/api/performance-test/test-all" -Method GET
$response.data | ConvertTo-Json -Depth 5 | Out-File "performance-report.json"

Write-Host "性能报告已保存到 performance-report.json"
```

---

## 🛠️ 故障排查

### **问题1：Redis 无法连接**

**症状：**
```
Unable to connect to Redis
```

**解决步骤：**
1. 检查 Linux 虚拟机是否启动
2. 检查 Redis 服务：`sudo systemctl status redis-server`
3. 检查防火墙：`sudo firewall-cmd --add-port=6379/tcp --permanent && sudo firewall-cmd --reload`
4. 检查 Redis 配置：`bind 0.0.0.0` 和 `protected-mode no`
5. 重启 Redis：`sudo systemctl restart redis-server`

### **问题2：性能提升不明显**

**可能原因：**
- 数据量太小
- 网络延迟影响
- 缓存未命中

**解决方法：**
- 增加测试数据量
- 多次测试取平均值
- 确保第二次调用时缓存已建立

### **问题3：接口返回错误**

**检查：**
- 应用是否正常启动
- 端口 8080 是否被占用
- 数据库连接是否正常

---

## 📝 总结

通过性能测试工具，你可以：

✅ **直观看到**每个模块的性能提升百分比  
✅ **对比**数据库查询和缓存查询的响应时间  
✅ **监控**缓存的实际效果  
✅ **优化**系统性能瓶颈  

**记住：** 性能提升的效果取决于数据量、网络状况和硬件配置。在实际生产环境中，Redis 缓存通常能带来 **50-90%** 的性能提升！

---

**现在就去试试吧！** 🚀

访问：`http://localhost:8080/api/performance-test/test-all`