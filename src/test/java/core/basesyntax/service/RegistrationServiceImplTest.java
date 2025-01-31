package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_NullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPassword");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_LoginLengthLessThen6_NotOk() {
        User user = new User();
        user.setLogin("abcd");
        user.setPassword("validPassword");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_UserWithLoginExists_NotOk() {
        User storedUser = new User();
        storedUser.setLogin("existingLogin");
        storedUser.setPassword("validPassword");
        storedUser.setAge(25);
        Storage.people.add(storedUser);
        User user = new User();
        user.setLogin("existingLogin");
        user.setPassword("validPassword");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_NullPassword_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_PasswordLengthLessThan6_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abc");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_NullAge_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_AgeLessThan18_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_NegativeAge_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(-18);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_Age18_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(18);
        assertEquals(user, service.register(user));
    }
}
