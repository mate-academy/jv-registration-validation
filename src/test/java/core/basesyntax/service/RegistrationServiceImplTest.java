package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final long DEFAULT_ID = 100L;
    private static final String DEFAULT_LOGIN = "login373";
    private static final String SHORT_LOGIN = "login";
    private static final String MINIMAL_LENGTH_LOGIN = "login3";
    private static final String DEFAULT_PASSWORD = "password373";
    private static final String MINIMAL_LENGTH_PASSWORD = "passwo";
    private static final String SHORT_PASSWORD = "passw";
    private static final int DEFAULT_AGE = 20;
    private static final Integer MINIMAL_AGE = 18;
    private static final Integer INVALID_AGE = 15;
    private static RegistrationServiceImpl registrService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(DEFAULT_ID);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    void register_allValid_ok() {
        User actualUser = registrService.register(user);
        assertEquals(user, actualUser);
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RegistrationException.class,() -> registrService.register(null),
                "Registering Null user should throw RegistrationException");
    }

    @Test
    void register_idIsNull_notOK() {
        user.setId(null);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Null id should throw RegistrationException");
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Null login should throw RegistrationException");
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Null password should throw RegistrationException");
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Null age should throw RegistrationException");
    }

    @Test
    void register_loginLessThanMin_notOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Short login should throw RegistrationException");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Short login should throw RegistrationException");
    }

    @Test
    void register_minimalLengthLogin_Ok() {
        user.setLogin(MINIMAL_LENGTH_LOGIN);
        User actualUser = registrService.register(user);
        assertEquals(user, actualUser);
    }

    @Test
    void register_passwordLessThanMin_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Short password should throw RegistrationException");
    }

    @Test
    void register_passwordMinimalLength_Ok() {
        user.setPassword(MINIMAL_LENGTH_PASSWORD);
        User actualUser = registrService.register(user);
        assertEquals(user, actualUser);
    }

    @Test
    void register_ageLessThanMin_notOK() {
        user.setAge(INVALID_AGE);
        assertThrows(RegistrationException.class,() -> registrService.register(user),
                "Age less than 18 should throw RegistrationException");
    }

    @Test
    void register_minimalAge_ok() {
        user.setAge(MINIMAL_AGE);
        User actualUser = registrService.register(user);
        assertEquals(user, actualUser);
    }

    @Test
    void register_loginTaken_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () ->
                registrService.register(user), "Login taken should throw RegistrationException");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
