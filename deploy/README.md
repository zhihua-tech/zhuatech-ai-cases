# 部署说明

1. 将根目录 `.env.example` 复制为 `.env`。
2. 修改 MySQL 密码；如需模型增强，再填写 AI Provider 配置。
3. 执行 `docker compose up --build -d`。
4. 访问 `http://localhost:8088`，后端接口位于 `http://localhost:8080/api`。

生产环境还应配置 HTTPS、反向代理、管理员认证、数据库备份、监控告警和密钥托管。
