package com.devpedrod.apiuserregister.facade;

import com.devpedrod.apiuserregister.dao.impl.AddressDAO;
import com.devpedrod.apiuserregister.dao.impl.FormationDAO;
import com.devpedrod.apiuserregister.dao.impl.GenericDAO;
import com.devpedrod.apiuserregister.dao.impl.UserDAO;
import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import com.devpedrod.apiuserregister.strategy.address.AddressStrategy;
import com.devpedrod.apiuserregister.strategy.address.DeleteAddressStrategy;
import com.devpedrod.apiuserregister.strategy.formation.DisableFormationStrategy;
import com.devpedrod.apiuserregister.strategy.formation.FormationStrategy;
import com.devpedrod.apiuserregister.strategy.user.UserDisableStrategy;
import com.devpedrod.apiuserregister.strategy.user.UserStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public abstract class AbstractFacade {
    protected Map<String, GenericDAO> daos = new HashMap<>();
    protected  Map<String, Map<String, List<IStrategy>>> businessRule = new HashMap<>();

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserStrategy userStrategy;
    @Autowired
    private UserDisableStrategy userDisableStrategy;

    @Autowired
    private AddressDAO addressDAO;
    @Autowired
    private AddressStrategy addressStrategy;
    @Autowired
    private DeleteAddressStrategy deleteAddressStrategy;

    @Autowired
    private FormationDAO formationDAO;
    @Autowired
    private FormationStrategy formationStrategy;
    @Autowired
    private DisableFormationStrategy disableFormationStrategy;


    public void initialize(){
        //----------------------- User --------------------------//
        daos.put(User.class.getName(), userDAO);

        List<IStrategy> rulesUser = new ArrayList<>(Collections.singleton(userStrategy));
        List<IStrategy> rulesDisableUser = new ArrayList<>(Collections.singleton(userDisableStrategy));

        Map<String,List<IStrategy>> mapKeyUser = new HashMap<>();
        mapKeyUser.put("SAVE", rulesUser);
        mapKeyUser.put("DISABLE", rulesDisableUser);

        businessRule.put(User.class.getName(), mapKeyUser);

        //----------------------- Address --------------------------//
        daos.put(Address.class.getName(), addressDAO);

        List<IStrategy> rulesAddress = new ArrayList<>(Collections.singleton(addressStrategy));
        List<IStrategy> rulesDeleteAddress = new ArrayList<>(Collections.singleton(deleteAddressStrategy));

        Map<String,List<IStrategy>> mapKeyAddress = new HashMap<>();
        mapKeyAddress.put("SAVE", rulesAddress);
        mapKeyAddress.put("DELETE", rulesDeleteAddress);

        businessRule.put(Address.class.getName(), mapKeyAddress);

        //----------------------- Formation --------------------------//
        daos.put(Formation.class.getName(), formationDAO);

        List<IStrategy> rulesFormations = new ArrayList<>(Collections.singleton(formationStrategy));
        List<IStrategy> rulesDisableFormation = new ArrayList<>(Collections.singleton(disableFormationStrategy));

        Map<String,List<IStrategy>> mapKeyFormation = new HashMap<>();
        mapKeyFormation.put("SAVE", rulesFormations);
        mapKeyFormation.put("DISABLE", rulesDisableFormation);

        businessRule.put(Formation.class.getName(), mapKeyFormation);

    }

}

