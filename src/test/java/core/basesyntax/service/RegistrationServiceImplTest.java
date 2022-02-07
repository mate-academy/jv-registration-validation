package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User registrateFirstUser;
    private User registrateNotSameLoginUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        registrateFirstUser = new User();
        registrateNotSameLoginUser = new User();
        registrateFirstUser.setLogin("nafania");
        registrateFirstUser.setPassword("oldDogBob");
        registrateFirstUser.setAge(25);
        registrateNotSameLoginUser.setLogin("nafaniaNotSame");
        registrateNotSameLoginUser.setPassword("youngBob");
        registrateNotSameLoginUser.setAge(20);
    }

    @Test
    void register_ageUser_notOk() {
        registrateFirstUser.setAge(16);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registrateFirstUser);
        });
    }

    @Test
    void register_passwordUser_notOk() {
        registrateFirstUser.setPassword("hello");
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registrateFirstUser);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullAge_notOk() {
        registrateFirstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registrateFirstUser);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        registrateFirstUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registrateFirstUser);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        registrateFirstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registrateFirstUser);
        });
    }

    @Test
    void register_notSuccess() {
        registrationService.register(registrateFirstUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(registrateFirstUser);
        });
    }

    @Test
    void register_Success() {
        registrationService.register(registrateNotSameLoginUser);
    }
}
