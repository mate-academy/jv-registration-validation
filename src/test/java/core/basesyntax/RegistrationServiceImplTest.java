package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationServiceImpl service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(20);

        User result = service.register(user);

        assertEquals(1, Storage.people.size());
        assertEquals(user, result);
        assertEquals(user, Storage.people.get(0));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword(null);
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(null);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("123");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_loginLengthFive_notOk() {
        User user = new User();
        user.setLogin("12345");
        user.setPassword("password");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = new User();
        user.setLogin("123456");
        user.setPassword("password");
        user.setAge(20);

        service.register(user);

        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("123");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_passwordLengthFive_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("12345");
        user.setAge(20);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("123456");
        user.setAge(20);

        service.register(user);

        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(-1);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(17);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_ageEighteen_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(18);

        service.register(user);

        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_ageNineteen_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(19);

        service.register(user);

        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password");
        user.setAge(20);

        Storage.people.add(user);

        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }
}