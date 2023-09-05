package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static final String NULL_STRING = null;
    private static final String EMPTY_STRING = "";
    private static final String CORRECT_LOGIN = "RedArtis";
    private static final String SHORT_LOGIN = "admin";
    private static final String NOT_TRIMMED_LOGIN = " admin    ";
    private static final String CORRECT_PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pass";
    private static final String NOT_TRIMMED_PASSWORD = "    pass     ";
    private static final int CORRECT_AGE = 22;
    private static final Integer NULL_AGE = null;
    private static final int NEGATIVE_AGE = -18;
    private static final int INCORRECT_AGE = 17;
    private static RegistrationService registrationService;


    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(NULL_STRING, CORRECT_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLoginLength_notOk() {
        User user1 = new User(SHORT_LOGIN, CORRECT_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        User user2 = new User(EMPTY_STRING, CORRECT_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        User user3 = new User(NOT_TRIMMED_LOGIN, CORRECT_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(CORRECT_LOGIN, NULL_STRING, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPasswordLength_notOk() {
        User user1 = new User(CORRECT_LOGIN, SHORT_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        User user2 = new User(CORRECT_LOGIN, EMPTY_STRING, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        User user3 = new User(CORRECT_LOGIN, NOT_TRIMMED_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user3));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(CORRECT_LOGIN, CORRECT_PASSWORD, NULL_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectAge_notOk() {
        User user1 = new User(CORRECT_LOGIN, SHORT_PASSWORD, NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        User user2 = new User(CORRECT_LOGIN, EMPTY_STRING, INCORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUser_ok() {
        User user = new User(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_AGE);
        User actual = registrationService.register(user);
        assertEquals(actual.getId(), 1L);
        assertEquals(Storage.people.size(), 1);
    }

    @Test
    void register_existingUser_notOk() {
        User existerUser = new User(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_AGE);
        Storage.people.add(existerUser);
        User newUser = new User(CORRECT_LOGIN, CORRECT_PASSWORD, CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
        assertEquals(Storage.people.size(), 1);
    }
}
