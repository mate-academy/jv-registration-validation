package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void setUpFirst() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUpEach() {
        user = new User("gerasimov", "wevrewvre", 18);
    }

    @Test
    void register_age_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void register_password_NotOk() {
        user.setPassword("rehba");
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            registration.register(null);
        });
    }

    @Test
    void register_Duplicate_NotOk() {
        registration.register(user);
        assertThrows(RuntimeException.class, () -> {
            registration.register(user);
        });
    }
}
