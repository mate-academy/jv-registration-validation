package core.basesyntax;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void userNull_NotOk() {
        assertThrows(RuntimeException.class, () -> registration.register(null));
    }

    @Test
    public void passwordNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void ageNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void loginNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void invalidAge_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void invalidPassword_NotOk() {
        user.setPassword("notOk");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void validUser_Ok() {
        User expected = user;
        User actual = registration.register(user);
        assertEquals(expected, actual);
    }

    @Test
    void loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void passwordIsEmptyLine_NotOk() {
        user.setPassword("is emptyLine");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }

    @Test
    public void loginEmptyLine_NotOk() {
        user.setLogin("Empty Line");
        assertThrows(RuntimeException.class, () -> registration.register(user));
    }
}
