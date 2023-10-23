package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "username";
    private static final String VALID_PASSWORD = "123456";
    private static final int VALID_AGE = 18;
    private static final String INVALID_LOGIN = "name";
    private static final String INVALID_PASSWORD = "1234";
    private static final int INVALID_AGE = 16;
    private static final String EMPTY_VALUE = "";

    private static RegistrationService registrationService;
    private static StorageDao storage;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
        user = new User();
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerNullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void registerExistingUser_notOk() {
        storage.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerInvalidPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(EMPTY_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerInvalidLogin_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(EMPTY_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerInvalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerValidUser_Ok() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        storage.add(user);
        assertEquals(user, storage.get(user.getLogin()));
    }
}
