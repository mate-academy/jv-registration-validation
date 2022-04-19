package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService validateRegistration = new RegistrationServiceImpl();
    private final StorageDao data = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_age_isOk() {
        user.setLogin("login@gmail.com");
        user.setAge(22);
        user.setPassword("password123");
        User actual = validateRegistration.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_age_notOk() {
        user.setLogin("login@gmail.com");
        user.setAge(17);
        user.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setLogin("login@gmail.com");
        user.setAge(-17);
        user.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("login@gmail.com");
        user.setAge(null);
        user.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }

    @Test
    void register_password_isOk() {
        user.setLogin("loginpas@gmail.com");
        user.setAge(22);
        user.setPassword("password123");
        User actual = validateRegistration.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_password_notOk() {
        user.setLogin("login@gmail.com");
        user.setAge(22);
        user.setPassword("passw");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("login@gmail.com");
        user.setAge(22);
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setAge(22);
        user.setPassword("21687998");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }

    @Test
    void register_sameUser_notOk() {
        user.setLogin("loginnnn@gmail.com");
        user.setAge(33);
        user.setPassword("password1");
        data.add(user);
        assertThrows(RuntimeException.class, () -> validateRegistration.register(user));
    }
}
