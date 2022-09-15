package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrServ = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("userLogin");
        user.setPassword("userPassword123");
        user.setAge(19);
        Storage.people.clear();
    }

    @Test
    void register_UserIsNull_NotOk() {
        user = null;
        assertThrows(RuntimeException.class,
                () -> registrServ.register(user));
    }

    @Test
    void register_UserThatAlreadyExist_NotOk() {
        registrServ.register(user);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_UserLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_UserAge16_NotOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_UserPasswordLength2_NotOk() {
        user.setPassword("12");
        assertThrows(RuntimeException.class, () -> registrServ.register(user));
    }

    @Test
    void register_User_Ok() {
        assertEquals(registrServ.register(user), user);
    }
}
