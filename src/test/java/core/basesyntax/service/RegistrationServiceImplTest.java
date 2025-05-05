package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String EXISTING_USER_LOGIN = "existingUserLogin";
    private static final String SHORT_LOGIN = "log";
    private static final String SHORT_PASSWORD = "pass";
    private static final String EMPTY_STRING = "";
    private static final int VALID_AGE = 24;
    private static final int MAX_VALID_AGE = 100;
    private static final int INVALID_AGE = 15;
    private static final int MAX_LOGIN_LENGTH = 25;
    private static final int MAX_PASSWORD_LENGTH = 25;
    private static final String TOO_LONG_LOGIN = "a".repeat(MAX_LOGIN_LENGTH + 1);
    private static final String TOO_LONG_PASSWORD = "a".repeat(MAX_PASSWORD_LENGTH + 1);

    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(null));
    }

    @Test
    public void register_existingUser_notOk() {
        User existingUser = createUser(EXISTING_USER_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(existingUser);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(existingUser));
    }

    @Test
    public void register_validUser_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        registrationService.register(newUser);
        assertThrows(RegistrationException.class, ()
                -> registrationService.register(newUser));
    }

    @Test
    public void register_validLogin_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationService.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User newUser = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(SHORT_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(SHORT_LOGIN));
    }

    @Test
    public void register_nullLogin_notOk() {
        User newUser = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(null));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(null));
    }

    @Test
    public void register_tooLongLogin_notOk() {
        User newUser = createUser(TOO_LONG_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(TOO_LONG_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(TOO_LONG_LOGIN));
    }

    @Test
    public void register_emptyLogin_notOk() {
        User newUser = createUser(EMPTY_STRING, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(EMPTY_STRING));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(EMPTY_STRING));
    }

    @Test
    public void register_validPassword_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationService.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_invalidPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_nullPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, null, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_emptyPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, EMPTY_STRING, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_tooLongPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, TOO_LONG_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_validAge_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationService.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_ageGreaterThanMax_notOk() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, MAX_VALID_AGE + 1);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_invalidAge_notOk() {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_nullAge_notOk() {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
