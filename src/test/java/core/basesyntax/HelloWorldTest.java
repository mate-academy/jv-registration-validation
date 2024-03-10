package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.RegistrationFailureException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_addUser_Ok() {
        User user = new User();
        user.setLogin("Marco123");
        user.setPassword("12345678");
        user.setAge(20);

        User result = registrationService.register(user);
        User storageUser = registrationService.getUser(user.getLogin());
        assertEquals(storageUser, result);
    }

    @Test
    void register_passwordEdges_Ok() {
        User user1 = new User();
        user1.setLogin("Marco1");
        user1.setPassword("123456");
        user1.setAge(27);

        User result = registrationService.register(user1);
        assertEquals(result, user1);

        User user2 = new User();
        user2.setLogin("Parmezan");
        user2.setPassword("12345678910");
        user2.setAge(50);
        result = registrationService.register(user2);
        assertEquals(result, user2);
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addIdenticalUsers_notOk() {
        User user = new User();
        user.setLogin("Antonio");
        user.setPassword("12345678");
        user.setAge(23);

        registrationService.register(user);
        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addYoungUser_notOk() {
        User user1 = new User();
        user1.setLogin("Martin77");
        user1.setPassword("12345678");
        user1.setAge(15);
        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user1));

        User user2 = new User();
        user2.setLogin("Kartoplia");
        user2.setPassword("12345678");
        user2.setAge(0);
        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(23);

        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("Arturitto");
        user.setPassword(null);
        user.setAge(72);

        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(30);

        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("Login");
        user.setPassword("12345678");
        user.setAge(45);

        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("Hello!");
        user.setPassword("1234");
        user.setAge(19);

        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }

    @Test
    void register_allParametersNull_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);

        assertThrows(RegistrationFailureException.class, () -> registrationService.register(user));
    }
}
