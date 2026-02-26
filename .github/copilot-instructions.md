# Bookstore Demo Project Instructions

## 项目核心背景 (Project Context)
本项目是一个基于 **Spring Boot 3.5.x** 构建的高度抽象化后端演示项目。
核心设计理念是：**“通过框架级的泛型设计，将 90% 的重复代码下沉到基类，开发者只需要关注那 10% 的核心业务逻辑。”**

## 环境配置 (Environment)
- **JDK**: 如果执行命令后发现JAVA_HOME找不到，则可以在终端执行命令，设置  `$env:JAVA_HOME = "C:\Users\yiyiz\.jdks\ms-17.0.18"`。注意，先执行命令，找不到环境变量再设置环境变量，否则可能需要重启IDE才能生效。

## 架构核心组件 (Core Architecture)

### 1. 核心分层设计 (Core Stratification)
`core` 包已重构为职责分明的分层结构：
- **`core.base`**: 存放 `Entity`, `AuditedAggregateRoot`, `ICrudAppService`, `CrudAppService`, `CrudController`, `BaseMapper` 等核心抽象。
- **`core.infrastructure`**: 存放 `security` (JWT 认证), `exception` (全局异常), `permission` (权限注册) 等底层横切关注点。
- **`core.query`**: 存放 `filter` (声明式过滤) 和 `result` (分页结果) 等查询协议相关类。
- **`core.seeding`**: 存放数据种子初始化逻辑。

### 2. 领域模型基类
- `Entity<TKey>`: `core.base.entity` 下所有实体的基类，统一定义 ID。
- `AuditedAggregateRoot<TKey>`: `core.base.entity` 下包含自动审计功能的聚合根基类。

### 3. 标准化 CRUD 与 权限框架
- `ICrudAppService`, `CrudAppService`: 在 `core.base.service` 下定义通用逻辑。
- `CrudController`: 在 `core.base.controller` 下暴露标准的 RESTful 接口。
- **权限与环境上下文**:
    - **ICurrentUser**: 必须注入 `ICurrentUser` 来访问当前用户信息信息（ID, Roles, Permissions）。严禁直接静态调用 `SecurityContextHolder`。
    - **声明式权限定义**: 实现 `IPermissionDefinitionProvider`（如 `IdentityPermissionDefinitionProvider`）来定义模块内的权限层级。
    - **权限控制**: 在 Controller 或方法上使用 `@PreAuthorize("hasAuthority('...')")` 或自定义注解结合 `PermissionChecker` 验证。
    - **Security**: 默认开启 JWT 校验，所有请求需通过 `Authorization: Bearer <token>` 访问，除非显式放行。

### 3. 数据隔离与映射
- **DTO 映射规范**: 
    - 实体转换 DTO 需通过 `Mapper` 接口（继承 `BaseMapper`）。
    - 涉及审计字段（`creationTime` 等）的更新 DTO 转换，请使用 `@IgnoreAuditedProperties` 忽略这些只读属性。
- **声明式过滤**: 通过 DTO 上的 `@Filter` 注解自动生成 JPA Specification。

### 4. 身份与认证 (Identity System)
- **用户密码**: 严禁明文存储，由 `AccountAppService` 统一使用 `PasswordEncoder` 处理。
- **权限授予**: 默认提供 `PermissionAppService` 用于检索和授予具体的权限。
- **机密管理规范**: 
    - **禁止硬编码**: 严禁在 Java 类或属性文件中硬编码生产环境密钥。
    - **环境隔离**: 必须区分 `dev` 和 `prod` 配置，生产密钥应通过环境变量（如 `APP_JWT_SECRET`）注入。
    - **安全标准**: JWT 密钥必须满足至少 256 位长度。

## 开发者关注点 (The 10% Logic)
当需要新增一个业务模块（如 `Book`）时，开发者仅需：
1. 定义 **Entity** 继承 `AuditedAggregateRoot`。
2. 定义 **DTOs**（Create/Update/GetList），在 `GetListInput` 中使用 `@Filter` 声明查询规则。
3. 定义 **Mapper** 接口继承 `BaseMapper`。
4. 定义 **Repository** 继承 `JpaRepository` 和 `JpaSpecificationExecutor`。
5. 定义 **Service/Controller** 分别继承 `CrudAppService` 与 `CrudController`。
6. **(可选)** 实现 `IPermissionDefinitionProvider` 并在 `IdentityPermissionNames.java` 中注册新增权限的常量名。

## 规范与约定
- 优先查找并利用核心基类（`core` 包下）的功能。
- 业务代码应按功能模块（Feature）聚合在 `modules` 包下。
- 注重 DTO 的声明式编程（如 `@Data`, `@SuperBuilder`, `@Filter`）。
- 测试驱动：集成测试应继承 `SpringBootTest` 并使用 `MockMvc` 验证完整链路。
- **文档同步 (强制执行)**:
    - 每次涉及架构变动、新增模块或重大功能更新，**必须**同步更新 [README.md](README.md) 和 [.github/copilot-instructions.md](.github/copilot-instructions.md)，确保文档与代码实时一致。
    - 在完成更新后，应在回复中提供对应的 **Git Commit 信息**。
