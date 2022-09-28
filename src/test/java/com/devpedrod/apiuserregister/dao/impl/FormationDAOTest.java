package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.repositories.FormationRepository;
import com.devpedrod.apiuserregister.strategy.formation.DisableFormationStrategy;
import com.devpedrod.apiuserregister.strategy.formation.FormationStrategy;
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

import java.time.LocalDateTime;
import java.util.List;
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

    // Formation parameters
    public static final Long ID = 1L;
    public static final String NAME = "   Ciência  da  computação    ";
    public static final String INSTITUITION = "    UFMG    ";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEntities();
    }

    @Test
    void whenGetByIdThenReturnAnFormationInstance() {
        Mockito.when(formationRepository.findById(ID)).thenReturn(optionalFormation);
        Formation formationResponse = formationDAO.getById(ID);

        Assertions.assertNotNull(formationResponse);
        Assertions.assertEquals(Formation.class, formationResponse.getClass());
        Assertions.assertEquals(ID, formationResponse.getId());
        Assertions.assertEquals(NAME, formationResponse.getName());
        Assertions.assertEquals(INSTITUITION, formationResponse.getInstitution());
        Assertions.assertEquals(user, formationResponse.getUser());
    }

    @Test
    void whenGetByIdThenReturnAnObjectNotFoundException() {
        Mockito.when(formationRepository.findById(ID_FOR_EXCEPTIONS))
                .thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND_MSG));
        try {
            formationDAO.getById(ID_FOR_EXCEPTIONS);
        } catch (Exception e){
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals(OBJECT_NOT_FOUND_MSG, e.getMessage());
        }
    }

    @Test
    void whenGetAllThenReturnAnListOfFormation(){
        Mockito.when(formationRepository.findAll(PageRequest.of(1,1)))
                .thenReturn(new PageImpl<>(List.of(formation)));

        Page<Formation> formationPage = formationDAO.getAll(PageRequest.of(1,1));

        Assertions.assertNotNull(formationPage);
        Assertions.assertEquals(1, formationPage.getTotalElements());
        Assertions.assertEquals(formation, formationPage.toList().get(0));
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(formationRepository.save(formation)).thenReturn(formation);

        user.setFormations(List.of(formation));
        formationDAO.save(formation);

        Mockito.verify(formationRepository, Mockito.times(1)).save(formation);
        Assertions.assertNotEquals(formation.getCreatedAt(), null);
        Assertions.assertEquals(formation.getUser(), user);
        Assertions.assertEquals(formation.getUser().getFormations().get(0), formation);
    }

    @Test
    void whenCreateWithStrategyThenReturnSuccess() {
        Mockito.when(formationRepository.save(formation)).thenReturn(formation);

        user.setFormations(List.of(formation));
        formationDAO.save(formation, obj -> {
            formationStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(formationRepository, Mockito.times(1)).save(formation);
        Assertions.assertNotEquals(formation.getCreatedAt(), null);
        Assertions.assertEquals(formation.getUser(), user);
        Assertions.assertEquals(formation.getUser().getFormations().get(0), formation);
        Assertions.assertEquals(formation.getName(), NAME.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(formation.getInstitution(), INSTITUITION.replaceAll("\\s+"," ").trim());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(formationRepository.saveAndFlush(formation)).thenReturn(formation);

        formationDAO.update(formation);

        Mockito.verify(formationRepository, Mockito.times(1)).saveAndFlush(formation);
        Assertions.assertNotEquals(formation.getUpdatedAt(), null);
        Assertions.assertTrue(formation.getUpdatedAt().isAfter(formation.getCreatedAt()));
    }

    @Test
    void whenUpdateWithStrategyThenReturnSuccess() {
        Mockito.when(formationRepository.saveAndFlush(formation)).thenReturn(formation);

        user.setFormations(List.of(formation));

        formationDAO.update(formation, obj -> {
            formationStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(formationRepository, Mockito.times(1)).saveAndFlush(formation);
        Assertions.assertNotEquals(formation.getCreatedAt(), null);
        Assertions.assertEquals(formation.getUser(), user);
        Assertions.assertEquals(formation.getUser().getFormations().get(0), formation);
        Assertions.assertEquals(formation.getName(), NAME.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(formation.getInstitution(), INSTITUITION.replaceAll("\\s+"," ").trim());
    }

    @Test
    void deleteWithSuccess(){
        Mockito.when(formationRepository.findById(ID)).thenReturn(optionalFormation);
        Mockito.doNothing().when(formationRepository).delete(formation);

        formationDAO.delete(ID);

        Mockito.verify(formationRepository, Mockito.times(1)).delete(formation);
    }

    @Test
    void deleteWithStrategyWithSuccess(){
        Mockito.when(formationRepository.findById(ID)).thenReturn(optionalFormation);
        Mockito.doNothing().when(formationRepository).delete(formation);

        formationDAO.delete(ID, obj -> {
            formation.setUser(null);
            return obj;
        });

        Assertions.assertNull(formation.getUser());
        Mockito.verify(formationRepository, Mockito.times(1)).delete(formation);
    }

    @Test
    void disableWithSuccess(){
        Mockito.when(formationRepository.findById(ID)).thenReturn(optionalFormation);
        Mockito.when(formationRepository.save(formation)).thenReturn(formation);

        formationDAO.disable(ID);

        Assertions.assertNotNull(formation.getDisabledAt());
        Assertions.assertTrue(formation.getDisabledAt().isAfter(formation.getCreatedAt()));

        Mockito.verify(formationRepository, Mockito.times(1)).save(formation);
    }

    @Test
    void disableWithStrategyWithSuccess(){
        Mockito.when(formationRepository.findById(ID)).thenReturn(optionalFormation);
        Mockito.when(formationRepository.save(formation)).thenReturn(formation);

        user.setFormations(List.of(formation));

        formationDAO.disable(ID, obj -> {
            disableFormationStrategy.applyBusinessRule(obj);
            return obj;
        });
        Assertions.assertNotNull(formation.getDisabledAt());
        Assertions.assertTrue(formation.getDisabledAt().isAfter(formation.getCreatedAt()));

        Mockito.verify(formationRepository, Mockito.times(1)).save(formation);
    }

    private void startEntities() {
        user = new User("João", "492.776.840-62", null, null, Status.ACTIVE, null);
        formation = new Formation(NAME, INSTITUITION, user);
        formation.setId(ID);
        formation.setCreatedAt(LocalDateTime.now());
        optionalFormation = Optional.of(formation);
    }
}
