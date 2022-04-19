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
    private User userWithNotValidPassword;
    private User userValid;
    private User userWithInvalidAge;
    private User userWithEqualLogin;
    private User userWithNullLogin;
    private User userWithNullPassword;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userWithNotValidPassword = new User("aboba", "5T76r", 23);
        userValid = new User("aliba", "Hsg7sjg79", 32);
        userWithInvalidAge = new User("johoba", "gjS8fhfH87", 14);
        userWithEqualLogin = new User("aliba", "87hhf77", 25);
        userWithNullLogin = new User(null, "yhfl6j", 0);
        userWithNullPassword = new User("eliba", null, 0);
        Storage.people.clear();
    }

    @Test
    void userIsValid_Ok() {
        User registeredUser = registrationService.register(userValid);
        assertTrue(Storage.people.contains(registeredUser));
        assertTrue(registeredUser.getAge() >= MIN_AGE);
        assertTrue(registeredUser.getPassword().length()
                >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void loginIsNull_NotOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void loginIsUsed_NotOk() {
        Storage.people.add(userValid);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithEqualLogin));
    }

    @Test
    void invalidAge_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    void passwordIsNull_NotOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @Test
    void passwordIsNotValid_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithNotValidPassword));
    }
}
