package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private RegistrationServiceImpl service = new RegistrationServiceImpl();
    private User user;

    @Test
    void register_nullUser_notOK() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void register_nullAge_notOK() {
        user = new User("Bob", "password", null);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_sameLogin_notOK() {
        user = new User("Bob", "password", 21);
        service.register(user);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userAge_notOK() {
        user = new User("Bob", "password", 15);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userAge_OK() {
        User actual = new User("Alice", "password", 20);
        User expected = service.register(actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    void register_passwordNull_notOK() {
        user = new User("Bob", null, 22);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordLength_notOK() {
        user = new User("Bob", "12345", 23);
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
