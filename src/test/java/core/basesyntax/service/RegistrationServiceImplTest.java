package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user;
    private Storage storage;

    @BeforeEach
    public void setup() {
        storage = new Storage();
        user = new User();
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(OwnRuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(OwnRuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void nullAge_NotOk() {
        user.setAge(null);
        assertThrows(OwnRuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void smallerThenEighteen_NotOk() {
        user.setAge(17);
        assertThrows(OwnRuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registeredUser_NotOk() {
        boolean actual = storage.people.contains(user.getLogin());
        assertFalse(actual);
    }

    @Test
    void passwordSmallerThenSix_NotOk() {
        user.setPassword("qwer");
        assertThrows(OwnRuntimeException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void loginSmallerThenSix_NotOk() {
        user.setLogin("Bodya");
        assertThrows(OwnRuntimeException.class, () -> {
            registrationService.register(user);
        });
    }
}
