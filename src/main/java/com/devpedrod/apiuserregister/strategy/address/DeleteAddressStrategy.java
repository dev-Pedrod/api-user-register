package com.devpedrod.apiuserregister.strategy.address;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.stereotype.Service;

@Service
public class DeleteAddressStrategy implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if(domainEntity instanceof Address address){
            address.getUser().setAddress(null);
            address.setUser(null);
           return address;
        }
        return null;
    }
}
