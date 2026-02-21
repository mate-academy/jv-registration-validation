package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl registrationServiceImpl = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_userLoginIsNull_NotOK() {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userLoginIs6_Ok() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytram");
        user.setAge(18);
        User result = registrationServiceImpl.register(user);
        assertEquals(user, result);
    }

    @Test
    void register_userLoginIs5orLess_NotOK() {
        User user = new User();
        user.setLogin("marty");
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userLoginAlreadyExists_NotOk() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytram");
        user.setAge(18);
        registrationServiceImpl.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userPassordIsNull_NotOK() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userPassordIs6_Ok() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytra");
        user.setAge(18);
        User result = registrationServiceImpl.register(user);
        assertEquals(user, result);
    }

    @Test
    void register_userPassordIs5orLess_NotOK() {
        User user = new User();
        user.setLogin("martynaaa");
        user.setAge(18);
        user.setPassword("anytr");
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userAgeIsNull_NotOK() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytram");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userAgeIs18_Ok() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytram");
        user.setAge(18);
        User result = registrationServiceImpl.register(user);
        assertEquals(user, result);
    }

    @Test
    void register_userAgeIsUnder18_NotOK() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytram");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(user);
        });
    }

    @Test
    void register_userIsNull_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationServiceImpl.register(null);
        });
    }

    @Test
    void register_userPassedAllRequirements_Ok() {
        User user = new User();
        user.setLogin("martyna");
        user.setPassword("anytram");
        user.setAge(18);
        User result = registrationServiceImpl.register(user);
        assertEquals(user, result);
    }
}
