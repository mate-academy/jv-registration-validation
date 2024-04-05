package service;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    RegistrationServiceImpl registrationService;
    User standartUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        standartUser = new User("PAMPERS", "12345678", 20);
    }

    @Test
    void register_User_Ok() {
        registrationService.register(standartUser);
    }

    @Test
    void register_SameUser_NotOk() {
        User user = new User("loginn", "password", 18);
        User sameUser = new User("loginn", "password", 18);
        registrationService.register(user);
        assertThrows(UserException.class, () -> {
            registrationService.register(sameUser);
        });
    }

    @Test
    void register_NullPassword_NotOk() {
        standartUser.setPassword(null);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_IncorrectPassword_NotOk() {
        standartUser.setPassword("1");
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_NullLogin_NotOk() {
        standartUser.setLogin(null);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_IncorrectLogin_NotOk() {
        standartUser.setLogin("A");
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_NullAge_NotOk() {
        standartUser.setAge(null);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }

    @Test
    void register_IncorrectAge_NotOk() {
        standartUser.setAge(12);
        assertThrows(UserException.class, () -> {
            registrationService.register(standartUser);
        });
    }
}