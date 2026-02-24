package com.bookstore.springboot.modules.permission;

import com.bookstore.springboot.core.permission.PermissionDefinition;
import com.bookstore.springboot.core.permission.PermissionRegistry;
import com.bookstore.springboot.modules.permission.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionAppService {

    @Autowired
    private PermissionRegistry permissionRegistry;

    @Autowired
    private PermissionGrantRepository permissionGrantRepository;

    public GetPermissionListResultDto getPermissions(String providerName, String providerKey) {
        List<PermissionGrant> grants = permissionGrantRepository.findByProviderNameAndProviderKey(providerName, providerKey);
        Set<String> grantedPermissionNames = grants.stream()
                .map(PermissionGrant::getName)
                .collect(Collectors.toSet());

        List<PermissionGroupDto> groups = permissionRegistry.getGroups().stream()
                .map(group -> PermissionGroupDto.builder()
                        .name(group.getName())
                        .displayName(group.getDisplayName())
                        .permissions(flattenPermissions(group.getChildren(), grantedPermissionNames))
                        .build())
                .collect(Collectors.toList());

        return GetPermissionListResultDto.builder()
                .entityDisplayName(providerKey)
                .groups(groups)
                .build();
    }

    private List<PermissionDto> flattenPermissions(List<PermissionDefinition> definitions, Set<String> grantedNames) {
        List<PermissionDto> dtos = new ArrayList<>();
        for (PermissionDefinition def : definitions) {
            dtos.add(PermissionDto.builder()
                    .name(def.getName())
                    .displayName(def.getDisplayName())
                    .parentName(def.getParentName())
                    .isGranted(grantedNames.contains(def.getName()))
                    .build());
            dtos.addAll(flattenPermissions(def.getChildren(), grantedNames));
        }
        return dtos;
    }

    @Transactional
    public void updatePermissions(String providerName, String providerKey, UpdatePermissionsDto input) {
        // Simple implementation: delete and re-insert
        // In ABP, it's more sophisticated
        permissionGrantRepository.deleteByProviderNameAndProviderKey(providerName, providerKey);

        List<PermissionGrant> newGrants = input.getPermissions().stream()
                .filter(UpdatePermissionDto::isGranted)
                .map(p -> PermissionGrant.builder()
                        .name(p.getName())
                        .providerName(providerName)
                        .providerKey(providerKey)
                        .build())
                .collect(Collectors.toList());

        permissionGrantRepository.saveAll(newGrants);
    }
}
