package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int APPROPRIATE_AGE = 20;
    private static final int NOT_APPROPRIATE_AGE_ONE = 17;
    private static final String APPROPRIATE_LOGIN_ONE = "userTest";
    private static final String APPROPRIATE_PASSWORD = "123456";
    private static final String NOT_APPROPRIATE_PASSWORD = "12345";
    private static RegistrationService registrationService;
    private static final StorageDaoImpl storageDao = new StorageDaoImpl();
    private User userTest;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userTest = new User();
        userTest.setLogin(APPROPRIATE_LOGIN_ONE);
        userTest.setAge(APPROPRIATE_AGE);
        userTest.setPassword(APPROPRIATE_PASSWORD);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_loginNull_notOk() {
        userTest.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_loginEmpty_notOk() {
        userTest.setLogin("");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_loginExistsInStorage_notOk() {
        storageDao.add(userTest);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_loginNew_ok() {
        User expectedUser = registrationService.register(userTest);
        User actualUser = storageDao.get(userTest.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_ageNull_notOk() {
        userTest.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_agePositive_notOk() {
        userTest.setAge(NOT_APPROPRIATE_AGE_ONE);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_passwordNull_notOk() {
        userTest.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_passwordToShort_notOk() {
        userTest.setPassword(NOT_APPROPRIATE_PASSWORD);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(userTest));
    }
}
