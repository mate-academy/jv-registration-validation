package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HelloWorldTest {
    private static final String CORRECT_LOGIN = "test_12345";
    private static final String CORRECT_PASSWORD = "Test_12345";
    private static final int CORRECT_AGE = 25;
    private static final String NOT_CORRECT_LOGIN = "test";
    private static final String NOT_CORRECT_PASSWORD = "12345";
    private static final int NOT_CORRECT_AGE = 25;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @Test
    void testValidPassword_shortPassword_notOk() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(NOT_CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void testValidLogin_shortLogin_notOk() {
        user.setLogin(NOT_CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void testValidAge_lowAge_notOk() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(NOT_CORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void testValidPassword_nullPassword_notOk() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(NOT_CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void testValidLogin_nullLogin_notOk() {
        user.setLogin(NOT_CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void testValidAge_nullAge_notOk() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(NOT_CORRECT_AGE);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    void testRegister_registrationSomeUser_notOk() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        registrationService.register(user);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void normalRegister_returnsUserSuccess_Ok() {
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        User actual = registrationService.register(user);
        assertEquals(User.class, actual.getClass());
    }
}
