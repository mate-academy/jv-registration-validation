package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static final int EIGHTEEN_AGE = 18;
    private static final int SEVENTEEN_AGE = 17;
    private static final String LOGIN = "login";
    private static final String REPEAT_LOGIN = "repeat-login";
    private static final String CORRECT_PASSWORD = "1234567";
    private static final String INCORRECT_PASSWORD = "12345";

    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeEach
    public void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerWithAllDataOk() {
        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(CORRECT_PASSWORD);

        User created = registrationService.register(inputUser);

        assertEquals(inputUser.getAge(), created.getAge());
        assertEquals(inputUser.getLogin(), created.getLogin());
        assertEquals(inputUser.getPassword(), created.getPassword());
    }

    @Test
    void registerWithAllDataNullNotOk() {
        User inputUser = new User();

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
        User firstUser = new User();
        firstUser.setAge(EIGHTEEN_AGE);
        firstUser.setLogin(REPEAT_LOGIN);
        firstUser.setPassword(CORRECT_PASSWORD);

        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(REPEAT_LOGIN);
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
        User inputUser = new User();
        inputUser.setAge(EIGHTEEN_AGE);
        inputUser.setLogin(LOGIN);
        inputUser.setPassword(INCORRECT_PASSWORD);

        assertThrows(RuntimeException.class, () -> registrationService.register(inputUser));
    }
}
