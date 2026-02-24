package com.bookstore.springboot.modules.permission;

import com.bookstore.springboot.modules.permission.dto.GetPermissionListResultDto;
import com.bookstore.springboot.modules.permission.dto.UpdatePermissionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionAppService permissionAppService;

    @GetMapping
    public GetPermissionListResultDto get(@RequestParam String providerName, @RequestParam String providerKey) {
        return permissionAppService.getPermissions(providerName, providerKey);
    }

    @PutMapping
    public void update(@RequestParam String providerName, @RequestParam String providerKey, @RequestBody UpdatePermissionsDto input) {
        permissionAppService.updatePermissions(providerName, providerKey, input);
    }
}
