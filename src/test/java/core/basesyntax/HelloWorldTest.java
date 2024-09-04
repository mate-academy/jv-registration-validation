package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HelloWorldTest {

    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        // Clear storage before each test
        core.basesyntax.db.Storage.people.clear();
    }

    @Test
    public void register_validUser_userRegistered() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    public void register_existingLogin_userNotRegistered() {
        User user1 = new User();
        user1.setLogin("existingLogin");
        user1.setPassword("password1");
        user1.setAge(20);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("existingLogin");
        user2.setPassword("password2");
        user2.setAge(25);

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user2);
        });
        assertEquals("User with this login already exists", thrown.getMessage());
    }

    @Test
    public void register_nullUser_throwsException() {
        InvalidUserException thrown = assertThrows(InvalidUserException.class, () -> {
            registrationService.register(null);
        });
        assertEquals("User cannot be null", thrown.getMessage());
    }

    @Test
    public void register_shortLogin_throwsException() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPass");
        user.setAge(20);

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Login must be at least 6 characters long", thrown.getMessage());
    }

    @Test
    public void register_shortPassword_throwsException() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(20);

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("Password must be at least 6 characters long", thrown.getMessage());
    }

    @Test
    public void register_underageUser_throwsException() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(17);

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });
        assertEquals("User must be at least 18 years old", thrown.getMessage());
    }
}
