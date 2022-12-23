package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import exception.RegistrationValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 20;
    private static final String INCORRECT_PASSWORD = "pass";
    private static final int INCORRECT_AGE = 17;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setLogin(VALID_LOGIN);
        testUser.setPassword(VALID_PASSWORD);
        testUser.setAge(VALID_AGE);
    }

    @Test
    void register_validUserRegister_ok() {
        registrationService.register(testUser);
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(testUser));
    }

    @Test
    void register_userLoginIsAlreadyExists_notOk() {
        storageDao.add(testUser);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_loginIsNull_notOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordIsNull_notOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        testUser.setAge(null);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_incorrectPassword_notOk() {
        testUser.setPassword(INCORRECT_PASSWORD);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(testUser));
    }

    @Test
    void register_incorrectAge_notOk() {
        testUser.setAge(INCORRECT_AGE);
        assertThrows(RegistrationValidationException.class,
                () -> registrationService.register(testUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
