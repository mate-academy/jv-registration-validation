package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "user_Lili";
    private static final String VALID_PASSWORD = "password";
    private static final int VALID_AGE = 24;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @Test
    void register_validData_ok() {
        registrationService = new RegistrationServiceImpl();
        User actual = user;
        assertEquals(registrationService.register(user), actual);
    }

    @Test
    void register_notValidAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
        user.setAge(0);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
        user.setAge(116);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_notValidPassword_notOk() {
        user.setPassword("123");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_existingLogin_notOk() {
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword("12345678");
        newUser.setAge(24);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(newUser)
        );
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
    }
}
