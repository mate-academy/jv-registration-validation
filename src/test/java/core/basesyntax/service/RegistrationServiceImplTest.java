package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.exception.RegistrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setLogin("validUser");
        user.setPassword("strongPass");
        user.setAge(20);

        User registeredUser =registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals("validUser", registeredUser.getLogin());
    }

    @Test
    void register_existingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("duplicateLogin");
        user1.setPassword("password123");
        user1.setAge(22);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("duplicateLogin"); // Такий самий логін
        user2.setPassword("password321");
        user2.setAge(25);

        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123"); // Менше 6 символів
        user.setAge(19);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("youngUser");
        user.setPassword("password123");
        user.setAge(17); // Менше 18 років

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
