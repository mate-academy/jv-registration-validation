package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void cleanUp() {
        Storage.PEOPLE.clear();
    }

    @Test
    public void register_validUser_successfulRegistration() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password123");
        user.setAge(20);

        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    public void register_existingUser_throwException() {
        User user1 = new User();
        user1.setLogin("username");
        user1.setPassword("password123");
        user1.setAge(20);

        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("username");
        user2.setPassword("anotherpassword");
        user2.setAge(25);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user2));

        String expectedMessage = "User with this login already exists";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void register_shortLogin_throwException() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password123");
        user.setAge(20);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

        String expectedMessage = "Login must be at least 6 characters long";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void register_shortPassword_throwException() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("short");
        user.setAge(20);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

        String expectedMessage = "Password must be at least 6 characters long";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void register_lowAge_throwException() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password123");
        user.setAge(17);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

        String expectedMessage = "Not valid age: 17. Min allowed age is 18";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void register_nullLogin_throwException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("password123");
        user.setAge(18);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

        String expectedMessage = "Login can't be null";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void register_nullPassword_throwException() {
        User user = new User();
        user.setLogin("I am twinkerbell");
        user.setPassword(null);
        user.setAge(18);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

        String expectedMessage = "Password can't be null";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void register_nullAge_throwException() {
        User user = new User();
        user.setLogin("May go away from tests?");
        user.setPassword("password123");
        user.setAge(null);

        Exception exception = assertThrows(InvalidDataException.class,
                () -> registrationService.register(user));

        String expectedMessage = "Age can't be null";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
