package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private List<User> users;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(20);
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_NotOk() {
        user.setAge(null);
        System.out.println("age1");
        System.out.println(user.getLogin());
        assertThrows(NullPointerException.class, () -> registrationService.register(user));

    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void uniqueLogin_Ok() {
        user.setLogin("Login");
        User userTwo = new User();
        userTwo.setLogin("Login");
        userTwo.setAge(25);
        userTwo.setPassword("passwordTwo");
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(userTwo));
    }

    @Test
    void correctAge_Ok() {
        user.setAge(20);
        int expectedAge = user.getAge();
        int actualAge = registrationService.register(user).getAge();
        assertEquals(expectedAge, actualAge);
    }

    @Test
    void register_ageIsLessThan18_NotOk() {
        User user = new User();
        user.setAge(17);
        user.setPassword("password");
        user.setLogin("Login");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, ", Expect an RuntimeException"
                + " when trying to register a user with age less then 18 years old");
    }

    @Test
    void passwordStrength_NotOk() {
        user.setPassword("dssfs");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
    @Test
    void register_passwordIsEmptyString_notOk() {
        User user = new User();
        user.setAge(61);
        user.setPassword("");
        user.setLogin("Login");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, ", Expect an RuntimeException when trying to register a user with password is empty");
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setAge(61);
        user.setLogin("Login");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, ", Expect an RuntimeException when trying to register a user with password is NULL");
    }
}
