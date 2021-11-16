package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Feel free to remove this class and create your own.
 */
public class HelloWorldTest {
    private static User user;
    private static RegistrationService registration;

    @BeforeAll
    static void beforeAll() {
        user = new User();
        registration= new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user.setAge(19);
        user.setLogin("Login");
        user.setPassword("Password");
    }


    @Test
    public void register_returnNull_NotOk() {
        User accepted = null;
        assertThrows(RuntimeException.class, () -> registration.register(null));
    }

    @Test
    public void register_userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registration.register(null));
    }

    @Test
    public void register_passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_ageNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_loginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_invalidAge_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_invalidPassword_NotOk() {
        user.setPassword("notOk");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_validUser_Ok() {
        User expected = user;
        User actual = registration.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_passwordIsEmptyBetweenLine_NotOk() {
        user.setPassword("is emptyLine");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void register_loginEmptyBetweenLine_NotOk() {
        user.setLogin("Empty Line");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }
}
