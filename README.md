# Bookstore Demo Project

这是一个基于 Spring Boot 3.x 构建的后端演示项目，主要用于学习和演示 Spring Boot 的核心功能及遵循现代开发最佳实践。

## 核心特性

本项目不仅实现了基础的 CRUD 功能，还引入了一些高级架构设计 concepts：

### 1. 领域模型与基类设计 (ABP-like Architecture)
借鉴了 ABP 框架的代码风格，在 `core` 包中定义了规范的领域模型基类：
- **`Entity<TKey>`**: 统一的实体标识基类，支持泛型主键。
- **`AuditedAggregateRoot<TKey>`**: 包含审计功能的聚合根基类。
- **`CrudAppService`**: 通用的增删改查服务基类，通过继承即可获得标准 CRUD 能力。
- **`CrudController`**: 通用的控制器基类，实现了标准的 RESTful 接口。

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

### 7. 身份与账户管理 (Identity & Account)
借鉴 ABP 的账户与身份模块，由于使用了 `core` 下的泛型基类，新模块的开发效率极高：
- **用户与角色 (Users & Roles)**: 标准化 CRUD，并通过 `@JoinTable` 实现多对多关联。
- **权限管理 (Permission Management)**:
    - **单表授权设计**: 仅通过一个 `permission_grants` 表即可实现对用户和角色的统一授权。
        - **ProviderName**: 标记授权对象类型（'R' 代表角色，'U' 代表用户）。
        - **ProviderKey**: 对应对象的唯一标识（角色名或用户 ID）。
        - **优势**: 结构极简且极具扩展性，未来支持部门或组织授权无需改表。
    - **声明式定义**: 通过实现 `IPermissionDefinitionProvider` 在各模块中定义专有权限。
- **Account 模块**: 独立于 Identity，处理注册、登录（JWT）及密码重置流程。

### 8. JWT 身份认证机制 (Security & JWT)
集成了 Spring Security 实现无状态认证：
- **ICurrentUser**: 核心上下文对象。通过注入 `ICurrentUser` 接口，Service 层可以随时获取当前登录用户的 ID、用户名、邮箱及权限状态，无需手动解析 SecurityContext。
- **PasswordEncoder**: 全局使用 `BCrypt` 对密码进行加盐哈希，确保数据存储安全。
- **TokenProvider**: 基于 JJWT 生成包含用户身份和角色申明（Claims）的签名令牌。
- **配置最佳实践**:
    - **开发环境**: 在 `application-dev.properties` 中预设开发密钥，方便即开即用。
    - **生产环境**: 严格通过环境变量 `APP_JWT_SECRET` 注入密钥，禁止在代码或 Git 仓库中硬码机密信息。
    - **密钥强度**: 强制要求 256 位及以上强度的密钥（HS256 算法要求）。

## 技术栈

- **Java**: 17
- **Framework**: Spring Boot 3.5.x
- **Security**: Spring Security + JJWT
- **ORM**: Spring Data JPA / Hibernate
- **Database**: MySQL (驱动已配置)
- **Utilities**: Lombok, MapStruct (AutoMapper for Java)

## 项目结构

```text
src/main/java/com/bookstore/springboot/
├── config/        # 全局配置 (Spring, OpenAPI 等)
├── core/          # 框架核心层 (高度解耦的内部框架)
│   ├── base/           # 业务基石 (Entity, Service, Controller, Mapper 基类)
│   ├── infrastructure/ # 技术基础设施 (Security/JWT, Exception, Permission 机制)
│   ├── query/          # 查询协议 (声明式 Filter, PagedResult)
│   └── seeding/        # 数据种子机制 (DataSeeder)
├── modules/       # 业务功能层 (10% 业务聚焦)
│   ├── account/   # 账号逻辑 (登录、注册)
│   ├── book/      # 图书模块
│   ├── identity/  # 身份模块 (User, Role)
│   └── permission/# 权限授予管理
└── BookstoreDemoApplication.java # 启动类
```

### 5. 对象映射 (AutoMapper Concept)
借鉴了 ABP 中的 `IObjectMapper` 概念，引入了 **MapStruct** 作为 Java 世界的 AutoMapper 替代方案：
- **解耦逻辑**: 在 `BookMapper` 中以接口形式声明映射规则，消除了 Service 层冗长的手工赋值代码。
- **自动更新**: 通过 `@MappingTarget` 特性，优雅地处理了 `UpdateBookDto` 对现有实体属性的部分更新。
- **类型安全**: 所有转换在编译期生成，性能等同于手写代码，且具备静态检查能力。

### 6. 分页、排序与声明式查询 (Paged & Declarative Query)
实现了一套自动化的分页与动态过滤机制，极大地减少了重复的查询代码：
- **标准化输入输出**: 统一使用 `PagedAndSortedResultRequestDto` 处理分页排序请求，返回 `PagedResultDto<T>` 包含数据与总条数。
- **声明式过滤 (`@Filter`)**: 在 DTO 字段上通过 `@Filter` 注解直接声明查询逻辑（如 `LIKE`, `EQUAL`, `GREATER_THAN` 等），无需在 Service 层编写任何过滤代码。
- **基类自动化处理**: `CrudAppService` 利用反射自动解析 DTO 中的注解并转化为 **JPA Specification**，实现了“配置即查询”的体验。
- **灵活扩展**: 默认支持基于字段名的自动匹配，同时也允许通过 `createFilteredQuery` 钩子方法在业务层实现更复杂的自定义逻辑。

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
5. 掌握基于注解和反射的声明式动态查询设计。
6. 理解 JPA Specification 在构建通用查询基类中的核心作用。
