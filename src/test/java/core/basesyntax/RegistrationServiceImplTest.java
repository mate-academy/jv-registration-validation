package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser, "User should not be null");
        assertEquals("validUser", registeredUser.getLogin(), "Login should match");
    }

    @Test
    void register_InvalidLogin_exceptionThrown() {
        User user = new User();
        user.setLogin("abc");

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Expected RegistrationException to be thrown for short login");
    }

    @Test
    void register_InvalidPassword_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("123");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Expected RegistrationException to be thrown for short password");
    }

    @Test
    void register_InvalidAge_exceptionThrown() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePass");
        user.setAge(16);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                "Expected RegistrationException to be thrown for age < 18");
    }
}
