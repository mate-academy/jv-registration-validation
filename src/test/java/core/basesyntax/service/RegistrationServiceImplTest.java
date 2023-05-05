package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static final int LOW_USER_AGE = 17;
    private static final int ALTERNATIVE_USER_AGE = 31;
    private static final int DEFAULT_USER_AGE = 18;
    private static final int NEGATIVE_USER_AGE = -31;
    private static final String DEFAUL_PASSWORD = "passw6";
    private static final String ALTERNATIVE_PASSWORD = "passwor8";
    private static final String SHORT5_PASSWORD = "abcdf";
    private static final String SHORT3_PASSWORD = "jj1";
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("Ihor", DEFAUL_PASSWORD, DEFAULT_USER_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_NullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_AlreadyExistUserLogin_notOk() {
        User firstUser = storageDao.add(user);
        assertEquals(user, firstUser);
        User secondUser = new User(firstUser.getLogin(), ALTERNATIVE_PASSWORD,
                ALTERNATIVE_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(secondUser);
        });
    }

    @Test
    void register_NullUserAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserAge_ok() {
        registrationService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void register_UserLowAge_notOk() {
        user.setAge(LOW_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserNegativeAge_notOk() {
        user.setAge(NEGATIVE_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_NullUserPass_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_UserPass_ok() {
        registrationService.register(user);
        User firstUser = storageDao.get(user.getLogin());
        assertEquals(user, firstUser);
        Storage.people.clear();
        user.setPassword(ALTERNATIVE_PASSWORD);
        registrationService.register(user);
        User secondUser = storageDao.get(user.getLogin());
        assertEquals(user, secondUser);
    }

    @Test
    void register_UserShortPass_notOk() {
        user.setPassword(SHORT3_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword(SHORT5_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
