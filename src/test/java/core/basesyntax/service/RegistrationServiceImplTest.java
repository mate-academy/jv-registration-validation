package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
    void register_validUser_Ok() {
        User user = new User("validUser", "validPassword", 20);
        registrationService.register(user);
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("abc", "validPass", 20);
        try {
            registrationService.register(user);
        } catch (RegistrationException e) {
            return;
        }
        fail("Login must be at least 6 characters long");
    }

    @Test
    void register_userIs_notOk() {
        User user = new User("duplicateUser", "strongPassword", 20);
        User user1 = new User("duplicateUser", "strongPass", 25);
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
    }

    @Test
    void register_userPassword_notOk() {
        User user = new User("validUser", "str", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordNull_notOk() {
        User user = new User("validUser", null, 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginNull_notOk() {
        User user = new User(null, "validPassword", 20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAge_notOk() {
        User user = new User("validUser", "validPassword", 16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
