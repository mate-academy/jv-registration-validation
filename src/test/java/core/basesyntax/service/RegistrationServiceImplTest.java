package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService validateRegistration;
    private User validUser;
    private User validUser2;
    private StorageDao data;

    @BeforeEach
    void setUp() {
        validateRegistration = new RegistrationServiceImpl();
        validUser = new User();
        validUser2 = new User();
        data = new StorageDaoImpl();
    }

    @Test
    void registerAge_isOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(22);
        validUser.setPassword("password123");
        User actual = validateRegistration.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void registerAge_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(17);
        validUser.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registerNegativeAge_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(-17);
        validUser.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registerNullAge_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(null);
        validUser.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registerPassword_isOk() {
        validUser.setLogin("loginpas@gmail.com");
        validUser.setAge(22);
        validUser.setPassword("password123");
        User actual = validateRegistration.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void registerPassword_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(22);
        validUser.setPassword("passw");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registerNullPassword_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(22);
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registerNullLogin_notOk() {
        validUser.setLogin(null);
        validUser.setAge(22);
        validUser.setPassword("21687998");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registerSameUser_notOk() {
        validUser.setLogin("loginnnn@gmail.com");
        validUser.setAge(33);
        validUser.setPassword("password1");

        validUser2.setLogin("log@gmail.com");
        validUser2.setAge(55);
        validUser2.setPassword("5879654");

        data.add(validUser);
        data.add(validUser2);
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser2));
    }
}
