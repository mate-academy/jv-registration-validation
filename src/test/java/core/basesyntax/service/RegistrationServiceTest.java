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

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.PEOPLE.clear();
    }

    @Test
    public void register_ValidUserWithBorderValues_SuccessfullyRegistered() {
        User user = createUser("Lawson", "f1newb", 18);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    public void register_ValidUserWithEquivalentClass_SuccessfullyRegistered() {
        User user = createUser("MaxVerstappen", "RedBullRacerNumber1", 25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
        Assertions.assertEquals(user.getPassword(), registeredUser.getPassword());
        Assertions.assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    public void register_MultipleUsers_SuccessfullyRegistered() {
        User user1 = createUser("first user", "password", 18);
        User user2 = createUser("second user", "password", 30);
        
        User registeredUser1 = registrationService.register(user1);
        User registeredUser2 = registrationService.register(user2);
        
        assertNotNull(registeredUser1.getId());
        assertNotNull(registeredUser2.getId());
        Assertions.assertNotEquals(registeredUser1.getLogin(), registeredUser2.getLogin());
    }

    @Test
    public void register_UserAlreadyExists_ThrowsException() {
        User existingUser = createUser("existingUser", "validPassword", 30);
        storageDao.add(existingUser);

        User newUser = createUser("existingUser", "newPassword", 40);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(newUser)
        );
    }

    @Test
    public void register_UserWithoutLogin_ThrowsException() {
        User user = createUser(null, "p@ssw0rd", 18);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_UserWithShortLogin_ThrowsException() {
        User user = createUser("Bob", "p@ssw0rd", 18);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_UserWithoutPassword_ThrowsException() {
        User user = createUser("Valid User", null, 18);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_UserWithShortPassword_ThrowsException() {
        User user = createUser("George", "123", 18);

        Assertions.assertThrows(
                InvalidUserException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    public void register_TooYoungUser_ThrowsException() {
        User user = createUser("Charles", "p@ssw0rd", 17);

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
