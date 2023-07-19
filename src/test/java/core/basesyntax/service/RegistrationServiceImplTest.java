package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User user = new User();
        user.setLogin("login8843");
        user.setPassword("password9475");
        user.setAge(26);

        User registeredUser = registrationService.register(user);

        Assertions.assertNotNull(registeredUser);
        Assertions.assertEquals(user, registeredUser);
    }

    @Test
    public void register_userWithExistingLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin("existingUser");
        existingUser.setPassword("existingPassword");
        existingUser.setAge(33);

        storageDao.add(existingUser);

        RegistrationService registrationService = new RegistrationServiceImpl();

        User newUser = new User();
        newUser.setLogin("existingUser");
        newUser.setPassword("newPassword");
        newUser.setAge(32);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
        Assertions.assertEquals("User with this login already exists", exception.getMessage());
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password9475");
        user.setAge(32);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        Assertions.assertEquals("Login can't be null", exception.getMessage());
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("usd");
        user.setPassword("password9475");
        user.setAge(25);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        Assertions.assertEquals("Login should be at least 6 characters", exception.getMessage());
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("login8843");
        user.setPassword(null);
        user.setAge(23);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        Assertions.assertEquals("Password can't be null", exception.getMessage());
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("login8843");
        user.setPassword("usd");
        user.setAge(23);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        Assertions.assertEquals("Password should be at least 6 characters", exception.getMessage());
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("login8843");
        user.setPassword("password9475");
        user.setAge(null);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        Assertions.assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    @Test
    public void register_underAge_notOk() {
        User user = new User();
        user.setLogin("login8843");
        user.setPassword("password9475");
        user.setAge(13);

        RegistrationException exception = Assertions.assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
        Assertions.assertEquals("User must be at least 18 years old", exception.getMessage());
    }

    private static class TestStorageDao implements StorageDao {
        private final Map<String, User> storage = new HashMap<>();

        @Override
        public User add(User user) {
            return storage.put(user.getLogin(), user);
        }

        @Override
        public User get(String login) {
            return storage.get(login);
        }
    }
}
