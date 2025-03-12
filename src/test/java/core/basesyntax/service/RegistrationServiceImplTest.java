package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private List<User> users;
    private static User nullUser;
    private static RegistrationService registrationServise;

    @BeforeAll
    static void BeforeAll() {
        registrationServise = new RegistrationServiceImpl();
    }

    @BeforeEach
    void BeforeEach() {
        users = new ArrayList<>();
        nullUser = null;
        users.add(registrationServise.register(nullUser));
        users.add(create_Users("Igor228", "123455", 18));
        users.add(create_Users("Alina45", "password", 20));
        users.add(create_Users("Alina45", "password", null));
        users.add(create_Users("Alina45", null, 20));
        users.add(create_Users(null, "password2211", 20));
    }

    User create_Users(String login, String pass, Integer age) {
        User userValid = new User();
        userValid.setLogin(login);
        userValid.setPassword(pass);
        userValid.setAge(age);

        return userValid;
    }

    @Test
    void registerNull_Ok() {
        User actual = registrationServise.register(nullUser);
        assertNull(actual);
    }

    @Test
    void registerAdd_Ok() {
        registrationServise.register(nullUser);
        registrationServise.register(users.get(1));
        registrationServise.register(users.get(2));
        registrationServise.register(users.get(2));
        boolean actual = Storage.people.size() == 2;
        assertTrue(actual);
    }

    @Test
    void register_nullAge_notOk() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            registrationServise.register(users.get(3));
        });
        assertEquals("null Age", exception.getMessage());
    }

    @Test
    void register_nullPass_notOk() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            registrationServise.register(users.get(4));
        });

        assertEquals("null Pass", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        Exception exception = assertThrows(NullPointerException.class, () -> {
            registrationServise.register(users.get(5));
        });

        assertEquals("null Login", exception.getMessage());
    }
}