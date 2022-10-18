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
        actualUser = new User();
    }

    @Test
    void userNotNullCheck() {
        actualUser.setLogin(NULL_VALUE);
        actualUser.setPassword(VALID_PASSWORD);
        actualUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userPassNotNullCheck() {
        actualUser.setLogin("Tom123");
        actualUser.setPassword(NULL_VALUE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userPassLengthValidation() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword("Pas12");
        actualUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userAgeNotNullCheck() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword("abs123");
        actualUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));

    }

    @Test
    void userAgeValidation() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword(VALID_PASSWORD);
        actualUser.setAge(11);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void userWithNegativeAge() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword(VALID_PASSWORD);
        actualUser.setAge(-19);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void pasWithoutUppercase_notOk() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword("1aad32");
        actualUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutLowerCase_notOk() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword("132AAA45");
        actualUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutNumber_notOk() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword("passWithoutNum");
        actualUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void passWithoutLetters_notOk() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword("1113233");
        actualUser.setAge(VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void twoUsersSameLoginCheck() {
        actualUser.setLogin(VALID_NAME);
        actualUser.setPassword(VALID_PASSWORD);
        actualUser.setAge(VALID_AGE);
        registrationService.register(actualUser);
        User secondUser = new User();
        secondUser.setLogin(VALID_NAME);
        secondUser.setPassword("AnotherPas3");
        secondUser.setAge(28);
        assertThrows(RuntimeException.class, () -> registrationService.register(secondUser));
    }

    @Test
    void twoUniqUsers() {
        actualUser.setLogin("Gregory_House");
        actualUser.setPassword("Md15051959");
        actualUser.setAge(63);
        User secondUser = new User();
        secondUser.setLogin("Jack15");
        secondUser.setPassword("mrJack22");
        secondUser.setAge(55);
        assertEquals(actualUser, registrationService.register(actualUser));
        assertEquals(secondUser, registrationService.register(secondUser));
    }
}
