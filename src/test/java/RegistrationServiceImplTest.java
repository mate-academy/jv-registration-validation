import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationServiceImpl.RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPassword");
        user.setAge(20);

        assertThrows(RegistrationServiceImpl.RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("short");
        user.setAge(20);

        assertThrows(RegistrationServiceImpl.RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);

        assertThrows(RegistrationServiceImpl.RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user = new User();
        user.setLogin("duplicateLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        registrationService.register(user);

        User duplicateUser = new User();
        duplicateUser.setLogin("duplicateLogin");
        duplicateUser.setPassword("anotherPassword");
        duplicateUser.setAge(25);

        assertThrows(RegistrationServiceImpl.RegistrationException.class,
                () -> registrationService.register(duplicateUser));
    }
}
