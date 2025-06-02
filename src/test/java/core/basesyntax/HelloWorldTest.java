package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HelloWorldTest {
    private RegistrationService registrationService;

    @Before
    public void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test(expected = RuntimeException.class)
    public void register_nullUser_notOk() {
        registrationService.register(null);
    }

    @Test(expected = RuntimeException.class)
    public void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("validPass");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test(expected = RuntimeException.class)
    public void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(20);
        registrationService.register(user);
    }

    @Test(expected = RuntimeException.class)
    public void register_underAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(17);
        registrationService.register(user);
    }

    @Test(expected = RuntimeException.class)
    public void register_existingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("existingLogin");
        user1.setPassword("password1");
        user1.setAge(25);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("existingLogin");
        user2.setPassword("password2");
        user2.setAge(30);
        registrationService.register(user2);
    }

    @Test
    public void register_validUser_ok() {
        User user = new User();
        user.setLogin("uniqueLogin");
        user.setPassword("securePass");
        user.setAge(21);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }
}

