### 第一阶段：基础设施与切面增强 (Infrastructure & AOP)
- [ ] **自动化审计注入 (Audit Logic Automation)**: 
    *   目前 `creationTime` 等还需要手动赋值或由 JPA 钩子处理。实现 `AuditPropertySetter`，利用 JPA 监听器（`@EntityListeners`）或 Hibernate 过滤器，根据当前登录用户自动填充 `CreatorId` 和 `CreationTime`。
- [ ] **软删除 (Soft Delete)**: 
    *   实现 `ISoftDelete` 接口。引入全局过滤器（`@Filter`），确保所有的 `repository.findAll()` 默认只返回 `isDeleted = false` 的数据。
- [ ] **工作单元 (Unit of Work)**: 
    *   深入理解 Spring 的 `@Transactional`。模仿 ABP 实现一个 UOW 拦截器，确保在事务提交前自动分发领域事件（Domain Events）。
- [ ] **全局异常标准化 (Standardized Error Response)**: 
    *   模仿 ABP 的 `RemoteServiceErrorResponse`，即使在 500 错误时也返回固定格式的 JSON（Code, Message, Details, ValidationErrors）。

### 第二阶段：领域驱动设计 (DDD Patterns)
- [ ] **规格模式 (Specification Pattern)**: 
    *   实现 `ISpecification<T>`。将复杂的查询逻辑从 Service 中提取出来，实现查询逻辑的复用和自由组合。
- [ ] **领域服务 (Domain Services)**: 
    *   当业务逻辑涉及多个聚合根或不适合放在实体内时，创建 `DomainService` 层，并强制要求不直接在 Controller 中调用。
- [ ] **值对象 (Value Objects)**: 
    *   实现不可变的 `ValueObject` 基类，处理诸如 `Address`, `Money` 等没有唯一标识的领域概念。

### 第三阶段：身份与多租户 (Security & Multi-Tenancy)
- [ ] **当前用户上下文 (CurrentUser context)**: 
    *   创建一个 `ICurrentUser` 接口。封装 Spring Security 的 `SecurityContextHolder`，让 Service 层通过简单的 `currentUser.getUserId()` 即可获取用户信息。
- [ ] **权限系统 (Permissions & Authorization)**: 
    *   实现基于字符串的权限代码（如 `Bookstore.Books.Create`）。自定义注解 `@RequiresPermission`，并在拦截器中校验当前用户是否拥有该权限。
- [ ] **多租户支持 (Multi-Tenancy)**: 
    *   这是 ABP 的核心。实现 `IMultiTenant` 接口，通过数据行隔离（TenantId 过滤）或多模式隔离（Schema per tenant）实现多租户。

### 第四阶段：分布式与扩展 (Distributed Systems)
- [ ] **本地/分布式事件总线 (Event Bus)**: 
    *   模仿 `ILocalEventBus`（基于 Spring ApplicationEvent）和 `IDistributedEventBus`（集成 RabbitMQ 或 Kafka）。
- [ ] **后台作业 (Background Jobs)**: 
    *   集成分布式任务调度（如 Quartz 或 Spring Task），并模仿 ABP 的 `BackgroundJob<TArgs>` 提供简单一致的定义和触发方式。
- [ ] **设置管理 (Setting Management)**: 
    *   实现一个全局配置存储库，支持按“全局”、“租户”、“用户”三个级别覆盖配置。

### 第五阶段：模块化与动态能力 (Modularity)
- [ ] **模块依赖系统 (Module System)**: 
    *   定义 `AppModule` 类，通过类似 `@DependsOn(AnotherModule.class)` 的方式管理不同 Jar 包或包路径下的扫描顺序和初始化逻辑。
- [ ] **特性管理 (Feature Management)**: 
    *   实现功能开关（Feature Flags），根据租户的版本或授权，动态启用或禁用某个 API 接口。
