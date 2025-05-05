package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String NAME_1 = "Adam";
    private static final String NAME_2 = "Bob";
    private static final String NAME_3 = "Kate";
    private static final String CORRECT_PASSWORD_8_CHARS = "1234qwer";
    private static final String CORRECT_PASSWORD_10_CHARS = "j48fb*&%mf";
    private static final String CORRECT_PASSWORD_15_CHARS = "J&gf9Jg3IOj5La2";
    private static final String INCORRECT_PASSWORD_1_CHAR = "g";
    private static final String INCORRECT_PASSWORD_4_CHAR = "hfdl";
    private static final String INCORRECT_PASSWORD_5_CHAR = "12345";
    private static final int CORRECT_AGE_1 = 20;
    private static final int CORRECT_AGE_2 = 25;
    private static final int CORRECT_MINIMUM_AGE = 18;
    private static final int INCORRECT_AGE_UNDER_MINIMUM = 16;
    private static final int INCORRECT_AGE_ZERO = 0;
    private static final int INCORRECT_AGE_LESS_ZERO = Integer.MIN_VALUE;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_alreadyRegisteredUser_notOk() {
        User correctUser1 = getPreparedUser(
                NAME_1, CORRECT_PASSWORD_8_CHARS, CORRECT_AGE_1);

        String message = "When user already registered you must throw exception";
        Storage.people.add(correctUser1);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(correctUser1), message);
    }

    @Test
    void register_incorrectUserAge_notOk() {
        User incorrectUserAgeUnderMinimum1 = getPreparedUser(
                NAME_1, CORRECT_PASSWORD_8_CHARS, INCORRECT_AGE_UNDER_MINIMUM);
        User incorrectUserAgeUnderMinimum2 = getPreparedUser(
                NAME_2, CORRECT_PASSWORD_8_CHARS, INCORRECT_AGE_ZERO);
        User incorrectUserAgeUnderMinimum3 = getPreparedUser(
                NAME_3, CORRECT_PASSWORD_15_CHARS, INCORRECT_AGE_LESS_ZERO);

        String message = "When age under 6, you must throw exception";
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserAgeUnderMinimum1), message);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserAgeUnderMinimum2), message);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserAgeUnderMinimum3), message);
    }

    @Test
    void register_incorrectUserPassword_notOk() {
        User incorrectUserPasswordLess1 = getPreparedUser(
                NAME_1, INCORRECT_PASSWORD_1_CHAR, CORRECT_AGE_1);
        User incorrectUserPasswordLess2 = getPreparedUser(
                NAME_2, INCORRECT_PASSWORD_5_CHAR, CORRECT_MINIMUM_AGE);
        User incorrectUserPasswordLess3 = getPreparedUser(
                NAME_3, INCORRECT_PASSWORD_4_CHAR, CORRECT_AGE_2);

        String message = "When password less than 6, you must throw exception";
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserPasswordLess1), message);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserPasswordLess2), message);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserPasswordLess3), message);
    }

    @Test
    void register_incorrectUserAgeException_ok() {
        User incorrectUserAgeUnderMinimum1 = getPreparedUser(
                NAME_1, CORRECT_PASSWORD_8_CHARS, INCORRECT_AGE_UNDER_MINIMUM);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(incorrectUserAgeUnderMinimum1),
                "If age less than " + CORRECT_MINIMUM_AGE + " you must throw exception");
    }

    @Test
    void register_nullUserPasswordException_ok() {
        User nullUserPassword = getPreparedUser(NAME_1, null, CORRECT_MINIMUM_AGE);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(nullUserPassword),
                "If password is null you must throw RuntimeException with info");
    }

    @Test
    void register_nullUserLoginException_ok() {
        User nullUserLogin = getPreparedUser(null, CORRECT_PASSWORD_8_CHARS, CORRECT_MINIMUM_AGE);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(nullUserLogin),
                "If login is null you must throw RuntimeException with info");
    }

    @Test
    void register_nullUserAge_notOk() {
        User nullUserAge = getPreparedUser(NAME_1, CORRECT_PASSWORD_8_CHARS, 0);
        nullUserAge.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(nullUserAge),
                "If age is null you must throw RuntimeException with info");
    }

    @Test
    void register_correctUser_ok() {
        User correctUser1 = getPreparedUser(
                NAME_1, CORRECT_PASSWORD_8_CHARS, CORRECT_AGE_1);
        User correctUser2 = getPreparedUser(
                NAME_2, CORRECT_PASSWORD_10_CHARS, CORRECT_AGE_2);
        User correctUser3 = getPreparedUser(
                NAME_3, CORRECT_PASSWORD_15_CHARS, CORRECT_MINIMUM_AGE);

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
        assertThrows(NullPointerException.class, () -> registrationService.register(null),
                "You must return NullPointerException when User is null");
    }

    User getPreparedUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
