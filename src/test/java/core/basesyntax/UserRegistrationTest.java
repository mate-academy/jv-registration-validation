package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserRegistrationTest {
    private static final String SUCCESS_LOGIN = "SUCCESS_LOGIN";
    private static final String SUCCESS_PASSWORD = "SUCCESS_PASSWORD_123";
    private static final Integer SUCCESS_AGE = 19;
    private static final String NOT_SUCCESS_LOGIN = "short";
    private static final String NOT_SUCCESS_PASSWORD = "short";
    private static final Integer NOT_SUCCESS_AGE = 15;
    private RegistrationServiceImpl registrationService;
    private static final  User user = new User();

    @BeforeAll
    static void setUserValidParams() {
        user.setLogin(SUCCESS_LOGIN);
        user.setPassword(SUCCESS_PASSWORD);
        user.setAge(SUCCESS_AGE);
    }

    @AfterEach
    void setValidUserParams() {
        user.setLogin(SUCCESS_LOGIN);
        user.setPassword(SUCCESS_PASSWORD);
        user.setAge(SUCCESS_AGE);
        registrationService.removeByLogin(SUCCESS_LOGIN);
    }

    @BeforeEach
    void setStorageDao() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void allUserValuesValid_OK() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void nullUser_NotOK() {
        assertNull(null);
    }

    @Test
    void addUserWithNullLogin_NotOK() {
        user.setLogin(null);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithNullPassword_NotOK() {
        user.setPassword(null);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithNullAge_NotOK() {
        user.setAge(null);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithNewLogin_OK() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void addUserWithExistingLogin_NotOK() {
        registrationService.register(user);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithShortLogin_NotOK() {
        user.setLogin(NOT_SUCCESS_LOGIN);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithLongLogin_OK() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void addUserWithShortPassword_NotOK() {
        user.setPassword(NOT_SUCCESS_PASSWORD);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithLongPassword_OK() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void addUserWithInvalidAge_NotOK() {
        user.setAge(NOT_SUCCESS_AGE);
        assertNull(registrationService.register(user));
    }

    @Test
    void addUserWithValidAge_OK() {
        assertEquals(user, registrationService.register(user));
    }

}
