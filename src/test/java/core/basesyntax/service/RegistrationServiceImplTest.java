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
    private static final String VALID_LOGIN = "validLogin";
    private static final String SHORT_LOGIN = "log";
    private static final String EMPTY_STRING = "";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String SHORT_PASSWORD = "pass";
    private static final int VALID_AGE = 22;
    private static final int YOUNGER_AGE = 17;
    private static final int NEGATIVE_AGE = -20;
    private static final String NULL_VALUE = null;
    private static final Integer NULL_AGE_VALUE = null;
    private static RegistrationService registrationService;
    private final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_userIsValid_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_loginIsShort_NotOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(NULL_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsShort_NotOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(NULL_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsUnderEighteen_NotOk() {
        user.setAge(YOUNGER_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(NULL_AGE_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsUsed_NotOk() {
        Storage.PEOPLE.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
