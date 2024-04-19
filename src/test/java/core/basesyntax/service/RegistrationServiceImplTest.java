package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "login555";
    private static final String CORRECT_PASSWORD = "password";
    private static final int CORRECT_AGE = 20;
    private static User user;
    private static User userTwo;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        userTwo = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setAge(CORRECT_AGE);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
    }

    @Test
    void register_validUser_returnsUser() {
        userTwo = registrationService.register(user);
        assertEquals(userTwo, user);
    }

    @Test
    void register_nullUser_throwsInvalidInputException() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_throwsInvalidInputException() {
        user.setLogin(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user),
                "Expected InvalidInputException for null login.");
    }

    @Test
    void register_emptyLogin_throwsInvalidInputException() {
        user.setLogin("");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_throwsInvalidInputException() {
        user.setLogin("log");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sufficientLoginLength_registersUser() {
        user.setLogin(CORRECT_LOGIN);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_nullPassword_throwsInvalidInputException() {
        user.setPassword(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_throwsInvalidInputException() {
        user.setPassword("");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_throwsInvalidInputException() {
        user.setPassword("123");
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sufficientPasswordLength_registersUser() {
        user.setPassword(CORRECT_PASSWORD);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_nullAge_throwsInvalidInputException() {
        user.setAge(null);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_throwsInvalidInputException() {
        user.setAge(0);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underage_throwsInvalidInputException() {
        user.setAge(10);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_throwsInvalidInputException() {
        user.setAge(-23);
        assertThrows(InvalidInputException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_isAddedToStorage() {
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @AfterEach
    void clearStorageAfterTest() {
        Storage.people.remove(user);
    }
}
