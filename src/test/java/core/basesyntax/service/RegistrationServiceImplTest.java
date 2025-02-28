package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("romander");
        user.setPassword("123456");
        user.setAge(20);;
    }

    @Test
    void register_shortLogin_NotOk() {
        user.setLogin("roman");
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_shortPassword_NotOk() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_youngAge_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void register_userContains_NotOk() {
        User otherUser = new User();
        otherUser.setLogin("romander");
        otherUser.setPassword("123456");
        otherUser.setAge(20);
        Storage.people.add(otherUser);
        assertThrows(RegistrationException.class, () -> {
            service.register(otherUser);
        });
    }

}
