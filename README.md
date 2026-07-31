# blog-forum

一个基于 Spring Boot、Spring Security 与 MyBatis-Plus 的博客论坛后端学习项目。

## Features

- 用户注册、登录与 JWT 鉴权
- 图形验证码与敏感词过滤
- 帖子发布、评论和分页查询
- 点赞、关注与粉丝关系
- 私信与未读消息
- Elasticsearch 内容搜索
- Redis 缓存与状态存储
- Kafka 事件生产与消费
- 站点数据统计
- 全局异常处理与接口访问日志

## Tech Stack

- Java 8
- Spring Boot 2.1.5
- Spring Security
- MyBatis-Plus
- MySQL
- Redis
- Kafka
- Elasticsearch
- Maven

## Project Structure

```text
src/main/java/cn/rich/community
├── config          # Security、Redis、线程池和 Web 配置
├── controller      # 用户、帖子、评论、关注、私信和搜索接口
├── entity          # 领域实体
├── event           # Kafka 事件生产与消费
├── mapper          # MyBatis 数据访问
├── service         # 业务逻辑
└── util            # JWT、Redis Key、敏感词等工具
```

数据库初始化脚本位于：

```text
src/main/resources/sql/
```

## Local Development

环境要求：

- JDK 8
- Maven
- MySQL
- Redis
- Kafka（事件功能）
- Elasticsearch（搜索功能）

```bash
git clone https://github.com/Qi18/blog-forum.git
cd blog-forum
```

1. 使用 `src/main/resources/sql/` 中的脚本初始化数据库。
2. 根据本地环境配置 `src/main/resources/application.properties`。
3. 启动依赖服务。
4. 启动应用：

```bash
./mvnw spring-boot:run
```

请勿将真实密码、Token 或生产环境连接信息提交到仓库。

## Status

该仓库是后端工程实践项目，重点覆盖认证授权、内容社区业务和常见中间件集成。
