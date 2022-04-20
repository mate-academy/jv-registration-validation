package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
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

    @Test
    void loginIsUsed_NotOk() {
        User userWithEqualLogin = new User("alice", "425gfh89", 110);
        User userValid = new User("alice", "fOre8dfg", 35);
        registrationService.register(userValid);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithEqualLogin));
    }

    @Test
    void loginIsNull_NotOk() {
        User userWithNullLogin = new User(null, "q3ff5e", 23);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void passwordIsNotValid_NotOk() {
        User userWithNotValidPassword = new User("john", "49Gк5", 18);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithNotValidPassword));
    }

    @Test
    void userIsValidAge_Ok() {
        User userValid = new User("alice", "fOre8dfg", 35);
        User userWithValidAge = registrationService.register(userValid);
        assertTrue(Storage.people.contains(userWithValidAge));
    }

    @Test
    void userIsValid_Ok() {
        User userValid = new User("alice", "fOre8dfg", 35);
        User registeredUser = registrationService.register(userValid);
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void invalidAge_NotOk() {
        User userWithInvalidAge = new User("rey", "dUrmd8sI", 17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    void passwordIsNull_NotOk() {
        User userWithNullPassword = new User("jack", null, 43);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @AfterEach
    public void cleanUpEach() {
        Storage.people.clear();
    }
}
