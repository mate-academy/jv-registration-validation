package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;
    private static StorageDaoImpl storageDao;

    private static final String VALID_LOGIN = "Login123";
    private static final String VALID_LOGIN2 = "UserAge18";
    private static final String VALID_LOGIN3 = "UserAge25";
    private static final String INVALID_LOGIN = "log";
    private static final String INVALID_LOGIN2 = "login";
    private static final String BLANK_PASSWORD = "";
    private static final String VALID_PASSWORD = "qwerty123";
    private static final String VALID_PASSWORD2 = "password18";
    private static final String INVALID_PASSWORD = "qwer";
    private static final String INVALID_PASSWORD2 = "abcdf";
    private static final Integer VALID_AGE = 23;
    private static final Integer INVALID_AGE = 16;
    private static final String EXISTING_USER_LOGIN = "existinguser";
    private static final String NEW_USER_PASSWORD = "newpassword";
    private static final String LOGIN1 = "Login1";
    private static final String VALID_PASSWORD3 = "passwd";
    private static final String VALID_PASSWORD4 = "passwd12";
    private static final String VALID_PASSWORD5 = "password25";
    private static final int AGE_ZERO = 0;
    private static final int AGE_MINUS_ONE = -1;
    private static final int AGE_MINUS_TEN = -10;
    private static final int AGE_TEN = 10;
    private static final int AGE_EIGHTEEN = 18;
    private static final int AGE_TWENTY = 20;
    private static final int AGE_TWENTY_FIVE = 25;
    private static final int AGE_THIRTY = 30;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_invalidLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(BLANK_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(INVALID_LOGIN2);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(AGE_ZERO);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(AGE_MINUS_ONE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(AGE_MINUS_TEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(AGE_TEN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validAge_Ok() {
        User userAge18 = new User();
        userAge18.setLogin(VALID_LOGIN2);
        userAge18.setPassword(VALID_PASSWORD2);
        userAge18.setAge(AGE_EIGHTEEN);

        assertDoesNotThrow(() -> registrationService.register(userAge18));

        User userAge25 = new User();
        userAge25.setLogin(VALID_LOGIN3);
        userAge25.setPassword(VALID_PASSWORD5);
        userAge25.setAge(AGE_TWENTY_FIVE);

        assertDoesNotThrow(() -> registrationService.register(userAge25));
    }

    @Test
    void register_invalidPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(BLANK_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(INVALID_PASSWORD2);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUser_NotOk() {
        User existingUser = new User();
        existingUser.setLogin(EXISTING_USER_LOGIN);
        existingUser.setPassword(VALID_PASSWORD);
        existingUser.setAge(AGE_TWENTY_FIVE);
        storageDao.add(existingUser);

        User newUser = new User();
        newUser.setLogin(EXISTING_USER_LOGIN);
        newUser.setPassword(NEW_USER_PASSWORD);
        newUser.setAge(AGE_THIRTY);

        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_user_Ok() {
        User register = new User();
        register.setLogin(LOGIN1);
        register.setPassword(VALID_PASSWORD3);
        register.setAge(AGE_EIGHTEEN);
        registrationService.register(register);
        assertEquals(AGE_EIGHTEEN, storageDao.get(register.getLogin()).getAge());
        assertEquals(VALID_PASSWORD3, storageDao.get(register.getLogin()).getPassword());
        assertEquals(LOGIN1, storageDao.get(register.getLogin()).getLogin());

        User register1 = new User();
        register1.setLogin(VALID_LOGIN);
        register1.setPassword(VALID_PASSWORD4);
        register1.setAge(AGE_TWENTY);
        registrationService.register(register1);
        assertEquals(AGE_TWENTY, storageDao.get(register1.getLogin()).getAge());
        assertEquals(VALID_PASSWORD4, storageDao.get(register1.getLogin()).getPassword());
        assertEquals(VALID_LOGIN, storageDao.get(register1.getLogin()).getLogin());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
