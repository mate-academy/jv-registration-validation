package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    void register_StorageHasUserWithSuchLogin_NotOk() {
        User storedUser = new User();
        storedUser.setLogin("existingUser");
        storedUser.setPassword("validPassword");
        storedUser.setAge(25);
        Storage.people.add(storedUser);
        User user = new User();
        user.setLogin("existingUser");
        user.setPassword("validPassword");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_ValidUser_Ok() {
        User user = new User();
        user.setLogin("notExistingUser");
        user.setPassword("validPassword");
        user.setAge(25);
        User actual = service.register(user);
        assertNotNull(user);
        assertEquals(user, actual);
    }

    @Test
    void register_NullLogin_NotOk() {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_LoginIsLessThan6Characters_NotOk() {
        User user = new User();
        user.setLogin("abcd");
        user.setPassword("validPassword");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_LoginIsAtLeast6Characters_Ok() {
        User user = new User();
        user.setLogin("sixLet");
        user.setPassword("ValidPassword");
        user.setAge(25);
        User actual = service.register(user);
        assertNotNull(user);
        assertEquals(user, actual);
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
    void register_PasswordIsLessThan6Characters_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("abc");
        user.setAge(25);
        assertNotNull(user);
        assertThrows(RegistrationException.class, () -> service.register(user));

    }

    @Test
    void register_PasswordIsAtLeast6Characters_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("SixLet");
        user.setAge(25);
        assertNotNull(user);
        assertEquals(service.register(user), user);

    }

    @Test
    void register_AgeLessThan18_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(15);
        assertNotNull(user);
        assertThrows(RegistrationException.class, () -> service.register(user));

    }

    @Test
    void register_NegativeAge_NotOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(-18);
        assertNotNull(user);
        assertThrows(RegistrationException.class, () -> service.register(user));
    }
}
