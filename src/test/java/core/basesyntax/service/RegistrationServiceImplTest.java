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
    private User user;

    @BeforeEach
     void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void userIsNull_notOk() {
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_duplicateLogin_notOk() {
        user.setLogin("login1414");
        user.setPassword("password1414");
        user.setAge(25);
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin("login1414");
        newUser.setPassword("password1414");
        newUser.setAge(25);
        RegistrationValidationException exception
                = assertThrows(RegistrationValidationException.class, () -> {
                    registrationService.register(newUser);
                });
        assertEquals("User with this login already exists", exception.getMessage());
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setLogin("logi");
        user.setPassword("password2024");
        user.setAge(32);
        RegistrationValidationException exception
                = assertThrows(RegistrationValidationException.class, () -> {
                    registrationService.register(user);
                });
        assertEquals("Login must be at least 6 characters long", exception.getMessage());
    }

    @Test
    void register_invalidPassword_notOk() {
        user.setLogin("validLogin");
        user.setPassword("pass");
        user.setAge(22);
        RegistrationValidationException exception
                = assertThrows(RegistrationValidationException.class, () -> {
                    registrationService.register(user);
                });
        assertEquals("Password must be at least 6 characters long", exception.getMessage());
    }

    @Test
    void register_underageUser_notOk() {
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);
        RegistrationValidationException exception
                = assertThrows(RegistrationValidationException.class, () -> {
                    registrationService.register(user);
                });
        assertEquals("Age must be at least 18 years old", exception.getMessage());
    }

    @Test
    void register_validUser_ok() {
        user.setLogin("validLogin1");
        user.setPassword("validPassword1");
        user.setAge(21);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
