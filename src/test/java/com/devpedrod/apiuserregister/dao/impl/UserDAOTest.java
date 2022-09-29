package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.Permission;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.repositories.UserRepository;
import com.devpedrod.apiuserregister.strategy.user.UserDisableStrategy;
import com.devpedrod.apiuserregister.strategy.user.UserStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;
import java.util.Set;

class UserDAOTest {

    @InjectMocks
    private UserDAO userDAO;
    @InjectMocks
    private UserDisableStrategy userDisableStrategy;
    @InjectMocks
    private UserStrategy userStrategy;
    @Mock
    private UserRepository userRepository;

    private Optional<User> optionalUser;
    private User user;
    private Formation formation;
    private Address address;
    private Permission permission;

    // Exceptions
    public static final long ID_FOR_EXCEPTIONS = 2L;
    public static final String OBJECT_NOT_FOUND_MSG = "Objeto não encontrado, id: " + ID_FOR_EXCEPTIONS;
    public static final String DATA_INTEGRITY_MESSAGE = "Este CPF já está cadastrado";

    // User parameters
    public static final Long ID = 1L;
    public static final String NAME = "  João   ";
    public static final String CPF = "492.776.840-62";
    public static final Status STATUS_ACTIVE = Status.ACTIVE;

    // Formation parameters
    public static final Long FORMATION_ID = 1L;
    public static final String FORMATION_NAME = "Ciência da computação";
    public static final String FORMATION_INSTITUITION = "UFMG";

    // Address parameters
    public static final Long ADDRESS_ID = 1L;
    public static final String STREET = "Rua 3";
    public static final Integer NUMBER = 1;
    public static final String CITY = " Belo Horizonte";
    public static final String NEIGHBORHOOD = "Pampulha";
    public static final String COUNTRY = "Brasil";

    // Permission parameters
    public static final Long PERMISSION_ID = 1L;
    public static final String PERMISSION_NAME = "Administrador";
    public static final String PERMISSION = "CRUD";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEntities();
    }
    

    private void startEntities() {
        formation = new Formation(FORMATION_NAME, FORMATION_INSTITUITION, user);
        address = new Address(STREET, NUMBER, CITY, NEIGHBORHOOD, COUNTRY, user);
        permission = new Permission(PERMISSION_NAME, PERMISSION, (Set<User>) user);
        user = new User(PERMISSION_NAME, CPF, List.of(formation), address, STATUS_ACTIVE, Set.of(permission));

        user.setId(ID);
        formation.setId(FORMATION_ID);
        address.setId(ADDRESS_ID);
        permission.setId(PERMISSION_ID);

        optionalUser = Optional.of(user);
    }
}