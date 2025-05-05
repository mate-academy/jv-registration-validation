package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login23";
    private static final Integer DEFAULT_AGE = 35;
    private static final String DEFAULT_PASSWORD = "password23";
    private static final Integer MIN_AGE = 18;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_ok() {
        User registeredUser = registrationService.register(user);
        assertSame(storageDao.get(DEFAULT_LOGIN), registeredUser);
        assertNotEquals(null, registeredUser.getId());
    }

    @Test
    void register_minAge_ok() {
        user.setAge(MIN_AGE);
        User registeredUser = registrationService.register(user);
        assertSame(storageDao.get(DEFAULT_LOGIN), registeredUser);
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLessMinLength_notOk() {
        user.setPassword("12345");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
