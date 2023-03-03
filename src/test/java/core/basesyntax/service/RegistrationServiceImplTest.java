package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final User USER_DEFAULT_1 = new User(1L,"login1", "123456", 50);
    private static final User USER_DEFAULT_2 = new User(2L, "login2", "789012", 25);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(USER_DEFAULT_1);
        storageDao.add(USER_DEFAULT_2);
    }

    @Test
    void register_NotOk_userIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_NotOk_loginFieldIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User(null, "", 0)));
    }

    @Test
    void register_NotOk_passwordFieldIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("", null, 0)));
    }

    @Test
    void register_NotOk_ageFieldIsNull() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(new User("", "", null)));
    }

    @Test
    void register_NotOk_ageFieldLessMinAge() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(
                        new User("login", "123456", RegistrationServiceImpl.MIN_AGE - 1)));
    }

    @Test
    void register_NotOk_passwordFieldLengthLessMinPasswordLength() {
        String password = "1".repeat(RegistrationServiceImpl.MIN_LENGTH_PASSWORD - 1);
        User user = new User("", password, 25);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_NullOk_userInStorage() {
        assertNull(registrationService.register(USER_DEFAULT_1));
    }

    @Test
    void register_Ok_registerNewUser() {
        User user = new User("login3", "123456", 25);
        assertNotNull(registrationService.register(user));
        assertEquals(3, Storage.people.size());
        assertEquals(3, Storage.people.get(2).getId());
    }
}
