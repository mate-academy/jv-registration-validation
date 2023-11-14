package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.InvalidUserDataException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User validUser;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void createValidUser() {
        validUser = new User();
        validUser.setLogin("existingUser");
        validUser.setPassword("password123");
        validUser.setAge(20);
    }

    private User createUserWithInvalidAge() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("password123");
        user.setAge(17);
        return user;
    }

    private User createUserWithShortPassword() {
        User user = new User();
        user.setLogin("newUser");
        user.setPassword("passs");
        user.setAge(20);
        return user;
    }

    private User createUserWithShortLogin() {
        User user = new User();
        user.setLogin("usrrr");
        user.setPassword("password123");
        user.setAge(20);
        return user;
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_ok() {
        User result = registrationService.register(validUser);

        assertNotNull(result);
        assertEquals(validUser.getLogin(), result.getLogin());
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_invalidAge_notOk() {
        User userWithInvalidAge = createUserWithInvalidAge();

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userWithInvalidAge);
        });
    }

    @Test
    public void register_shortPassword_notOk() {
        User userWithShortPassword = createUserWithShortPassword();

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userWithShortPassword);
        });
    }

    @Test
    public void register_shortLogin_notOk() {
        User userWithShortLogin = createUserWithShortLogin();

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(userWithShortLogin);
        });
    }

    @Test
    public void register_userExists_notOk() {
        Storage.people.add(validUser);

        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(validUser);
        });
    }
}
