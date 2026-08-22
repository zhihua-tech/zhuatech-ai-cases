# API 摘要

| 方法 | 地址 | 说明 |
| --- | --- | --- |
| GET | `/api/cases` | 查询启用的案例 |
| GET | `/api/cases/{slug}` | 查询案例字段和说明 |
| POST | `/api/cases/{slug}/run` | 运行指定案例 |
| GET | `/api/admin/overview` | 管理控制台指标 |
| GET | `/api/admin/cases` | 查询全部案例，包括停用项 |
| PATCH | `/api/admin/cases/{id}` | 更新启用或推荐状态 |
| GET | `/api/admin/executions` | 查询最近运行记录 |

运行请求示例：

```json
{
  "inputs": {
    "contractText": "甲方应在验收后90日内付款，合同到期自动续约。",
    "contractType": "软件服务合同"
  }
}
```

生产部署前应为 `/api/admin/**` 增加企业统一身份认证和细粒度权限。
