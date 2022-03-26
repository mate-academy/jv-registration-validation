package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User userBob;
    private User userAlice;
    private User userTom;
    private User userAlice2;
    private User userNull;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userBob = new User("bob", "5T76r", 19);
        userAlice = new User("alice", "kU876fd", 28);
        userTom = new User("tom", "oR67nF", 16);
        userAlice2 = new User("alice", "87hhf77", 28);
        userNull = new User(null, null, 0);
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_loginIsNull_NotOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userNull));
    }

    @Test
    void register_loginIsNotUsed_Ok() {
        boolean actual = Storage.people.contains(registrationService.register(userAlice));
        assertTrue(actual);
    }

    @Test
    void register_loginIsNotUsed_NotOk() {
        Storage.people.add(userAlice);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userAlice2));
    }

    @Test
    void register_validAge_notOk() {
        assertThrows(RuntimeException.class, () ->
                Storage.people.contains(registrationService.register(userTom)));
    }

    @Test
    void register_passwordIsNull_notOk() {
        assertThrows(NullPointerException.class, () ->
                registrationService.register(userNull));
    }

    @Test
    void register_passwordIsNotValid_NotOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(userBob));
    }
}
