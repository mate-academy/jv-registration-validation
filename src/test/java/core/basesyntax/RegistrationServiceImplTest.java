package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static RegistrationService registrationService;

    @BeforeAll
    public static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_WithAllData_Ok() {
        User inputUser = new User();
        inputUser.setAge(18);
        inputUser.setLogin("login");
        inputUser.setPassword("12345678");

        User created = registrationService.register(inputUser);

        assertEquals(inputUser, created);
    }

    @Test
    void register_WithAgeLessThenEighteen_NotOk() {
        User inputUser = new User();
        inputUser.setAge(17);
        inputUser.setLogin("login");
        inputUser.setPassword("12345678");

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void register_UserWhenAgeIsNull_NotOk() {
        User inputUser = new User();
        inputUser.setAge(null);
        inputUser.setLogin("login");
        inputUser.setPassword("12345678");

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void register_UserWhenPasswordIsNull_NotOk() {
        User inputUser = new User();
        inputUser.setAge(18);
        inputUser.setLogin("login");
        inputUser.setPassword(null);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void register_UserWhenLoginAlreadyTaken_NotOk() {
        String repeatLogin = "repeat-login";
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin(repeatLogin);
        firstUser.setPassword("12345678");

        User inputUser = new User();
        inputUser.setAge(18);
        inputUser.setLogin(repeatLogin);
        inputUser.setPassword("12345678");

        registrationService.register(firstUser);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void register_UserWhenLoginIsNull_NotOk() {
        User inputUser = new User();
        inputUser.setAge(18);
        inputUser.setLogin(null);
        inputUser.setPassword("12345678");

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void register_WithPasswordLengthLessThenSix_NotOk() {
        final String incorrectPassword = "12345";
        User inputUser = new User();
        inputUser.setAge(18);
        inputUser.setLogin("login");
        inputUser.setPassword(incorrectPassword);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }
}
