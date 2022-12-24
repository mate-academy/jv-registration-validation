package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String UNIQUE_LOGIN = "arsenvovk";
    private static final String USED_LOGIN = "bobhomenko";
    private static final String VALID_PASSWORD = "1vtqn9frf";
    private static final String INVALID_PASSWORD = "qwer";
    private static final Integer ADULT_AGE = 27;
    private static final Integer YOUNG_AGE = 17;
    private static final Integer INVALID_AGE = -8;
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        testUser = new User();
    }

    @Test
    void register_userIsNull_notOk() {
        testUser = null;
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testUser.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        testUser.setLogin(USED_LOGIN);
        storageDao.add(testUser);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullAge_notOk() {
        testUser.setAge(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testUser.setAge(INVALID_AGE);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_youngUser_notOk() {
        testUser.setAge(YOUNG_AGE);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testUser.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_lessCharPassword_notOk() {
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_failedRegistrationWithValidLogin_notOk() {
        testUser.setLogin(UNIQUE_LOGIN);
        testUser.setAge(YOUNG_AGE);
        testUser.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_failedRegistrationWithValidAge_notOk() {
        testUser.setLogin(UNIQUE_LOGIN);
        testUser.setAge(ADULT_AGE);
        testUser.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_failedRegistrationWithValidPassword_notOk() {
        testUser.setLogin(USED_LOGIN);
        testUser.setAge(null);
        testUser.setPassword(VALID_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_failedRegistration_notOk() {
        testUser.setLogin(USED_LOGIN);
        testUser.setAge(INVALID_AGE);
        testUser.setPassword(INVALID_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_notNullReturnUser_ok() {
        testUser.setLogin(UNIQUE_LOGIN);
        testUser.setAge(ADULT_AGE);
        testUser.setPassword(VALID_PASSWORD);
        assertNotNull(registrationService.register(testUser));
    }

    @Test
    void register_successfulRegistration_ok() {
        testUser.setLogin(UNIQUE_LOGIN);
        testUser.setAge(ADULT_AGE);
        testUser.setPassword(VALID_PASSWORD);
        assertEquals(registrationService.register(testUser), storageDao.get(testUser.getLogin()));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
