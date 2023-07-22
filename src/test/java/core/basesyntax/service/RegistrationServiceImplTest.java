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
    private static final String FIRST_VALID_LOGIN = "Traineeeeeeeeee";
    private static final String SECOND_VALID_LOGIN = "Taylor";
    private static final String THIRD_VALID_LOGIN = "GeorgeTheMagician";
    private static final String FOURTH_VALID_LOGIN = "abcabcc";
    private static final String FIRST_VALID_PASSWORD = "password";
    private static final String SECOND_VALID_PASSWORD = "Swifty";
    private static final String THIRD_VALID_PASSWORD = "333666999";
    private static final String FOURTH_VALID_PASSWORD = "!@#$%^";
    private static final int FIRST_VALID_AGE = 25;
    private static final int SECOND_VALID_AGE = 18;
    private static final int THIRD_VALID_AGE = 19;
    private static final int FOURTH_VALID_AGE = 90;
    private static final String NON_VALID_LOGIN = "Max";
    private static final String NON_VALID_EDGE_LOGIN = "marty";
    private static final String NON_VALID_PASSWORD = "dnd";
    private static final String NON_VALID_EDGE_PASSWORD = "rumba";
    private static final int NON_VALID_AGE = -15;
    private static final int NON_VALID_EDGE_AGE = 17;
    private static final String EMPTY_FIELD = "";
    private static final int ZERO_AGE = 0;
    private static final User FIRST_VALID_USER = new User(FIRST_VALID_LOGIN,
            SECOND_VALID_PASSWORD, SECOND_VALID_AGE);
    private static final User SECOND_VALID_USER = new User(SECOND_VALID_LOGIN,
            SECOND_VALID_PASSWORD, SECOND_VALID_AGE);
    private static final User THIRD_VALID_USER = new User(THIRD_VALID_LOGIN,
            THIRD_VALID_PASSWORD, THIRD_VALID_AGE);
    private static final User FIRST_USER_FOR_REPEAT = new User(FOURTH_VALID_LOGIN,
            FOURTH_VALID_PASSWORD, FOURTH_VALID_AGE);
    private static final User SECOND_USER_FOR_REPEAT = new User(FOURTH_VALID_LOGIN,
            FOURTH_VALID_PASSWORD, FOURTH_VALID_AGE);
    private static final User FIRST_NON_VALID_USER = new User(NON_VALID_LOGIN,
            SECOND_VALID_PASSWORD, SECOND_VALID_AGE);
    private static final User SECOND_NON_VALID_USER = new User(SECOND_VALID_LOGIN,
            SECOND_VALID_PASSWORD, NON_VALID_AGE);
    private static final User THIRD_NON_VALID_USER = new User(THIRD_VALID_LOGIN,
            NON_VALID_PASSWORD, THIRD_VALID_AGE);
    private static final User FIRST_NON_VALID_EDGE_USER = new User(
            NON_VALID_EDGE_LOGIN, FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
    private static final User SECOND_NON_VALID_EDGE_USER = new User(
            FIRST_VALID_LOGIN, FIRST_VALID_PASSWORD, NON_VALID_EDGE_AGE);
    private static final User THIRD_NON_VALID_EDGE_USER = new User(
            FIRST_VALID_LOGIN, NON_VALID_EDGE_PASSWORD, FIRST_VALID_AGE);
    private static final User FOURTH_NON_VALID_USER = new User(null,
            null, null);
    private static final User FIFTH_NON_VALID_USER = new User(FIRST_VALID_LOGIN,
            null, null);
    private static final User SIXTH_NON_VALID_USER = new User(FIRST_VALID_LOGIN,
            FIRST_VALID_PASSWORD, null);
    private static final User EMPTY_USER = new User(EMPTY_FIELD,
            EMPTY_FIELD, ZERO_AGE);
    private static final User NULL_USER = null;
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
        User registeredUser = registrationService.register(FIRST_VALID_USER);
        assertNotNull(registeredUser);
        assertEquals(FIRST_VALID_USER, registeredUser);
    }

    @Test
    void register_multipleUserRegistration_ok() {
        User actualSecondUser = registrationService.register(SECOND_VALID_USER);
        assertNotNull(actualSecondUser);
        assertEquals(SECOND_VALID_USER, actualSecondUser);
        User actualFirstUser = registrationService.register(THIRD_VALID_USER);
        assertNotNull(actualFirstUser);
        assertEquals(THIRD_VALID_USER, actualFirstUser);
    }

    @Test
    void register_usersWithNullFields_notOk() {
        RegistrationException registrationException1 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        FOURTH_NON_VALID_USER));
        RegistrationException registrationException2 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        FIFTH_NON_VALID_USER));
        RegistrationException registrationException3 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        SIXTH_NON_VALID_USER));
        assertEquals(NULL_FIELDS_EXCEPTION_MESSAGE,
                registrationException1.getMessage());
        assertEquals(NULL_FIELDS_EXCEPTION_MESSAGE,
                registrationException2.getMessage());
        assertEquals(NULL_FIELDS_EXCEPTION_MESSAGE,
                registrationException3.getMessage());
    }

    @Test
    void register_loginAgePassword_notOk() {
        RegistrationException registrationException1 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        FIRST_NON_VALID_USER));
        assertEquals(MIN_LOGIN_EXCEPTION_MESSAGE,
                registrationException1.getMessage());
        RegistrationException registrationException2 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        SECOND_NON_VALID_USER));
        assertEquals(MIN_AGE_EXCEPTION_MESSAGE,
                registrationException2.getMessage());
        RegistrationException registrationException3 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        THIRD_NON_VALID_USER));
        assertEquals(MIN_PASSWORD_EXCEPTION_MESSAGE,
                registrationException3.getMessage());

        RegistrationException registrationException4 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        FIRST_NON_VALID_EDGE_USER));
        assertEquals(MIN_LOGIN_EXCEPTION_MESSAGE,
                registrationException4.getMessage());
        RegistrationException registrationException5 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        SECOND_NON_VALID_EDGE_USER));
        assertEquals(MIN_AGE_EXCEPTION_MESSAGE,
                registrationException5.getMessage());
        RegistrationException registrationException8 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        THIRD_NON_VALID_EDGE_USER));
        assertEquals(MIN_PASSWORD_EXCEPTION_MESSAGE,
                registrationException8.getMessage());
    }

    @Test
    void register_userWithEmptyData_notOk() {
        RegistrationException registrationException = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        EMPTY_USER));
        assertEquals(MIN_LOGIN_EXCEPTION_MESSAGE,
                registrationException.getMessage());
    }

    @Test
    void register_nullUser_notOk() {
        RegistrationException registrationException = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        NULL_USER));
        assertEquals("User shouldn't be null.",
                registrationException.getMessage());
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(FIRST_USER_FOR_REPEAT);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        SECOND_USER_FOR_REPEAT));
        assertEquals("User already exists with login " + SECOND_USER_FOR_REPEAT.getLogin(),
                registrationException.getMessage());
    }
}
