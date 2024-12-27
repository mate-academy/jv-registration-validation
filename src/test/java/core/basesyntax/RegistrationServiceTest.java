package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private StorageDao storageDao;
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_success() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("securePassword");
        user.setAge(25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_existingLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin("existingUser");
        existingUser.setPassword("password123");
        existingUser.setAge(30);
        storageDao.add(existingUser);

        User newUser = new User();
        newUser.setLogin("existingUser");
        newUser.setPassword("newPassword123");
        newUser.setAge(25);

        assertThrows(InvalidUserException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("short");
        user.setAge(25);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underAgeUser_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(17);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("password123");
        user.setAge(null);

        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserException.class, () -> registrationService.register(null));
    }
}
