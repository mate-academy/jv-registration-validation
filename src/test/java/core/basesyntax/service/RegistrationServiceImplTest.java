package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.*;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        // Create a user with unique login for the test
        User user = new User();
        user.setLogin("john_doe_" + System.currentTimeMillis());
        user.setPassword("strong_password");
        user.setAge(25);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
        User retrievedUser = storageDao.get(user.getLogin());
        assertNotNull(retrievedUser);
        assertEquals(registeredUser.getId(), retrievedUser.getId());
        assertEquals(registeredUser.getLogin(), retrievedUser.getLogin());
        assertEquals(registeredUser.getPassword(), retrievedUser.getPassword());
        assertEquals(registeredUser.getAge(), retrievedUser.getAge());
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("strong_password");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("strong_password");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("john_doe");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("john_doe");
        user.setPassword("abc");
        user.setAge(25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("john_doe");
        user.setPassword("strong_password");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("john_doe");
        user.setPassword("strong_password");
        user.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("john_doe");
        user1.setPassword("strong_password");
        user1.setAge(25);
        registrationService.register(user1);
        User user2 = new User();
        user2.setLogin("john_doe");
        user2.setPassword("another_password");
        user2.setAge(30);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }
}
