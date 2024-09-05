package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static List<User> users;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        users = new ArrayList<>();
        users.add(new User("UserX2023", "pass6789", 35));
        users.add(new User("JohnDoe23", "john4567", 28));
        users.add(new User("Anna1998", "password123", 25));
    }

    @Test
    void userNotInDb_isOK() {
        User actual = registrationService.register(new User("Ivan2004", "1234567", 20));
        assertEquals(new User("Ivan2004", "1234567", 20), actual);
    }

    @Test
    void userAge_isNotOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Ivan2004", "1234567", 10));
        });
    }

    @Test
    void userLogin_isNotOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Ivan", "1234567", 18));
        });
    }

    @Test
    void userPassword_isNotOK() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(new User("Ivan2004", "123", 18));
        });
    }

    @Test
    void userIsNull() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void userInDb_isNotOK() {
        registrationService.register(
                new User("JohnDoe", "john123", 28));
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(
                    new User("JohnDoe", "john123", 28));
        });
    }

    @Test
    void userAll_isOK() {
        List<User> actual = new ArrayList<>();
        for (User user : users) {
            User userIsRegister = registrationService.register(user);
            actual.add(userIsRegister);
        }
        assertEquals(users, actual);
    }
}
