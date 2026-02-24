package com.bookstore.springboot.core.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 *  种子数据管理器。它会自动注入所有实现了 IDataSeedContributor 的 Bean，并按顺序执行它们的 seed 方法。
 */
@Component
public class DataSeeder {
    
    @Autowired(required = false)
    private List<IDataSeedContributor> contributors;

    public void seed(DataSeedContext context) {
        if (contributors != null) {
            for (IDataSeedContributor contributor : contributors) {
                contributor.seed(context);
            }
        }
    }
}
