package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String FIRST_VALID_LOGIN = "Trainee";
    private static final String SECOND_VALID_LOGIN = "Taylor";
    private static final String THIRD_VALID_LOGIN = "GeorgeTheMagician";
    private static final String FIRST_VALID_PASSWORD = "password";
    private static final String SECOND_VALID_PASSWORD = "Swifty";
    private static final int FIRST_VALID_AGE = 25;
    private static final int SECOND_VALID_AGE = 18;
    private static final int THIRD_VALID_AGE = 19;
    private static final String NON_VALID_LOGIN = "Max";
    private static final String NON_VALID_PASSWORD = "dnd";
    private static final int NON_VALID_AGE = 15;
    private static final String EMPTY_LOGIN = "";
    private static final String EMPTY_PASSWORD = "";
    private static final int ZERO_AGE = 0;
    private static final User VALID_USER = new User(FIRST_VALID_LOGIN,
            SECOND_VALID_PASSWORD, SECOND_VALID_AGE);
    private static final User EXISTING_USER = new User(SECOND_VALID_LOGIN,
            FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
    private static final User NEW_EXISTING_USER = new User(SECOND_VALID_LOGIN,
            FIRST_VALID_PASSWORD, FIRST_VALID_AGE);
    private static final User FIRST_NON_VALID_USER = new User(NON_VALID_LOGIN,
            SECOND_VALID_PASSWORD, SECOND_VALID_AGE);
    private static final User SECOND_NON_VALID_USER = new User(SECOND_VALID_LOGIN,
            SECOND_VALID_PASSWORD, NON_VALID_AGE);
    private static final User THIRD_NON_VALID_USER = new User(THIRD_VALID_LOGIN,
            NON_VALID_PASSWORD, THIRD_VALID_AGE);
    private static final User FOURTH_NON_VALID_USER = new User(null,
            null, null);
    private static final User FIFTH_NON_VALID_USER = new User(FIRST_VALID_LOGIN,
            null, null);
    private static final User SIXTH_NON_VALID_USER = new User(FIRST_VALID_LOGIN,
            FIRST_VALID_PASSWORD, null);
    private static final User EMPTY_USER = new User(EMPTY_LOGIN,
            EMPTY_PASSWORD, ZERO_AGE);
    private static final User NULL_USER = null;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
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
        assertEquals("All fields should be filled.",
                registrationException1.getMessage());
        assertEquals("All fields should be filled.",
                registrationException2.getMessage());
        assertEquals("All fields should be filled.",
                registrationException3.getMessage());
    }

    @Test
    void register_validUser_ok() {
        User registeredUser = registrationService.register(VALID_USER);
        assertNotNull(registeredUser);
        assertEquals(VALID_USER, registeredUser);
    }

    @Test
    void register_loginAgePassword_notOk() {
        RegistrationException registrationException1 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        FIRST_NON_VALID_USER));
        assertEquals("The login should be at least 6 characters.",
                registrationException1.getMessage());
        RegistrationException registrationException2 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        SECOND_NON_VALID_USER));
        assertEquals("The age should be more than 18yo.",
                registrationException2.getMessage());
        RegistrationException registrationException3 = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        THIRD_NON_VALID_USER));
        assertEquals("The password should be at least 6 characters.",
                registrationException3.getMessage());
    }

    @Test
    void register_userWithEmptyData_notOk() {
        RegistrationException registrationException = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        EMPTY_USER));
        assertEquals("The login should be at least 6 characters.",
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
        Storage.people.add(EXISTING_USER);
        RegistrationException registrationException = assertThrows(
                RegistrationException.class, () -> registrationService.register(
                        NEW_EXISTING_USER));
        assertEquals("User already exists with login " + NEW_EXISTING_USER.getLogin(),
                registrationException.getMessage());
    }
}
