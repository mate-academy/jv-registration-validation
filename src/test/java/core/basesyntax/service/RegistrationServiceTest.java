package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    public static final String NULL_VALUE = null;
    public static final String VALID_NAME = "Tom123";
    public static final String VALID_PASSWORD = "aBc321";
    public static final Integer VALID_AGE = 22;

    private static RegistrationService registrationService;
    private static User actualUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        actualUser = new User(VALID_NAME, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void userLoginNull_notOk() {
        actualUser.setLogin(NULL_VALUE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userPassNull_notOk() {
        actualUser.setPassword(NULL_VALUE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void shortUserPass_notOk() {
        actualUser.setPassword("Pas12");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userAgeNull_notOk() {
        actualUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));

    }

    @Test
    void userAgeLower18_notOk() {
        actualUser.setAge(11);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userAgeNegativeValue_notOk() {
        actualUser.setAge(-18);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void pasWithoutUppercase_notOk() {
        actualUser.setPassword("1aad32");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutLowerCase_notOk() {
        actualUser.setPassword("132AAA45");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutNumber_notOk() {
        actualUser.setPassword("passWithoutNum");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutLetters_notOk() {
        actualUser.setPassword("123331224");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void twoUsersSameLogin_notOk() {
        registrationService.register(actualUser);
        User secondUser = new User(VALID_NAME, "AnotherPas3", VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void twoUniqUsers() {
        actualUser = new User("Gregory_House", "Md15051959", 63);
        User secondUser = new User("Jack15", "mrJack22", 55);
        assertEquals(actualUser, registrationService.register(actualUser));
        assertEquals(secondUser, registrationService.register(secondUser));
    }
}
