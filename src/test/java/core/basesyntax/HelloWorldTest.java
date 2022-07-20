package core.basesyntax;

import static core.basesyntax.service.RegistrationServiceImpl.MIN_PASSWORD_LENGTH;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_USER_AGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static RegistrationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOK() {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
        assertEquals("User can't be null.", thrown.getMessage());
    }

    @Test
    void register_nullAge_notOK() {
        User user = new User("Bob1", "password", null);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertEquals("User age can't be null.", thrown.getMessage());
    }

    @Test
    void register_sameLogin_notOK() {
        User user = new User("Bob", "password", 21);
        service.register(user);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertEquals("User with this login already exists.", thrown.getMessage());
    }

    @Test
    void register_userAge_notOK() {
        User user = new User("Bob2", "password", 15);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertEquals("User age can't be less than "
                + MIN_USER_AGE + " or be negative.", thrown.getMessage());
    }

    @Test
    void register_userAge_OK() {
        User actual = new User("Alice", "password", 20);
        User expected = service.register(actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    void register_passwordNull_notOK() {
        User user = new User("Bob3", null, 22);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertEquals("Password can't be null.", thrown.getMessage());
    }

    @Test
    void register_passwordLength_notOK() {
        User user = new User("Bob4", "12345", 23);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
        assertEquals("Password can't be less than "
                + MIN_PASSWORD_LENGTH + " symbols.", thrown.getMessage());
    }

    @Test
    void register_userAdd_OK() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("Name0", "password", 18));
        userList.add(new User("Name1", "password", 20));
        userList.add(new User("Name2", "password", 66));
        userList.add(new User("Name3", "password", 25));
        for (User actual: userList) {
            User expected = service.register(actual);
            assertTrue(actual.equals(expected));
        }
    }
}
