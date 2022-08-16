package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.UsersAgeNotValidException;
import core.basesyntax.exceptions.UsersPasswordNotValidException;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private List<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void validUserCase_Ok() {
        User user1 = new User("marko", "gjtor3r", 20);
        User user2 = new User("franklin", "34432fk", 18);
        User user3 = new User("Brad","007009", 19);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        for (User user : users) {
            registrationService.register(user);
        }
        assertEquals(1, user1.getId());
        assertEquals(2, user2.getId());
        assertEquals(3,user3.getId());
        assertEquals(3, Storage.people.size());
        assertTrue(Storage.people.contains(new User("marko", "gjtor3r", 20)));
        assertTrue(Storage.people.contains(new User("franklin", "34432fk", 18)));
    }

    @Test
    void equalsLoginTrue_NotOk() {
        User newUser = new User("marko", "34432fk", 18);
        List<User> actual = new ArrayList<>();
        assertThrows(UserAlreadyExistException.class, () ->
                actual.add(registrationService.register(newUser)));
    }

    @Test
    void passwordValidation_NotOk() {
        User newUser = new User("alice", "0g4", 18);
        List<User> actual = new ArrayList<>();
        assertThrows(UsersPasswordNotValidException.class, () ->
                actual.add(registrationService.register(newUser)));
    }

    @Test
    void ageValidation_NotOk() {
        User newUser = new User("jacob", "34432fk", 10);
        List<User> actual = new ArrayList<>();
        assertThrows(UsersAgeNotValidException.class, () ->
                actual.add(registrationService.register(newUser)));
    }

    @Test
    void register_nullLogin_NotOk() {
        User newUser = new User(null, "34432fk", 18);
        List<User> actual = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                actual.add(registrationService.register(newUser)));
    }

    @Test
    void register_nullPassword_NotOk() {
        User newUser = new User("philip", null, 18);
        List<User> actual = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                actual.add(registrationService.register(newUser)));
    }

    @Test
    void register_nullAge_NotOk() {
        User newUser = new User("brandon", "34432fk", null);
        List<User> actual = new ArrayList<>();
        assertThrows(NullPointerException.class, () ->
                actual.add(registrationService.register(newUser)));
    }
}
