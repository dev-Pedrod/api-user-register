package com.devpedrod.apiuserregister.strategy.address;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.stereotype.Service;

@Service
public class AddressStrategy implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if(domainEntity instanceof Address address){
            address.setCity(address.getCity().trim().replaceAll(" "," "));
            address.setCountry(address.getCountry().trim().replaceAll(" "," "));
            address.setStreet(address.getStreet().trim().replaceAll(" "," "));
            address.setState(address.getState().trim().replaceAll(" "," "));
            return address;
        }
        return null;
    }
}
