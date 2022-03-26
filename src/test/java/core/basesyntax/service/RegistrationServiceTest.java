package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final int MINIMUM_AGE = 18;
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
        userWithNotValidPassword = new User("bob", "5T76r", 19);
        userValid = new User("alice", "kU876fd", 28);
        userWithInvalidAge = new User("tom", "oR67nF", 16);
        userWithEqualLogin = new User("alice", "87hhf77", 28);
        userWithNullLogin = new User(null, "yhfl6j", 0);
        userWithNullPassword = new User("jack", null, 0);
        Storage.people.clear();
    }

    @Test
    void register_userIsValid_Ok() {
        assertTrue(Storage.people.contains(registrationService.register(userValid)));
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_ageIsValid() {
        assertTrue(registrationService.register(userValid).getAge() >= MINIMUM_AGE);
    }

    @Test
    void register_passwordIsValid_Ok() {
        assertTrue(registrationService.register(userValid).getPassword().length()
                >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void register_loginIsNull_NotOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullLogin));
    }

    @Test
    void register_loginIsUsed_NotOk() {
        Storage.people.add(userValid);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithEqualLogin));
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithInvalidAge));
    }

    @Test
    void register_passwordIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userWithNullPassword));
    }

    @Test
    void register_passwordIsNotValid_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userWithNotValidPassword));
    }
}
