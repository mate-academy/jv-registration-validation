package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    public static final int LOWER_THAN_RECOMMENDED_AGE = 10;
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
    void containsAtLeastSixCharacter_Ok() {
        User actual = registrationService.register(user);
        assertTrue(actual.getPassword().length() >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void containsLessThanSixCharacter_NotOk() {
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userOver18_Ok() {
        assertEquals(MIN_AGE, registrationService.register(user).getAge());
    }

    @Test
    void userUnder18_NotOk() {
        user.setAge(LOWER_THAN_RECOMMENDED_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userNegativeAge_NotOk() {
        user.setAge(-MIN_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void userAlreadyExists_NotOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void nullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullUserPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullUserAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
