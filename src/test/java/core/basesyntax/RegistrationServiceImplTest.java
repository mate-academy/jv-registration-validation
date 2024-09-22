package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        User validUser = new User();
        validUser.setLogin("validUser");
        validUser.setPassword("qwertyQ123");
        validUser.setAge(20);

        User registeredUser = registrationService.register(validUser);
        assertNotNull(registeredUser, "User should be registered");
        assertEquals(validUser.getLogin(), registeredUser.getLogin());
    }

    @Test
    public void register_nullUser_notOk() {
        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User cannot be null", thrown.getMessage());
    }

    @Test
    public void register_loginTooShort_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("qwertyQ123");
        user.setAge(20);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters", thrown.getMessage());
    }

    @Test
    public void register_passwordTooShort_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("12345");
        user.setAge(20);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", thrown.getMessage());
    }

    @Test
    public void register_ageBelow18_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("qwertyQ123");
        user.setAge(17);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old", thrown.getMessage());
    }

    @Test
    public void register_duplicateLogin_notOk() {
        User firstUser = new User();
        firstUser.setLogin("validUser");
        firstUser.setPassword("qwertyQ123");
        firstUser.setAge(20);
        registrationService.register(firstUser);

        User secondUser = new User();
        secondUser.setLogin("validUser");
        secondUser.setPassword("password456");
        secondUser.setAge(22);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(secondUser));
        assertEquals("User with this login already exists", thrown.getMessage());
    }

    @Test
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("qwertyQ123");
        user.setAge(20);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters", thrown.getMessage());
    }

    @Test
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword(null);
        user.setAge(20);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", thrown.getMessage());
    }

    @Test
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("qwertyQ123");
        user.setAge(null);

        RegistrationException thrown = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old", thrown.getMessage());
    }
}
