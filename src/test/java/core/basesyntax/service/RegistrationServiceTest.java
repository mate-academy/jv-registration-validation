package core.basesyntax.service;

import core.basesyntax.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_successfullyRegistered() {
        User user = createUser("johnsmith", "password123", 25);

        User registeredUser = registrationService.register(user);

        Assertions.assertNotNull(registeredUser.getId());
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    public void register_userWithShortLogin_throwsInvalidUserException() {
        User user = createUser("abc", "password123", 25);

        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_userWithShortPassword_throwsInvalidUserException() {
        User user = createUser("johnsmith", "abc", 25);

        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_throwsInvalidUserException() {
        User user = createUser("johnsmith", "password123", 15);

        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_existingUserLogin_throwsInvalidUserException() {
        User existingUser = createUser("existinguser", "password123", 25);
        storageDao.add(existingUser);

        User user = createUser("existinguser", "password456", 30);

        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}