package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class AddressDAOTest {

    @InjectMocks
    private AddressDAO addressDAO;
    @Mock
    private AddressRepository addressRepository;

    private Address address;
    private Optional<Address> optionalAddress;
    private User user;

    // Exceptions
    public static final long ID_FOR_EXCEPTIONS = 2L;
    public static final String OBJECT_NOT_FOUND_MSG = "Objeto não encontrado, id: " + ID_FOR_EXCEPTIONS;

    // Address parameters
    public static final Long ID = 1L;
    public static final String STREET = "Rua 3";
    public static final Integer NUMBER = 1;
    public static final String CITY = "Belo Horizonte";
    public static final String NEIGHBORHOOD = "Pampulha";
    public static final String COUNTRY = "Brasil";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEntities();
    }

    @Test
    void whenGetByIdThenReturnAnAddressInstance() {
        Mockito.when(addressRepository.findById(ID)).thenReturn(optionalAddress);
        Address addressResponse = addressDAO.getById(ID);

        Assertions.assertNotNull(addressResponse);
        Assertions.assertEquals(Address.class, addressResponse.getClass());
        Assertions.assertEquals(ID, addressResponse.getId());
        Assertions.assertEquals(STREET, addressResponse.getStreet());
        Assertions.assertEquals(NUMBER, addressResponse.getNumber());
        Assertions.assertEquals(CITY, addressResponse.getCity());
        Assertions.assertEquals(NEIGHBORHOOD, addressResponse.getNeighborhood());
        Assertions.assertEquals(COUNTRY, addressResponse.getCountry());
        Assertions.assertEquals(user, addressResponse.getUser());
    }

    @Test
    void whenGetByIdThenReturnAnObjectNotFoundException() {
        Mockito.when(addressRepository.findById(ID_FOR_EXCEPTIONS))
                .thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND_MSG));
        try {
            addressDAO.getById(ID_FOR_EXCEPTIONS);
        } catch (Exception e){
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals(OBJECT_NOT_FOUND_MSG, e.getMessage());
        }
    }

    private void startEntities() {
        user = new User("João", "492.776.840-62", null, address, Status.ACTIVE, null);
        address = new Address(STREET, NUMBER, CITY, NEIGHBORHOOD, COUNTRY, user);
        address.setId(ID);
        optionalAddress = Optional.of(address);
    }
}