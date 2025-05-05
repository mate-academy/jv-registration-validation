package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "123456789";
    private static final int VALID_AGE = 30;
    private static final int MIN_AGE = 18;
    private static final User FIRST_USER_WITH_LOGIN_MICHAEL =
            new User("Michael", VALID_PASSWORD, VALID_AGE);
    private static final User NULL_USER = null;
    private static final User INVALID_PASSWORD_USER =
            new User("Olivia", "12345", VALID_AGE);
    private static final User VALID_PASSWORD_USER_SOPHIA = new User("Sophia", "123456", VALID_AGE);
    private static final User VALID_PASSWORD_USER_ISABELLA =
            new User("Isabella", "1234567", VALID_AGE);
    private static final User NULL_PASSWORD_USER = new User("William", null, VALID_AGE);
    private static final User INVALID_AGE_USER = new User("Benjamin", VALID_PASSWORD, 16);
    private static final User VALID_AGE_USER_SEBASTIAN = new User("Sebastian", VALID_PASSWORD, 19);
    private static final User VALID_AGE_USER_SAMUEL = new User("Samuel", VALID_PASSWORD, MIN_AGE);
    private static final User NULL_LOGIN_USER =
            new User(null, VALID_PASSWORD, VALID_AGE);
    private static final User INVALID_LOGIN_USER =
            new User("Carl", VALID_PASSWORD, VALID_AGE);
    private static final User VALID_LOGIN_USER_POLINA = new
            User("Polina", VALID_PASSWORD, VALID_AGE);
    private static final User VALID_LOGIN_USER_TETIANA = new
            User("Tetiana", VALID_PASSWORD, VALID_AGE);
    private static final User SECOND_USER_WITH_LOGIN_MICHAEL = new
            User("Michael", VALID_PASSWORD, VALID_AGE);
    private static RegistrationService registrationService;

    @BeforeAll
    static void registrationServiceInitialization() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidPasswordUsers_throwsInvalidDataException() {
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_PASSWORD_USER);
        });
        assertEquals("User's length of password is "
                        + INVALID_PASSWORD_USER.getPassword().length()
                        + ". It shouldn't be shorter than 6 characters!",
                invalidDataException.getMessage());
    }

    @Test
    void register_validPasswordUsers_notThrowsInvalidDataException() {
        User actual1 = registrationService.register(VALID_PASSWORD_USER_SOPHIA);
        User actual2 = registrationService.register(VALID_PASSWORD_USER_ISABELLA);
        assertEquals(VALID_PASSWORD_USER_SOPHIA, actual1);
        assertEquals(VALID_PASSWORD_USER_ISABELLA, actual2);
    }

    @Test
    void register_nullPasswordUser_throwsInvalidDataException() {
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
        assertEquals("User's password is null!", invalidDataException.getMessage());
    }

    @Test
    void register_invalidAgeUser_throwsInvalidDataException() {
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_AGE_USER);
        });
        assertEquals("User's is not valid! It's " + INVALID_AGE_USER.getAge() + ", but min "
                + "age is " + MIN_AGE, invalidDataException.getMessage());
    }

    @Test
    void register_validAgeUsers_notThrowsInvalidDataException() {
        User actual1 = registrationService.register(VALID_AGE_USER_SEBASTIAN);
        User actual2 = registrationService.register(VALID_AGE_USER_SAMUEL);
        assertEquals(VALID_AGE_USER_SEBASTIAN, actual1);
        assertEquals(VALID_AGE_USER_SAMUEL, actual2);
    }

    @Test
    void register_nullLoginUser_notThrowsInvalidDataException() {
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
        assertEquals("User's login is null!", invalidDataException.getMessage());
    }

    @Test
    void register_invalidLoginUser_throwsInvalidDataException() {
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_LOGIN_USER);
        });
        assertEquals("User's length of login is " + INVALID_LOGIN_USER.getLogin().length()
                        + ". It shouldn't be shorter than 6 characters!",
                invalidDataException.getMessage());
    }

    @Test
    void register_validLoginUsers_throwsInvalidDataException() {
        User actual1 = registrationService.register(VALID_LOGIN_USER_POLINA);
        User actual2 = registrationService.register(VALID_LOGIN_USER_TETIANA);
        assertEquals(VALID_LOGIN_USER_POLINA, actual1);
        assertEquals(VALID_LOGIN_USER_TETIANA, actual2);
    }

    @Test
    void register_nullUser_throwsInvalidDataException() {
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_USER);
        });
        assertEquals("User is null!", invalidDataException.getMessage());
    }

    @Test
    void register_usersWithSameName_throwsRegistrationException() {
        Storage.people.add(FIRST_USER_WITH_LOGIN_MICHAEL);
        var invalidDataException = assertThrows(RegistrationException.class, () -> {
            registrationService.register(SECOND_USER_WITH_LOGIN_MICHAEL);
        });
        assertEquals("The storage already have user with login "
                + SECOND_USER_WITH_LOGIN_MICHAEL.getLogin(), invalidDataException.getMessage());
    }
}
