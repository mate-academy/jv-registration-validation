package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUser_Success() {
        User user = new User("validuser", "password123", 18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    public void register_NullLogin_ExceptionThrown() {
        User user = new User(null, "password123", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortLogin_ExceptionThrown() {
        User user = new User("short", "password123", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullPassword_ExceptionThrown() {
        User user = new User("validuser", null, 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword_ExceptionThrown() {
        User user = new User("validuser", "123", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UnderageUser_ExceptionThrown() {
        User user = new User("validuser", "password123", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ExistingUser_ExceptionThrown() {
        User user1 = new User("user1", "password123", 18);
        storageDao.add(user1);

        User user2 = new User("user1", "anotherPassword", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    public void register_AllValidButBorderlineLengthLogin_Success() {
        User user = new User("123456", "password123", 18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    public void register_AllValidButBorderlineLengthPassword_ExceptionThrown() {
        User user = new User("validuser", "12345", 18);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
