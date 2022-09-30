package com.devpedrod.apiuserregister.dao.impl;

import com.devpedrod.apiuserregister.domain.Permission;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.domain.enums.Status;
import com.devpedrod.apiuserregister.exceptions.ObjectNotFoundException;
import com.devpedrod.apiuserregister.repositories.PermissionRepository;
import com.devpedrod.apiuserregister.strategy.permission.PermissionStrategy;
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
import java.util.Set;

public class PermissionDAOTest {

    @InjectMocks
    private PermissionDAO permissionDAO;
    @InjectMocks
    private PermissionStrategy permissionStrategy;
    @Mock
    private PermissionRepository permissionRepository;

    private Permission permission;
    private Optional<Permission> optionalPermission;
    private User user;

    // Exceptions
    public static final long ID_FOR_EXCEPTIONS = 2L;
    public static final String OBJECT_NOT_FOUND_MSG = "Objeto não encontrado, id: " + ID_FOR_EXCEPTIONS;

    // Permission parameters
    public static final Long ID = 1L;
    public static final String NAME = "   Administrador ";
    public static final String PERMISSION = "    CRUD    ";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startEntities();
    }

    @Test
    void whenGetByIdThenReturnAnPermissionInstance() {
        Mockito.when(permissionRepository.findById(ID)).thenReturn(optionalPermission);
        Permission permissionResponse = permissionDAO.getById(ID);

        Assertions.assertNotNull(permissionResponse);
        Assertions.assertEquals(Permission.class, permissionResponse.getClass());
        Assertions.assertEquals(ID, permissionResponse.getId());
        Assertions.assertEquals(NAME, permissionResponse.getName());
        Assertions.assertEquals(PERMISSION, permissionResponse.getPermission());
        Assertions.assertEquals(user, permissionResponse.getUsers().stream().toList().get(0));
    }

    @Test
    void whenGetByIdThenReturnAnObjectNotFoundException() {
        Mockito.when(permissionRepository.findById(ID_FOR_EXCEPTIONS))
                .thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND_MSG));
        try {
            permissionDAO.getById(ID_FOR_EXCEPTIONS);
        } catch (Exception e){
            Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
            Assertions.assertEquals(OBJECT_NOT_FOUND_MSG, e.getMessage());
        }
    }

    @Test
    void whenGetAllThenReturnAnListOfPermission(){
        Mockito.when(permissionRepository.findAll(PageRequest.of(1,1)))
                .thenReturn(new PageImpl<>(List.of(permission)));

        Page<Permission> permissionPage = permissionDAO.getAll(PageRequest.of(1,1));

        Assertions.assertNotNull(permissionPage);
        Assertions.assertEquals(1, permissionPage.getTotalElements());
        Assertions.assertEquals(permission, permissionPage.toList().get(0));
    }

    @Test
    void whenCreateThenReturnSuccess() {
        Mockito.when(permissionRepository.save(permission)).thenReturn(permission);

        user.setPermissions(Set.of(permission));
        permissionDAO.save(permission);

        Mockito.verify(permissionRepository, Mockito.times(1)).save(permission);
        Assertions.assertNotEquals(permission.getCreatedAt(), null);
        Assertions.assertEquals(permission.getUsers().stream().toList().get(0), user);
        Assertions.assertTrue(permission.getUsers().stream().toList().get(0).getPermissions().contains(permission));
    }

    @Test
    void whenCreateWithStrategyThenReturnSuccess() {
        Mockito.when(permissionRepository.save(permission)).thenReturn(permission);

        user.setPermissions(Set.of(permission));
        permissionDAO.save(permission, obj -> {
            permissionStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(permissionRepository, Mockito.times(1)).save(permission);
        Assertions.assertNotEquals(permission.getCreatedAt(), null);
        Assertions.assertTrue(permission.getUsers().contains(user));
        Assertions.assertTrue(permission.getUsers().stream().toList().get(0).getPermissions().contains(permission));
        Assertions.assertEquals(permission.getName(),NAME.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(permission.getPermission(), PERMISSION.replaceAll("\\s+"," ").trim());
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        Mockito.when(permissionRepository.saveAndFlush(permission)).thenReturn(permission);

        permissionDAO.update(permission);

        Mockito.verify(permissionRepository, Mockito.times(1)).saveAndFlush(permission);
        Assertions.assertNotEquals(permission.getUpdatedAt(), null);
        Assertions.assertTrue(permission.getUpdatedAt().isAfter(permission.getCreatedAt()));
    }

    @Test
    void whenUpdateWithStrategyThenReturnSuccess() {
        Mockito.when(permissionRepository.saveAndFlush(permission)).thenReturn(permission);

        user.setPermissions(Set.of(permission));

        permissionDAO.update(permission, obj -> {
            permissionStrategy.applyBusinessRule(obj);
            return obj;
        });

        Mockito.verify(permissionRepository, Mockito.times(1)).saveAndFlush(permission);
        Assertions.assertNotEquals(permission.getCreatedAt(), null);
        Assertions.assertTrue(permission.getUsers().contains(user));
        Assertions.assertTrue(permission.getUsers().stream().toList().get(0).getPermissions().contains(permission));
        Assertions.assertEquals(permission.getName(), NAME.replaceAll("\\s+"," ").trim());
        Assertions.assertEquals(permission.getPermission(), PERMISSION.replaceAll("\\s+"," ").trim());
    }

    @Test
    void deleteWithSuccess(){
        Mockito.when(permissionRepository.findById(ID)).thenReturn(optionalPermission);
        Mockito.doNothing().when(permissionRepository).delete(permission);

        permissionDAO.delete(ID);

        Mockito.verify(permissionRepository, Mockito.times(1)).delete(permission);
    }

    @Test
    void deleteWithStrategyWithSuccess(){
        Mockito.when(permissionRepository.findById(ID)).thenReturn(optionalPermission);
        Mockito.doNothing().when(permissionRepository).delete(permission);

        permissionDAO.delete(ID, obj -> {
            permission.setUsers(null);
            return obj;
        });

        Assertions.assertNull(permission.getUsers());
        Mockito.verify(permissionRepository, Mockito.times(1)).delete(permission);
    }

    @Test
    void disableWithSuccess(){
        Mockito.when(permissionRepository.findById(ID)).thenReturn(optionalPermission);
        Mockito.when(permissionRepository.save(permission)).thenReturn(permission);

        permissionDAO.disable(ID);

        Assertions.assertNotNull(permission.getDisabledAt());
        Assertions.assertTrue(permission.getDisabledAt().isAfter(permission.getCreatedAt()));

        Mockito.verify(permissionRepository, Mockito.times(1)).save(permission);
    }

    @Test
    void disableWithStrategyWithSuccess(){
        Mockito.when(permissionRepository.findById(ID)).thenReturn(optionalPermission);
        Mockito.when(permissionRepository.save(permission)).thenReturn(permission);

        permissionDAO.disable(ID, obj -> obj
        );
        Assertions.assertNotNull(permission.getDisabledAt());
        Assertions.assertTrue(permission.getDisabledAt().isAfter(permission.getCreatedAt()));

        Mockito.verify(permissionRepository, Mockito.times(1)).save(permission);
    }

    private void startEntities() {
        user = new User("João", "492.776.840-62", null, null, Status.ACTIVE, null);
        permission = new Permission(NAME, PERMISSION, Set.of(user));
        permission.setId(ID);
        optionalPermission = Optional.of(permission);
    }
}
