package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.Before;
import org.junit.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @Before
    public void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPass");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        assertNotNull("Registered user's id should not be null", registeredUser.getId());
        assertEquals("Login should match", user.getLogin(), registeredUser.getLogin());
    }

    @Test(expected = RegistrationException.class)
    public void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("duplicate");
        user1.setPassword("password");
        user1.setAge(25);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("duplicate");
        user2.setPassword("password123");
        user2.setAge(30);
        registrationService.register(user2);
    }

    @Test(expected = RegistrationException.class)
    public void register_loginTooShort_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPass");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void register_passwordTooShort_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("short");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void register_ageUnder18_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPass");
        user.setAge(17);
        registrationService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("validPass");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword(null);
        user.setAge(20);
        registrationService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("validPass");
        user.setAge(null);
        registrationService.register(user);
    }

    @Test(expected = RegistrationException.class)
    public void register_nullUser_notOk() {
        registrationService.register(null);
    }
}
