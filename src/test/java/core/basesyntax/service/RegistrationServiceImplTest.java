package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String EXISTING_USER_LOGIN = "existingUserLogin";
    private static final String SHORT_LOGIN = "log";
    private static final String SHORT_PASSWORD = "pass";
    private static final String EMPTY_STRING = "";
    private static final int VALID_AGE = 24;
    private static final int INVALID_AGE = 15;

    private RegistrationService registrationValidator;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationValidator = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void register_existingUser_notOk() {
        User existingUser = createUser(EXISTING_USER_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(existingUser);
        assertThrows(RegistrationException.class, ()
                -> registrationValidator.register(existingUser));
    }

    @Test
    public void register_newUser_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationValidator.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_validLogin_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationValidator.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_invalidLogin_notOk() {
        User newUser = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(SHORT_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(SHORT_LOGIN));
    }

    @Test
    public void register_nullLogin_notOk() {
        User newUser = createUser(null, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(null));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(null));
    }

    @Test
    public void register_emptyLogin_notOk() {
        User newUser = createUser(EMPTY_STRING, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(EMPTY_STRING));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(EMPTY_STRING));
    }

    @Test
    public void register_validPassword_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationValidator.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_invalidPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_nullPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, null, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_emptyPassword_notOk() {
        User newUser = createUser(VALID_LOGIN, EMPTY_STRING, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_validAge_ok() throws RegistrationException {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        registrationValidator.register(newUser);
        assertEquals(newUser, storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_invalidAge_notOk() {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
        assertNull(storageDao.get(VALID_LOGIN));
    }

    @Test
    public void register_nullAge_notOk() {
        User newUser = createUser(VALID_LOGIN, VALID_PASSWORD, null);
        assertNull(storageDao.get(VALID_LOGIN));
        assertThrows(RegistrationException.class, () -> registrationValidator.register(newUser));
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
