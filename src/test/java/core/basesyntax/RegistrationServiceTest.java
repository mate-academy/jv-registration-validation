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
    private static final String VALID_USER_NAME = "validUser";
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
        user = new User("userOne", "password123", 18);
    }

    @Test
    public void register_ValidUser_Success() {
        User actual = registrationService.register(user);
        assertNotNull(actual);
        assertEquals(user.getLogin(), actual.getLogin());
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
    public void register_AgeNull_ExceptionThrown() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_PasswordLessThanSix_ExceptionThrown() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UnderageUser_ExceptionThrown() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ExistingUser_ExceptionThrown() {
        User user1 = new User(VALID_USER_NAME, "password123", 18);
        storageDao.add(user1);
        User duplicateUser = new User(VALID_USER_NAME, "anotherPassword", 19);
        assertThrows(RegistrationException.class, () -> registrationService
                .register(duplicateUser));
    }
}
