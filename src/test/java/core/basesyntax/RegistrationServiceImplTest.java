package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;

    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_throwsException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_throwsException() {
        User user = new User();
        user.setLogin("");
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_throwsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword(null);
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_throwsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLessThanMinLength_throwsException() {
        User user = new User();
        user.setLogin("Bob");
        user.setPassword("qwerty1234");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLessThanMinLength_throwsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("qwert");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_throwsException() {
        User user = new User();
        user.setLogin("Alice");
        user.setPassword("qwerty1234");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setPassword("qwerty123");
        user.setLogin("Alex1976");
        user.setAge(27);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
        User storedUser = storageDao.get(user.getLogin());
        assertEquals(user, storedUser);
    }

    @Test
    void register_invalidAge_throwsException() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("password");
        user.setAge(-1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_exactMinAge_ok() {
        User user = new User();
        user.setLogin("Alice123");
        user.setPassword("qwerty1234");
        user.setAge(MIN_AGE);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
        User storedUser = storageDao.get(user.getLogin());
        assertEquals(user, storedUser);
    }

    @Test
    void register_loginAtMinLength_ok() {
        User user = new User();
        user.setLogin("Bob125");
        user.setPassword("qwerty1234");
        user.setAge(25);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_passwordAtMinLength_ok() {
        User user = new User();
        user.setLogin("Bob1256");
        user.setPassword("qwerty");
        user.setAge(25);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_userAlreadyExists_throwsException() {
        User user = new User();
        user.setLogin("ExistingUser");
        user.setPassword("password123");
        user.setAge(25);

        registrationService.register(user);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
