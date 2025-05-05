package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User defaultUser;

    private static final String CORRECT_LOGIN = "Johnson";
    private static final int CORRECT_AGE = 20;
    private static final int YOUNG_AGE = 17;
    private static final int INCORRECT_AGE = -5;
    private static final String CORRECT_PASSWORD = "John328";
    private static final String WRONG_PASSWORD = "lol";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        storageDao = new StorageDaoImpl();
        defaultUser = new User();
        defaultUser.setLogin(CORRECT_LOGIN);
        defaultUser.setAge(CORRECT_AGE);
        defaultUser.setPassword(CORRECT_PASSWORD);
    }

    @Test
    void correctData_ok() {
        assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    public void checkAlreadyExistedUser_notOk() {
        storageDao.add(defaultUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithNullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithNullAge_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithSmallAge_notOk() {
        defaultUser.setAge(YOUNG_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithInvalidAge_notOk() {
        defaultUser.setAge(INCORRECT_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithSmallPassword_notOk() {
        defaultUser.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
