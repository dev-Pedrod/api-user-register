package com.devpedrod.apiuserregister.strategy.user;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.stereotype.Service;

import static java.time.LocalDateTime.now;

@Service
public class UserDisableStrategy implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if (domainEntity instanceof User user) {
            user.getPermissions().parallelStream().forEach(permission -> permission.setDisabledAt(now()));
            if(user.getAddress() != null) {
                user.getAddress().setDisabledAt(now());
            }
        }
        return null;
    }
}
