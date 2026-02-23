# Bookstore Demo Project

这是一个基于 Spring Boot 3.x 构建的后端演示项目，主要用于学习和演示 Spring Boot 的核心功能及遵循现代开发最佳实践。

## 核心特性

本项目不仅实现了基础的 CRUD 功能，还引入了一些高级架构设计 concepts：

### 1. 领域模型与基类设计 (ABP-like Architecture)
借鉴了 ABP 框架的代码风格，项目中定义了规范的领域模型基类：
- **`Entity<TKey>`**: 统一的实体标识基类，支持泛型主键（本项目已切换为 `UUID` 以支持分布式场景）。
- **`AuditedAggregateRoot<TKey>`**: 包含审计功能的聚合根基类，自动处理：
  - `creationTime` / `creatorId`
  - `lastModificationTime` / `lastModifierId`
- **DTO 基类**: 同步提供了 `EntityDto` 和 `AuditedEntityDto`，确保前后端数据传输的一致性。

### 2. 规范化种子数据初始化 (Data Seeding)
实现了一套可扩展且规范化的数据初始化机制：
- **`IDataSeedContributor`**: 模块化的种子数据贡献者接口。
- **`DataSeeder`**: 自动扫描并执行所有贡献者，支持幂等操作，确保系统启动时基础数据的完整性。

### 3. 数据访问层 (Persistence)
- 使用 **Spring Data JPA** 进行数据访问。
- 采用 **UUID** 作为数据库主键，提高系统可移植性。
- 通过 JPA 生命周期钩子（如 `@PrePersist`）自动初始化 UUID。

### 4. 异常处理 (Exception Handling)
- 全局异常处理器 `GlobalExceptionHandler`。
- 自定义业务异常 `ResourceNotFoundException`。
- 统一的 RESTful 错误响应格式。

## 技术栈

- **Java**: 17
- **Framework**: Spring Boot 3.5.x
- **ORM**: Spring Data JPA / Hibernate
- **Database**: MySQL (驱动已配置)
- **Utilities**: Lombok (使用 `@SuperBuilder` 支持深层继承)

## 项目结构

```text
src/main/java/com/bookstore/springboot/
├── controller/    # REST API 控制器
├── dto/           # 数据传输对象 (及 base 基类)
├── entity/        # 数据库实体 (及 base 基类)
├── data/          # 种子数据初始化逻辑 (Data Seed)
├── repository/    # JPA Repositories
├── service/       # 业务逻辑接口及实现
└── exception/     # 全局异常处理
```

## 快速开始

1. **配置数据库**: 在 `src/main/resources/application.properties` 中修改数据库连接信息。
2. **运行项目**:
   ```bash
   ./mvnw spring-boot:run
   ```
3. **数据初始化**: 项目启动时会自动执行 `BookDataSeedContributor` 中的逻辑，向数据库插入演示书籍数据。

## 学习重点

1. 理解泛型基类在多层架构中的应用。
2. 掌握 UUID 作为主键在 Spring Boot 中的配置与自动生成。
3. 学习如何构建解耦的、基于接口的 Data Seeding 框架。
4. 实践 Lombok 在复杂继承体系下的高级用法。
