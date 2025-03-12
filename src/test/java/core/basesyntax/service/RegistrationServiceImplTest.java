package core.basesyntax.service;

import core.basesyntax.Expected.myExpectedClass;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private List<User> users;
    private static User nullUser;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        users = new ArrayList<>();
        nullUser = null;
        users.add(nullUser);
        users.add(createUsers("Igor228", "123455", 18));
        users.add(createUsers("Alina45", "password", 20));
        users.add(createUsers("Alina45", "password", null));
        users.add(createUsers("Alina45", null, 20));
        users.add(createUsers(null, "password2211", 20));
    }

    User createUsers(String login, String pass, Integer age) {
        User userValid = new User();
        userValid.setLogin(login);
        userValid.setPassword(pass);
        userValid.setAge(age);

        return userValid;
    }

    @Test
    void registerNull_Ok() {
        Exception exception = assertThrows(myExpectedClass.class, () -> {
            registrationService.register(users.get(0));
        });
        assertEquals("User cannot be null", exception.getMessage());
    }

    @Test
    void registerAdd_Ok() {
        registrationService.register(users.get(1));
        registrationService.register(users.get(2));
        registrationService.register(users.get(2));
        boolean actual = Storage.people.size() == 2;
        assertTrue(actual);
    }

    @Test
    void registerAdd_notOk() {

    }

    @Test
    void register_nullAge_Ok() {
        Exception exception = assertThrows(myExpectedClass.class, () -> {
            registrationService.register(users.get(3));
        });
        assertEquals("User Age cannot be null", exception.getMessage());
    }

    @Test
    void register_nullPass_Ok() {
        Exception exception = assertThrows(myExpectedClass.class, () -> {
            registrationService.register(users.get(4));
        });

        assertEquals("User Pass cannot be null", exception.getMessage());
    }

    @Test
    void register_nullLogin_Ok() {
        Exception exception = assertThrows(myExpectedClass.class, () -> {
            registrationService.register(users.get(5));
        });

        assertEquals("User Login cannot be null", exception.getMessage());
    }
}