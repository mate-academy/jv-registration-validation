package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static final int EIGHTEEN_AGE = 18;
    private static final int SEVENTEEN_AGE = 17;
    private static final String LOGIN = "login";
    private static final String CORRECT_PASSWORD = "1234567";

    private static RegistrationService registrationService;

    @BeforeAll
    public static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerWithAllDataOk() {
        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(CORRECT_PASSWORD);

        User created = registrationService.register(inputUser);

        assertEquals(inputUser, created);
    }

    @Test
    void registerWithAllDataNullNotOk() {
        User inputUser = new User();
        inputUser.setPassword(null);
        inputUser.setLogin(null);
        inputUser.setAge(null);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void registerWithAgeLessThenEighteenNotOk() {
        User inputUser = new User();
        inputUser.setAge(SEVENTEEN_AGE);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(CORRECT_PASSWORD);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void registerUserWhenAgeIsNullNotOk() {
        User inputUser = new User();
        inputUser.setAge(null);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(CORRECT_PASSWORD);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void registerUserWhenPasswordIsNullNotOk() {
        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(null);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void registerUserWhenLoginAlreadyTakenNotOk() {
        final String repeatLogin = "repeat-login";
        User firstUser = new User();
        firstUser.setAge(EIGHTEEN_AGE);
        firstUser.setLogin(repeatLogin);
        firstUser.setPassword(CORRECT_PASSWORD);

        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(repeatLogin);
        inputUser.setPassword(CORRECT_PASSWORD);

        registrationService.register(firstUser);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void registerUserWhenLoginIsNullNotOk() {
        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(null);
        inputUser.setPassword(CORRECT_PASSWORD);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }

    @Test
    void registerWithPasswordLengthLessThenSixNotOk() {
        final String incorrectPassword = "12345";
        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(incorrectPassword);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }
}
