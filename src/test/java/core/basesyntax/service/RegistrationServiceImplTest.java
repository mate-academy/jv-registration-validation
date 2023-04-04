package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_USER_LOGIN = "tonyPix";
    private static final int DEFAULT_USER_AGE = 25;
    private static final int USER_AGE_EIGHTEEN = 18;
    private static final int LOW_USER_AGE = 15;
    private static final String DEFAULT_USER_PASS = "qwerty12345";
    private static final String SHORT_PASSWORD = "pass";
    private static RegistrationServiceImpl regService;
    private final StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeAll
    static void beforeAll() {
        regService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_USER_AGE);
        user.setLogin(DEFAULT_USER_LOGIN);
        user.setPassword(DEFAULT_USER_PASS);
    }

    @Test
    void register_userUnderEighteen_NotOk() {
        user.setAge(LOW_USER_AGE);
        assertThrows(RegistrationException.class, () -> {
            regService.register(user);
        });
    }

    @Test
    void register_userIsEighteen_Ok() {
        user.setAge(USER_AGE_EIGHTEEN);
        regService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(actual, user);
    }

    @Test
    void register_userIsAdult_Ok() {
        regService.register(user);
        User actual = storageDao.get(user.getLogin());
        assertEquals(user, actual);

    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            regService.register(user);
        });
    }

    @Test
    void register_userLoginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            regService.register(user);
        });
    }

    @Test
    void register_userWithThisUserNameIsExist_NotOk() {
        regService.register(user);
        assertThrows(RegistrationException.class, () -> {
            regService.register(user);
        });
    }

    @Test
    void register_shortPass_NotOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> {
            regService.register(user);
        });
    }

    @Test
    void register_register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            regService.register(null);
        });
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
