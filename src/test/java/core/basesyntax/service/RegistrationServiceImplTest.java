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
    private static final String BOB_LOGIN = "Bob";
    private static final String JOHN_LOGIN = "John";
    private static final String VALID_PASSWORD = "4567891";
    private static final String SHORT_PASSWORD = "45678";
    private static final int VALID_AGE = 19;
    private static final int THRESHOLD_AGE = 18;
    private static final int NEGATIVE_THRESHOLD_AGE = -18;

    private static final int NON_VALID_AGE = 17;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(BOB_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void registerValidUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void lessThanMinAge_NotOk() {
        user.setAge(NON_VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userIsNull_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void alreadyRegister_NotOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithMinAge_Ok() {
        user.setLogin(JOHN_LOGIN);
        user.setAge(THRESHOLD_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void tooShortPass_NotOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void negativeAgeUser_NotOk() {
        user.setAge(NEGATIVE_THRESHOLD_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
