package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);

        user = new User();
        user.setLogin("validLogin1");
        user.setPassword("1234567");
        user.setAge(20);
    }

    @Test
    void register_validUser_Ok() {
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void register_shortLogin_notOk() {
        user = new User();
        user.setLogin("valid");
        user.setPassword("12345678");
        user.setAge(20);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
                registrationService.register(user);
        });
        assertEquals("Login must be at least 6 character", exception.getMessage());
    }


    @Test
    void register_shortPassword_NotOk() {
        user = new User();
        user.setLogin("validLogin2");
        user.setPassword("123");
        user.setAge(20);

        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Your password must be at least 6 character", exception.getMessage());
    }

    @Test
    void register_exitingLogin_NotOk() {
        registrationService.register(user);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("This login is already used", exception.getMessage());
    }

    @Test
    void registered_nullLogin_NotOk() {
        user = new User();
        user.setLogin(null);
        user.setPassword("123456789");
        user.setAge(20);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login cannot be empty", exception.getMessage());
    }

    @Test
    void registered_nullPassword_NotOk() {
        user = new User();
        user.setLogin("validLogin3");
        user.setPassword(null);
        user.setAge(20);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void registered_nullAge_NotOk() {
        user = new User();
        user.setLogin("validLogin4");
        user.setPassword("12345567");
        user.setAge(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Age cannot be empty", exception.getMessage());
    }

    @Test
    void registered_age_NotOk() {
        user = new User();
        user.setLogin("validLogin5");
        user.setPassword("12344567");
        user.setAge(17);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("You are to young", exception.getMessage());
    }
}