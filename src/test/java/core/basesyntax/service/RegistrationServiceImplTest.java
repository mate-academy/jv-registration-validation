package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.AgeRestrictionException;
import core.basesyntax.exception.PasswordException;
import core.basesyntax.exception.PasswordLengthException;
import core.basesyntax.exception.UserLoginException;
import core.basesyntax.exception.UserParamException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("login!");
        user.setPassword("password");
        user.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_ok() {
        Assertions.assertNotNull(registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(UserParamException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_similarLoginUser_notOk() {
        registrationService.register(user);
        Assertions.assertThrows(UserLoginException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(UserLoginException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_lessThanSixSymbolPassword_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(PasswordLengthException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(PasswordException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(17);
        Assertions.assertThrows(AgeRestrictionException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(AgeRestrictionException.class, () -> {
            registrationService.register(user);
        });
    }
}
