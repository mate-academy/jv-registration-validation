package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_OK = "example";
    private static final String PASSWORD_OK = "123456";
    private static final int AGE_OK = 18;
    private static final String LOGIN_NOT_OK = "examp";
    private static final String LOGIN_WITH_SPACE = "examp le";
    private static final String PASSWORD_NOT_OK = "12345";
    private static final int AGE_NOT_OK = 17;
    private static final long ID = 3423432423L;
    private static final long ID2 = 43323209888932L;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User userOk;
    private User userNotOk;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        userOk = new User();
        userOk.setId(ID2);
        userOk.setLogin(LOGIN_OK);
        userOk.setPassword(PASSWORD_OK);
        userOk.setAge(AGE_OK);

        userNotOk = new User();
        userNotOk.setId(ID);
        userNotOk.setLogin(LOGIN_NOT_OK);
        userNotOk.setPassword(PASSWORD_NOT_OK);
        userNotOk.setAge(AGE_NOT_OK);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userExist_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(userOk);
            registrationService.register(userOk);
        });
    }

    @Test
    void register_userLoginNull_notOk() {
        userOk.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userOk));
    }

    @Test
    void register_userLoginToShort_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(userNotOk));
    }

    @Test
    void register_userLoginContainSpace_notOk() {
        userOk.setLogin(LOGIN_WITH_SPACE);
        assertThrows(ValidationException.class, () -> registrationService.register(userOk));
    }

    @Test
    void register_userPasswordNull_notOk() {
        userOk.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userOk));
    }

    @Test
    void register_userPasswordToShort_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(userNotOk));
    }

    @Test
    void register_userAgeNull_notOk() {
        userOk.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(userOk));
    }

    @Test
    void register_userAgeToSmall_notOk() {
        assertThrows(ValidationException.class, () -> registrationService.register(userNotOk));
    }

    @Test
    void register_userExistAfterRegistration_Ok() {
        registrationService.register(userOk);
        assertEquals(userOk, storageDao.get(userOk.getLogin()));
    }
}
