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
    private static final String VALID_LOGIN = "anton1";
    private static final String INVALID_LENGTH_LOGIN = "5char";
    private static final String VALID_PASSWORD1 = "password";
    private static final String VALID_PASSWORD2 = "another password";
    private static final String INVALID_LENGTH_PASSWORD = "5char";
    private static final String LOGIN_WITH_SPACE = "an ton";
    private static final String LOGIN_OUT_OF_SPACES = "      ";

    private static final Integer VALID_AGE1 = 20;
    private static final Integer VALID_AGE2 = 19;
    private static final Integer INVALID_AGE = 17;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User testUser1 = new User();
    private User testUser2 = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        testUser1 = new User(VALID_LOGIN, VALID_PASSWORD1, VALID_AGE1);
        testUser2 = new User(VALID_LOGIN, VALID_PASSWORD2, VALID_AGE2);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_Valid_Data_Ok() {
        registrationService.register(testUser1);
        assertEquals(testUser1, storageDao.get(testUser1.getLogin()));
    }

    @Test
    void register_Invalid_Age_NotOk() {
        testUser1.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Age_Is_Null_NotOk() {
        testUser1.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Invalid_Login_NotOk() {
        testUser1.setLogin(INVALID_LENGTH_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_LoginWithSpacesOnly_NotOk() {
        testUser1.setLogin(LOGIN_OUT_OF_SPACES);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_LoginWithOneSpace_NotOk() {
        testUser1.setLogin(LOGIN_WITH_SPACE);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Login_Is_Null_NotOk() {
        testUser1.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Invalid_Password_NotOk() {
        testUser1.setPassword(INVALID_LENGTH_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_Password_Is_Null_NotOk() {
        testUser1.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_User_Is_Null_NotOk() {
        testUser1 = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser1));
    }

    @Test
    void register_User_Login_Exists_NotOk() {
        storageDao.add(testUser1);
        assertThrows(RegistrationException.class, () -> registrationService.register(testUser2));
    }
}
