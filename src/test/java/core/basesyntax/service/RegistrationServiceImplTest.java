package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static RegistrationService registrationService;
    private User userValid;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userValid = new User("aliba", "Hsg7sjg79", 32);
        Storage.people.clear();
    }

    @Test
    void userIsValid_Ok() {
        User registeredUser = registrationService.register(userValid);
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void loginIsNull_NotOk() {
        User userWithNullLogin = new User(null, "yhfl6j", 0);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void loginIsUsed_NotOk() {
        User userWithEqualLogin = new User("aliba", "87hhf77", 25);
        registrationService.register(userValid);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithEqualLogin));
    }

    @Test
    void invalidAge_NotOk() {
        User userWithInvalidAge = new User("johoba", "gjS8fhfH87", 14);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    void passwordIsNull_NotOk() {
        User userWithNullPassword = new User("eliba", null, 0);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @Test
    void passwordIsNotValid_NotOk() {
        User userWithNotValidPassword = new User("aboba", "5T76r", 23);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithNotValidPassword));
    }
}
