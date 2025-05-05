package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createUser(null, "password", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createUser("short", "password", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user = createUser("duplicateLogin", "password", 25);
        registrationService.register(user);

        User duplicate = createUser("duplicateLogin", "anotherPassword", 23);

        assertThrows(RegistrationException.class, () -> registrationService.register(duplicate));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createUser("validLogin", null, 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createUser("validLogin", "short", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underAge_notOK() {
        User user = createUser("validLogin", "password", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        User validUser = createUser("validUser", "password", 25);

        assertDoesNotThrow(() -> registrationService.register(validUser));
        assertNotNull(storageDao.get(validUser.getLogin()));
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
