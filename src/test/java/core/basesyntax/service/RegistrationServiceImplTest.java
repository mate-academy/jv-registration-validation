package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.UserAlreadyExistException;
import core.basesyntax.exceptions.UsersAgeNotValidException;
import core.basesyntax.exceptions.UsersPasswordNotValidException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }


    @Test
    void validUserCase_Ok() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User("franklin", "34432fk", 18));
        for (User user : users) {
            registrationService.register(user);
        }
        assertEquals(2, Storage.people.size());
        assertTrue(Storage.people.contains(new User("marko", "gjtor3r", 20)));
        assertTrue(Storage.people.contains(new User("franklin", "34432fk", 18)));
    }

    @Test
    void equalsLogin_NotOk() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User("marko", "34432fk", 18));
        List<User> actual = new ArrayList<>();
        assertThrows(UserAlreadyExistException.class, () -> {
            for (User user : users) {
                User current = registrationService.register(user);
                actual.add(current);
            }
        });
    }

    @Test
    void passwordValidation_NotOk() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User("alice", "0g4", 18));
        List<User> actual = new ArrayList<>();
        assertThrows(UsersPasswordNotValidException.class, () -> {
            for (User user : users) {
                User current = registrationService.register(user);
                actual.add(current);
            }
        });
    }

    @Test
    void ageValidation_NotOk() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User("alice", "34432fk", 10));
        List<User> actual = new ArrayList<>();
        assertThrows(UsersAgeNotValidException.class, () -> {
            for (User user : users) {
                User current = registrationService.register(user);
                actual.add(current);
            }
        });
    }

    @Test
    void nullLogin_NotOk() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User(null, "34432fk", 18));
        List<User> actual = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> {
            for (User user : users) {
                User current = registrationService.register(user);
                actual.add(current);
            }
        });
    }

    @Test
    void nullPassword_NotOk() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User("marko", null, 18));
        List<User> actual = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> {
            for (User user : users) {
                User current = registrationService.register(user);
                actual.add(current);
            }
        });
    }

    @Test
    void nullAge_NotOk() {
        List<User> users = new ArrayList<>();
        users.add(new User("marko", "gjtor3r", 20));
        users.add(new User("marko", "34432fk", null));
        List<User> actual = new ArrayList<>();
        assertThrows(NullPointerException.class, () -> {
            for (User user : users) {
                User current = registrationService.register(user);
                actual.add(current);
            }
        });
    }
}