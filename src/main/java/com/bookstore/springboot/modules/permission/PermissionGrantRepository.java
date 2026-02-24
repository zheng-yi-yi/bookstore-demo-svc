package com.bookstore.springboot.modules.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PermissionGrantRepository extends JpaRepository<PermissionGrant, UUID> {
    List<PermissionGrant> findByProviderNameAndProviderKey(String providerName, String providerKey);
    void deleteByProviderNameAndProviderKey(String providerName, String providerKey);
}
