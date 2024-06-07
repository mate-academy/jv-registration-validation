package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService service;

    @BeforeEach
    void init() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void registerValidUser_Ok() {
        User fitUser = new User("validLogin", "123456789", 25);
        User actual = service.register(fitUser);
        assertEquals(fitUser, actual);
    }

    @Test
    void registerNullUser_notOk() {
        User nullUser = null;
        assertThrows(RuntimeException.class, () -> {
            service.register(nullUser);
        });
    }

    @Test
    void registerUserWithTooShortPassword_notOk() {
        User peter = new User("IvanGun", "1", 25);
        assertThrows(RuntimeException.class, () -> {
            service.register(peter);
        });
    }

    @Test
    void registerTooYoungUser_notOk() {
        User young = new User("PeterPen", "123456", 16);
        assertThrows(RuntimeException.class, () -> {
            service.register(young);
        });
    }

    @Test
    void registerUserWithTooShortLogin_notOk() {
        User peter = new User("I", "123456", 25);
        assertThrows(RuntimeException.class, () -> {
            service.register(peter);
        });
    }

    @Test
    void registerTheSameUserLogin_notOk() {
        User first = new User("validLogin", "123456789", 25);
        User second = new User("validLogin", "987654321", 52);
        assertThrows(RuntimeException.class, () -> {
            service.register(second);
        });
    }
}
