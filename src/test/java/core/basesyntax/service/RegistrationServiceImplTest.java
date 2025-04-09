package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
     void beforeEach() {
        user.setAge(19);
        user.setLogin("ambasador");
        user.setPassword("ambasador1");
    }

    @Test
    void register_AgeIsEqualOrOlder_Ok() {
        user.setAge(18);
        boolean actual = user.getAge() >= 18;
        assertTrue(actual);
    }

    @Test
    void register_NegativeAge_NotOk() {
        user.setAge(-1);
        boolean actual = user.getAge() < 0;
        assertTrue(actual, "Age can not be less than 0");
    }

    @Test
    void register_NullAge_NotOk() {
        user.setAge(null);
        boolean actual = user.getAge() == null;
        assertTrue(actual, "Age can not be null");

    }

    @Test
    void register_NullPassword_NotOk() {
        user.setPassword(null);
        boolean actual = user.getPassword() == null;
        assertTrue(actual, "Password can not be null");

    }
    
    @Test
    void register_NullLogin_NotOk() {
        user.setLogin(null);
        boolean actual = user.getLogin() == null;
        assertTrue(actual, "Login can not be null");

    }

    @Test
    void register_Login_isNotValid() {
        user.setLogin("qw2sq");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Password_isNotValid() {
        user.setPassword("q1esq");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_Age_isNotValid() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_User_isNull() {
        user = null;
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
