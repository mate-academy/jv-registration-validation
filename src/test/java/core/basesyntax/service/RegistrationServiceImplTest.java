package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User Bohdan = new User("Bohdan", "password", 58);
    private static final User Dan = new User("Dan", "NothingPassword", 68);
    private User defaultUser;
    private RegistrationService service;

    @BeforeEach
    void getService() {
        RegistrationService service = new RegistrationServiceImpl();
        User defaultUser = new User("artemk", "12345678", 18);
    }

    @Test
    void setNullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(null);
        });
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void setNullLoginUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            defaultUser.setLogin(null);
            service.register(defaultUser);
        });
    }

    @Test
    void setNullPasswordUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            defaultUser.setPassword(null);
            service.register(defaultUser);
        });
    }

    @Test
    void setNullAgeUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            defaultUser.setAge(null);
            service.register(defaultUser);
        });
    }

    @Test
    void setMinorUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            defaultUser.setAge(17);
            service.register(defaultUser);
            defaultUser.setAge(-17);
            service.register(defaultUser);
        });
    }

    @Test
    void setIllegalPasswordUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            defaultUser.setPassword("123");
            service.register(defaultUser);
        });
    }

    @Test
    void goodExampleUser() {
        RegistrationService service = new RegistrationServiceImpl();
        service.register(Bohdan);
        service.register(Dan);
        assertFalse(Storage.people.isEmpty());
        List<User> users = List.of(Bohdan, Dan);
        assertEquals(users.size(), Storage.people.size());
    }
}
