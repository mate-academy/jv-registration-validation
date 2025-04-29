package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_loginLessThan6Chars_notOk() {
        User user = new User();
        user.setLogin("abc");
        user.setPassword("validPass123");
        user.setAge(25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        User existingUser = new User();
        existingUser.setLogin("existingUser");
        existingUser.setPassword("password1");
        existingUser.setAge(30);
        registrationService.register(existingUser); // додаємо першого користувача

        User newUser = new User();
        newUser.setLogin("existingUser"); // той же логін
        newUser.setPassword("password2");
        newUser.setAge(22);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser); // має кинути виключення
        });
    }

    @Test
    void register_passwordLessThan6Chars_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("12345"); // пароль менше 6 символів
        user.setAge(25);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(17); // менше 18

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPass");
        user.setAge(25);

        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }
}
