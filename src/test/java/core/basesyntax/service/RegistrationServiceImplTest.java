package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("log");
        user.setPassword("validPass");
        user.setAge(22);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("user's login is at least 6 characters", exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin2");
        user.setPassword("123");
        user.setAge(23);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("user's password is at least 6 characters", exception.getMessage());
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validLogin4");
        user.setPassword("valPassword");
        user.setAge(17);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("user's age is at least 18 years old", exception.getMessage());
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user = new User();
        user.setLogin("validLog");
        user.setPassword("validPass");
        user.setAge(22);

        User user2 = new User();
        user2.setLogin("validLog");
        user2.setPassword("valPass");
        user2.setAge(22);
        registrationService.register(user2);

        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Login is already taken", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("valPassword");
        user.setAge(23);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Login cannot be empty", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(23);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Password cannot be empty", exception.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("valPassword");
        user.setAge(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        assertEquals("Age cannot be empty", exception.getMessage());
    }
}
