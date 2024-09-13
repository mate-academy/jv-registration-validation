package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;

    // Константы для повторно используемых значений
    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPass";
    private static final String EXISTING_LOGIN = "existingLogin";
    private static final String PASSWORD1 = "password1";
    private static final String PASSWORD2 = "password2";
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
        user.setPassword(PASSWORD1);
        user.setAge(20);
        Storage.people.add(user);

        User sameUser = new User();
        sameUser.setLogin(EXISTING_LOGIN);
        sameUser.setPassword(PASSWORD2);
        sameUser.setAge(25);

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
                registrationService.register(sameUser)
        );
        assertEquals("User with this login already exists", thrown.getMessage());
    }

    @Test
    public void register_nullUser_throwsException() {
        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
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

        InvalidUserException thrown = assertThrows(InvalidUserException.class, () ->
                registrationService.register(user)
        );
        assertEquals("User must be at least 18 years old", thrown.getMessage());
    }
}
