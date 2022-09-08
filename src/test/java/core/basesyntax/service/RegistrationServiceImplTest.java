package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.UsersAgeNotValidException;
import core.basesyntax.exceptions.UsersPasswordNotValidException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_validUserCase_Ok() {
        User franklin = new User("franklin", "34432fk", 18);
        User marko = new User("marko", "gjtor3", 20);
        User brad = new User("Brad","007009", 19);
        registrationService.register(franklin);
        registrationService.register(marko);
        registrationService.register(brad);
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(franklin));
        assertTrue(Storage.people.contains(marko));
        assertTrue(Storage.people.contains(brad));
    }

    @Test
    void register_userExist_NotOk() {
        User marko1 = new User("marko", "34432fk", 18);
        User marko2 = new User("marko", "34432fk", 18);
        registrationService.register(marko1);
        assertThrows(UserAlreadyExistException.class, () ->
                registrationService.register(marko2));
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(marko1));
    }

    @Test
    void register_passwordValidation_NotOk() {
        User alice = new User("alice", "passw", 18);
        assertThrows(UsersPasswordNotValidException.class, () ->
                registrationService.register(alice));
    }

    @Test
    void register_ageValidation_NotOk() {
        User jacob = new User("jacob", "34432fk", 17);
        assertThrows(UsersAgeNotValidException.class, () ->
                registrationService.register(jacob));
    }

    @Test
    void register_nullLogin_NotOk() {
        User newUser = new User(null, "34432fk", 18);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        User philip = new User("philip", null, 18);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(philip));
    }

    @Test
    void register_nullAge_NotOk() {
        User brandon = new User("brandon", "34432fk", null);
        assertThrows(NullPointerException.class, () ->
                registrationService.register(brandon));
    }
}
