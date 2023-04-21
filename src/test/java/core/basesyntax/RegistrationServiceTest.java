package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String VALID_LOGIN = "login";
    private static final String VALID_PASSWORD = "password";
    private static final String ANOTHER_VALID_LOGIN = "anotherLogin";
    private static final String ANOTHER_VALID_PASSWORD = "anotherPassword";
    private static final String INVALID_LOGIN_WITH_LENGTH_ZERO = "";
    private static final String INVALID_LOGIN_WITH_LENGTH_THREE = "qwe";
    private static final String INVALID_LOGIN_WITH_LENGTH_FIVE = "asdfg";
    private static final String INVALID_PASSWORD_WITH_LENGTH_ZERO = "";
    private static final String INVALID_PASSWORD_WITH_LENGTH_THREE = "qwe";
    private static final String INVALID_PASSWORD_WITH_LENGTH_FIVE = "asdfg";
    private static final int VALID_AGE = 18;
    private static final int ANOTHER_VALID_AGE = 22;
    private static final int INVALID_AGE_SIX = 6;
    private static final int INVALID_AGE_NINE = 9;
    private static final int INVALID_AGE_SIXTEEN = 16;

    private static StorageDao storageDao;
    private static Storage storage;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storage = new Storage();
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginTooShort_notOk() {
        user.setLogin(INVALID_LOGIN_WITH_LENGTH_ZERO);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_LOGIN_WITH_LENGTH_THREE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_LOGIN_WITH_LENGTH_FIVE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordTooShort_notOk() {
        user.setPassword(INVALID_PASSWORD_WITH_LENGTH_ZERO);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_PASSWORD_WITH_LENGTH_THREE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_PASSWORD_WITH_LENGTH_FIVE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageInvalid_notOk() {
        user.setAge(INVALID_AGE_SIX);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(INVALID_AGE_NINE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(INVALID_AGE_SIXTEEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theSameLoginExists_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_validUserData_ok() {
        User expectedUser = new User();
        expectedUser.setLogin(ANOTHER_VALID_LOGIN);
        expectedUser.setPassword(ANOTHER_VALID_PASSWORD);
        expectedUser.setAge(ANOTHER_VALID_AGE);
        User actualUser = registrationService.register(expectedUser);
        assertEquals(expectedUser, actualUser);
    }
}
