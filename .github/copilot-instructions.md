# Bookstore Demo Project Instructions

## 项目核心背景 (Project Context)
本项目是一个基于 **Spring Boot 3.5.x** 构建的高度抽象化后端演示项目。
核心设计理念是：**“通过框架级的泛型设计，将 90% 的重复代码下沉到基类，开发者只需要关注那 10% 的核心业务逻辑。”**

## 环境配置 (Environment)
- **JDK**: 如果执行命令后发现JAVA_HOME找不到，则可以在终端执行命令，设置  `$env:JAVA_HOME = "C:\Users\yiyiz\.jdks\ms-17.0.18"`。注意，先执行命令，找不到环境变量再设置环境变量，否则可能需要重启IDE才能生效。

## 架构核心组件 (Core Architecture)

### 1. 领域模型基类
- `Entity<TKey>`: 所有实体的基类，统一定义 ID。
- `AuditedAggregateRoot<TKey>`: 包含自动审计功能（`creationTime`, `lastModificationTime` 等）的聚合根基类。

### 2. 标准化 CRUD 框架 (The 90% Downlink)
- `ICrudAppService<TEntityDto, TKey, ...>`: 定义标准 CRUD 接口。
- `CrudAppService<TEntity, TEntityDto, TKey, ...>`: 实现通用的增删改查逻辑，包括：
    - 自动分页与排序。
    - **声明式过滤**：通过 DTO 上的 `@Filter` 注解自动生成 JPA Specification。
    - 自动对象映射（集成 MapStruct）。
- `CrudController<...>`: 通用控制器基类，暴露标准的 RESTful 接口。

### 3. 数据与异常处理
- **Data Seeding**: 实现 `IDataSeedContributor` 接口即可在启动时初始化数据。
- **Exception Handling**: `GlobalExceptionHandler` 统一处理 `ResourceNotFoundException` 等业务异常。

## 开发者关注点 (The 10% Logic)
当需要新增一个业务模块（如 `Book`）时，开发者仅需：
1. 定义 **Entity** 继承 `AuditedAggregateRoot`。
2. 定义 **DTOs**（Create/Update/GetList），在 `GetListInput` 中使用 `@Filter` 声明查询规则。
3. 定义 **Mapper** 接口继承 `BaseMapper`。
4. 定义 **Repository** 继承 `JpaRepository` 和 `JpaSpecificationExecutor`。
5. 定义 **Service/Controller** 继承对应的 `Crud` 基类，通常无需编写任何方法体。

## 规范与约定
- 优先查找并利用基类（`base` 包下）的功能。
- 注重 DTO 的声明式编程（如 `@Data`, `@SuperBuilder`, `@Filter`）。
- 测试驱动：集成测试应继承 `SpringBootTest` 并使用 `MockMvc` 验证完整链路。
