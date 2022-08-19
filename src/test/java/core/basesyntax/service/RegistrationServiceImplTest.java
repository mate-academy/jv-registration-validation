package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.UsersAgeNotValidException;
import core.basesyntax.exceptions.UsersPasswordNotValidException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void validUserCase_Ok() {
        User marko = new User("marko", "gjtor3r", 20);
        User franklin = new User("franklin", "34432fk", 18);
        User brad = new User("Brad","007009", 19);
        registrationService.register(marko);
        registrationService.register(franklin);
        registrationService.register(brad);
        assertEquals(1, marko.getId());
        assertEquals(2, franklin.getId());
        assertEquals(3, brad.getId());
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(marko));
        assertTrue(Storage.people.contains(franklin));
        assertTrue(Storage.people.contains(brad));
        Storage.people.clear();
    }

    @Test
    void equalsLoginTrue_NotOk() {
        User marko1 = new User("marko", "34432fk", 18);
        User marko2 = new User("marko", "34432fk", 18);
        registrationService.register(marko1);
        assertThrows(UserAlreadyExistException.class, () ->
                registrationService.register(marko2));
        assertEquals(1, Storage.people.size());
        assertTrue(Storage.people.contains(marko1));
        Storage.people.clear();
    }

    @Test
    void passwordValidation_NotOk() {
        User alice = new User("alice", "0g4", 18);
        assertThrows(UsersPasswordNotValidException.class, () ->
                registrationService.register(alice));
    }

    @Test
    void ageValidation_NotOk() {
        User jacob = new User("jacob", "34432fk", 10);
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
