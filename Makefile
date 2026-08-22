# Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/
.PHONY: test build demo up down
test:
	cd backend && mvn test
build:
	cd backend && mvn package
	cd frontend && npm ci && npm run build
demo:
	cd frontend && npm run dev:demo
up:
	docker compose up --build
down:
	docker compose down
