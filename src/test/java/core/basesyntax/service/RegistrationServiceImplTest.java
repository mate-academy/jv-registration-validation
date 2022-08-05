package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin("user123@gmail.com");
        user.setPassword("psw123456+");
        user.setAge(18);
    }

    @Test
    void register_validUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual, user, "The registration of the user has failed.");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginExists_notOk() {
        User userExist = new User();
        userExist.setLogin(user.getLogin());
        userExist.setPassword("1234567");
        userExist.setAge(20);
        assertThrows(RuntimeException.class, () -> registrationService.register(userExist));
    }

    @Test
    void register_notValidAge_notOk() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_notValidPasswordLength_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_notValidPasswordWithSpaces_notOk() {
        user.setPassword("       ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
