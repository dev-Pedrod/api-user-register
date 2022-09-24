package com.devpedrod.apiuserregister.strategy.formation;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.stereotype.Service;

@Service
public class FormationStrategy implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if(domainEntity instanceof Formation formation){
            formation.setName(formation.getName().replaceAll("\\s+"," ").trim());
            formation.setInstitution(formation.getInstitution().replaceAll("\\s+"," ").trim());
            return formation;
        }
        return null;
    }
}
