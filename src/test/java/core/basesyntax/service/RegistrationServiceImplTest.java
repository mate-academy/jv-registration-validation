package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
    private final static int DEFAULT_AGE = 19;
    private final static String DEFAULT_PASSWORD = "Qwertyu";
    private final static String DEFAULT_LOGIN = "validUser";
    private final static String DEFAULT_EXCEPTION = RegistrationException.class.toString();
    private static RegistrationService registrationServic;
    private static StorageDaoImpl storageDao;
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationServic = new RegistrationServiceImpl();
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
    void register_userIsNull_exception() {
        user = null;
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(user),
                String.format("%s should be thrown user is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_validUser_ok() {
        registrationServic.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }

    @Test
    void register_existingUserLogin_exception() {
        User newUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
        registrationServic.register(user);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(newUser),
                String.format("%s should be thrown if login already exist", DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullLogin_exception() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(user),
                String.format("%s should be thrown if login is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_invalidUserAge_exception() {
        user.setAge(15);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(user),
                String.format("%s should be thrown if age is less 18", DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullAge_exception() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(user),
                String.format("%s should be thrown if age is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_invalidUserPassword_exception() {
        user.setPassword("qwert");
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(user),
                String.format("%s should be thrown if password length is less 6", DEFAULT_EXCEPTION));
    }

    @Test
    void register_nullPassword_exception() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,
                () -> registrationServic.register(user),
                String.format("%s should be thrown if password is null", DEFAULT_EXCEPTION));
    }

    @Test
    void register_limitAgeAndPassword_ok() {
        user.setPassword("qwerty");
        user.setAge(18);
        registrationServic.register(user);
        Assertions.assertEquals(user, storageDao.get(DEFAULT_LOGIN),
                "User should be added to storage");
    }

}