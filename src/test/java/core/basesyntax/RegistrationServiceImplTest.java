package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exeption.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear(); // очищення сховища перед кожним тестом
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("secure123");
        user.setAge(25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser.getId());
        assertEquals("validUser", registeredUser.getLogin());
        assertEquals("secure123", registeredUser.getPassword());
        assertEquals(25, registeredUser.getAge());
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("secure123");
        user.setAge(25);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(25);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("teenUser");
        user.setPassword("secure123");
        user.setAge(17);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("duplicate");
        user1.setPassword("password1");
        user1.setAge(30);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("duplicate");
        user2.setPassword("password2");
        user2.setAge(22);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("secure123");
        user.setAge(25);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(25);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("secure123");
        user.setAge(null);

        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(null));
    }
}
