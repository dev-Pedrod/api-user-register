package com.devpedrod.apiuserregister.strategy.formation;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class DisableFormationStrategy implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if(domainEntity instanceof Formation formation){
            formation.getUser().getFormations()
                    .stream()
                    .filter(x -> x.equals(formation))
                    .collect(Collectors.toList())
                    .remove(formation);
            return formation;
        }
        return null;
    }
}
