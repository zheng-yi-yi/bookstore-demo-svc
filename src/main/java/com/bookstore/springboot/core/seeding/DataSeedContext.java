package com.bookstore.springboot.core.seeding;

import java.util.HashMap;
import java.util.Map;

/**
 * 种子数据上下文。允许在不同的贡献者之间传递共享属性或配置。
 */
public class DataSeedContext {
    private final Map<String, Object> properties;

    public DataSeedContext() {
        this.properties = new HashMap<>();
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }
}

