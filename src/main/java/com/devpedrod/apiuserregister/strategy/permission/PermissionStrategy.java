package com.devpedrod.apiuserregister.strategy.permission;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.Permission;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.stereotype.Service;

@Service
public class PermissionStrategy implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if(domainEntity instanceof Permission permission) {
            permission.setName(permission.getName().replaceAll("\\s+"," ").trim());
            permission.setPermission(permission.getPermission().replaceAll("\\s+"," ").trim());

            return permission;
        }
        return null;
    }
}
