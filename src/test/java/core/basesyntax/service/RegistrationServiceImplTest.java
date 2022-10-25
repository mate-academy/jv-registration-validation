package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user;
    private RegistrationService service;

    @BeforeEach
    void beforeAll() {
        service = new RegistrationServiceImpl();
        user = new User();
        user.setPassword("123456");
        user.setAge(18);
        user.setLogin("Login");
    }

    @Test
    void passwordIsNotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void userIsOk() {
        User actual = service.register(user);
        assertTrue(actual == user);
        service.remove(actual);
    }

    @Test
    void ageIsNotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }

    @Test
    void userIsNull() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void addSameUserNotOk() {
        service.register(user);
        assertThrows(RuntimeException.class, () -> {
            service.register(user);
        });
    }
}
