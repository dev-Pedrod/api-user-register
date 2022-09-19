package com.devpedrod.apiuserregister.facade;

import com.devpedrod.apiuserregister.dao.impl.GenericDAO;
import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class Facade extends AbstractFacade implements IFacade{

    private DomainEntity executeRules(DomainEntity domainEntity, List<IStrategy> bnsRules) {
        for (IStrategy bnsRule : bnsRules) {
            bnsRule.applyBusinessRule(domainEntity);
        }
        return domainEntity;
    }

    @Override
    public void save(DomainEntity object) {
        super.initialize();
        Map<String, List<IStrategy>> entityMap = businessRule.get(object.getClass().getName());
        List<IStrategy> strategies = entityMap.get("SAVE");

        GenericDAO dao = daos.get(object.getClass().getName());
        dao.save(object, x -> executeRules((DomainEntity) x, strategies));
    }

    @Override
    public void delete(Long id, String className) {
        super.initialize();
        Map<String, List<IStrategy>> entityMap = businessRule.get(className);
        List<IStrategy> strategies = entityMap.get("DELETE");

        GenericDAO dao = daos.get(className);
        if(strategies != null) {
            dao.delete(id, x -> executeRules((DomainEntity) x, strategies));
            return;
        }
        dao.delete(id);
    }

    @Override
    public void disable(Long id, String className) {
        super.initialize();

        Map<String, List<IStrategy>> entityMap = businessRule.get(className);
        List<IStrategy> strategies = entityMap.get("DISABLE");

        GenericDAO dao = daos.get(className);
        if(strategies != null) {
            dao.disable(id, x -> executeRules((DomainEntity) x, strategies));
            return;
        }
        dao.disable(id);
    }

    @Override
    public void update(DomainEntity object) {
        super.initialize();
        Map<String, List<IStrategy>> entityMap = businessRule.get(object.getClass().getName());
        List<IStrategy> strategies = entityMap.get("SAVE");

        GenericDAO dao = daos.get(object.getClass().getName());
        dao.update(object, x -> executeRules((DomainEntity) x, strategies));
    }

    @Override
    public Page<DomainEntity> getAll(Pageable pageable, String className) {
        super.initialize();

        GenericDAO dao = daos.get(className);
        return dao.getAll(pageable);
    }

    @Override
    public DomainEntity getById(Long id, String className) {
        super.initialize();

        GenericDAO dao = daos.get(className);
        return dao.getById(id);
    }
}
