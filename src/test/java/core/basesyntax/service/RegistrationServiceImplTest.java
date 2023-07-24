package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String FIFTEEN_CHARS_VALID_LOGIN = "Traineeeeeeeeee";
    private static final String SIX_CHARS_VALID_LOGIN = "Taylor";
    private static final String SEVENTEEN_CHARS_VALID_LOGIN = "GeorgeTheMagician";
    private static final String SEVEN_CHARS_VALID_LOGIN = "abcabcc";
    private static final String EIGHT_CHARS_VALID_PASSWORD = "password";
    private static final String SIX_CHARS_VALID_PASSWORD = "Swifty";
    private static final String SYMBOL_SIX_CHARS_VALID_PASSWORD = "!@#$%^";
    private static final int TWENTY_FIVE_VALID_AGE = 25;
    private static final int EIGHTEEN_VALID_AGE = 18;
    private static final int NINETEEN_VALID_AGE = 19;
    private static final int NINETY_VALID_AGE = 90;
    private static final String THREE_CHARS_NON_VALID_LOGIN = "Max";
    private static final String FIVE_CHARS_NON_VALID_LOGIN = "marty";
    private static final String THREE_CHARS_NON_VALID_PASSWORD = "dnd";
    private static final String FIVE_CHARS_NON_VALID_PASSWORD = "rumba";
    private static final String EMPTY_NON_VALID_FIELD = "";
    private static final int NEGATIVE_NON_VALID_AGE = -15;
    private static final int SEVENTEEN_NON_VALID_AGE = 17;
    private static final int ZERO_NON_VALID_AGE = 0;
    private static final String NULL_FIELDS_EXCEPTION_MESSAGE =
            "All fields should be filled.";
    private static final String MIN_LOGIN_EXCEPTION_MESSAGE =
            "The login should be at least 6 characters.";
    private static final String MIN_AGE_EXCEPTION_MESSAGE =
            "The age should be more than 18yo.";
    private static final String MIN_PASSWORD_EXCEPTION_MESSAGE =
            "The password should be at least 6 characters.";

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User validUser = createUser(FIFTEEN_CHARS_VALID_LOGIN,
                SIX_CHARS_VALID_PASSWORD, EIGHTEEN_VALID_AGE);
        User registeredUser = registrationService.register(validUser);
        assertEquals(validUser, registeredUser);
    }

    @Test
    void register_userNotNull_ok() {
        User validUser = createUser(SIX_CHARS_VALID_LOGIN,
                SIX_CHARS_VALID_PASSWORD, EIGHTEEN_VALID_AGE);
        User actualUser = registrationService.register(validUser);
        assertNotNull(actualUser);
    }

    @Test
    void register_userWithNullLogin_notOk() {
        User nonValidUser = createUser(null, 
                SIX_CHARS_VALID_PASSWORD, EIGHTEEN_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(NULL_FIELDS_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithNullPassword_notOk() {
        User nonValidUser = createUser(FIFTEEN_CHARS_VALID_LOGIN,
                null, NINETY_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(NULL_FIELDS_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithNullAge_notOk() {
        User nonValidUser = createUser(FIFTEEN_CHARS_VALID_LOGIN,
                EIGHT_CHARS_VALID_PASSWORD, null);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(NULL_FIELDS_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithThreeCharsLogin_notOk() {
        User nonValidUser = createUser(THREE_CHARS_NON_VALID_LOGIN,
                SIX_CHARS_VALID_PASSWORD, EIGHTEEN_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_LOGIN_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithFiveCharsLogin_notOk() {
        User nonValidUser = createUser(FIVE_CHARS_NON_VALID_LOGIN,
                EIGHT_CHARS_VALID_PASSWORD, TWENTY_FIVE_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_LOGIN_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithNegativeAge_notOk() {
        User nonValidUser = createUser(SIX_CHARS_VALID_LOGIN,
                SIX_CHARS_VALID_PASSWORD, NEGATIVE_NON_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_AGE_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithSeventeenAge_notOk() {
        User nonValidUser = createUser(FIFTEEN_CHARS_VALID_LOGIN,
                EIGHT_CHARS_VALID_PASSWORD, SEVENTEEN_NON_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_AGE_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithThreeCharsPassword_notOk() {
        User nonValidUser = createUser(SEVENTEEN_CHARS_VALID_LOGIN,
                THREE_CHARS_NON_VALID_PASSWORD, NINETEEN_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_PASSWORD_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithFiveCharsPassword_notOk() {
        User nonValidUser = createUser(FIFTEEN_CHARS_VALID_LOGIN,
                FIVE_CHARS_NON_VALID_PASSWORD, TWENTY_FIVE_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_PASSWORD_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_userWithEmptyFields_notOk() {
        User nonValidUser = createUser(EMPTY_NON_VALID_FIELD,
                EMPTY_NON_VALID_FIELD, ZERO_NON_VALID_AGE);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(nonValidUser));
        assertEquals(MIN_LOGIN_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException registrationException = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        null));
        assertEquals("User shouldn't be null.",
                registrationException.getMessage());
    }

    @Test
    void register_existingUser_notOk() {
        User validUser = createUser(SEVEN_CHARS_VALID_LOGIN,
                SYMBOL_SIX_CHARS_VALID_PASSWORD, NINETY_VALID_AGE);
        User repeatedValidUser = createUser(SEVEN_CHARS_VALID_LOGIN,
                SYMBOL_SIX_CHARS_VALID_PASSWORD, NINETY_VALID_AGE);
        Storage.people.add(validUser);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class,
                () -> registrationService.register(repeatedValidUser));
        assertEquals("User already exists with login "
                        + repeatedValidUser.getLogin(),
                registrationException.getMessage());
    }

    private User createUser(String login, String password, Integer age) {
        return new User(login, password, age);
    }
}
