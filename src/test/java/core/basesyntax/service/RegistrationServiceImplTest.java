package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User defaultUser;
    private RegistrationService service;
    private User bohdan;
    private User dan;

    @BeforeEach
    void getService() {
        service = new RegistrationServiceImpl();
        defaultUser = new User("artemk", "12345678", 18);
        bohdan = new User("Bohdan", "password", 58);
        dan = new User("Dan", "NothingPassword", 68);
        Storage.people.clear();
    }

    @Test
    void setNullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            service.register(new User());
        });
    }

    @Test
    void setNullLoginUser_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setNullPasswordUser_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setNullAgeUser_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setMinorUserAge_notOk() {
        defaultUser.setAge(17);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setNegativeAge_notOk() {
        defaultUser.setAge(-17);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }

    @Test
    void setIllegalPasswordUser_notOk() {
        defaultUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });

    }

    @Test
    void typicalExample_Ok() {
        service.register(bohdan);
        service.register(dan);
        assertEquals(bohdan, Storage.people.get(0));
        assertEquals(dan, Storage.people.get(1));
    }

    @Test
    void addUserWithSameLogin_notOk() {
        service.register(defaultUser);
        assertThrows(RuntimeException.class, () -> {
            service.register(defaultUser);
        });
    }
}
