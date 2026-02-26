package com.bookstore.springboot.modules.permission;

import com.bookstore.springboot.core.seeding.DataSeedContext;
import com.bookstore.springboot.core.seeding.IDataSeedContributor;
import com.bookstore.springboot.core.infrastructure.permission.PermissionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionDataSeedContributor implements IDataSeedContributor {

    @Autowired
    private PermissionGrantRepository permissionGrantRepository;

    @Autowired
    private PermissionRegistry permissionRegistry;

    @Override
    public void seed(DataSeedContext context) {
        // Seed permissions for 'admin' role
        String providerName = "R";
        String providerKey = "admin";

        if (permissionGrantRepository.findByProviderNameAndProviderKey(providerName, providerKey).isEmpty()) {
            List<PermissionGrant> adminGrants = permissionRegistry.getAllPermissions().keySet().stream()
                    .map(name -> PermissionGrant.builder()
                            .name(name)
                            .providerName(providerName)
                            .providerKey(providerKey)
                            .build())
                    .collect(Collectors.toList());
            
            permissionGrantRepository.saveAll(adminGrants);
        }
    }
}

