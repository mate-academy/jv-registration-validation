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
    public static final int MIN_AGE = 18;
    public static final int LOWER_THAN_RECOMMENDED_AGE = 17;
    public static final String USER_LOGIN = "user";
    public static final String VALID_PASSWORD = "validPassword";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setPassword(VALID_PASSWORD);
        user.setLogin(USER_LOGIN);
        user.setAge(MIN_AGE);
    }

    @Test
    void register_validLogin_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_invalidLogin_notOk() {
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(LOWER_THAN_RECOMMENDED_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUserAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
