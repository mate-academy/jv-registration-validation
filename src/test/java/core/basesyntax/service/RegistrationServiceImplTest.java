package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 20;
    private static final long VALID_ID = 123883022L;
    private static final String VALID_LOGIN = "UserName";
    private static final String VALID_PASSWORD = "BestPasswordEver123";
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setId(VALID_ID);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
    }

    @Test
    void register_lowerThanExpectedAge_NotOk() {
        int age = 17;
        user.setAge(age);
        assertTrue(user.getAge() < 18);
    }

    @Test
    void register_equalsToExpectedAge_Ok() {
        int age = 18;
        user.setAge(age);
        assertEquals(user.getAge(), 18);
    }

    @Test
    void register_graterThanExpectedAge_Ok() {
        int age = 19;
        user.setAge(age);
        assertFalse(user.getAge() < 18);
    }

    @Test
    void register_negativeAge_notOk() {
        int age = -18;
        user.setAge(age);
        assertTrue(user.getAge() < 0);
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Password can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_emptyPassword_NotOk() {
        String password = "";
        user.setPassword(password);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Password can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_lengthOfPasswordIs3_NotOk() {
        String password = "asd";
        user.setPassword(password);
        assertTrue(user.getPassword().length() < 6);
    }

    @Test
    void register_lengthOfPasswordIs5_NotOk() {
        String password = "small";
        user.setPassword(password);
        assertTrue(user.getPassword().length() < 6);
    }

    @Test
    void register_lengthOfPasswordEqualsTo6_Ok() {
        String password = "small1";
        user.setPassword(password);
        assertTrue(user.getPassword().length() <= 6);
    }

    @Test
    void register_lengthOfPasswordGreaterThan6_Ok() {
        String password = "theBiggestPassword";
        user.setPassword(password);
        assertTrue(user.getPassword().length() > 6);
    }

    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Login can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Login can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        Exception exception = assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        String expected = "Age can't be null";
        String actual = exception.getMessage();
        assertTrue(actual.contains(expected));
    }
}

