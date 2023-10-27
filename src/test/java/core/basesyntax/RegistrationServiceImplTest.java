package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String INVALID_DATA = "Invalid user data."
            + " Please check login, password, and age.";
    private static final String EXISTS_MSG = "User with this login already exists.";
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUserRegistration_NotNullAndEquals() {
        User user = new User();
        user.setLogin("testuser1");
        user.setPassword("password1231");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        Assertions.assertNotNull(registeredUser);
        Assertions.assertNotNull(registeredUser.getId());
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    public void register_InvalidUserRegistration_ExceptionThrown() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password123");
        user.setAge(20);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }

    @Test
    public void register_InvalidUserPassLen_ExceptionThrown() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("short");
        user.setAge(20);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }

    @Test
    public void register_InvalidUserAge_ExceptionThrown() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password123");
        user.setAge(17);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }

    @Test
    public void register_DuplicateUser_ExceptionThrown() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password123");
        user.setAge(20);

        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(EXISTS_MSG, exception.getMessage());
    }

    @Test
    public void register_NullUser_ExceptionThrown() {
        User user = null;

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }

    @Test
    public void register_NullLogin_ExceptionThrown() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(20);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }

    @Test
    public void register_NullPassword_ExceptionThrown() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword(null);
        user.setAge(20);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }

    @Test
    public void register_NullAge_ExceptionThrown() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password123");
        user.setAge(null);

        InvalidUserException exception = Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertEquals(INVALID_DATA, exception.getMessage());
    }
}
