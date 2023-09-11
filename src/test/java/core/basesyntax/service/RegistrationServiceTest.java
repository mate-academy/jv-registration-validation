package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import core.basesyntax.InvalidUserException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
        user = createUser("Sample", "123456", 18);
    }

    @AfterEach
    void clearStorage() {
        Storage.PEOPLE.clear();
    }

    @Test
    public void register_validUser_successfullyRegistered() {
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    public void register_multipleUsers_successfullyRegistered() {
        User user1 = createUser("first user", "password", 18);
        User user2 = createUser("second user", "password", 30);
        
        User registeredUser1 = registrationService.register(user1);
        User registeredUser2 = registrationService.register(user2);
        
        assertNotNull(registeredUser1.getId());
        assertNotNull(registeredUser2.getId());
        Assertions.assertNotEquals(registeredUser1.getLogin(), registeredUser2.getLogin());
    }

    @Test
    public void register_userAlreadyExists_throwsException() {
        User existingUser = user;
        storageDao.add(existingUser);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(existingUser)
        );
    }

    @Test
    public void register_userWithoutLogin_throwsException() {
        user.setLogin(null);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_userWithShortLogin_throwsException() {
        user.setLogin("Lewis");

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_userWithoutPassword_throwsException() {
        user.setPassword(null);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_userWithShortPassword_throwsException() {
        user.setPassword("12345");

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_userWithoutAge_throwsException() {
        user.setAge(null);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_tooYoungUser_throwsException() {
        user.setAge(17);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
