package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_blankLogin_notOk() {
        user.setLogin("   ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_blankPassword_notOk() {
        user.setPassword("   ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-13);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanMinAge_notOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_equalAge_isOk() {
        user = new User();
        user.setLogin("login123");
        user.setAge(18);
        user.setPassword("24pass");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_repeatedLogin_NotOk() {
        user.setLogin("login123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_overAge_isOk() {
        user = new User();
        user.setLogin("login678");
        user.setAge(39);
        user.setPassword("34login_password");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_goodUser_isOk() {
        user.setLogin("new_login");
        user.setAge(44);
        user.setPassword("90login_password");
        User expected = user;
        User actual = registrationService.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_oneMoreUser_isOk() {
        user = new User();
        user.setLogin("user_login");
        user.setAge(24);
        user.setPassword("user_password");
        assertEquals(user, registrationService.register(user));
    }
}
