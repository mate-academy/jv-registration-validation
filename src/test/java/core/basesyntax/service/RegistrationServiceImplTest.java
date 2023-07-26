package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 18;
    private static final String VALID_LOGIN = "validlogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final int ZERO_AGE = 0;
    private static final int NEGATIVE_AGE = -15;
    private static final int SMALL_AGE = 15;
    private static final String FIVE_LETTERS_INVALID_INPUT = "worst";
    private static final String THREE_LETTERS_INVALID_INPUT = "bad";
    private static final String EMPTY_INVALID_INPUT = "";
    private static final String NULL_INPUT = null;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    User validUserSetUp() {
        User actualUser = new User();
        actualUser.setAge(VALID_AGE);
        actualUser.setLogin(VALID_LOGIN);
        actualUser.setPassword(VALID_PASSWORD);
        return actualUser;
    }

    @Test
    void register_validUser_isOk() {
        User actualUser = validUserSetUp();
        assertNotNull(registrationService.register(actualUser));
        assertTrue(Storage.people.contains(actualUser));
    }

    @Test
    void register_nullUser_notOk() {
        User actualUser = null;
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The entered data is incorrect. "
                + "The user may not exist.", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setLogin(NULL_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("Please enter a login", exception.getMessage());
    }

    @Test
    void register_smallLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setLogin(THREE_LETTERS_INVALID_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The login " + THREE_LETTERS_INVALID_INPUT
                + " is incorrect.", exception.getMessage());
    }

    @Test
    void register_fiveLettersLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setLogin(FIVE_LETTERS_INVALID_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The login " + FIVE_LETTERS_INVALID_INPUT
                + " is incorrect.", exception.getMessage());
    }

    @Test
    void register_emptyLogin_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setLogin(EMPTY_INVALID_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The login " + EMPTY_INVALID_INPUT
                + " is incorrect.", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(NULL_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("Please enter a password", exception.getMessage());
    }

    @Test
    void register_smallPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(THREE_LETTERS_INVALID_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The password " + THREE_LETTERS_INVALID_INPUT
                + " is incorrect.", exception.getMessage());
    }

    @Test
    void register_fiveLettersPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(FIVE_LETTERS_INVALID_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The password " + FIVE_LETTERS_INVALID_INPUT
                + " is incorrect.", exception.getMessage());
    }

    @Test
    void register_emptyPassword_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setPassword(EMPTY_INVALID_INPUT);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The password " + EMPTY_INVALID_INPUT
                + " is incorrect.", exception.getMessage());
    }

    @Test
    void register_zeroAge_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setAge(ZERO_AGE);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The user is underage.", exception.getMessage());
    }

    @Test
    void register_negativeAge_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setAge(NEGATIVE_AGE);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The user is underage.", exception.getMessage());
    }

    @Test
    void register_smallAge_notOk() {
        User actualUser = validUserSetUp();
        actualUser.setAge(SMALL_AGE);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The user is underage.", exception.getMessage());
    }

    @Test
    void register_existingUser_notOk() {
        User actualUser = validUserSetUp();
        Storage.people.add(actualUser);
        RegistrationException exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(actualUser);
        });
        assertEquals("The entered data is incorrect. "
                + "Perhaps the user already exists.", exception.getMessage());
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
