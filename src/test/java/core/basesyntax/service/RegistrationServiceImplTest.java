package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;
    private User user2;
    private User user3;
    private User user4;
    private RuntimeException thrown;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Jac");
        user.setAge(19);
        user.setPassword("1245787");
        user2 = new User();
        user2.setLogin("serhii");
        user2.setAge(17);
        user2.setPassword("1245787");
        user3 = new User();
        user3.setLogin("Kat");
        user3.setAge(18);
        user3.setPassword("1245787");
        user4 = new User();
        user4.setLogin("Daniel");
        user4.setAge(22);
        user4.setPassword("1245787");

    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login can't be null", thrown.getMessage());
    }

    @Test
    void register_18Age_itsOk() {
        boolean actual = registrationService.register(user3).equals(user3);
        assertTrue(actual);
    }

    @Test
    void register_moreAge_itsOk() {
        boolean actual = registrationService.register(user).equals(user);
        assertTrue(actual);
    }

    @Test
    void register_minAge_itsOk() {
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user2);
        });
        assertEquals("Login cannot be less than 18", thrown.getMessage());
    }

    @Test
    void register_negativeAge_itsOk() {
        user.setAge(-1);
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login cannot be less than 18", thrown.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login can't be null", thrown.getMessage());
    }

    @Test
    void register_repeatedLogin_notOk() {
        user2.setAge(18);
        storageDao.add(user2);
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user2);
        });
        assertEquals("there is user with such login in the Storage", thrown.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        user4.setPassword(null);
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user4);
        });
        assertEquals("Password can't be null", thrown.getMessage());
    }

    @Test
    void register_minPassword_notOk() {
        user4.setPassword("1425");
        thrown = assertThrows(RegistrationExceptions.class, () -> {
            registrationService.register(user4);
        });
        assertEquals("Password must be at least six characters long", thrown.getMessage());
    }
}
