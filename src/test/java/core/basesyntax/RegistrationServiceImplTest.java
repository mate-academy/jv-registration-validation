package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String LOGIN_IS_OK = "Vasyl";
    private static final String PASSWORD_IS_OK = "123456";
    private static final int AGE_IS_OK = 18;
    private static RegistrationServiceImpl registrationService;
    private User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(LOGIN_IS_OK);
        user.setPassword(PASSWORD_IS_OK);
        user.setAge(AGE_IS_OK);
    }

    @Test
    void register_existsLogin_notOk() {
        User actualUser = new User();
        actualUser.setLogin("Vasyl");
        actualUser.setPassword("123456");
        actualUser.setAge(18);
        registrationService.register(actualUser);
        assertEquals(actualUser, user);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        User actualUser = new User();
        actualUser.setLogin("Moshun");
        actualUser.setPassword("123456");
        actualUser.setAge(18);
        assertTrue(((actualUser.getLogin() != null)
                && (actualUser.getPassword().length() >= PASSWORD_IS_OK.length())
                && (actualUser.getAge() >= AGE_IS_OK)));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_lessAge() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        System.out.println(user.getLogin().isEmpty());
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }
}
