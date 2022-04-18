package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private final RegistrationService validateRegistration = new RegistrationServiceImpl();
    private final User validUser = new User();
    private final User validUser2 = new User();
    private final StorageDao data = new StorageDaoImpl();

    @Test
    void age_isOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(22);
        validUser.setPassword("password123");
        User actual = validateRegistration.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void age_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(17);
        validUser.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void negativeAge_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(-17);
        validUser.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void nullAge_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(null);
        validUser.setPassword("password123");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void password_isOk() {
        validUser.setLogin("loginpas@gmail.com");
        validUser.setAge(22);
        validUser.setPassword("password123");
        User actual = validateRegistration.register(validUser);
        assertEquals(validUser, actual);
    }

    @Test
    void password_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(22);
        validUser.setPassword("passw");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void nullPassword_notOk() {
        validUser.setLogin("login@gmail.com");
        validUser.setAge(22);
        validUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void nullLogin_notOk() {
        validUser.setLogin(null);
        validUser.setAge(22);
        validUser.setPassword("21687998");
        assertThrows(RuntimeException.class, () -> validateRegistration.register(validUser));
    }

    @Test
    void registrationUserWhenUserDataBase_isOk() {
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
