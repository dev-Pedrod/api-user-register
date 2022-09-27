package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.repositories.AddressRepository;
import com.devpedrod.apiuserregister.repositories.FormationRepository;
import com.devpedrod.apiuserregister.strategy.address.AddressStrategy;
import com.devpedrod.apiuserregister.strategy.address.DeleteAddressStrategy;
import com.devpedrod.apiuserregister.strategy.formation.DisableFormationStrategy;
import com.devpedrod.apiuserregister.strategy.formation.FormationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class FormationDAOTest {

    @InjectMocks
    private FormationDAO formationDAO;
    @InjectMocks
    private FormationStrategy formationStrategy;
    @InjectMocks
    private DisableFormationStrategy disableFormationStrategy;
    @Mock
    private FormationRepository formationRepository;

    private Formation formation;
    private Optional<Formation> optionalFormation;
    private User user;

    // Exceptions
    public static final long ID_FOR_EXCEPTIONS = 2L;
    public static final String OBJECT_NOT_FOUND_MSG = "Objeto não encontrado, id: " + ID_FOR_EXCEPTIONS;

    // Address parameters
    public static final Long ID = 1L;
    public static final String NAME = "Ciência da computação";
    public static final String INSTITUITION = "UFMG";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEntities();
    }

    

    private void startEntities() {
        user = new User("João", "492.776.840-62", null, null, Status.ACTIVE, null);
        formation = new Formation(NAME, INSTITUITION, user);
        formation.setId(ID);
        optionalFormation = Optional.of(formation);
    }

}
