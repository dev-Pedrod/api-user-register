package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.repositories.AddressRepository;
import com.devpedrod.apiuserregister.strategy.address.AddressStrategy;
import com.devpedrod.apiuserregister.strategy.address.DeleteAddressStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

class AddressDAOTest {

    @InjectMocks
    private AddressDAO addressDAO;
    @InjectMocks
    private AddressStrategy addressStrategy;
    @InjectMocks
    private DeleteAddressStrategy deleteAddressStrategy;
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
    public static final String STREET = "Rua 3  ";
    public static final Integer NUMBER = 1;
    public static final String CITY = " Belo   Horizonte";
    public static final String NEIGHBORHOOD = "  Pampulha  ";
    public static final String COUNTRY = "Brasil   ";

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

    @Test
    void whenGetAllThenReturnAnListOfAddress(){
        Mockito.when(addressRepository.findAll(PageRequest.of(1,1)))
                .thenReturn(new PageImpl<>(List.of(address)));

        Page<Address> addressPage = addressDAO.getAll(PageRequest.of(1,1));

        Assertions.assertNotNull(addressPage);
        Assertions.assertEquals(1, addressPage.getTotalElements());
        Assertions.assertEquals(address, addressPage.toList().get(0));
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(addressRepository.save(address)).thenReturn(address);
        addressDAO.save(address);

        Mockito.verify(addressRepository, Mockito.times(1)).save(address);
        Assertions.assertNotEquals(address.getCreatedAt(), null);
    }

    @Test
    void whenCreateWithStrategyThenReturnSuccess() {
        Mockito.when(addressRepository.save(address)).thenReturn(address);

        addressDAO.save(address, obj -> {
            addressStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(addressRepository, Mockito.times(1)).save(address);
        Assertions.assertNotEquals(address.getCreatedAt(), null);
        Assertions.assertEquals(address.getUser(), user);
        Assertions.assertEquals(address.getUser().getAddress(), address);

        Assertions.assertEquals(address.getStreet(), STREET.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(address.getCity(), CITY.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(address.getCountry(), COUNTRY.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(address.getNeighborhood(), NEIGHBORHOOD.replaceAll("\\s+"," ").trim());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(addressRepository.saveAndFlush(address)).thenReturn(address);
        addressDAO.update(address);

        Mockito.verify(addressRepository, Mockito.times(1)).saveAndFlush(address);
        Assertions.assertNotEquals(address.getUpdatedAt(), null);
        Assertions.assertNotEquals(address.getUpdatedAt(), address.getCreatedAt());
    }

    @Test
    void whenUpdateWithStrategyThenReturnSuccess() {
        Mockito.when(addressRepository.saveAndFlush(address)).thenReturn(address);

        addressDAO.update(address, obj -> {
            addressStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(addressRepository, Mockito.times(1)).saveAndFlush(address);
        Assertions.assertEquals(address.getUser(), user);
        Assertions.assertEquals(address.getUser().getAddress(), address);
        Assertions.assertNotEquals(address.getUpdatedAt(), null);
        Assertions.assertNotEquals(address.getUpdatedAt(), address.getCreatedAt());
        Assertions.assertTrue(address.getUpdatedAt().isAfter(address.getCreatedAt()));

        Assertions.assertEquals(address.getStreet(), STREET.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(address.getCity(), CITY.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(address.getCountry(), COUNTRY.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(address.getNeighborhood(), NEIGHBORHOOD.replaceAll("\\s+"," ").trim());
    }

    @Test
    void deleteWithSuccess(){
        Mockito.when(addressRepository.findById(ID)).thenReturn(optionalAddress);
        Mockito.doNothing().when(addressRepository).delete(address);

        addressDAO.delete(ID);

        Mockito.verify(addressRepository, Mockito.times(1)).delete(address);
    }

    @Test
    void deleteWithStrategyWithSuccess(){
        Mockito.when(addressRepository.findById(ID)).thenReturn(optionalAddress);
        Mockito.doNothing().when(addressRepository).delete(address);

        user.setAddress(address);

        addressDAO.delete(ID, obj -> {
            deleteAddressStrategy.applyBusinessRule(obj);
            return obj;
        });

        Assertions.assertNull(address.getUser());
        Assertions.assertNull(user.getAddress());
        Mockito.verify(addressRepository, Mockito.times(1)).delete(address);
    }

    @Test
    void disableWithSuccess(){
        Mockito.when(addressRepository.findById(ID)).thenReturn(optionalAddress);
        Mockito.when(addressRepository.save(address)).thenReturn(address);

        addressDAO.disable(ID);

        Assertions.assertNotNull(address.getDisabledAt());
        Assertions.assertTrue(address.getDisabledAt().isAfter(address.getCreatedAt()));

        Mockito.verify(addressRepository, Mockito.times(1)).save(address);
    }

    @Test
    void disableWithStrategyWithSuccess(){
        Mockito.when(addressRepository.findById(ID)).thenReturn(optionalAddress);
        Mockito.when(addressRepository.save(address)).thenReturn(address);

        user.setAddress(address);

        addressDAO.disable(ID, obj -> {
            obj.getUser().setAddress(null);
            return obj;
        });

        Assertions.assertNotNull(address.getDisabledAt());
        Assertions.assertTrue(address.getDisabledAt().isAfter(address.getCreatedAt()));
        Assertions.assertNull(user.getAddress());

        Mockito.verify(addressRepository, Mockito.times(1)).save(address);
    }

    private void startEntities() {
        user = new User("João", "492.776.840-62", null, null, Status.ACTIVE, null);
        address = new Address(STREET, NUMBER, CITY, NEIGHBORHOOD, COUNTRY, user);
        address.setId(ID);
        optionalAddress = Optional.of(address);
    }
}