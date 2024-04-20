package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String CORRECT_LOGIN = "login555";
    private static final String CORRECT_PASSWORD = "password";
    private static final int CORRECT_AGE = 20;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_returnsUser() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,CORRECT_AGE);
        User secondUser = registrationService.register(user);
        assertEquals(secondUser, user);
    }

    @Test
    void register_nullUser_throwsInvalidInputException() {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,CORRECT_AGE);
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Expected InvalidInputException for null login.");
    }

    @Test
    void register_emptyLogin_throwsInvalidInputException() {
        User user = new User("",CORRECT_PASSWORD,CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_throwsInvalidInputException() {
        User user = new User("log",CORRECT_PASSWORD,CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sufficientLoginLength_registersUser() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,CORRECT_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullPassword_throwsInvalidInputException() {
        User user = new User(null,CORRECT_PASSWORD,CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,"",CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,"123",CORRECT_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_sufficientPasswordLength_registersUser() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,CORRECT_AGE);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullAge_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underage_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_throwsInvalidInputException() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,-23);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_isAddedToStorage() {
        User user = new User(CORRECT_LOGIN,CORRECT_PASSWORD,CORRECT_AGE);
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual);
    }

    @Test
    void register_ageEighteenAndUserAlreadyExists_throwsException() {
        User existingUser = new User(CORRECT_LOGIN, CORRECT_PASSWORD, 18);
        Storage.people.add(existingUser);

        User newUser = new User(CORRECT_LOGIN, CORRECT_PASSWORD, 18);

        assertThrows(RegistrationException.class, () -> registrationService.register(newUser),
                "Expected RegistrationException for user already exists.");
        Storage.people.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
