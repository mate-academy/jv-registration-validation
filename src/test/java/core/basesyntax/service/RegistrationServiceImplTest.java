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
    private static final String DEFAULT_PASSWORD = "Qwerty";
    private static final int MIN_AGE = 18;
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
        user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MIN_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(null),
                String.format("%s should be thrown if user is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }

    @Test
    void register_existingLogin_notOk() {
        User newUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, MIN_AGE);
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
    void register_invalidAge_notOk() {
        user.setAge(15);
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
    void register_invalidPassword_notOk() {
        user.setPassword("qwert");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if password length is less "
                                + DEFAULT_PASSWORD.length(), DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(user),
                String.format("%s should be thrown if password is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_notLimitAge_ok() {
        user.setAge(MIN_AGE + 1);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }

    @Test
    void register_notLimitPassword_ok() {
        user.setPassword(DEFAULT_PASSWORD + "qw");
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }
}
