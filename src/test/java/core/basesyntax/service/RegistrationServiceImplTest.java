package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "validlogin";
    private static final String VALID_PASSWORD = "validpassword";

    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertNotNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    void register_nullUser_NotOk() {
        checkUser(null);
    }

    @Test
    void register_shortNameUser_NotOk() {
        User user = new User("short", VALID_PASSWORD, VALID_AGE);
        checkUser(user);
    }

    @Test
    void register_shortPassword_NotOk() {
        User user = new User(VALID_LOGIN, "short", VALID_AGE);
        checkUser(user);
    }

    @Test
    void register_underageUser_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, 16);
        checkUser(user);
    }

    @Test
    void register_existingUser_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        registrationService.register(user);
        User user1 = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        checkUser(user1);
    }

    @Test
    void register_nullLogin_NotOk() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        checkUser(user);
    }

    @Test
    void register_nullPassword_NotOk() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        checkUser(user);
    }

    @Test
    void register_nullAge_NotOk() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        checkUser(user);
    }

    private void checkUser(User user) {
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }
}
