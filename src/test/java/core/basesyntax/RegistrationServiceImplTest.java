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
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void testValidUserRegistration() {
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
    public void testInvalidUserRegistrationShortLogin() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password123");
        user.setAge(20);

        try {
            registrationService.register(user);
            Assertions.fail("Expected InvalidUserException");
        } catch (InvalidUserException e) {
            Assertions.assertEquals("Invalid user data. "
                    + "Please check login, password, and age.", e.getMessage());
        }
    }

    @Test
    public void testInvalidUserRegistrationShortPassword() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("short");
        user.setAge(20);

        try {
            registrationService.register(user);
            Assertions.fail("Expected InvalidUserException");
        } catch (InvalidUserException e) {
            Assertions.assertEquals("Invalid user data."
                    + " Please check login, password, and age.", e.getMessage());
        }
    }

    @Test
    public void testInvalidUserRegistrationUnderage() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password123");
        user.setAge(17);

        try {
            registrationService.register(user);
            Assertions.fail("Expected InvalidUserException");
        } catch (InvalidUserException e) {
            Assertions.assertEquals("Invalid user data."
                    + " Please check login, password, and age.", e.getMessage());
        }
    }

    @Test
    public void testInvalidUserRegistrationDuplicateUser() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("password123");
        user.setAge(20);

        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);

        try {
            registrationService.register(user);
            Assertions.fail("Expected InvalidUserException");
        } catch (InvalidUserException e) {
            Assertions.assertEquals("User with this login already exists.", e.getMessage());
        }
    }
}
