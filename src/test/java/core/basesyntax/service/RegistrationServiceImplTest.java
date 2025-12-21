package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private RegistrationService service = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User user = new User();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin("bobbyT");
        user.setPassword("123456");
        user.setAge(MIN_AGE);
    }

    @Test
    void register_validUser_Ok() {
        User actual = service.register(user);
        assertNotNull(actual);
        assertEquals("bobbyT", actual.getLogin());
        User stored = storageDao.get("bobbyT");
        assertNotNull(stored);
        assertEquals(MIN_AGE, stored.getAge());
    }

    @Test
    void register_existedLogin_notOk() {
        storageDao.add(user);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("User is already exist", exception.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Login can't be null", exception.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("bob");
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_loginExactly5_notOk() {
        user.setLogin("12345");
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Login must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Password can't be null", exception.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12345");
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Age can't be null", exception.getMessage());
    }

    @Test
    void register_ageUnder18_notOk() {
        user.setAge(17);
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Age must be at least 18", exception.getMessage());
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        RegistrationException exception = assertThrows(RegistrationException.class, () ->
                service.register(user));
        assertEquals("Password must be at least 6 characters", exception.getMessage());
    }
}
