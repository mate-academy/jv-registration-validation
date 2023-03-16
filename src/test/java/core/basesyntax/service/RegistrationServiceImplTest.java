package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int LOW_USER_AGE = 12;
    private static final int ALTERNATIVE_USER_AGE = 31;
    private static final int NEGATIVE_USER_AGE = -31;
    private static final String DEFAUL_PASSWORD = "password1";
    private static final String SHORT_PASSWORD = "jj1";
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("Ihor", DEFAUL_PASSWORD, ALTERNATIVE_USER_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerNullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerAlreadyExistUserLogin_notOk() {
        storageDao.add(user);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNullUserAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserAge_ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void registerUserLowAge_notOk() {
        user.setAge(LOW_USER_AGE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserNegativeAge_notOk() {
        user.setAge(NEGATIVE_USER_AGE);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerNullUserPass_notOk() {
        user.setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registerUserPass_ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void registerUserShortPass_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }
}
