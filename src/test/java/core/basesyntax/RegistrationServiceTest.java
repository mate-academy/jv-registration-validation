package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException; // Обновленное название исключения
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;

    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPass";
    private static final String EXISTING_LOGIN = "existingLogin";
    private static final String SHORT_LOGIN = "short";

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    public void register_validUser_userRegistered() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(registeredUser, user);
        assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    public void register_existingLogin_userNotRegistered() {
        User user = new User();
        user.setLogin(EXISTING_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(20);
        Storage.people.add(user);

        User sameUser = new User();
        sameUser.setLogin(EXISTING_LOGIN);
        sameUser.setPassword(VALID_PASSWORD);
        sameUser.setAge(25);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(sameUser)
        );
        assertEquals("User with this login already exists", thrown.getMessage());
    }

    @Test
    public void register_nullUser_throwsException() {
        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(null)
        );
        assertEquals("User cannot be null", thrown.getMessage());
    }

    @Test
    public void register_shortLogin_throwsException() {
        User user = new User();
        user.setLogin(SHORT_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(20);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("Login must be at least 6 characters long", thrown.getMessage());
    }

    @Test
    public void register_shortPassword_throwsException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(SHORT_LOGIN);
        user.setAge(20);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("Password must be at least 6 characters long", thrown.getMessage());
    }

    @Test
    public void register_nullLogin_throwsException() {
        User user = new User();
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(20);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("Login cannot be null", thrown.getMessage());
    }

    @Test
    public void register_nullPassword_throwsException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(20);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("Password cannot be null", thrown.getMessage());
    }

    @Test
    public void register_nullAge_throwsException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(null);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("Age cannot be null", thrown.getMessage());
    }

    @Test
    public void register_negativeAge_throwsException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(-5);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("Age cannot be negative", thrown.getMessage());
    }

    @Test
    public void register_underageUser_throwsException() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(17);

        UserRegistrationException thrown = assertThrows(UserRegistrationException.class, () ->
                registrationService.register(user)
        );
        assertEquals("User must be at least 18 years old", thrown.getMessage());
    }
}
