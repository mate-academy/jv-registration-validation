package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private static final String NAME_1 = "Adam";
    private static final String NAME_2 = "Bob";
    private static final String NAME_3 = "Kate";
    private static final String CORRECT_PASSWORD_8_CHARS = "1234qwer";
    private static final String CORRECT_PASSWORD_10_CHARS = "j48fb*&%mf";
    private static final String CORRECT_PASSWORD_15_CHARS = "J&gf9Jg3IOj5La2";
    private static final int CORRECT_AGE_1 = 20;
    private static final int CORRECT_AGE_2 = 25;
    private static final int CORRECT_MINIMUM_AGE = 18;
    private static final int INCORRECT_AGE_UNDER_MINIMUM = 16;
    private static RegistrationServiceImpl registrationService;


    private User nullUser;
    private User correctUser1;
    private User correctUser2;
    private User correctUser3;
    private User incorrectUserAgeUnderMinimum;
    private User nullUserAge;
    private User nullUserLogin;
    private User nullUserPassword;


    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        nullUser = null;

        correctUser1 = getPreparedUser(NAME_1, CORRECT_PASSWORD_8_CHARS, CORRECT_AGE_1);
        correctUser2 = getPreparedUser(NAME_2, CORRECT_PASSWORD_10_CHARS, CORRECT_AGE_2);
        correctUser3 = getPreparedUser(NAME_3, CORRECT_PASSWORD_15_CHARS, CORRECT_MINIMUM_AGE);

        incorrectUserAgeUnderMinimum = getPreparedUser(
                NAME_1, CORRECT_PASSWORD_8_CHARS, INCORRECT_AGE_UNDER_MINIMUM);

        nullUserAge = getPreparedUser(NAME_1, CORRECT_PASSWORD_8_CHARS, 0);
        nullUserAge.setAge(null);
        nullUserLogin = getPreparedUser(null, CORRECT_PASSWORD_8_CHARS, CORRECT_MINIMUM_AGE);
        nullUserPassword = getPreparedUser(NAME_1, null, CORRECT_MINIMUM_AGE);
    }

    @Test
    void register_incorrectUserAge_NotOk() {
        try {
            registrationService.register(incorrectUserAgeUnderMinimum);
        } catch (RuntimeException e) {
            assertEquals("User must be at least " + CORRECT_MINIMUM_AGE + " years old",
                    e.getMessage());
            return;
        }
        fail("If age less than \" + CORRECT_MINIMUM_AGE + \" you must throw exception");
    }

    @Test
    void register_nullUserPassword_NotOk() {
        try {
            registrationService.register(nullUserPassword);
        } catch (RuntimeException e) {
            assertEquals("For registration User must be set password",
                    e.getMessage());
            return;
        }
        fail("If password is null you must throw RuntimeException with info");
    }

    @Test
    void register_nullUserLogin_NotOk() {
        try {
            registrationService.register(nullUserLogin);
        } catch (RuntimeException e) {
            assertEquals("For registration User must be set login",
                    e.getMessage());
            return;
        }
        fail("If login is null you must throw RuntimeException with info");
    }

    @Test
    void register_nullUserAge_NotOk() {
        try {
            registrationService.register(nullUserAge);
        } catch (RuntimeException e) {
            assertEquals("For registration User must be set age",
                    e.getMessage());
            return;
        }
        fail("If age is null you must throw RuntimeException with info");
    }

    @Test
    void register_correctAddingToStorage_Ok() {
        Long expectedFirstUserId = 1L;
        Long expectedSecondUserId = 2L;
        Long expectedThirdUserId = 3L;

        registrationService.register(correctUser1);
        registrationService.register(correctUser2);
        registrationService.register(correctUser3);

        Long currentFirstUserId = correctUser1.getId();
        Long currentSecondUserId = correctUser2.getId();
        Long currentThirdUserId = correctUser3.getId();

        assertEquals(expectedFirstUserId, currentFirstUserId,
                "You must add first user to storage");
        assertEquals(expectedSecondUserId, currentSecondUserId,
                "You must add second user to storage");
        assertEquals(expectedThirdUserId, currentThirdUserId,
                "You must add third user to storage");
    }

    @Test
    void register_correctUser_Ok() {
        String errorMessage = "You fail adding correct user: ";
        assertEquals(correctUser1, registrationService.register(correctUser1),
               errorMessage + correctUser1.getLogin());
        assertEquals(correctUser2, registrationService.register(correctUser2),
                errorMessage + correctUser2.getLogin());
        assertEquals(correctUser3, registrationService.register(correctUser3),
                errorMessage + correctUser3.getLogin());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> registrationService.register(nullUser),
                "You must return NullPointerException when User in null");
    }

    User getPreparedUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}