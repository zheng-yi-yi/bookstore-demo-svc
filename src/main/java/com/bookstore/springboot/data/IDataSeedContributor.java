package com.bookstore.springboot.data;

/**
 * 种子数据贡献者接口。每个模块或实体可以实现此接口来定义自己的初始化逻辑。
 */
public interface IDataSeedContributor {
    void seed(DataSeedContext context);
}
