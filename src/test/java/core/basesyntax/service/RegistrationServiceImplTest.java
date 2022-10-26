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
    private static final String DEFAULT_VALID_LOGIN = "Academy";
    private static final String DEFAULT_VALID_PASSWORD = "Mate22";
    private static final String SHORT_VALID_PASSWORD = "Mate";
    private static final String LONG_VALID_PASSWORD = "MateAcademy2022";
    private static final int DEFAULT_VALID_AGE = 18;
    private static final int ABOVE_VALID_AGE = 27;
    private static final int BELOW_VALID_AGE = 17;
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(DEFAULT_VALID_AGE);
        user.setLogin(DEFAULT_VALID_LOGIN);
        user.setPassword(DEFAULT_VALID_PASSWORD);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageBelowLimit_notOk() {
        user.setAge(BELOW_VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageAboveLimit_ok() {
        user.setAge(ABOVE_VALID_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword(SHORT_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_longPassword_ok() {
        user.setPassword(LONG_VALID_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_validPassword_ok() {
        user.setPassword(DEFAULT_VALID_PASSWORD);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_adultUser_ok() {
        user.setAge(DEFAULT_VALID_AGE);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_sameLogin_notOk() {
        User sameLogin = new User();
        user.setPassword(DEFAULT_VALID_PASSWORD);
        registrationService.register(user);
        sameLogin.setPassword(LONG_VALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLogin));

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
