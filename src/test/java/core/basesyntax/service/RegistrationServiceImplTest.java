package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(24);
        user.setLogin("abababalamaga");
        user.setPassword("19840321");
        user.setId(59L);
    }

    @Test
    void register_isNotAdult_notOk() {
        user.setAge(14);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNotValid_notOk() {
        user.setAge(-50);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLength_notOk() {
        user.setPassword("666");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isPasswordEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithExistingLogin_notOk() {
        registrationService.register(user);
        User current = new User();
        current.setAge(25);
        current.setLogin("abababalamaga");
        current.setPassword("blablabla");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

}
