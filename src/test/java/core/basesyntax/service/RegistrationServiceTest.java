package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
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
        userWithNullLogin = new User(null, "q3ff5e", 23);
        userWithNullPassword = new User("jack", null, 43);
        userWithNotValidPassword = new User("john", "49Gк5", 18);
        userValid = new User("alice", "fOre8dfg", 35);
        userWithInvalidAge = new User("rey", "dUrmd8sI", 15);
        userWithEqualLogin = new User("alice", "425gfh89", 110);
        Storage.people.clear();
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
    void userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void passwordIsNotValid_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithNotValidPassword));
    }

    @Test
    void invalidAge_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    void userIsValid_Ok() {
        User registeredUser = registrationService.register(userValid);
        assertTrue(Storage.people.contains(registeredUser));
        assertTrue(registeredUser.getAge() >= MIN_AGE);
        assertTrue(registeredUser.getPassword().length()
                >= MIN_LENGTH);
    }

    @Test
    void passwordIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullPassword));
    }
}
