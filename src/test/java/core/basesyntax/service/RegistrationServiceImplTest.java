package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String INVALID_LOGIN = "log";
    private static final int MAX_LOGIN_LENGTH = 25;
    private static final String TOO_LONG_LOGIN = "a".repeat(MAX_LOGIN_LENGTH + 1);
    private static final String EXISTING_LOGIN = "existingLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String INVALID_PASSWORD = "pass";
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final String TOO_LONG_PASSWORD = "a".repeat(MAX_PASSWORD_LENGTH + 1);
    private static final int VALID_AGE = 21;
    private static final int INVALID_AGE = 17;
    private static final int GREATER_THAN_MAX_AGE = 101;
    private static final String EMPTY_STRING = "";
    private static RegistrationServiceImpl registrationServiceImpl;
    private static StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        registrationServiceImpl = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    public void afterEach() {
        Storage.people.clear();
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_emptyLogin_notOk() {
        User user = createUser(EMPTY_STRING, EMPTY_STRING, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = createUser(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_emptyPassword_notOk() {
        User user = createUser(VALID_LOGIN, EMPTY_STRING, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_tooLongLogin_notOk() {
        User user = createUser(TOO_LONG_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_existingLogin_notOk() {
        User user = createUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(user);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User user = createUser(INVALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_tooLongPassword_notOk() {
        User user = createUser(VALID_LOGIN, TOO_LONG_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_invalidPassword_notOk() {
        User user = createUser(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_ageGraeaterThanMax_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, GREATER_THAN_MAX_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_invalidAge_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    public void register_validUser_ok() throws RegistrationException {
        User expectedUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        registrationServiceImpl.register(expectedUser);
        User actualUser = storageDao.get(VALID_LOGIN);
        assertEquals(expectedUser, actualUser);
        assertEquals(expectedUser.getLogin(), actualUser.getLogin());
        assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        assertEquals(expectedUser.getAge(), actualUser.getAge());
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
