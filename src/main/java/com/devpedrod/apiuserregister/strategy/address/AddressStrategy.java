package com.devpedrod.apiuserregister.strategy.address;

import com.devpedrod.apiuserregister.dao.impl.AddressDAO;
import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressStrategy implements IStrategy {

    @Autowired
    private AddressDAO addressDAO;
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if(domainEntity instanceof Address address){
            if(address.getUser().getAddress() != null){
                addressDAO.delete(address.getUser().getAddress().getId());
            }
            address.getUser().setAddress(address);
            address.setCity(address.getCity().replaceAll("\\s+"," ").trim());
            address.setCountry(address.getCountry().replaceAll("\\s+"," ").trim());
            address.setStreet(address.getStreet().replaceAll("\\s+"," ").trim());
            address.setState(address.getState().replaceAll("\\s+"," ").trim());
            return address;
        }
        return null;
    }
}
