package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private StorageDao storage;
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storage = new StorageDaoImpl();
        service = new RegistrationServiceImpl(storage);
    }

    @Test
    void register_validUser_ok() {
        User user = new User(1L, "login123", "password", 20);
        User result = service.register(user);
        assertEquals(user, result);
        assertEquals(user,storage.get("login123"));

    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(1L, null, "password", 20);
        assertThrows(RegistrationException.class,
                () -> service.register(user));

    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User(1L, "abacds", null, 20);
        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User(1L, "abcdeg", "123456", null);
        assertThrows(RegistrationException.class,
                () -> service.register(user));
    }

    @Test
    void register_shortlogin_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "", "password", 20)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "a", "password", 20)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "abc", "password", 20)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "abcde", "password", 20)));

    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "login123", "", 20)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "login123", "a", 20)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "login123", "abc", 20)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "login123", "abcde", 20)));

    }

    @Test
    void register_underageUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(
                        new User(1L, "login123", "password", 17)));

        assertThrows(RegistrationException.class,
                () -> service.register(
                        new User(1L, "login123", "password", 10)));

        assertThrows(RegistrationException.class,
                () -> service.register(
                        new User(1L, "login123", "password", 0)));
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "login123", "password", -1)));
        assertThrows(RegistrationException.class,
                () -> service.register(new User(1L, "login123", "password", -100)));

    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = new User(1L, "login123", "password", 20);
        storage.add(existingUser);
        User duplicateUser = new User(2L, "login123", "anotherpassword", 25);
        assertThrows(RegistrationException.class, () -> service.register(duplicateUser));
    }

    @Test
    void register_minLogin_Length_Ok() {
        User user = new User(1L, "abcdef", "password", 20);
        User result = service.register(user);
        assertEquals(user, result);
        assertNotNull(storage.get("abcdef"));

    }

    @Test
    void register_longLogin_Ok() {
        User user = new User(1L, "abcdefgh", "password", 20);
        User result = service.register(user);
        assertEquals(user, result);
        assertNotNull(storage.get("abcdefgh"));
    }

    @Test
    void register_minPasswordLength_ok() {
        User user = new User(1L, "login123", "abcdef", 20);
        User result = service.register(user);
        assertEquals(user, result);
        assertNotNull(storage.get("login123"));
    }

    @Test
    void register_longPassword_Ok() {
        User user = new User(1L, "login123", "abcdefgh", 20);
        User result = service.register(user);
        assertEquals(user,result);
        assertNotNull(storage.get("login123"));

    }

    @Test
    void register_minAge_ok() {
        User user = new User(1L, "login123", "password", 18);
        User result = service.register(user);
        assertEquals(user, result);
        assertNotNull(storage.get("login123"));
    }
}
