# Bookstore Demo Project Todo List

## 1. 基础设施与安全增强 (Infrastructure & Security)
- [ ] 引入 Spring Security 依赖并完成基础配置
- [ ] 实现基于 JWT 的身份认证机制 (TokenProvider, Filter)
- [ ] 在 `core` 包中定义 `ICurrentUser` 接口及实现，提供当前登录用户信息
- [ ] 实现全局权限拦截器/切面，支持声明式权限检查 (支持 ABP 风格的权限控制)

## 2. 身份管理模块 (Identity Module)
- [ ] **用户管理 (User Management)**
    - [x] 定义 `User` 实体 (继承 `AuditedAggregateRoot`)
    - [x] 实现用户实体的 CRUD (利用 `CrudAppService` 基类)
    - [ ] 实现用户密码加密存储与登录逻辑
- [ ] **角色管理 (Role Management)**
    - [ ] 定义 `Role` 实体
    - [ ] 实现角色管理的 CRUD 接口
    - [ ] 实现用户与角色的关联逻辑 (UserRoles)

## 3. 权限管理模块 (Permission Management)
- [ ] 定义权限定义提供者 (`PermissionDefinitionProvider`) 统一管理权限项
- [ ] 实现权限授予逻辑 (Grant Permissions to User/Role)
- [ ] 实现 `IPermissionChecker` 用于在业务逻辑中校验权限

## 4. 其它 ABP 标准功能 (Optional)
- [ ] **审计日志增强**: 记录详细的实体变更日志
- [ ] **设置管理 (Setting Management)**: 实现 key-value 形式的系统设置
- [ ] **多租户 (Multi-Tenancy)**: 实现基础的数据隔离逻辑
