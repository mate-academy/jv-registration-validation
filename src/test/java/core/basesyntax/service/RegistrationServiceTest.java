package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
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

    @Test
    void userNotNullCheck() {
        actualUser = new User(NULL_VALUE, VALID_PASSWORD, VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userPassNotNullCheck() {
        actualUser = new User("Tom123", NULL_VALUE, VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userPassLengthValidation() {
        actualUser = new User(VALID_NAME, "Pas12", VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userAgeNotNullCheck() {
        actualUser = new User(VALID_NAME, VALID_PASSWORD, null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));

    }

    @Test
    void userAgeValidation() {
        actualUser = new User(VALID_NAME, VALID_PASSWORD, 11);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userWithNegativeAge() {
        actualUser = new User(VALID_NAME, VALID_PASSWORD, -19);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void pasWithoutUppercase_notOk() {
        actualUser = new User(VALID_NAME, "1aad32", VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutLowerCase_notOk() {
        actualUser = new User(VALID_NAME, "132AAA45", VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutNumber_notOk() {
        actualUser = new User(VALID_NAME, "passWithoutNum", VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutLetters_notOk() {
        actualUser = new User(VALID_NAME,"1113233", VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void twoUsersSameLoginCheck() {
        actualUser = new User(VALID_NAME, VALID_PASSWORD, VALID_AGE);
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
