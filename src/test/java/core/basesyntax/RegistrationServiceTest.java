package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {

    private static StorageDaoImpl storageDao;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User("validuser", "password123", 18);
    }

    @Test
    public void register_ValidUser_Success() {
        User expected = user;
        User actual = registrationService.register(expected);
        assertNotNull(actual);
        assertEquals(expected.getLogin(), actual.getLogin());
    }

    @Test
    public void register_NullLogin_ExceptionThrown() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortLogin_ExceptionThrown() {
        user.setLogin("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullPassword_ExceptionThrown() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword_ExceptionThrown() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UnderageUser_ExceptionThrown() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ExistingUser_ExceptionThrown() {
        User user1 = new User("user1", "password123", 18);
        storageDao.add(user1);

        User user2 = new User("user1", "anotherPassword", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }
}
