package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.Permission;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.dto.response.FieldMessage;
import com.devpedrod.apiuserregister.exceptions.DataIntegrityException;
import com.devpedrod.apiuserregister.exceptions.MethodArgumentNotValidException;
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
    public static final MethodArgumentNotValidException METHOD_ARGUMENT_NOT_VALID_EXCEPTION = new MethodArgumentNotValidException(List.of(new FieldMessage("CPF","CPF ínvalido")));

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

    @Test
    void whenGetByIdThenReturnAnAddressInstance() {
        Mockito.when(userRepository.findById(ID)).thenReturn(optionalUser);
        User userResponse = userDAO.getById(ID);

        Assertions.assertNotNull(userResponse);
        Assertions.assertEquals(User.class, userResponse.getClass());
        Assertions.assertEquals(ID, userResponse.getId());
        Assertions.assertEquals(NAME, userResponse.getName());
        Assertions.assertEquals(CPF, userResponse.getCpf());
        Assertions.assertEquals(STATUS_ACTIVE, userResponse.getStatus());

        Assertions.assertEquals(userResponse.getAddress(), address);
        Assertions.assertEquals(userResponse.getFormations().get(0), formation);
        Assertions.assertTrue(userResponse.getPermissions().contains(permission));
    }

    @Test
    void whenGetByIdThenReturnAnObjectNotFoundException() {
        Mockito.when(userRepository.findById(ID_FOR_EXCEPTIONS))
                .thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND_MSG));
        try {
            userDAO.getById(ID_FOR_EXCEPTIONS);
        } catch (Exception e){
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals(OBJECT_NOT_FOUND_MSG, e.getMessage());
        }
    }

    @Test
    void whenGetAllThenReturnAnListOfAddress(){
        Mockito.when(userRepository.findAll(PageRequest.of(1,1)))
                .thenReturn(new PageImpl<>(List.of(user)));

        Page<User> userPage = userDAO.getAll(PageRequest.of(1,1));

        Assertions.assertNotNull(userPage);
        Assertions.assertEquals(1, userPage.getTotalElements());
        Assertions.assertEquals(user, userPage.toList().get(0));
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(userRepository.save(user)).thenReturn(user);
        userDAO.save(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        Assertions.assertNotEquals(user.getCreatedAt(), null);
    }

    @Test
    void whenCreateThenReturnADataIntegrityException() {
        User userForException = new User(NAME, CPF, null, null, null, null);

        Mockito.when(userRepository.findByCpf(CPF)).thenReturn(optionalUser);
        Mockito.when(userRepository.save(user)).thenReturn(userForException);

        userDAO.save(user);

        try {
            userDAO.save(userForException, obj -> {
                userStrategy.applyBusinessRule(obj);
                return obj;
            });
        } catch (Exception e){
            Assertions.assertEquals(DataIntegrityException.class, e.getClass());
            Assertions.assertEquals(DATA_INTEGRITY_MESSAGE, e.getMessage());
        }
    }

    @Test
    void whenCreateThenReturnAMethodArgumentNotValidException() {
        User userForException = new User(NAME, "11111111", null, null, null, null);
        Mockito.when(userRepository.save(user)).thenReturn(userForException);

        try {
            userDAO.save(userForException, obj -> {
                userStrategy.applyBusinessRule(obj);
                return obj;
            });
        } catch (MethodArgumentNotValidException e){
            Assertions.assertEquals(MethodArgumentNotValidException.class, e.getClass());
            Assertions.assertEquals(METHOD_ARGUMENT_NOT_VALID_EXCEPTION.getMessages().get(0), e.getMessages().get(0));
        }
    }

    @Test
    void whenCreateWithStrategyThenReturnSuccess() {
        Mockito.when(userRepository.save(user)).thenReturn(user);

        userDAO.save(user, obj -> {
            userStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        
        Assertions.assertNotEquals(user.getCreatedAt(), null);
        Assertions.assertEquals(user.getName(), NAME.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(user.getCpf(), CPF.trim().replaceAll("[.-]", ""));
        Assertions.assertEquals(user.getAddress(), address);
        Assertions.assertEquals(user.getFormations().get(0), formation);
        Assertions.assertTrue(user.getPermissions().contains(permission));
    }

    private void startEntities() {
        formation = new Formation(FORMATION_NAME, FORMATION_INSTITUITION, user);
        address = new Address(STREET, NUMBER, CITY, NEIGHBORHOOD, COUNTRY, user);
        permission = new Permission(PERMISSION_NAME, PERMISSION, (Set<User>) user);
        user = new User(NAME, CPF, List.of(formation), address, STATUS_ACTIVE, Set.of(permission));

        user.setId(ID);
        formation.setId(FORMATION_ID);
        address.setId(ADDRESS_ID);
        permission.setId(PERMISSION_ID);

        optionalUser = Optional.of(user);
    }
}