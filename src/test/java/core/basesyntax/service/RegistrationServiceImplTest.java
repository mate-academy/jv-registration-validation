package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @DisplayName("register() with valid user should work")
    @Test
    void register_validUser_ok() {
        user.setLogin("login2334");
        user.setPassword("1234567");
        user.setAge(18);
        User registered = registrationService.register(user);
        assertNotNull(registered);
    }

    @DisplayName("register() with null user should throw exception")
    @Test
    void register_nullUser_notOk() {
        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
        assertEquals("User cannot be null", ex.getMessage());
    }

    @DisplayName("register() with valid login should work")
    @Test
    void register_validLogin_ok() {
        String login = "Oleksandr123";
        user.setLogin(login);
        user.setPassword("1234567");
        user.setAge(18);
        User registered = registrationService.register(user);
        assertNotNull(registered);
        assertEquals(login, registered.getLogin());
    }

    @DisplayName(" with short login should throw exception")
    @Test
    void register_shortLogin_notOk() {
        user.setLogin("Ole");
        user.setPassword("1234567");
        user.setAge(18);
        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Login must be at least 6 characters", ex.getMessage());
    }

    @DisplayName("register() with unique login should work")
    @Test
    void register_userWithUniqueLogin_ok() {
        User user1 = new User();
        user1.setLogin("login2334");
        user1.setPassword("1234567");
        user1.setAge(18);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("login23345");
        user2.setPassword("12345671");
        user2.setAge(18);
        User registeredUser2 = registrationService.register(user2);
        assertNotNull(registeredUser2);
        assertEquals("login23345", registeredUser2.getLogin());
    }

    @DisplayName("register() with duplicate login should throw exception")
    @Test
    void register_userWithExistingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("login2334");
        user1.setPassword("1234567");
        user1.setAge(18);
        registrationService.register(user1);

        User user2 = new User();
        user2.setLogin("login2334");
        user2.setPassword("12345671");
        user2.setAge(18);

        RegistrationException registrationException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
        assertEquals("User with this login already exists", registrationException.getMessage());
    }

    @DisplayName("register() with valid password should work")
    @Test
    void register_validPassword_ok() {
        User user = new User();
        user.setLogin("Oleksandr123");
        user.setPassword("1234567");
        user.setAge(21);

        User registered = registrationService.register(user);
        assertNotNull(registered);
        assertEquals("1234567", registered.getPassword());
    }

    @DisplayName("register() with short password should throw exception")
    @Test
    void register_invalidPassword_notOk() {
        User user = new User();
        user.setLogin("Oleksandr123");
        user.setPassword("123");
        user.setAge(21);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", ex.getMessage());
    }

    @DisplayName("register() with valid age should work")
    @Test
    void register_validAge_ok() {
        User user = new User();
        user.setLogin("Oleksabdr123");
        user.setPassword("1234567");
        user.setAge(19);

        User registered = registrationService.register(user);
        assertEquals(19, registered.getAge());
    }

    @DisplayName("register() with age under 18 should throw exception")
    @Test
    void register_ageLessThan18_notOk() {
        User user = new User();
        user.setLogin("Oleksabdr123");
        user.setPassword("1234567");
        user.setAge(17);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("User must be at least 18 years old", ex.getMessage());
    }

    @DisplayName("register() with null password should throw exception")
    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword(null);
        user.setAge(18);

        RegistrationException ex = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Password must be at least 6 characters", ex.getMessage());
    }

}
