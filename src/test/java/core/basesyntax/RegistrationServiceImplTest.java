package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_validUser_successfulRegistration() {
        User user = new User();
        user.setLogin("yaroslav");
        user.setPassword("password123");
        user.setAge(22);
        assertDoesNotThrow(() -> registrationService.register(user));
        assertNotNull(storageDao.get("yaroslav"));
    }

    @Test
    void register_existingLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin("existing_user");
        existingUser.setPassword("password123");
        existingUser.setAge(30);
        storageDao.add(existingUser);
        User userWithExistingLogin = new User();
        userWithExistingLogin.setLogin("existing_user");
        userWithExistingLogin.setPassword("newpassword");
        userWithExistingLogin.setAge(22);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(userWithExistingLogin));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password123");
        user.setAge(22);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("yaroslav");
        user.setPassword("short");
        user.setAge(22);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_youngUser_notOk() {
        User user = new User();
        user.setLogin("yaroslav");
        user.setPassword("password123");
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("yaroslav");
        user.setPassword("password123");
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_storageDaoReturnsNullForGet_notOk() {
        User user = new User();
        user.setLogin("nonexistent_user");
        user.setPassword("password123");
        user.setAge(25);
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
