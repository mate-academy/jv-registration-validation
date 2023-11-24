package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationServiceException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String EMPTY_ROW = "";
    private static final String INVALID_LOGIN_SHORTER_BY_FEW_SYMBOLS = "LOG";
    private static final String INVALID_LOGIN_SHORTER_BY_ONE = "LOGIN";
    private static final String MIN_VALID_LOGIN = "LOGIN@";
    private static final String VALID_LOGIN_LONGER_BY_ONE = "LOGINS@";
    private static final String EXISTING_LOGIN = "LOGIN_OLEG";
    private static final String INVALID_PASS_SHORTER_BY_FEW_SYMBOLS = "PAS";
    private static final String INVALID_PASS_SHORTER_BY_ONE = "PASS@";
    private static final String MIN_VALID_PASSWORD = "PASSWO";
    private static final String VALID_PASS_LONGER_BY_ONE = "PASSWOR";
    private static final int INVALID_AGE_LESS_BY_FEW_SYMBOLS = 15;
    private static final int INVALID_AGE_LESS_BY_ONE = 17;
    private static final int MIN_VALID_AGE = 18;
    private static final int VALID_AGE_GREATER_BY_ONE = 19;
    private static final User EXISTING
            = new User(EXISTING_LOGIN, MIN_VALID_PASSWORD, MIN_VALID_AGE);
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        storageDao.add(EXISTING);
    }

    @Test
    void register_ValidUser_Success() {
        User user = new User(MIN_VALID_LOGIN, MIN_VALID_PASSWORD, MIN_VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());

        User userValidLongerByOne
                = new User(VALID_LOGIN_LONGER_BY_ONE,
                VALID_PASS_LONGER_BY_ONE, VALID_AGE_GREATER_BY_ONE);
        registeredUser = registrationService.register(userValidLongerByOne);

        assertNotNull(registeredUser.getId());
        assertEquals(userValidLongerByOne.getLogin(), registeredUser.getLogin());
        assertEquals(userValidLongerByOne.getPassword(), registeredUser.getPassword());
        assertEquals(userValidLongerByOne.getAge(), registeredUser.getAge());
    }

    @Test
    void register_NullUser_ThrowsRegistrationServiceException() {
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(null));
    }

    @Test
    void register_DuplicateLogin_ThrowsRegistrationServiceException() {
        User user = new User(EXISTING.getLogin(), MIN_VALID_PASSWORD, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_ThrowsRegistrationServiceException() {
        User user = new User(null, MIN_VALID_PASSWORD, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidLoginLength_ThrowsRegistrationServiceException() {
        User userEmptyLogin = new User(EMPTY_ROW, MIN_VALID_PASSWORD, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userEmptyLogin));

        User userInvalidShorterByFew
                = new User(INVALID_LOGIN_SHORTER_BY_FEW_SYMBOLS, MIN_VALID_PASSWORD, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userInvalidShorterByFew));

        User userInvalidShorterByOne
                = new User(INVALID_LOGIN_SHORTER_BY_ONE, MIN_VALID_PASSWORD, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userInvalidShorterByOne));
    }

    @Test
    void register_NullPassword_ThrowsRegistrationServiceException() {
        User user = new User(MIN_VALID_LOGIN, null, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidPasswordLength_ThrowsRegistrationServiceException() {
        User userEmptyPassword = new User(MIN_VALID_LOGIN, EMPTY_ROW, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userEmptyPassword));

        User userInvalidShorterByFew = new User(MIN_VALID_LOGIN,
                INVALID_PASS_SHORTER_BY_FEW_SYMBOLS, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userInvalidShorterByFew));

        User userInvalidShorterByOne = new User(MIN_VALID_LOGIN,
                INVALID_PASS_SHORTER_BY_ONE, MIN_VALID_AGE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userInvalidShorterByOne));
    }

    @Test
    void register_NullAge_ThrowsRegistrationServiceException() {
        User user = new User(MIN_VALID_LOGIN, MIN_VALID_PASSWORD, null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void register_InvalidAge_ThrowsRegistrationServiceException() {
        User userInvalidShorterByFew
                = new User(MIN_VALID_LOGIN, MIN_VALID_PASSWORD, INVALID_AGE_LESS_BY_FEW_SYMBOLS);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userInvalidShorterByFew));

        User userInvalidShorterByOne
                = new User(MIN_VALID_LOGIN, MIN_VALID_PASSWORD, INVALID_AGE_LESS_BY_ONE);
        assertThrows(RegistrationServiceException.class,
                () -> registrationService.register(userInvalidShorterByOne));
    }
}
