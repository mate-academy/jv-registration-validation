package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY_STRING = "";
    private static final String CORRECT_LOGIN = "RedArtis";
    private static final String SHORT_LOGIN = "admin";
    private static final String NOT_TRIMMED_LOGIN = " admin    ";
    private static final String CORRECT_PASSWORD = "password";
    private static final String SHORT_PASSWORD = "pass";
    private static final String NOT_TRIMMED_PASSWORD = "    pass     ";
    private static final int CORRECT_AGE = 22;
    private static final int NEGATIVE_AGE = -18;
    private static final int INCORRECT_AGE = 17;
    private static RegistrationService registrationService;
    private final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUpDefaultUser() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLength_notOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin(NOT_TRIMMED_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(EMPTY_STRING);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword(NOT_TRIMMED_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setAge(INCORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_user_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
