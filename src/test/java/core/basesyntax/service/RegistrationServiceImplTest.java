package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_EXCEPTION = RegistrationException.class.toString();
    private static final String DEFAULT_LOGIN = "validUser";
    private static final String DEFAULT_PASSWORD = "Qwertyu";
    private static final String MIN_LENGTH_PASSWORD = "Qwerty";
    private static final String INVALID_PASSWORD = "Qwert";
    private static final int DEFAULT_AGE = 19;
    private static final int MIN_AGE = 18;
    private static final int INVALID_AGE = 15;

    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if user is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }

    @Test
    void register_existingUserLogin_notOk() {
        User newUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        registrationService.register(user);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser),
                String.format("%s should be thrown if login already exist", DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if login is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_invalidUserAge_notOk() {
        user.setAge(INVALID_AGE);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if age is less " + MIN_AGE, DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if age is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_invalidUserPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if password length is less "
                                + MIN_LENGTH_PASSWORD.length(), DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if password is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_limitAgeAndPassword_ok() {
        user.setPassword(MIN_LENGTH_PASSWORD);
        user.setAge(MIN_AGE);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }
}
